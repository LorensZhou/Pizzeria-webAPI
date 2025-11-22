package nl.novi.pizzeria_webAPI.service;

import jakarta.transaction.Transactional;
import nl.novi.pizzeria_webAPI.dto.InvoiceInputDto;
import nl.novi.pizzeria_webAPI.dto.InvoiceOutputDto;
import nl.novi.pizzeria_webAPI.exception.ResourceNotFoundException;
import nl.novi.pizzeria_webAPI.mapper.InvoiceMapper;
import nl.novi.pizzeria_webAPI.model.*;
import nl.novi.pizzeria_webAPI.repository.InvoiceRepository;
import nl.novi.pizzeria_webAPI.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepos;
    private final OrderRepository orderRepos;

    public InvoiceService(InvoiceRepository invoiceRepos, OrderRepository orderRepos) {
        this.invoiceRepos = invoiceRepos;
        this.orderRepos = orderRepos;
    }

    @Transactional
    public InvoiceOutputDto createInvoice(InvoiceInputDto invoiceInDto) {

        Order order = this.orderRepos.findById(invoiceInDto.orderNum)
                .orElseThrow(()->new ResourceNotFoundException("Order cannot be found with id " + invoiceInDto.orderNum));

        //Er mag alleen een invoice aanwezig zijn voor een bepaalde order nummer
        if(order.getInvoice() != null){
            throw new IllegalArgumentException("Invoice already exists for order id: " + invoiceInDto.orderNum);
        }

        Invoice invoice = InvoiceMapper.toEntity(invoiceInDto);

        //maak bi-directionele relatie tussen de invoice en de desbetreffende order
        order.setInvoice(invoice); //owner side
        invoice.setOrder(order);   //mappedBy side

        //forceer de save/flush van de invoice om the juste invoice.id te verkrijgen
        this.invoiceRepos.save(invoice);

        //opslaan order (doordat order de eigenaar zijde is, door cascadeType.ALL op invoice
        //zal de invoice automatische worden opgeslagen
        this.orderRepos.save(order);

        InvoiceOutputDto invoiceOutDto = InvoiceMapper.toDto(invoice);

        return fillWithOrderAndInvoice(invoiceOutDto, order, invoice);
    }

    public InvoiceOutputDto updateInvoiceAmount(long id, double newInvoiceAmount) {

        Invoice existingInvoice = this.invoiceRepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice cannot be found with id " + id));

        if(existingInvoice.getOrder() == null){
            throw new IllegalArgumentException("Invoice has no reference to an order");
        }

        Order order = existingInvoice.getOrder();

        existingInvoice.setInvoiceAmount(newInvoiceAmount);

        Invoice updatedInvoice = this.invoiceRepos.save(existingInvoice);

        InvoiceOutputDto invoiceOutDto = InvoiceMapper.toDto(updatedInvoice);

        //pas de invoiceAmount van de invoiceOutDto aan met de waarde in de invoice
        if(updatedInvoice.getInvoiceAmount() != 0.0){
            invoiceOutDto.invoiceAmount = updatedInvoice.getInvoiceAmount();
        }

        return fillWithOrderAndInvoice(invoiceOutDto, order, updatedInvoice);
    }

    public InvoiceOutputDto printInvoice(long id){

        Invoice existingInvoice = this.invoiceRepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice cannot be found with id " + id));

        if(existingInvoice.getOrder() == null){
            throw new IllegalArgumentException("Invoice has no reference to an order");
        }

        Order order = existingInvoice.getOrder();

        if(existingInvoice.getInvoiceStatus() ==  InvoiceStatus.CREATED) {
            existingInvoice.setInvoiceStatus(InvoiceStatus.PRINTED);
        }
        Invoice updatedInvoice = this.invoiceRepos.save(existingInvoice);

        InvoiceOutputDto invoiceOutDto = InvoiceMapper.toDto(updatedInvoice);

        return fillWithOrderAndInvoice(invoiceOutDto, order, updatedInvoice);
    }

    public List<InvoiceOutputDto>getAllInvoices(){

        List<Invoice> invoices = this.invoiceRepos.findAll();
        List<InvoiceOutputDto> invoiceOutDtos = invoices
                .stream()
                .map(InvoiceMapper::toDto)
                .toList();

        for (InvoiceOutputDto invoiceOutDto: invoiceOutDtos){

            Order order = this.orderRepos.findById(invoiceOutDto.orderNum)
                    .orElseThrow(() -> new ResourceNotFoundException("Order cannot be found with id " + invoiceOutDto.orderNum));

            Invoice invoice = this.invoiceRepos.findById(invoiceOutDto.id)
                            .orElseThrow(() -> new ResourceNotFoundException("Invoice cannot be found with id " + invoiceOutDto.id));

            fillWithOrderAndInvoice(invoiceOutDto, order, invoice);
        }

        return invoiceOutDtos;
    }

    public void deleteInvoice(long id){

        Invoice existingInvoice = this.invoiceRepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice cannot be found with id " + id));

        Order order =existingInvoice.getOrder();

        if(order != null){
            order.setInvoice(null);
            this.orderRepos.save(order);
        }

        this.invoiceRepos.delete(existingInvoice);
    }

    //helper functie voor vullen van invoiceOutDto
    private InvoiceOutputDto fillWithOrderAndInvoice(InvoiceOutputDto invoiceOutDto, Order order, Invoice invoice) {

        invoiceOutDto.orderNum = order.getId();
        invoiceOutDto.customerNum = order.getCustomer().getId();

        if(order.getCustomer().getLastname() != null && !order.getCustomer().getLastname().isEmpty()) {
            invoiceOutDto.customerName = order.getCustomer().getName() + " " + order.getCustomer().getLastname();
        }
        else {
            invoiceOutDto.customerName = order.getCustomer().getName();
        }

        invoiceOutDto.orderDate = order.getOrderDate();
        invoiceOutDto.orderAmount = order.getOrderAmount();
        invoiceOutDto.paymentStatus = order.getPaymentStatus();

        if(invoice.getInvoiceAmount() == 0.0){
            //bij create is de invoice.invoiceAmount standaard 0.0, daardoor moet invoiceOutDto.invoiceAmount
            //de waarde overnemen van de order.orderAmount
            //bij update van invoice.invoiceAmount is de waarde niet meer 0.0
            invoiceOutDto.invoiceAmount = order.getOrderAmount();
        }
        else{
            //pas de invoiceOutDto.invoiceAmount aan met de waarde in de invoice.invoiceAmount,
            //indien invoice.invoiceAmount niet 0.0 is
            invoiceOutDto.invoiceAmount = invoice.getInvoiceAmount();
        }

        return invoiceOutDto;
    }
}
