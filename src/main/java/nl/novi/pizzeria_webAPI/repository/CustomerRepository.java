package nl.novi.pizzeria_webAPI.repository;

import nl.novi.pizzeria_webAPI.model.Customer;
import nl.novi.pizzeria_webAPI.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByNameAndLastname(String name, String lastname);
    //vind de customer die gelinked is aan de profiel
    Optional<Customer>findByProfile(Profile profile);
    Optional<Customer> findByNameAndLastname(String name, String lastname);
}
