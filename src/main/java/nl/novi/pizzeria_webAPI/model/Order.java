package nl.novi.pizzeria_webAPI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
        this.menuItemNum = menuItemNum;
        this.orderAmount = orderAmount;
        this.paymentType = PaymentType.CASH; //hardcode assign
        this.paymentStatus = PaymentStatus.TOPAY;
        this.orderStatus = OrderStatus.CREATED;
    }


    //insert into roles(rolename)
    //values ('ROLE_EMPLOYEE'), ('ROLE_CUSTOMER'), ('ROLE_CHEF');
}



