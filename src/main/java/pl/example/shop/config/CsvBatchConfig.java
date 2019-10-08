package pl.example.shop.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import pl.example.shop.batch.JobCompletionListener;
import pl.example.shop.batch.ProcesorImpl;
import pl.example.shop.batch.ReaderImpl;
import pl.example.shop.batch.WriterImlp;
import pl.example.shop.domain.Product;


@Configuration
@EnableBatchProcessing
public class CsvBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;        //stworzenie job ktory twory bedzie przetwarzal plik

    @Autowired
    private StepBuilderFactory stepBuilderFactory;      //tworzy kroki, jakie beda wykonywane podczas 1 liniki pliku

    @Autowired
    private ReaderImpl reader;

    @Autowired
    private ProcesorImpl procesor;

    @Autowired
    private WriterImlp writer;

    public Job fileToDateBaseJob(JobCompletionListener jobCompletionListener, String filePath){     //po zakonczeniu joba wypisane zostaje ze job sie zakonczyl
        return jobBuilderFactory.get("FileProcesor")
                .incrementer(new RunIdIncrementer())        //inkrementuje iD lini
                .listener(jobCompletionListener)
                .flow(step(filePath))                       //wywolanie funkcji deklaracja krokow
                .end()
                .build();
    }

    public Step step(String filePath){              //funkcja ktora tworzy wywolywanie krokow
        return stepBuilderFactory.get("FileProcesor")
                .<Product, Product>chunk(100)   //pierwszy product format linijki w pliku csv a drugi to do jakiego obiektu zostanie skastowana linika pliku csv chunkSize to lile linijek bedzie jednoczesnie zaczytane z pliku
                .reader(reader.itemReader(filePath))
                .processor(procesor)
                .writer(writer)
                .taskExecutor(taskExecutor())
                .build();
    }

    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor =new SimpleAsyncTaskExecutor("FileExecutor");
        simpleAsyncTaskExecutor.setConcurrencyLimit(2);     //limit watkow na 2
        return simpleAsyncTaskExecutor;
    }

}
