package nl.novi.pizzeria_webAPI.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class DetailInputDto {

    @NotNull
    public Integer itemId;
    @NotNull
    @Min(value=1)
    public Integer itemQuantity;
}
