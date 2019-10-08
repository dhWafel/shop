package pl.example.shop.batch;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.example.shop.domain.Product;
import pl.example.shop.repository.ProductRepository;

import java.util.List;

@Component
public class WriterImlp implements ItemWriter<Product> {


    @Autowired
    private ProductRepository productRepository;

    @Override
    public void write(List<? extends Product> list) throws Exception {
                productRepository.saveAll(list);
    }
}
