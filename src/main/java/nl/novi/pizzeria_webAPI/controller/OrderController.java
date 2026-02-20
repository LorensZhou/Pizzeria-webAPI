package nl.novi.pizzeria_webAPI.controller;

import jakarta.validation.Valid;
import nl.novi.pizzeria_webAPI.dto.OrderInputDto;
import nl.novi.pizzeria_webAPI.dto.OrderOutputDto;
import nl.novi.pizzeria_webAPI.model.Profile;
import nl.novi.pizzeria_webAPI.repository.ProfileRepository;
import nl.novi.pizzeria_webAPI.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;
    private final ProfileRepository profileRepos;

    public OrderController(OrderService service, ProfileRepository profileRepos) {
        this.service = service;
        this.profileRepos = profileRepos;
    }

    @GetMapping("")
    public ResponseEntity<List<OrderOutputDto>>getAllOrders(){
        return ResponseEntity.ok(this.service.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOutputDto> getOrderById(@PathVariable Long id) {
        OrderOutputDto orderOutputDto = this.service.getOrderById(id);

        URI uri = ServletUriComponentsBuilder
                        .fromCurrentRequest() //de pad "http://localhost:8080/orders/{id}"
                        .build()
                        .toUri();

        return ResponseEntity.ok()
                .location(uri)
                .body(orderOutputDto);
    }

    //getmapping voor ingelogde customer met een profiel om een de orders te bekijken
    @GetMapping("/auth-customer/{username}")
    public ResponseEntity<List<OrderOutputDto>>getOrdersAuthCustomer(@PathVariable String username, @AuthenticationPrincipal UserDetails userdetails){
        Profile profile = this.profileRepos.findById(username).orElse(null);

        if(profile == null){
            return ResponseEntity.notFound().build();
        }

        //Deze endpoint is alleen toegankelijk voor de customer met de opgegeven username
        if(!userdetails.getUsername().equals(username)){
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(this.service.getOrdersAuthCustomer(profile));
    }

    @PostMapping("")
    public ResponseEntity<OrderOutputDto>createOrder(@Valid @RequestBody OrderInputDto orderInputDto) {

        OrderOutputDto orderOutputDto = this.service.createOrder(orderInputDto);
        long orderid = orderOutputDto.id;

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + orderid).toUriString());

        return ResponseEntity.created(uri).body(orderOutputDto);

    }

    @PatchMapping("/{id}/addItem")
    public ResponseEntity<OrderOutputDto>updateOrderAddItem(@PathVariable long id, @RequestParam int newItemId, @RequestParam int quantity){
        OrderOutputDto orderOutputDto = this.service.updateOrderAddItem(id, newItemId, quantity);
        return ResponseEntity.ok(orderOutputDto);
    }

    @PatchMapping("/{id}/updateQuantity")
    public ResponseEntity<OrderOutputDto>updateOrderItemQ(@PathVariable long id, @RequestParam int itemId, @RequestParam int quantity){
        OrderOutputDto orderOutputDto = this.service.updateOrderItemQ(id, itemId, quantity);
        return ResponseEntity.ok(orderOutputDto);
    }

    @PatchMapping("/{id}/{action}")
    public ResponseEntity<OrderOutputDto>updateOrderByAction(@PathVariable long id, @PathVariable String action){
        OrderOutputDto orderOutputDto = this.service.updateOrderByAction(id, action);
        return ResponseEntity.ok(orderOutputDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteOrder(@PathVariable Long id){
        this.service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/{itemId}")
    public ResponseEntity<Void>deleteOrderItem(@PathVariable Long id, @RequestParam int itemId){
        this.service.deleteOrderItem(id, itemId);
                return ResponseEntity.noContent().build();
    }

}
