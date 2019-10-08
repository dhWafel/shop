package pl.example.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.example.shop.domain.FavoriteProduct;

import java.util.List;
import java.util.Optional;

public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Long> {

    Optional<FavoriteProduct> findByUserIdAndProductId(Long userId, Long productId);

    Page<FavoriteProduct> findByUserId(Long userId, Pageable pageable);

}
