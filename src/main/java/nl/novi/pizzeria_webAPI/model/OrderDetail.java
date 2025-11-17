package nl.novi.pizzeria_webAPI.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orderDetails")
@Getter
@Setter
@NoArgsConstructor
//Lombok zal equals en hashCode genereren voor uniekheid van 'order' en 'item'
@EqualsAndHashCode(of = {"order", "item"})
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // The foreign key (order.id) voor de join met de item table bidirectionele relatie
    @ManyToOne
    @JoinColumn(name="orderId")
    private Order order;

    // The foreign key (item.id) voor de join met de item table unidirectionele relatie
    @ManyToOne
    @JoinColumn(name="itemId")
    private Item item;

    private int itemQuantity;
    private double itemPrice;

    public double getAmount() {
        return this.itemQuantity * this.itemPrice;
    }

}