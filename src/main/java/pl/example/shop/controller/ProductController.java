package pl.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.example.shop.domain.Product;
import pl.example.shop.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<Product> getListProducts(){
        return productService.getAllProduct();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @GetMapping("/{id}")
    public Product findProduct(@PathVariable Long id) throws Exception {
        return productService.findById(id);
    }

    @DeleteMapping
    public void deleteProduct(@PathVariable Long id){
        productService.deleteById(id);
    }

    @GetMapping
    public Page<Product> productPage(Pageable pageable){
        return productService.productPage(pageable);
    }
}
