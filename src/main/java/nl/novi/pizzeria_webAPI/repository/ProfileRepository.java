package nl.novi.pizzeria_webAPI.repository;

import nl.novi.pizzeria_webAPI.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, String> {

}
