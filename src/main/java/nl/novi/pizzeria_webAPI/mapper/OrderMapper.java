package nl.novi.pizzeria_webAPI.mapper;

import nl.novi.pizzeria_webAPI.dto.DetailOutputDto;
import nl.novi.pizzeria_webAPI.dto.OrderInputDto;
import nl.novi.pizzeria_webAPI.dto.OrderOutputDto;
import nl.novi.pizzeria_webAPI.model.Order;
import nl.novi.pizzeria_webAPI.model.OrderDetail;
import nl.novi.pizzeria_webAPI.model.OrderStatus;
import nl.novi.pizzeria_webAPI.model.PaymentStatus;

public class OrderMapper {

    //mapper heeft functie voor conversie van OrderDtos naar Order entities en andersom
    //mapper heeft functie voor conversie van DetailDtos naar OrderDetail entities

    public static Order toEntity(OrderInputDto orderInputDto) {
        Order order = new Order();

        order.setCustomerNum(orderInputDto.customerNum);
        order.setEmployeeNum(orderInputDto.employeeNum);
        //het vullen van order.orderDetails wordt gedaan in OrderService
        order.setPaymentStatus(PaymentStatus.TOPAY);
        order.setOrderStatus(OrderStatus.CREATED);

        return order;
    }

    public static OrderOutputDto toDto(Order order){
        OrderOutputDto orderOutputDto = new OrderOutputDto();

        orderOutputDto.id = order.getId();
        orderOutputDto.customerNum = order.getCustomerNum();
        orderOutputDto.employeeNum = order.getEmployeeNum();

        //nieuwe mapping, conversie van type orderDetails naar type orderDetailOutputDto
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


    public static Order toEntityByPatch(OrderInputDto orderInputDto) {
        Order order = new Order();

        order.setCustomerNum(orderInputDto.customerNum);
        order.setEmployeeNum(orderInputDto.employeeNum);
        //hier kan de orderDate gewoon worden gevuld met orderInputDto.orderDate
        order.setOrderDate(orderInputDto.orderDate);

        order.setPaymentStatus(orderInputDto.paymentStatus);
        order.setOrderStatus(orderInputDto.orderStatus);

        return order;
    }

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
