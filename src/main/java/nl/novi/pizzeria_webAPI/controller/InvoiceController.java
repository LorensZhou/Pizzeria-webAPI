package nl.novi.pizzeria_webAPI.controller;

import jakarta.validation.Valid;
import nl.novi.pizzeria_webAPI.dto.InvoiceInputDto;
import nl.novi.pizzeria_webAPI.dto.InvoiceOutputDto;
import nl.novi.pizzeria_webAPI.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<InvoiceOutputDto>createInvoice(@Valid @RequestBody InvoiceInputDto invoiceInDto) {
        InvoiceOutputDto invoiceOutDto = this.service.createInvoice(invoiceInDto);
        return new ResponseEntity<>(invoiceOutDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<InvoiceOutputDto>updateInvoiceAmount(@PathVariable long id, @RequestParam double newInvoiceAmount){
        InvoiceOutputDto invoiceOutDto = this.service.updateInvoiceAmount(id, newInvoiceAmount);
        return ResponseEntity.ok(invoiceOutDto);
    }

    @PatchMapping("{id}/printInvoice")
    public ResponseEntity<InvoiceOutputDto>printInvoice(@PathVariable long id){
        InvoiceOutputDto invoiceOutDto = this.service.printInvoice(id);
        return ResponseEntity.ok(invoiceOutDto);
    }


    @GetMapping("")
    public ResponseEntity<List<InvoiceOutputDto>>getAllInvoices(){
        return ResponseEntity.ok(this.service.getAllInvoices());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteInvoice(@PathVariable long id){
        this.service.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}
