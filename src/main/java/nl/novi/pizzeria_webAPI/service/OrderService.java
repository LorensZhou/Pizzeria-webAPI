package nl.novi.pizzeria_webAPI.service;


import jakarta.validation.Valid;
import nl.novi.pizzeria_webAPI.dto.OrderInputDto;
import nl.novi.pizzeria_webAPI.dto.OrderOutputDto;
import nl.novi.pizzeria_webAPI.exception.ResourceNotFoundException;
import nl.novi.pizzeria_webAPI.mapper.OrderMapper;
import nl.novi.pizzeria_webAPI.model.Order;
import nl.novi.pizzeria_webAPI.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepos) {
        this.orderRepository = orderRepos;
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

    public OrderOutputDto updateMenuItemNum(long id, int newMenuItemNum) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        if(newMenuItemNum > 0){
            existingOrder.setMenuItemNum(newMenuItemNum);
        }else{
            throw new IllegalArgumentException("Menu item number must be greater than 0");
        }
        Order updatedOrder = orderRepository.save(existingOrder);
        return OrderMapper.toDto(updatedOrder);
    }
}
