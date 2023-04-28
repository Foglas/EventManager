package cz.uhk.fim.projekt.EventManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spustitelná třída
 */
@EnableJpaRepositories("cz.uhk.fim.projekt.EventManager.dao")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class EventManagerApplication {


	public static void main(String[] args) {
		SpringApplication.run(EventManagerApplication.class, args);
	}

}
