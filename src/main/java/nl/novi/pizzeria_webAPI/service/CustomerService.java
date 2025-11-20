package nl.novi.pizzeria_webAPI.service;

import nl.novi.pizzeria_webAPI.dto.CustomerInputDto;
import nl.novi.pizzeria_webAPI.dto.CustomerOutputDto;
import nl.novi.pizzeria_webAPI.exception.InvalidDeletionException;
import nl.novi.pizzeria_webAPI.exception.ResourceNotFoundException;
import nl.novi.pizzeria_webAPI.mapper.CustomerMapper;
import nl.novi.pizzeria_webAPI.model.Customer;
import nl.novi.pizzeria_webAPI.repository.CustomerRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository customerRepos;

    public CustomerService(CustomerRepository customerRepos) {
        this.customerRepos = customerRepos;
    }

    public CustomerOutputDto createCustomer(CustomerInputDto customerInDto){
        //via customerInDto wordt customer entiteit opgeslagen
        Customer customer = CustomerMapper.toEntity(customerInDto);
        customerRepos.save(customer);
        //de customer entiteit wordt teruggestuurd via de mapper die het converteert naar CustomerOutputDto
        return CustomerMapper.toDto(customer);
    }

    public CustomerOutputDto replaceCustomer(int id, CustomerInputDto customerInDto){
        Customer existingCustomer = customerRepos.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Customer with id " + id + " not found"));
        existingCustomer.setName(customerInDto.name);
        existingCustomer.setLastname(customerInDto.lastname);

        Customer updatedCustomer = customerRepos.save(existingCustomer);
        return CustomerMapper.toDto(updatedCustomer);
    }

    public List<CustomerOutputDto> getAllCustomers() {
        List<Customer> customers = customerRepos.findAll();
        return customers.stream()
                .map(CustomerMapper::toDto)
                .toList();
    }

    public void deleteCustomer(int id){
        if(!customerRepos.existsById(id)){
            throw new ResourceNotFoundException("Customer with id " + id + " not found");
        }
        try {customerRepos.deleteById(id);}
        catch(DataIntegrityViolationException e){
            throw new InvalidDeletionException("Customer with id " + id + " can not be deleted. Delete the customer first from the order(s)");
        }
    }

}

