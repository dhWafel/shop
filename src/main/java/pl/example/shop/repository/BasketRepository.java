package pl.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.example.shop.domain.Basket;

import java.util.List;


public interface BasketRepository extends JpaRepository<Basket, Long> {


    List<Basket> findByUserId (Long id);

}
