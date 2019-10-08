package pl.example.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.example.shop.domain.ClientOrder;

import java.util.List;

public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {

    Page<ClientOrder> findByUserId(Long id, Pageable pageable);

    List<ClientOrder> findByOrderNumber(String orderNumber);
}
