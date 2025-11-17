package nl.novi.pizzeria_webAPI.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import nl.novi.pizzeria_webAPI.model.OrderStatus;
import nl.novi.pizzeria_webAPI.model.PaymentStatus;

import java.time.LocalDate;
import java.util.Set;

public class OrderInputDto {

    //validatie wordt hier gedaan bij OrderInputDto om exception op te vangen
    @NotNull(message="The customer number is required.")
    @Min(value=1, message = "The customer number should be greater than 0.")
    public Integer customerNum;

    @NotNull(message="The employee number is required.")
    @Min(value=1, message = "The employee number should be greater than 0.")
    public Integer employeeNum;

    public Set<DetailInputDto> detailInputDtos;;
    public double orderAmount;
    public LocalDate orderDate;

    public PaymentStatus paymentStatus;
    public OrderStatus orderStatus;
}
