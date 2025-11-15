package nl.novi.pizzeria_webAPI.service;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import nl.novi.pizzeria_webAPI.dto.DetailInputDto;
import nl.novi.pizzeria_webAPI.dto.OrderInputDto;
import nl.novi.pizzeria_webAPI.dto.OrderOutputDto;
import nl.novi.pizzeria_webAPI.exception.ResourceNotFoundException;
import nl.novi.pizzeria_webAPI.mapper.OrderMapper;
import nl.novi.pizzeria_webAPI.model.*;
import nl.novi.pizzeria_webAPI.repository.ItemRepository;
import nl.novi.pizzeria_webAPI.repository.OrderDetailRepository;
import nl.novi.pizzeria_webAPI.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderDetailRepository detailRepository;

    public OrderService(OrderRepository orderRepos, ItemRepository itemRepos, OrderDetailRepository detailRepos) {
        this.orderRepository = orderRepos;
        this.itemRepository = itemRepos;
        this.detailRepository = detailRepos;
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
        //voeg nieuwe orderDetail toe aan de set van orderDetails
        orderDetails.add(newOrderDetail);
        //vervang de bestaande set orderDetails met de nieuwe set orderDetails
        existingOrder.setOrderDetails(orderDetails);

        updateOrderTotalAmount(existingOrder);
        //opslaan van de order, hierdoor wordt door cascade de nieuwe orderDetail ook opgeslagen
        Order updatedOrder = orderRepository.save(existingOrder);
        return OrderMapper.toDto(updatedOrder);
    }

    @Transactional
    public OrderOutputDto updateOrderItemQ(long id, int itemId, int quantity) {

        Order existingOrder = orderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        Item item = itemRepository.findById(itemId).orElseThrow(()-> new ResourceNotFoundException("Item not found"));


        OrderDetail orderDetail = detailRepository.findByOrderAndItem(existingOrder, item)
                .orElseThrow(()-> new ResourceNotFoundException("Item with id: " + item.getId() + " not found in the order " + existingOrder.getId())
                            );
        //de hoeveelheid wordt in de orderdetail aangepast
        orderDetail.setItemQuantity(quantity);
        //er wordt een nieuwe berekening gemaakt van het totaal bedrag in de order
        updateOrderTotalAmount(existingOrder);

        Order updatedOrder = orderRepository.save(existingOrder);
        return OrderMapper.toDto(updatedOrder);
    }

    //order aanpassen voor de status afgehandeld en voor betaalstatus betaald
    @Transactional
    public OrderOutputDto updateOrderByAction(long id, String action) {

        Order existingOrder = orderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        switch (action) {
            case "orderCompleted":
                if(existingOrder.getOrderStatus() == OrderStatus.COMPLETED){
                    throw new IllegalArgumentException("Order is already completed");
                }
                else if(existingOrder.getOrderStatus() == OrderStatus.CREATED) {
                    existingOrder.setOrderStatus(OrderStatus.COMPLETED);
                }
                break;
            case "orderPaid":
                if(existingOrder.getPaymentStatus() == PaymentStatus.PAID){
                    throw new IllegalArgumentException("Order is already paid");
                }
                else if(existingOrder.getPaymentStatus() == PaymentStatus.TOPAY){
                    existingOrder.setPaymentStatus(PaymentStatus.PAID);
                }
                break;
            default: throw new IllegalArgumentException("This action (" + action + ") is not valid");
        }

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
