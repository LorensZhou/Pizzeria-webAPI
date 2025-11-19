package nl.novi.pizzeria_webAPI.mapper;

import nl.novi.pizzeria_webAPI.dto.EmployeeInputDto;
import nl.novi.pizzeria_webAPI.dto.EmployeeOutputDto;
import nl.novi.pizzeria_webAPI.model.Employee;

public class EmployeeMapper {

    public Employee toEntity(EmployeeInputDto employeeInputDto) {
        Employee employee = new Employee();
        employee.setName(employeeInputDto.name);
        employee.setLastname(employeeInputDto.lastname);

        return employee;
    }

    public EmployeeOutputDto toDto(Employee employee){
        EmployeeOutputDto emplOutDto = new EmployeeOutputDto();
        emplOutDto.id = employee.getId();
        emplOutDto.name = employee.getName();
        emplOutDto.lastname = employee.getLastname();

        return emplOutDto;
    }
}
