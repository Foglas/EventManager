package cz.uhk.fim.projekt.EventManager.config;

import cz.uhk.fim.projekt.EventManager.Domain.Category;
import cz.uhk.fim.projekt.EventManager.dao.CategoryRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class EventConfig {

    @Bean
    CommandLineRunner commandLineRunner(CategoryRepo categoryRepo){
        return args -> {
            Category jednicka = new Category("Malsdad", "jenad");
            //Category dvojka= new Category("asdapodu", "weqoi");
           // Category trojka = new Category("weqwy", "jsdfoh");

            categoryRepo.save(jednicka);
        };
    }
}
