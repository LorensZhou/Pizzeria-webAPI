package nl.novi.pizzeria_webAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(length=252)
    private String orderReference;

    //order is de owner side voor customer relatie, uni-directionele relatie
    //customer weet niets van order, terwijl order wel weet van de customer
    @ManyToOne
    @JoinColumn(name="customer_num", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name= "employee_num", nullable = false)
    private Employee employee;
    //cascade zorgt ervoor dat indien een order wordt verwijderd, de kinderen (orderdetail objecten) met het zelfde ordernummer ook worden verwijderd
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetail> orderDetails;

    //Order is de eigenaar van deze foreign key, het is een bi-directionele relatie
    //order weet van de invoice en op zijn buurt weet de invoice van de order
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "invoice_num", nullable = true)
    private Invoice invoice;

    private double orderAmount;
    @CreationTimestamp
    private LocalDate orderDate;

    //zorgt ervoor dat paymenttype geschreven wordt door JPA als ("VISA", "MASTERCARD", enz.)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}



