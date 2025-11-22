package nl.novi.pizzeria_webAPI.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(mappedBy = "employee")
    private Set<Order> orders;

    @Column(length=128)
    private String name;
    @Column(length=128)
    private String lastname;

}
