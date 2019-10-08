package pl.example.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.example.shop.service.FileService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaAuditing      //włącza listenery na klasy bazo-danowe
@EnableSwagger2
@EnableScheduling
public class ShopApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Autowired
	private FileService fileService;

	@Override
	public void run(String... args) throws Exception {

		new Thread(fileService,"fileService").start();	//uruchomienie nowego watku z metoda run() z klasy FileService

	}
}
