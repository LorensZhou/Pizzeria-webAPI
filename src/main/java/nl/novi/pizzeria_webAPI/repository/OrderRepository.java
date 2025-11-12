package nl.novi.pizzeria_webAPI.repository;

import nl.novi.pizzeria_webAPI.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
