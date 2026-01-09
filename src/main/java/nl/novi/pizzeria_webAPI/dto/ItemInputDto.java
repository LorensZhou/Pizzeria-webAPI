package nl.novi.pizzeria_webAPI.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ItemInputDto {
    public Integer id;
    @NotNull(message="The item name is required.")
    @Size(min=2, max=128, message="The item name must be between 2 and 128 characters.")
    public String name;
    @NotNull(message="The item price is required.")
    @Min(value=0, message = "The item price should be greater than 0.")
    public double price;
}
