package de.ing.easyfinancing.creditApplication.rest;

import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.ing.easyfinancing.creditApplication.events.CreditApplicationEnteredEvent;
import de.ing.easyfinancing.creditApplication.model.CreditApplication;

@RestController
@RequestMapping("/demo")
public class DummyRestController {
	
	@GetMapping(path="/event", produces = MediaType.APPLICATION_JSON_VALUE)
	public CreditApplicationEnteredEvent getApplication() {
		CreditApplication app =  CreditApplication
				.builder()
				.applicationDate(LocalDateTime.now())
				.applicationState("Dummy")
				.firstName("Max")
				.city("Köln")
				.monthlyIncome(1000)
				.monthlyExpenditure(200)
				.creditApplicationId("12345")
				.creditSum(3000)
				.lastName("Mustermann")
				.build();
		
		return CreditApplicationEnteredEvent.builder().creditApplication(app).build();
	}

}
