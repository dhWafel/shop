package pl.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.example.shop.domain.Product;
import pl.example.shop.domain.rest.ExceptionsResponse;
import pl.example.shop.service.ProductService;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<Product> getListProducts() {
        return productService.getAllProduct();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PostMapping("/upload")
    public void upload(@RequestParam MultipartFile file){
        productService.upload(file);

    }

    @GetMapping("/{id}")
    public Product findProduct(@PathVariable Long id) throws Exception {
        return productService.findById(id);
    }

    @DeleteMapping
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping
    public Page<Product> productPage(Pageable pageable) {
        return productService.productPage(pageable);
    }

    @PutMapping
    public Product update(@RequestBody Product product) throws Exception {
        return productService.update(product);
    }


    @ExceptionHandler(RollbackException.class)
    public ResponseEntity<ExceptionsResponse> exceptionResponse(RollbackException excep) {

        Map<String, String> stringStringMap = new HashMap<>();

        ConstraintViolationException excep1 = (ConstraintViolationException) excep.getCause(); //exception ktory przechowywuje komunikaty dla innych zmiennych, [get.Cause - pobiera konkretny exceptions]

        excep1.getConstraintViolations().forEach(e -> {
            if (stringStringMap.containsKey(e.getPropertyPath().toString())) {
                stringStringMap.computeIfPresent(e.getPropertyPath().toString(), (k, v) -> v.concat(" ").concat(e.getMessage()));
            } else {
                stringStringMap.put(e.getPropertyPath().toString(), e.getMessage());
            }
        });

        return ResponseEntity.status(500).body(ExceptionsResponse.builder()
                .dateOccurse(LocalDateTime.now())
                .message(stringStringMap)
                .build());
    }
}
