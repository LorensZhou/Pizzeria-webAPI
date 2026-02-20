package nl.novi.pizzeria_webAPI.repository;

import nl.novi.pizzeria_webAPI.model.Customer;
import nl.novi.pizzeria_webAPI.model.Employee;
import nl.novi.pizzeria_webAPI.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    //check doen of de employee al bestaat in een order, dan mogen wij de employee naam niet aanpassen
    boolean existsByEmployee(Employee employee);

    //check doen of de customer al bestaat in een order, dan mogen wij de customer naam niet aanpassen
    boolean existsByCustomer(Customer customer);

    //vind alle orders van de customer id
    List<Order>findAllByCustomer(Customer customer);
}
