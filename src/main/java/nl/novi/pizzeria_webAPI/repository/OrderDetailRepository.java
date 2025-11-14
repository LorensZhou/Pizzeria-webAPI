package nl.novi.pizzeria_webAPI.repository;

import nl.novi.pizzeria_webAPI.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
