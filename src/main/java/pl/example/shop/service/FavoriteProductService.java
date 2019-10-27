package pl.example.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.example.shop.domain.FavoriteProduct;
import pl.example.shop.domain.Product;
import pl.example.shop.domain.User;
import pl.example.shop.repository.FavoriteProductRepository;
import pl.example.shop.repository.ProductRepository;
import pl.example.shop.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@Service
public class FavoriteProductService {

    @Autowired
    private FavoriteProductRepository favoriteProductRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public FavoriteProduct addFavoriteProducts(Long productId) {

        String getEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        return favoriteProductRepository.findByUserEmailAndProductId(getEmail, productId)
                .orElseGet(() -> {
                    Optional<User> u = userRepository.findByEmail(getEmail);

                    if (u.isPresent()) {
                        Optional<Product> p = productRepository.findById(productId);
                        if (p.isPresent()) {
                            return favoriteProductRepository.save(FavoriteProduct.builder()
                                    .user(u.get())
                                    .product(p.get())
                                    .build());
                        } else throw new EntityNotFoundException("Product " + productId + "dosen't exist");
                    } else throw new EntityNotFoundException("User dosen't exist");
                });
    }

    public Page<FavoriteProduct> showFavoriteProductList(@PageableDefault Pageable pageable) {
        String getEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return favoriteProductRepository.findByUserEmail( getEmail, pageable);
    }

    public void deleteFavoriteProduct(Long favoriteProductId) {
        favoriteProductRepository.deleteById(favoriteProductId);
    }
}
