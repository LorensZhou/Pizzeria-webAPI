package nl.novi.pizzeria_webAPI.repository;

import nl.novi.pizzeria_webAPI.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
