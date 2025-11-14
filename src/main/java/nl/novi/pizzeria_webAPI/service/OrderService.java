package nl.novi.pizzeria_webAPI.service;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import nl.novi.pizzeria_webAPI.dto.OrderInputDto;
import nl.novi.pizzeria_webAPI.dto.OrderOutputDto;
import nl.novi.pizzeria_webAPI.exception.ResourceNotFoundException;
import nl.novi.pizzeria_webAPI.mapper.OrderMapper;
import nl.novi.pizzeria_webAPI.model.Item;
import nl.novi.pizzeria_webAPI.model.Order;
import nl.novi.pizzeria_webAPI.model.OrderDetail;
import nl.novi.pizzeria_webAPI.repository.ItemRepository;
import nl.novi.pizzeria_webAPI.repository.OrderDetailRepository;
import nl.novi.pizzeria_webAPI.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public OrderService(OrderRepository orderRepos, ItemRepository itemRepos) {
        this.orderRepository = orderRepos;
        this.itemRepository = itemRepos;
    }

    public OrderOutputDto createOrder(OrderInputDto orderInputDto) {
        Order order = OrderMapper.toEntityByCreate(orderInputDto);
        orderRepository.save(order);

        return OrderMapper.toDto(order);
    }

    public List<OrderOutputDto> getAllOrders() {
        List<Order> orders = this.orderRepository.findAll();

        return orders
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    public OrderOutputDto getOrderById(Long id) {
        return OrderMapper.toDto(
                this.orderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Order not found")));
    }

    @Transactional
    public OrderOutputDto updateOrderAddItem(long id, int newItemId, int quantity) {

        //haal de bestaande order op
        Order existingOrder = orderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        //haal de nieuwe item op en daarmee ook de itemPrice
        Item newItemToAdd = itemRepository.findById(newItemId).orElseThrow(()-> new ResourceNotFoundException("Item not found"));
        //maak een nieuwe OrderDetail aan
        OrderDetail newOrderDetail = new OrderDetail();
        newOrderDetail.setItem(newItemToAdd);
        newOrderDetail.setItemPrice(newItemToAdd.getPrice());
        newOrderDetail.setItemQuantity(quantity);

        //maakt link tussen de nieuwe OrderDetail en de bestaande order via foreign key orderId
        newOrderDetail.setOrder(existingOrder);

        //de nieuwe orderDetail toevoegen aan de ArrayList orderDetails
        Set<OrderDetail> orderDetails = existingOrder.getOrderDetails();
        if(orderDetails == null) {
            orderDetails = new HashSet<>();
        }
        orderDetails.add(newOrderDetail);
        existingOrder.setOrderDetails(orderDetails);

        //de totale bedrag van de order aanpassen
        updateOrderTotalAmount(existingOrder);
        //opslaan van de order, hierdoor wordt door cascade de nieuwe orderDetail ook opgeslagen
        Order updatedOrder = orderRepository.save(existingOrder);
        return OrderMapper.toDto(updatedOrder);
    }

    //helper functie voor berekening totale bedrag van de order
    private void updateOrderTotalAmount(Order order){
        double totalAmount = 0.0;
        if(order.getOrderDetails() != null){
            for(OrderDetail orderDetail : order.getOrderDetails()){
                totalAmount += orderDetail.getAmount();
            }
        }
        order.setOrderAmount(totalAmount);
    }
}
