package nl.novi.pizzeria_webAPI.repository;

import nl.novi.pizzeria_webAPI.model.Customer;
import nl.novi.pizzeria_webAPI.model.Employee;
import nl.novi.pizzeria_webAPI.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

    //check doen of de employee al bestaat in een order
    boolean existsByEmployee(Employee employee);

    //check doen of de customer al bestaat in een order
    boolean existsByCustomer(Customer customer);
}
