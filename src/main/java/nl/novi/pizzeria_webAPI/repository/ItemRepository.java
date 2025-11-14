package nl.novi.pizzeria_webAPI.repository;

import nl.novi.pizzeria_webAPI.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer>{

}
