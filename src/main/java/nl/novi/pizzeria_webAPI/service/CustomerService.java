package nl.novi.pizzeria_webAPI.service;

import nl.novi.pizzeria_webAPI.dto.CustomerInputDto;
import nl.novi.pizzeria_webAPI.dto.CustomerOutputDto;
import nl.novi.pizzeria_webAPI.exception.InvalidDeletionException;
import nl.novi.pizzeria_webAPI.exception.InvalidReplaceException;
import nl.novi.pizzeria_webAPI.exception.RecordAlreadyExistsException;
import nl.novi.pizzeria_webAPI.exception.ResourceNotFoundException;
import nl.novi.pizzeria_webAPI.mapper.CustomerMapper;
import nl.novi.pizzeria_webAPI.model.Customer;
import nl.novi.pizzeria_webAPI.model.Profile;
import nl.novi.pizzeria_webAPI.repository.CustomerRepository;
import nl.novi.pizzeria_webAPI.repository.OrderRepository;
import nl.novi.pizzeria_webAPI.repository.ProfileRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final ProfileRepository profileRepos;
    private CustomerRepository customerRepos;
    private OrderRepository orderRepos;

    public CustomerService(CustomerRepository customerRepos, OrderRepository orderRepos, ProfileRepository profileRepos) {
        this.customerRepos = customerRepos;
        this.orderRepos = orderRepos;
        this.profileRepos = profileRepos;
    }

    public CustomerOutputDto createCustomer(CustomerInputDto customerInDto){

        //check of de combinatie name en lastname al bestaat
        if(customerRepos.existsByNameAndLastname(customerInDto.name, customerInDto.lastname)) {
            throw new RecordAlreadyExistsException("A customer with this name and lastname already exists");
        }

        //via customerInDto wordt customer entiteit opgeslagen
        Customer customer = CustomerMapper.toEntity(customerInDto);

        //zoeken of er een profiel bestaat met dezelfde name en lastname
        Optional<Profile>existingProfile = profileRepos.findByNameAndLastname(customerInDto.name, customerInDto.lastname);

        //indien aanwezig dan die profiel koppelen aan de customer
        if(existingProfile.isPresent()){
            customer.setProfile(existingProfile.get());
        }else{
            //anders profiel blijft null
            customer.setProfile(null);
        }

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

