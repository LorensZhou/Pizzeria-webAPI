package nl.novi.pizzeria_webAPI.repository;

import nl.novi.pizzeria_webAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
