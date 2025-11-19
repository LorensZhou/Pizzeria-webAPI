package nl.novi.pizzeria_webAPI.dto;

import nl.novi.pizzeria_webAPI.model.InvoiceStatus;

public class InvoiceOutputDto {

    public Long id;
    public String invoiceRef;
    public Long orderNum;
    public InvoiceStatus invoiceStatus;
}
