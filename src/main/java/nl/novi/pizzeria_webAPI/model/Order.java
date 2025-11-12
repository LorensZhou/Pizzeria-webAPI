package nl.novi.pizzeria_webAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    private int customerNum;
    private int employeeNum;
    private int menuItemNum;
    private double orderAmount;

    //zorgt ervoor dat paymenttype geschreven wordt door JPA als ("VISA", "MASTERCARD", enz.)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public Order(int customerNum, int employeeNum, PaymentType paymentType, PaymentStatus paymentStatus, OrderStatus orderStatus) {
        this.customerNum = customerNum;
        this.employeeNum = employeeNum;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
    }

    public Order(int customerNum, int employeeNum, int menuItemNum, double orderAmount) {
        this.customerNum = customerNum;
        this.employeeNum = employeeNum;
        this.orderAmount = orderAmount;
        this.menuItemNum = menuItemNum;
        this.paymentType = PaymentType.CASH;
        this.paymentStatus = PaymentStatus.PAID;
        this.orderStatus = OrderStatus.CREATED;
    }
}



