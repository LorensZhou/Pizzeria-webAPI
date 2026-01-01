package nl.novi.pizzeria_webAPI.mapper;

import nl.novi.pizzeria_webAPI.dto.DetailOutputDto;
import nl.novi.pizzeria_webAPI.dto.OrderOutputDto;
import nl.novi.pizzeria_webAPI.model.Order;
import nl.novi.pizzeria_webAPI.model.OrderDetail;
import nl.novi.pizzeria_webAPI.model.OrderStatus;
import nl.novi.pizzeria_webAPI.model.PaymentStatus;

public class OrderMapper {

    //mapper heeft functie voor conversie van OrderDtos naar Order entities en andersom
    //mapper heeft functie voor conversie van DetailDtos naar OrderDetail entities

    public static Order toEntity() {
        Order order = new Order();

        //het vullen van order.customerNum wordt gedaan in service laag d.m.v. customer object
        //het vullen van orderInputDto.employeeNum wordt gedaan in de service laag d.m.v. employee object
        //het vullen van order.orderDetails wordt gedaan in OrderService
        order.setOrderReference("");
        order.setPaymentStatus(PaymentStatus.TOPAY);
        order.setOrderStatus(OrderStatus.CREATED);

        return order;
    }

    public static OrderOutputDto toDto(Order order){
        OrderOutputDto orderOutputDto = new OrderOutputDto();

        orderOutputDto.id = order.getId();
        orderOutputDto.orderReference = order.getOrderReference();

        //additionele beveiliging om null voor customer object op te vangen
        if(order.getCustomer() != null) {
            orderOutputDto.customerNum = order.getCustomer().getId();
        }
        else{
            orderOutputDto.customerNum = 0;
        }

        //additionele beveiliging om null voor employee object op te vangen
        if(order.getEmployee() != null) {
            orderOutputDto.employeeNum = order.getEmployee().getId();
        }
        else{
            orderOutputDto.employeeNum = 0;
        }

        //nieuwe mapping, conversie van type OrderDetail naar type DetailOutputDto
        if(order.getOrderDetails()!=null){
            orderOutputDto.detailOutputDtos = order.getOrderDetails()
                    .stream()
                    .map(OrderMapper::toDetailDto)
                    .collect(java.util.stream.Collectors.toSet());
        }

        orderOutputDto.orderAmount = order.getOrderAmount();
        orderOutputDto.orderDate = order.getOrderDate();

        orderOutputDto.paymentStatus = order.getPaymentStatus();
        orderOutputDto.orderStatus = order.getOrderStatus();

        //detailOutputDtos wordt meegestuurd met orderOutputDto
        return orderOutputDto;
    }

    //conversie van OrderDetail naar DetailOutputDto
    public static DetailOutputDto toDetailDto(OrderDetail orderDetail){
        DetailOutputDto detailOutDto = new DetailOutputDto();
        detailOutDto.id = orderDetail.getId();
        detailOutDto.itemId = orderDetail.getItem().getId(); //haalt de id van item op via de item object
        detailOutDto.itemName = orderDetail.getItem().getName();
        detailOutDto.itemQuantity = orderDetail.getItemQuantity();
        detailOutDto.itemPrice = orderDetail.getItemPrice();
        detailOutDto.amount = orderDetail.getAmount();
        return detailOutDto;
    }

}
