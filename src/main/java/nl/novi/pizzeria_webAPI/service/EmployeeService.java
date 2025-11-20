package nl.novi.pizzeria_webAPI.service;

import nl.novi.pizzeria_webAPI.dto.EmployeeInputDto;
import nl.novi.pizzeria_webAPI.dto.EmployeeOutputDto;
import nl.novi.pizzeria_webAPI.exception.InvalidDeletionException;
import nl.novi.pizzeria_webAPI.exception.InvalidReplaceException;
import nl.novi.pizzeria_webAPI.exception.ResourceNotFoundException;
import nl.novi.pizzeria_webAPI.mapper.EmployeeMapper;
import nl.novi.pizzeria_webAPI.model.Employee;
import nl.novi.pizzeria_webAPI.repository.EmployeeRepository;
import nl.novi.pizzeria_webAPI.repository.OrderRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepos;
    private final OrderRepository orderRepos;

    public EmployeeService(EmployeeRepository employeeRepos, OrderRepository orderRepos) {
        this.employeeRepos = employeeRepos;
        this.orderRepos = orderRepos;
    }

    public EmployeeOutputDto createEmployee(EmployeeInputDto employeeInDto) {
        Employee employee = EmployeeMapper.toEntity(employeeInDto);
        this.employeeRepos.save(employee);
        return EmployeeMapper.toDto(employee);
    }

    public EmployeeOutputDto replaceEmployee(int id, EmployeeInputDto employeeInDto) {
        Employee existingEmployee = this.employeeRepos.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee with id " + id + " not found"));

        if(this.orderRepos.existsByEmployee(existingEmployee)){
            throw new InvalidReplaceException("Employee with id "
                    + id
                    + " can not be updated, because it is part of an existing order. "
                    + "To preserve the integrity of past orders, updates are forbidden.");
        }

        existingEmployee.setName(employeeInDto.name);
        existingEmployee.setLastname(employeeInDto.lastname);

        Employee updatedEmployee = this.employeeRepos.save(existingEmployee);

        return EmployeeMapper.toDto(updatedEmployee);
    }

    public List<EmployeeOutputDto> getAllEmployees() {
        List<Employee> employees = this.employeeRepos.findAll();

        return employees
                .stream()
                .map(EmployeeMapper::toDto)
                .toList();
    }

    public void deleteEmployee(int id) {
        if(!this.employeeRepos.existsById(id)) {
            throw new ResourceNotFoundException("Employee with id " + id + " not found");
        }
        try{
            this.employeeRepos.deleteById(id);
        }
        catch(DataIntegrityViolationException e) {
            throw new InvalidDeletionException("Employee with id "
                                               + id
                                               + " can not be deleted. Delete the employee first from the order(s)");
        }
    }
    
}
