package nl.novi.pizzeria_webAPI.dto;

import nl.novi.pizzeria_webAPI.model.InvoiceStatus;
import nl.novi.pizzeria_webAPI.model.OrderDetail;
import nl.novi.pizzeria_webAPI.model.PaymentStatus;

import java.time.LocalDate;
import java.util.Set;

public class InvoiceOutputDto {

    public Long id;
    public Long orderNum;
    public int customerNum;
    public String customerName;
    public LocalDate orderDate;
    public double orderAmount;
    public double invoiceAmount;
    public PaymentStatus paymentStatus;
    public Set<DetailOutputDto> detailOutputDtos;
    public InvoiceStatus invoiceStatus;

}
