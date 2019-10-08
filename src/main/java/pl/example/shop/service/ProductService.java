package pl.example.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.example.shop.domain.Product;
import pl.example.shop.repository.ProductRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

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

    public Product update(Product product) throws Exception {
        return productRepository.findById(product.getId()).map(p -> {
            if(!p.getName().equals(product.getName())){
                p.setName(product.getName());
            }
            if(!p.getDyscription().equals(product.getDyscription())){
                p.setDyscription(product.getDyscription());
            }
            if(!p.getPrice().equals(product.getPrice())){
                p.setPrice(product.getPrice());
            }
            if(!p.getQuantity().equals(product.getQuantity())){
                p.setQuantity(product.getQuantity());
            }

            return productRepository.save(p);

        }).orElseThrow(()->new Exception("No data"));
    }

    public void upload(MultipartFile file) {

        //formulka do przetwarzania pliku (to w try)
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            productRepository.saveAll(bufferedReader.lines().skip(1)
                    .map(s -> s.split(","))
                    .map(p -> Product
                            .builder()
                            .name(p[0])
                            .dyscription(p[1])
                            .price(Double.valueOf(p[2]))
                            .quantity(Integer.valueOf(p[3]))
                            .build())
                    .collect(Collectors.toList()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
