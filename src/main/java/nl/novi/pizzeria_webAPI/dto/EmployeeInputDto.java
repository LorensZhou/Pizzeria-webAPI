package nl.novi.pizzeria_webAPI.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EmployeeInputDto {

    public Integer id;
    @NotNull(message="Name is required.")
    @Size(min=2, max=128, message="Name must be between 2 and 128 characters.")
    public String name;
    public String lastname;
}
