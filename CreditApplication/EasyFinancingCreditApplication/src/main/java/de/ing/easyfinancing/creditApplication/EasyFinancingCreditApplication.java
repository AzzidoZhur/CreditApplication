package de.ing.easyfinancing.creditApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import de.ing.easyfinancing.creditApplication.events.CreditApplicationScoringDispatcher;
// Aenderung
@SpringBootApplication
@EnableBinding(CreditApplicationScoringDispatcher.class)
public class EasyFinancingCreditApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyFinancingCreditApplication.class, args);
	}

}
