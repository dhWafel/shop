package pl.example.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.example.shop.domain.Basket;

import java.util.List;


public interface BasketRepository extends JpaRepository<Basket, Long> {


    List<Basket> findByUserId (Long id);

    List<Basket> findByUserEmail(String email);

    Page<Basket> findByUserEmail(String email, Pageable page);
}
