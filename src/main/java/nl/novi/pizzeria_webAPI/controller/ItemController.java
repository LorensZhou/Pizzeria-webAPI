package nl.novi.pizzeria_webAPI.controller;


import jakarta.validation.Valid;
import nl.novi.pizzeria_webAPI.dto.ItemInputDto;
import nl.novi.pizzeria_webAPI.dto.ItemOutputDto;
import nl.novi.pizzeria_webAPI.model.Item;
import nl.novi.pizzeria_webAPI.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<ItemOutputDto>createItem(@Valid @RequestBody ItemInputDto itemInputDto) {
        ItemOutputDto itemOutputDto = this.service.createItem(itemInputDto);
        return new ResponseEntity<>(itemOutputDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem( @PathVariable int id) {
        this.service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("")
    public ResponseEntity<List<ItemOutputDto>> getAllItems(){
        return ResponseEntity.ok(this.service.getAllItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemOutputDto> getItemById(@PathVariable int id){
        return ResponseEntity.ok(this.service.getItemById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemOutputDto> replaceItem(@PathVariable int id, @Valid @RequestBody ItemInputDto itemInputDto) {
        ItemOutputDto itemOutputDto = this.service.replaceItem(id, itemInputDto);
        return ResponseEntity.ok(itemOutputDto);
    }

}


