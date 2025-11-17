package nl.novi.pizzeria_webAPI.dto;

import nl.novi.pizzeria_webAPI.model.OrderStatus;
import nl.novi.pizzeria_webAPI.model.PaymentStatus;

import java.time.LocalDate;
import java.util.Set;

public class OrderOutputDto {
    public long id;
    public int customerNum;
    public int employeeNum;
    public Set<DetailOutputDto> detailOutputDtos;
    public double orderAmount;
    public LocalDate orderDate;

    public PaymentStatus paymentStatus;
    public OrderStatus orderStatus;
}
