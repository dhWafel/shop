package pl.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.example.shop.domain.FavoriteProduct;
import pl.example.shop.service.FavoriteProductService;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/favorite-product")
public class FavoriteProductController {

    @Autowired
    private FavoriteProductService favoriteProductService;

    @PostMapping("/{productId}")
    public FavoriteProduct addFavoriteProduct(@PathVariable Long productId){
        return favoriteProductService.addFavoriteProducts(productId);
    }

    @GetMapping
    public Page<FavoriteProduct> showFavoriteProductList(@RequestParam Integer page, @RequestParam Integer size){
        return favoriteProductService.showFavoriteProductList(PageRequest.of(page, size));
    }

    @DeleteMapping("/{favoriteProductId}")
    public void deleteFavoriteProduct(@PathVariable Long favoriteProductId){
        favoriteProductService.deleteFavoriteProduct(favoriteProductId);
    }
}
