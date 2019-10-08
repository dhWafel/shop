package pl.example.shop.batch;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import pl.example.shop.domain.Product;

@Component
public class ProcesorImpl implements ItemProcessor<Product, Product> {
    @Override
    public Product process(Product product) throws Exception {
        return product;
    }
}
