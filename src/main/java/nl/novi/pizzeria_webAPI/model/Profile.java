package nl.novi.pizzeria_webAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profiles", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueNameAndLastname", columnNames = {"name", "lastname"})
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
    //de owner van de relatie met User, de username is link tussen entiteit Profile en entiteit User, unilitarale relatie
    //@MapsId zorgt ervoor dat wanneer er een profile wordt gecreerd, er wordt gekeken of een user aanwezig is met dezelfde username
    //indien die user niet aanwezig is wordt er een fout melding gegeven. Zonder een user kan een profile niet worden aangemaakt
    @OneToOne
    @MapsId
    @JoinColumn(name = "username")
    private User user;
}
