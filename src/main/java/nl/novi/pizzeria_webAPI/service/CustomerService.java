package nl.novi.pizzeria_webAPI.service;

import nl.novi.pizzeria_webAPI.dto.CustomerInputDto;
import nl.novi.pizzeria_webAPI.dto.CustomerOutputDto;
import nl.novi.pizzeria_webAPI.exception.InvalidDeletionException;
import nl.novi.pizzeria_webAPI.exception.InvalidReplaceException;
import nl.novi.pizzeria_webAPI.exception.ResourceNotFoundException;
import nl.novi.pizzeria_webAPI.mapper.CustomerMapper;
import nl.novi.pizzeria_webAPI.model.Customer;
import nl.novi.pizzeria_webAPI.repository.CustomerRepository;
import nl.novi.pizzeria_webAPI.repository.OrderRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository customerRepos;
    private OrderRepository orderRepos;

    public CustomerService(CustomerRepository customerRepos, OrderRepository orderRepos) {
        this.customerRepos = customerRepos;
        this.orderRepos = orderRepos;
    }

    public CustomerOutputDto createCustomer(CustomerInputDto customerInDto){
        //via customerInDto wordt customer entiteit opgeslagen
        Customer customer = CustomerMapper.toEntity(customerInDto);
        this.customerRepos.save(customer);
        //de customer entiteit wordt teruggestuurd via de mapper die het converteert naar CustomerOutputDto
        return CustomerMapper.toDto(customer);
    }

    public CustomerOutputDto replaceCustomer(int id, CustomerInputDto customerInDto){
        Customer existingCustomer = this.customerRepos.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Customer with id " + id + " not found"));

        if(this.orderRepos.existsByCustomer(existingCustomer)){
            throw new InvalidReplaceException("Customer with id "
                    + id
                    + " can not be updated, because it is part of an existing order. "
                    + "To preserve the integrity of past orders, updates are forbidden.");
        }

        existingCustomer.setName(customerInDto.name);
        existingCustomer.setLastname(customerInDto.lastname);

        Customer updatedCustomer = this.customerRepos.save(existingCustomer);
        return CustomerMapper.toDto(updatedCustomer);
    }

    public List<CustomerOutputDto> getAllCustomers() {
        List<Customer> customers = this.customerRepos.findAll();
        return customers.stream()
                .map(CustomerMapper::toDto)
                .toList();
    }

    public void deleteCustomer(int id){
        if(!this.customerRepos.existsById(id)){
            throw new ResourceNotFoundException("Customer with id " + id + " not found");
        }
        try {this.customerRepos.deleteById(id);}
        catch(DataIntegrityViolationException e){
            throw new InvalidDeletionException("Customer with id "
                                                + id
                                                + " can not be deleted. Delete first the order(s) from this customer");
        }
    }

}

