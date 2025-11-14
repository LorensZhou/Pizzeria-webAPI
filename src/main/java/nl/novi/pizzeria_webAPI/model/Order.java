package nl.novi.pizzeria_webAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
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
    private int customerNum;
    private int employeeNum;
    //cascade zorgt ervoor dat indien een order wordt verwijderd, de kinderen (orderdetail objecten) met het zelfde ordernummer ook worden verwijderd
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetail> orderDetails;

    private double orderAmount;
    private Date orderDate;

    //zorgt ervoor dat paymenttype geschreven wordt door JPA als ("VISA", "MASTERCARD", enz.)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    //insert into roles(rolename)
    //values ('ROLE_EMPLOYEE'), ('ROLE_CUSTOMER'), ('ROLE_CHEF');
}



