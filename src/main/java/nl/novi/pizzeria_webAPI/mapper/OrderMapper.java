package nl.novi.pizzeria_webAPI.mapper;

import nl.novi.pizzeria_webAPI.dto.OrderInputDto;
import nl.novi.pizzeria_webAPI.dto.OrderOutputDto;
import nl.novi.pizzeria_webAPI.model.Order;
import nl.novi.pizzeria_webAPI.model.OrderStatus;
import nl.novi.pizzeria_webAPI.model.PaymentStatus;

public class OrderMapper {

    public static Order toEntityByCreate(OrderInputDto orderInputDto) {
        Order order = new Order();

        order.setCustomerNum(orderInputDto.customerNum);
        order.setEmployeeNum(orderInputDto.employeeNum);
        order.setOrderDetails(orderInputDto.orderDetails);
        order.setOrderAmount(orderInputDto.orderAmount);
        order.setOrderDate(orderInputDto.orderDate);

        order.setPaymentStatus(PaymentStatus.TOPAY);
        order.setOrderStatus(OrderStatus.CREATED);

        return order;
    }

    public static Order toEntityByPatch(OrderInputDto orderInputDto) {
        Order order = new Order();

        order.setCustomerNum(orderInputDto.customerNum);
        order.setEmployeeNum(orderInputDto.employeeNum);
        order.setOrderDetails(orderInputDto.orderDetails);
        order.setOrderAmount(orderInputDto.orderAmount);
        order.setOrderDate(orderInputDto.orderDate);

        order.setPaymentStatus(orderInputDto.paymentStatus);
        order.setOrderStatus(orderInputDto.orderStatus);

        return order;
    }

    public static OrderOutputDto toDto(Order order){
        OrderOutputDto orderOutputDto = new OrderOutputDto();

        orderOutputDto.id = order.getId();
        orderOutputDto.customerNum = order.getCustomerNum();
        orderOutputDto.employeeNum = order.getEmployeeNum();
        orderOutputDto.orderDetails = order.getOrderDetails();
        orderOutputDto.orderAmount = order.getOrderAmount();
        orderOutputDto.orderDate = order.getOrderDate();

        orderOutputDto.paymentStatus = order.getPaymentStatus();
        orderOutputDto.orderStatus = order.getOrderStatus();

        return orderOutputDto;
    }


}
