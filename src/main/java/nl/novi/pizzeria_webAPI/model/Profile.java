package nl.novi.pizzeria_webAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profiles", uniqueConstraints = {
        @UniqueConstraint(name = "uq-profile_NameAndLastname", columnNames = {"name", "lastname"})
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Profile {
    @Id
    private String username;
    private String name;
    private String lastname;
    private String address;
    private String bankAccount;

    @OneToOne(mappedBy = "profile") // 'profile' is de naam van het veld in Customer.java
    private Customer customer;

}
