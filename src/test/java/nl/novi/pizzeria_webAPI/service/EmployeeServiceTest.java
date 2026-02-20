package nl.novi.pizzeria_webAPI.service;

import nl.novi.pizzeria_webAPI.dto.EmployeeInputDto;
import nl.novi.pizzeria_webAPI.dto.EmployeeOutputDto;
import nl.novi.pizzeria_webAPI.exception.InvalidDeletionException;
import nl.novi.pizzeria_webAPI.exception.InvalidReplaceException;
import nl.novi.pizzeria_webAPI.exception.ResourceNotFoundException;
import nl.novi.pizzeria_webAPI.model.Employee;
import nl.novi.pizzeria_webAPI.repository.EmployeeRepository;
import nl.novi.pizzeria_webAPI.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    Employee employee;

    @BeforeEach
    void setUp() {
        this.employee = new Employee("Lorens", "Zhou");
        this.employee.setId(123);
    }

    @Mock
    EmployeeRepository employeeRepos;

    @Mock
    OrderRepository orderRepos;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    @DisplayName("Should return a new employee")
    public void test1() {
        //test voor employeeService.createEmployee()
        //arrange
        EmployeeInputDto employeeInputDto = new EmployeeInputDto();
        employeeInputDto.name = "Lisa";
        employeeInputDto.lastname = "Loonhout";
        employeeInputDto.id = 1;

        this.employee.setName(employeeInputDto.name);
        this.employee.setLastname(employeeInputDto.lastname);
        this.employee.setId(employeeInputDto.id);


        // mocken van save() van de repository
        Mockito.when(employeeRepos.save(Mockito.any(Employee.class))).thenReturn(this.employee);

        //act
        EmployeeOutputDto employeeOutputDto = employeeService.createEmployee(employeeInputDto);

        //assert
        assertNotNull(employeeOutputDto);
        assertEquals("Lisa", employeeOutputDto.name);
        assertEquals("Loonhout", employeeOutputDto.lastname);
        assertEquals(1, employeeOutputDto.id);

        //verifieren of de repository wordt aangeroepen
        Mockito.verify(employeeRepos, Mockito.times(1)).save(Mockito.any(Employee.class));
    }

    @Test
    @DisplayName("should replace employee when employee does not exists in any order")
    public void test2(){
        //employeeService.replaceEmployee(int id, EmployeeInputDto employeeInDto)
        //arrange
        //the id van this.employee = 123 en we willen this.employee updaten
        int employeeId = 123;
        EmployeeInputDto employeeInputDto = new EmployeeInputDto();
        employeeInputDto.name = "Lisa";
        employeeInputDto.lastname = "Loonhout";

        //Mock: vind de bestaande employee
        Mockito.when(employeeRepos.findById(employeeId)).thenReturn(Optional.of(this.employee));

        //Mock: checken dat het niet in een order zit
        Mockito.when(orderRepos.existsByEmployee(this.employee)).thenReturn(false);

        //Mock: de save actie (retourneert de updated item)
        Mockito.when(employeeRepos.save(Mockito.any(Employee.class))).thenAnswer(i->i.getArguments()[0]);

        //act
        EmployeeOutputDto employeeOutputDto = employeeService.replaceEmployee(employeeId, employeeInputDto);

        //assert
        assertEquals("Lisa", employeeOutputDto.name);
        assertEquals("Loonhout", employeeOutputDto.lastname);
        Mockito.verify(employeeRepos, Mockito.times(1)).save(this.employee);
    }

    @Test
    @DisplayName("should throw InvalidReplaceException when employee is part of an existing order")
    public void test3(){
        //employeeService.replaceEmployee(int id, EmployeeInputDto employeeInDto) unhappy flow
        //arrange
        //the id van this.employee = 123 en we willen this.employee updaten
        int employeeId = 123;
        EmployeeInputDto employeeInputDto = new EmployeeInputDto();
        employeeInputDto.name = "Lisa";
        employeeInputDto.lastname = "Loonhout";

        //Mock: vind de bestaande employee
        Mockito.when(employeeRepos.findById(employeeId)).thenReturn(Optional.of(this.employee));

        //Mock: checken dat het in een order zit
        Mockito.when(orderRepos.existsByEmployee(this.employee)).thenReturn(true);

        //act and assert
        //verifieren dat de service een correct exception gooit
        assertThrows(InvalidReplaceException.class, ()->employeeService.replaceEmployee(employeeId, employeeInputDto));

        //verifieren
        //verzekeren dat de save() operatie nooit wordt aangeroepen omdat de exception het proces stop
        Mockito.verify(employeeRepos, Mockito.never()).save(Mockito.any(Employee.class));
    }

    @Test
    @DisplayName("Should return a list of employees")
    public void test4(){
        //employeeService.getAllEmployees()
        //arrange
        Employee employee1 = new Employee("Anne", "Bakker");
        employee1.setId(1);
        Employee employee2 = new Employee("Jeanette", "Wijnveld");
        employee1.setId(2);

        List<Employee> employees = List.of(employee1, employee2);

        //Mock: de repository van employee mocken om lijst van employees te retourneren
        Mockito.when(employeeRepos.findAll()).thenReturn(employees);

        //act
        List<EmployeeOutputDto> employeeOutputDtos = employeeService.getAllEmployees();

        //assert
        assertNotNull(employeeOutputDtos);
        assertEquals(2, employeeOutputDtos.size());

        assertEquals("Anne", employeeOutputDtos.get(0).name);
        assertEquals("Bakker", employeeOutputDtos.get(0).lastname);

        assertEquals("Jeanette", employeeOutputDtos.get(1).name);
        assertEquals("Wijnveld", employeeOutputDtos.get(1).lastname);

        //verify
        Mockito.verify(employeeRepos, Mockito.times(1)).findAll();
    }


    @Test
    @DisplayName("Should delete an existing employee")
    public void test5() {
        //employeeService.deleteEmployee(int id)
        //arrange
        int employeeId = this.employee.getId();
        //Mock de existsById voor een return waarde van true zodat de employeeservice geen ResourceNotFoundException gooit
        Mockito.when(employeeRepos.existsById(employeeId)).thenReturn(true);

        //act
        employeeService.deleteEmployee(employeeId);

        //assert
        //omdat de methode void is, verifieren we dat de repository alleen een keer wordt aan geroepen met juiste id
        Mockito.verify(employeeRepos, Mockito.times(1)).deleteById(employeeId);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when employee does not exist when delete")
    public void test6() {
        //test voor employeeService.deleteEmployee() unhappy flow
        //arrange
        int employeeId = 999;
        Mockito.when(employeeRepos.existsById(employeeId)).thenReturn(false);
        //act and assert, verwacht dat de service een exception gaat gooien
        assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployee(employeeId));

        //verifieren dat de deleteById werd niet aangeroepen omdat de check heeft gefaald
        Mockito.verify(employeeRepos, Mockito.never()).deleteById(anyInt());
    }

    @Test
    @DisplayName("Should throw InvalidDeletionException when DB throws DataIntegrityViolation when trying to delete an employee that is in an order")
    public void test7() {
        //test voor employeeService.deleteEmployee() unhappy flow
        //arrange
        int employeeId = this.employee.getId();

        //Mock de existsById voor een return waarde van true zodat de employeeservice geen ResourceNotFoundException gooit
        Mockito.when(employeeRepos.existsById(employeeId)).thenReturn(true);

        //Mock: (simulatie) vertel de repository om een integrity exception te gooien wanneer deleteById wordt aangeroepen
        //Wij gebruiken doThrow omdat deleteById is een void methode
        Mockito.doThrow(new DataIntegrityViolationException("Foreign key constraint"))
                .when(employeeRepos).deleteById(employeeId);

        //act and assert
        //verifieren dat de service de repos exception opvangt en gooit de custom InvalidDeletionException
        assertThrows(InvalidDeletionException.class, () -> employeeService.deleteEmployee(employeeId));

        //verifieren
        //checken dat deleteById is aangeroepen door itemService.deleteItem()
        Mockito.verify(employeeRepos, Mockito.times(1)).deleteById(employeeId);
    }

}