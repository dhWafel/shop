package pl.example.shop.batch;


import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import pl.example.shop.domain.Product;

@Component
public class ReaderImpl {

    public ItemReader<Product> itemReader(String filePath){
        return new FlatFileItemReaderBuilder<Product>()
                .name("ProductReader")
                .linesToSkip(1)
                .resource(new FileSystemResource(filePath))
                .delimited()
                .names(new String[]{
                        "name","dyscription","price","quantity"
                })
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Product>(){{setTargetType(Product.class);}})
                .build();
    }

}
