package nl.novi.pizzeria_webAPI.repository;

import nl.novi.pizzeria_webAPI.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
