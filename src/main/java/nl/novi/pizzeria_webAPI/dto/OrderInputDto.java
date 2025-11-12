package nl.novi.pizzeria_webAPI.dto;

import nl.novi.pizzeria_webAPI.model.OrderStatus;
import nl.novi.pizzeria_webAPI.model.PaymentStatus;
import nl.novi.pizzeria_webAPI.model.PaymentType;

public class OrderInputDto {
    public int customerNum;
    public int employeeNum;
    public int menuItemNum;
    public double orderAmount;

    public PaymentType paymentType;
    public PaymentStatus paymentStatus;
    public OrderStatus orderStatus;
}
