package nl.novi.pizzeria_webAPI.service;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import nl.novi.pizzeria_webAPI.dto.DetailInputDto;
import nl.novi.pizzeria_webAPI.dto.OrderInputDto;
import nl.novi.pizzeria_webAPI.dto.OrderOutputDto;
import nl.novi.pizzeria_webAPI.exception.ResourceNotFoundException;
import nl.novi.pizzeria_webAPI.mapper.OrderMapper;
import nl.novi.pizzeria_webAPI.model.Item;
import nl.novi.pizzeria_webAPI.model.Order;
import nl.novi.pizzeria_webAPI.model.OrderDetail;
import nl.novi.pizzeria_webAPI.repository.ItemRepository;
import nl.novi.pizzeria_webAPI.repository.OrderRepository;
import org.springframework.stereotype.Service;

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
    public OrderOutputDto createOrder(OrderInputDto orderInputDto) {
        Order order = OrderMapper.toEntity(orderInputDto);

        Set<OrderDetail> orderDetails = new HashSet<>();
        double calculatedTotalAmount = 0.0;

        if(orderInputDto.detailInputDtos !=null){
            for (DetailInputDto detailInputDto : orderInputDto.detailInputDtos)
            {
                Item item = itemRepository
                        .findById(detailInputDto.itemId)
                        .orElseThrow(()->new ResourceNotFoundException("Item not found with id: " + detailInputDto.itemId));

                //aanmaken en vullen de nieuwe orderDetail
                OrderDetail newOrderDetail = new OrderDetail();
                newOrderDetail.setItem(item);
                newOrderDetail.setItemQuantity(detailInputDto.itemQuantity);
                newOrderDetail.setItemPrice(item.getPrice());

                //aanmaken bi-directionele relatie
                newOrderDetail.setOrder(order);
                orderDetails.add(newOrderDetail);
                calculatedTotalAmount += newOrderDetail.getAmount();
            }
        }

        order.setOrderDetails(orderDetails);
        order.setOrderAmount(calculatedTotalAmount);
        //opslaan order als parent (cascade save voor OrderDetails met juiste foreign key)
        orderRepository.save(order);

        return OrderMapper.toDto(order);
    }

    @Transactional
    public OrderOutputDto updateOrderAddItem(long id, int newItemId, int quantity) {

        Order existingOrder = orderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        Item newItem = itemRepository.findById(newItemId).orElseThrow(()-> new ResourceNotFoundException("Item not found"));

        //maak een nieuwe OrderDetail aan
        OrderDetail newOrderDetail = new OrderDetail();
        newOrderDetail.setItem(newItem);
        newOrderDetail.setItemPrice(newItem.getPrice());
        newOrderDetail.setItemQuantity(quantity);

        //maakt link tussen de nieuwe OrderDetail en de bestaande order via foreign key orderId
        newOrderDetail.setOrder(existingOrder);

        //de nieuwe orderDetail toevoegen aan de ArrayList orderDetails
        Set<OrderDetail> orderDetails = existingOrder.getOrderDetails();
        if(orderDetails == null) {
            orderDetails = new HashSet<>();
        }
        //voeg nieuwe orderDetail toe aan de hashset van orderDetails
        orderDetails.add(newOrderDetail);
        existingOrder.setOrderDetails(orderDetails);

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
