package nl.novi.pizzeria_webAPI.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class DetailInputDto {

    @NotNull(message= "The item id is required.")
    public Integer itemId;
    @NotNull
    @Min(value=1, message="The quantity should be greater than 0.")
    public Integer itemQuantity;
}
