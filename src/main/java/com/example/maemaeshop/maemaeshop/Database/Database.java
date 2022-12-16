package com.example.maemaeshop.maemaeshop.Database;
import com.example.maemaeshop.maemaeshop.entities.Product;
import com.example.maemaeshop.maemaeshop.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {

    @Bean
    CommandLineRunner initDatabase (ProductRepository repository){
        return  new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Product productA=  new Product("aa","aa","aa",11,11,"aa");
                Product productB=  new Product("bb","bb","bb",22,22,"bb");
                repository.save(productA);
                repository.save(productB);
            }
        };
    }


}
