package nl.novi.pizzeria_webAPI.mapper;

import nl.novi.pizzeria_webAPI.dto.CustomerInputDto;
import nl.novi.pizzeria_webAPI.dto.CustomerOutputDto;
import nl.novi.pizzeria_webAPI.model.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerInputDto customerInputDto) {

        Customer customer = new Customer();
        customer.setName(customerInputDto.name);
        customer.setLastname(customerInputDto.lastname);

        return customer;
    }

    public static CustomerOutputDto toDto(Customer customer) {

        CustomerOutputDto customerOutDto = new CustomerOutputDto();
        customerOutDto.id = customer.getId();
        customerOutDto.name = customer.getName();
        customerOutDto.lastname = customer.getLastname();

        return customerOutDto;
    }
}
