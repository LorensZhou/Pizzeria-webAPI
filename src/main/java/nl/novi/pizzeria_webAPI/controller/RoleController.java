package nl.novi.pizzeria_webAPI.controller;

import nl.novi.pizzeria_webAPI.dto.RoleDto;
import nl.novi.pizzeria_webAPI.model.Role;
import nl.novi.pizzeria_webAPI.repository.RoleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoleController {

    private final RoleRepository roleRepos;

    public RoleController(RoleRepository roleRepos) {
        this.roleRepos = roleRepos;
    }
    @GetMapping("/roles")
    public List<RoleDto> getRoles() {
        List<RoleDto> roleDtos = new ArrayList<>();
        for (Role r : roleRepos.findAll()) {
            RoleDto roleDto = new RoleDto();
            roleDto.rolename = r.getRolename();
            roleDtos.add(roleDto);
        }
        return roleDtos;
    }
}

