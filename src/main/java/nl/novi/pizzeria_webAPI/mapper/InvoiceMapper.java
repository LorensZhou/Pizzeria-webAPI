package nl.novi.pizzeria_webAPI.mapper;

import nl.novi.pizzeria_webAPI.dto.InvoiceInputDto;
import nl.novi.pizzeria_webAPI.dto.InvoiceOutputDto;
import nl.novi.pizzeria_webAPI.model.Invoice;
import nl.novi.pizzeria_webAPI.model.InvoiceStatus;

public class InvoiceMapper {

    public static Invoice toEntity(InvoiceInputDto invoiceInputDto) {

        Invoice invoice = new Invoice();
        //het vullen van invoice.orderNum wordt gedaan in service laag d.m.v. order object
        invoice.setInvoiceStatus(InvoiceStatus.CREATED);

        return invoice;
    }

    public static InvoiceOutputDto toDto(Invoice invoice) {

        InvoiceOutputDto invoiceOutputDto = new InvoiceOutputDto();
        invoiceOutputDto.id = invoice.getId();
        invoiceOutputDto.invoiceStatus = invoice.getInvoiceStatus();
        //de rest van de attributen worden gevuld in de service laag

        return invoiceOutputDto;

    }
}
