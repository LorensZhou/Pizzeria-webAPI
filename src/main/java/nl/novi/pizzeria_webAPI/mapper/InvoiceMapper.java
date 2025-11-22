package nl.novi.pizzeria_webAPI.mapper;

import nl.novi.pizzeria_webAPI.dto.InvoiceInputDto;
import nl.novi.pizzeria_webAPI.dto.InvoiceOutputDto;
import nl.novi.pizzeria_webAPI.model.Invoice;
import nl.novi.pizzeria_webAPI.model.InvoiceStatus;
import nl.novi.pizzeria_webAPI.model.Order;

public class InvoiceMapper {

    public static Invoice toEntity(InvoiceInputDto invoiceInputDto) {

        Invoice invoice = new Invoice();
        //het vullen van invoice.orderNum wordt gedaan in service laag d.m.v. order object
        invoice.setInvoiceStatus(InvoiceStatus.CREATED);
        //invoiceAmount wordt standaard op 0.0 gezet, indien de klant een korting krijgt dan wordt deze gevuld met orderAmount - korting
        invoice.setInvoiceAmount(0.0);

        return invoice;
    }

    public static InvoiceOutputDto toDto(Invoice invoice) {

        InvoiceOutputDto invoiceOutputDto = new InvoiceOutputDto();
        invoiceOutputDto.id = invoice.getId();
        invoiceOutputDto.invoiceStatus = invoice.getInvoiceStatus();
        Order order = invoice.getOrder();

        //nieuwe mapping, conversie van type orderDetails naar type orderDetailOutputDto
        if(order != null && order.getOrderDetails()!=null){
            invoiceOutputDto.detailOutputDtos = order.getOrderDetails()
                    .stream()
                    .map(OrderMapper::toDetailDto)
                    .collect(java.util.stream.Collectors.toSet());
        }

        if(invoice.getOrder() != null){
            invoiceOutputDto.orderNum = invoice.getOrder().getId(); //ophalen order id
        }
        //de rest van de attributen worden gevuld in de service laag

        return invoiceOutputDto;

    }
}
