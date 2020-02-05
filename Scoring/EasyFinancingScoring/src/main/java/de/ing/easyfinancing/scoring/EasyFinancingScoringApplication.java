package de.ing.easyfinancing.scoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import de.ing.easyfinancing.scoring.events.CreditApplicationScoringDispatcher;

@SpringBootApplication
@EnableBinding(CreditApplicationScoringDispatcher.class)
public class EasyFinancingScoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyFinancingScoringApplication.class, args);
	}

}
