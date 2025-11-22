package nl.novi.pizzeria_webAPI.repository;

import nl.novi.pizzeria_webAPI.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
