package nl.novi.pizzeria_webAPI.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //Invoice is niet de eigenaar, is de target
    @OneToOne(mappedBy="invoice")
    private Order order;

    private InvoiceStatus invoiceStatus;

}
