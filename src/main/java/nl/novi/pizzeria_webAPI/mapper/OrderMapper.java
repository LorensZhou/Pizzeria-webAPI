package nl.novi.pizzeria_webAPI.mapper;

import nl.novi.pizzeria_webAPI.dto.OrderInputDto;
import nl.novi.pizzeria_webAPI.dto.OrderOutputDto;
import nl.novi.pizzeria_webAPI.model.Order;
import nl.novi.pizzeria_webAPI.model.OrderStatus;
import nl.novi.pizzeria_webAPI.model.PaymentStatus;
import nl.novi.pizzeria_webAPI.model.PaymentType;

public class OrderMapper {

    public static Order toEntityByCreate(OrderInputDto orderInputDto) {
        Order order = new Order();

        order.setCustomerNum(orderInputDto.customerNum);
        order.setEmployeeNum(orderInputDto.employeeNum);
        order.setMenuItemNum(orderInputDto.menuItemNum);
        order.setOrderAmount(orderInputDto.orderAmount);

        order.setPaymentType(PaymentType.CASH);
        order.setPaymentStatus(PaymentStatus.TOPAY);
        order.setOrderStatus(OrderStatus.CREATED);

        return order;
    }

    public static Order toEntityByPatch(OrderInputDto orderInputDto) {
        Order order = new Order();

        order.setCustomerNum(orderInputDto.customerNum);
        order.setEmployeeNum(orderInputDto.employeeNum);
        order.setMenuItemNum(orderInputDto.menuItemNum);
        order.setOrderAmount(orderInputDto.orderAmount);

        order.setPaymentType(orderInputDto.paymentType);
        order.setPaymentStatus(orderInputDto.paymentStatus);
        order.setOrderStatus(orderInputDto.orderStatus);

        return order;
    }

    public static OrderOutputDto toDto(Order order){
        OrderOutputDto orderOutputDto = new OrderOutputDto();

        orderOutputDto.orderId = order.getOrderId();
        orderOutputDto.customerNum = order.getCustomerNum();
        orderOutputDto.employeeNum = order.getEmployeeNum();
        orderOutputDto.menuItemNum = order.getMenuItemNum();
        orderOutputDto.orderAmount = order.getOrderAmount();

        orderOutputDto.paymentType = order.getPaymentType();
        orderOutputDto.paymentStatus = order.getPaymentStatus();
        orderOutputDto.orderStatus = order.getOrderStatus();

        return orderOutputDto;
    }


}
