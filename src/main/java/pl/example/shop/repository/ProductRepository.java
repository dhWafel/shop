package pl.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.example.shop.domain.Product;

public interface ProductRepository extends JpaRepository <Product, Long>{
}
