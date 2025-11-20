package nl.novi.pizzeria_webAPI.service;


import jakarta.transaction.Transactional;
import nl.novi.pizzeria_webAPI.dto.DetailInputDto;
import nl.novi.pizzeria_webAPI.dto.OrderInputDto;
import nl.novi.pizzeria_webAPI.dto.OrderOutputDto;
import nl.novi.pizzeria_webAPI.exception.ResourceNotFoundException;
import nl.novi.pizzeria_webAPI.mapper.OrderMapper;
import nl.novi.pizzeria_webAPI.model.*;
import nl.novi.pizzeria_webAPI.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepos;
    private final ItemRepository itemRepos;
    private final OrderDetailRepository detailRepos;
    private final EmployeeRepository employeeRepos;
    private final CustomerRepository customerRepos;

    public OrderService(OrderRepository orderRepos,
                        ItemRepository itemRepos,
                        OrderDetailRepository detailRepos,
                        EmployeeRepository employeeRepos,
                        CustomerRepository customerRepos) {

        this.orderRepos = orderRepos;
        this.itemRepos = itemRepos;
        this.detailRepos = detailRepos;
        this.employeeRepos = employeeRepos;
        this.customerRepos = customerRepos;
    }

    public List<OrderOutputDto> getAllOrders() {
        List<Order> orders = this.orderRepos.findAll();

        List<OrderOutputDto> orderOutputDtos = orders
                                               .stream()
                                               .map(OrderMapper::toDto)
                                               .toList();
        for(OrderOutputDto orderOutputDto : orderOutputDtos) {
            addEmployeeAndCustomerName(orderOutputDto);
        }

        return orderOutputDtos;
    }

    public OrderOutputDto getOrderById(Long id) {
        OrderOutputDto orderOutputDto = OrderMapper.toDto(
                this.orderRepos.findById(id).orElseThrow(()->new ResourceNotFoundException("Order not found")));

        return addEmployeeAndCustomerName(orderOutputDto);
    }

    @Transactional
    public OrderOutputDto createOrder(OrderInputDto orderInputDto) {

        Employee employee = this.employeeRepos.findById(orderInputDto.employeeNum)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found"));

        Customer customer = this.customerRepos.findById(orderInputDto.employeeNum)
                .orElseThrow(()->new ResourceNotFoundException("Customer not found"));

        Order order = OrderMapper.toEntity(orderInputDto);
        //het vullen van employeeNum wordt hier gedaan en niet in de ordermapper
        //het vullen van customerNum wordt hier gedaan en niet in de ordermapper
        order.setEmployee(employee);
        order.setCustomer(customer);

        Set<OrderDetail> orderDetails = new HashSet<>();
        double calculatedTotalAmount = 0.0;

        if(orderInputDto.detailInputDtos !=null){
            for (DetailInputDto detailInputDto : orderInputDto.detailInputDtos)
            {
                Item item = this.itemRepos
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
        this.orderRepos.save(order);

        return addEmployeeAndCustomerName(OrderMapper.toDto(order));
    }

    @Transactional
    public OrderOutputDto updateOrderAddItem(long id, int newItemId, int quantity) {

        Order existingOrder = this.orderRepos.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        Item newItem = this.itemRepos.findById(newItemId).orElseThrow(()-> new ResourceNotFoundException("Item not found"));

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
        Order updatedOrder = this.orderRepos.save(existingOrder);
        return addEmployeeAndCustomerName(OrderMapper.toDto(updatedOrder));
    }

    @Transactional
    public OrderOutputDto updateOrderItemQ(long id, int itemId, int quantity) {

        Order existingOrder = this.orderRepos.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        Item item = this.itemRepos.findById(itemId).orElseThrow(()-> new ResourceNotFoundException("Item not found"));


        OrderDetail orderDetail = this.detailRepos.findByOrderAndItem(existingOrder, item)
                .orElseThrow(()-> new ResourceNotFoundException("Item with id: " + item.getId() + " not found in the order " + existingOrder.getId())
                            );
        //de hoeveelheid wordt in de orderdetail aangepast
        orderDetail.setItemQuantity(quantity);
        //er wordt een nieuwe berekening gemaakt van het totaal bedrag in de order
        updateOrderTotalAmount(existingOrder);

        Order updatedOrder = this.orderRepos.save(existingOrder);
        return addEmployeeAndCustomerName(OrderMapper.toDto(updatedOrder));
    }

    //order aanpassen voor de status afgehandeld en voor betaalstatus betaald
    @Transactional
    public OrderOutputDto updateOrderByAction(long id, String action) {

        Order existingOrder = this.orderRepos.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
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

        Order updatedOrder = this.orderRepos.save(existingOrder);
        return addEmployeeAndCustomerName(OrderMapper.toDto(updatedOrder));
    }

    public void deleteOrder(Long id) {
        if (!this.orderRepos.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        this.orderRepos.deleteById(id);
    }

    public void deleteOrderItem(Long id, int itemId) {
        Order order = this.orderRepos.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        Item item = this.itemRepos.findById(itemId).orElseThrow(()-> new ResourceNotFoundException("Item not found"));

        OrderDetail orderDetailToDelete = this.detailRepos.findByOrderAndItem(order, item)
                .orElseThrow(()-> new ResourceNotFoundException("Item with id: " + item.getId() + " not found in the order " + order.getId())
                );
        this.detailRepos.delete(orderDetailToDelete);
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

    //helper functie retourneert orderOutputDto met employeeName en customerName
    private OrderOutputDto addEmployeeAndCustomerName(OrderOutputDto orderOutputDto){

        Employee employee = this.employeeRepos.findById(orderOutputDto.employeeNum)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found for this order with id: " + orderOutputDto.employeeNum));
        if(employee.getLastname() != null && !employee.getLastname().isEmpty()) {
            orderOutputDto.employeeName = employee.getName() + " " + employee.getLastname();
        }
        else {
            orderOutputDto.employeeName = employee.getName();
        }

        Customer customer = this.customerRepos.findById(orderOutputDto.customerNum)
                .orElseThrow(()->new ResourceNotFoundException("Customer not found for this order with id: " + orderOutputDto.customerNum));
        if(customer.getLastname() != null && !customer.getLastname().isEmpty()) {
            orderOutputDto.customerName = customer.getName() + " " + customer.getLastname();
        }
        else {
            orderOutputDto.customerName = customer.getName();
        }

        return orderOutputDto;
    }
}
