package nl.novi.pizzeria_webAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "customers", uniqueConstraints = {
        @UniqueConstraint(name = "uq_customer_NameAndLastname", columnNames = {"name", "lastname"})
         }
)
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String lastname;

    //deze creeert een kollom 'profile_username' in de customer tabel
    //als customer geen profiel heeft wordt de username op de waarde null gezet
    //nullable is op true gezet, want het kan voorkomen dat een customer geen profiel heeft
    @OneToOne(optional = true)
    @JoinColumn(name= "profile_username", referencedColumnName = "username", nullable = true)
    private Profile profile;

    @OneToMany(mappedBy="customer")
    private Set<Order> orders;

}
