package nl.novi.pizzeria_webAPI.controller;

import jakarta.validation.Valid;
import nl.novi.pizzeria_webAPI.dto.CustomerInputDto;
import nl.novi.pizzeria_webAPI.dto.CustomerOutputDto;
import nl.novi.pizzeria_webAPI.model.Customer;
import nl.novi.pizzeria_webAPI.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service){
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<CustomerOutputDto> createCustomer(@Valid @RequestBody CustomerInputDto customerInDto){

        CustomerOutputDto customerOutDto = this.service.createCustomer(customerInDto);
        return new ResponseEntity<>(customerOutDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerOutputDto>replaceCustomer(@PathVariable int id,
                                                            @Valid @RequestBody CustomerInputDto customerInDto ){

        CustomerOutputDto customerOutDto = this.service.replaceCustomer(id, customerInDto);
        return ResponseEntity.ok(customerOutDto);
    }

    @GetMapping("")
    public ResponseEntity<List<CustomerOutputDto>>getAllCustomers(){
        return ResponseEntity.ok(this.service.getAllCustomers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteCustomer(@PathVariable int id){
        this.service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}
