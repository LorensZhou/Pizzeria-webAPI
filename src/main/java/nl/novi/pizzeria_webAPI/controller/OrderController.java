package nl.novi.pizzeria_webAPI.controller;

import jakarta.validation.Valid;
import nl.novi.pizzeria_webAPI.dto.OrderInputDto;
import nl.novi.pizzeria_webAPI.dto.OrderOutputDto;
import nl.novi.pizzeria_webAPI.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<OrderOutputDto>>getAllOrders(){
        return ResponseEntity.ok(this.service.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOutputDto> getOrderById(@PathVariable Long id) {

        return ResponseEntity.ok(this.service.getOrderById(id));
    }

    @PostMapping("")
    public ResponseEntity<OrderOutputDto>createOrder(@Valid @RequestBody OrderInputDto orderInputDto) {

        OrderOutputDto orderOutputDto = this.service.createOrder(orderInputDto);
        return new ResponseEntity<>(orderOutputDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/addItem")
    public ResponseEntity<OrderOutputDto>updateOrderAddItem(@PathVariable long id, @RequestParam int newItemId, @RequestParam int quantity){
        OrderOutputDto orderOutputDto = this.service.updateOrderAddItem(id, newItemId, quantity);
        return ResponseEntity.ok(orderOutputDto);
    }

}
