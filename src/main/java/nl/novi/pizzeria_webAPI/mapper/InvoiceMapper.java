package nl.novi.pizzeria_webAPI.mapper;

import nl.novi.pizzeria_webAPI.dto.InvoiceInputDto;
import nl.novi.pizzeria_webAPI.dto.InvoiceOutputDto;
import nl.novi.pizzeria_webAPI.model.Invoice;

public class InvoiceMapper {

    public Invoice toEntity(InvoiceInputDto invoiceInputDto) {

        Invoice invoice = new Invoice();
        invoice.setInvoiceRef(invoiceInputDto.invoiceRef);
        //het vullen van invoice.orderNum wordt gedaan in service laag d.m.v. order object
        invoice.setInvoiceStatus(invoiceInputDto.invoiceStatus);

        return invoice;
    }

    public InvoiceOutputDto toDto(Invoice invoice) {

        InvoiceOutputDto invoiceOutputDto = new InvoiceOutputDto();
        invoiceOutputDto.id = invoice.getId();
        invoiceOutputDto.invoiceRef = invoice.getInvoiceRef();
        invoiceOutputDto.invoiceStatus = invoice.getInvoiceStatus();

        return invoiceOutputDto;

    }
}
