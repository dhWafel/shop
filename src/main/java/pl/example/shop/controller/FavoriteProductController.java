package pl.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.example.shop.domain.FavoriteProduct;
import pl.example.shop.service.FavoriteProductService;

@RestController
@RequestMapping("/api/favorite-product")
public class FavoriteProductController {

    @Autowired
    private FavoriteProductService favoriteProductService;

    @PostMapping("/{userId}/{productId}")
    public FavoriteProduct addFavoriteProduct(@PathVariable Long userId, @PathVariable Long productId){
        return favoriteProductService.addFavoriteProducts(userId, productId);
    }

    @GetMapping("/{userId}")
    public Page<FavoriteProduct> showFavoriteProductList(@PathVariable Long userId, Pageable pageable){
        return favoriteProductService.showFavoriteProductList(userId, pageable);
    }

    @DeleteMapping("/{favoriteProductId}")
    public void deleteFavoriteProduct(@PathVariable Long favoriteProductId){
        favoriteProductService.deleteFavoriteProduct(favoriteProductId);
    }
}
