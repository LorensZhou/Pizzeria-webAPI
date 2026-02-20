package nl.novi.pizzeria_webAPI.controller;

import jakarta.validation.Valid;
import nl.novi.pizzeria_webAPI.dto.EmployeeInputDto;

import nl.novi.pizzeria_webAPI.dto.EmployeeOutputDto;
import nl.novi.pizzeria_webAPI.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<EmployeeOutputDto>createEmployee(@Valid @RequestBody EmployeeInputDto employeeInputDto){
        EmployeeOutputDto employeeOutDto = this.service.createEmployee(employeeInputDto);
        return new ResponseEntity<>(employeeOutDto, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<EmployeeOutputDto>>getAllEmployees(){
        return ResponseEntity.ok(this.service.getAllEmployees());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeOutputDto>replaceEmployee(@PathVariable int id, @Valid @RequestBody EmployeeInputDto employeeInputDto){
        EmployeeOutputDto employeeOutDto = this.service.replaceEmployee(id, employeeInputDto);
        return ResponseEntity.ok(employeeOutDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id){
        this.service.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
