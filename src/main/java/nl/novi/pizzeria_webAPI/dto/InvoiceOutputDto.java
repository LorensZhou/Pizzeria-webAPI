package nl.novi.pizzeria_webAPI.dto;

import nl.novi.pizzeria_webAPI.model.InvoiceStatus;

import java.time.LocalDate;
import java.util.Set;

public class InvoiceOutputDto {

    public Long id;
    public Long orderNum;
    public int customerNum;
    public String customerName;
    public LocalDate orderDate;
    public double orderAmount;
    public Set<DetailOutputDto> detailOutputDtos;
    public InvoiceStatus invoiceStatus;
}
