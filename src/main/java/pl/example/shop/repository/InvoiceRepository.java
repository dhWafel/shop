package pl.example.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.example.shop.domain.Invoice;


public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Invoice findByOrderNumber(String orderNumber);

    Page<Invoice> findByUserId(Long id, Pageable pageable);
}
