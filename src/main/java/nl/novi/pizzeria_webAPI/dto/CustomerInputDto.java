package nl.novi.pizzeria_webAPI.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CustomerInputDto {

    @NotNull
    @Size(min = 2, max = 128)
    public String name;
    public String lastname;
}
