package de.ing.easyfinancing.citycheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import de.ing.easyfinancing.citycheck.events.CreditApplicationCityCheckChannels;

@SpringBootApplication

@EnableBinding(CreditApplicationCityCheckChannels.class)
public class EasyFinancingCityCheckApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyFinancingCityCheckApplication.class, args);
	}

}
