package nl.novi.pizzeria_webAPI.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import nl.novi.pizzeria_webAPI.model.InvoiceStatus;

public class InvoiceInputDto {

    @NotNull(message="The order number is required.")
    @Min(value=1, message = "The order number should be greater than 0.")
    public Long orderNum;
    public InvoiceStatus invoiceStatus;

}
