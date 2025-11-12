package nl.novi.pizzeria_webAPI.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import nl.novi.pizzeria_webAPI.model.OrderStatus;
import nl.novi.pizzeria_webAPI.model.PaymentStatus;
import nl.novi.pizzeria_webAPI.model.PaymentType;

public class OrderInputDto {

    //validatie wordt hier gedaan bij OrderInputDto om exception op te vangen
    @NotNull(message="The customer number is required.")
    @Min(value=1, message = "The customer number should be greater than 0.")
    public Integer customerNum;
    @NotNull(message="The employee number is required.")
    @Min(value=1, message = "The employee number should be greater than 0.")
    public Integer employeeNum;
    public int menuItemNum;
    public double orderAmount;

    public PaymentType paymentType;
    public PaymentStatus paymentStatus;
    public OrderStatus orderStatus;
}
