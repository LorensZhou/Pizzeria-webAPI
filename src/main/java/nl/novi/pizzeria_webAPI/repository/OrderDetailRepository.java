package nl.novi.pizzeria_webAPI.repository;

import nl.novi.pizzeria_webAPI.model.Item;
import nl.novi.pizzeria_webAPI.model.Order;
import nl.novi.pizzeria_webAPI.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    //een eigen find declareren voor zoeken van orderdetail aan de hand van order en item
    Optional<OrderDetail> findByOrderAndItem(Order order, Item item);

}
