package nl.novi.pizzeria_webAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    //de target van de relatie met Profile, cascadeType.ALL zorgt ervoor als de user (parent) wordt verwijderd ook de profile wordt verwijderd
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

}