package nl.novi.pizzeria_webAPI.mapper;

import nl.novi.pizzeria_webAPI.dto.OrderInputDto;
import nl.novi.pizzeria_webAPI.dto.OrderOutputDto;
import nl.novi.pizzeria_webAPI.model.Order;

public class OrderMapper {

    public static Order toEntity(OrderInputDto orderInputDto) {
        Order order = new Order();
        order.setCustomerNum(orderInputDto.customerNum);
        order.setMenuItemNum(orderInputDto.menuItemNum);
        order.setEmployeeNum(orderInputDto.employeeNum);
        order.setOrderAmount(orderInputDto.orderAmount);

        return order;
    }

    public static OrderOutputDto toDto(Order order){
        OrderOutputDto orderOutputDto = new OrderOutputDto();
        orderOutputDto.orderId = order.getOrderId();
        orderOutputDto.customerNum = order.getCustomerNum();
        orderOutputDto.menuItemNum = order.getMenuItemNum();
        orderOutputDto.employeeNum = order.getEmployeeNum();
        orderOutputDto.orderAmount = order.getOrderAmount();

        return orderOutputDto;
    }


}
