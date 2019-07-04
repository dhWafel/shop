package pl.example.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import pl.example.shop.domain.Product;
import pl.example.shop.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }

    public Product findById(Long id) throws Exception {
        return productRepository.findById(id).orElseThrow(() -> new Exception("Product is not found!!!"));
    }


    public Page<Product> productPage(@PageableDefault Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
