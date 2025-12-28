package nl.novi.pizzeria_webAPI.repository;

import nl.novi.pizzeria_webAPI.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, String> {
    boolean existsByNameAndLastname(String name, String lastname);
    Optional<Profile>findByNameAndLastname(String name, String lastname);

}
