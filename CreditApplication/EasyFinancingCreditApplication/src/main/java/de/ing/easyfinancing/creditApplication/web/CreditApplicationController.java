package de.ing.easyfinancing.creditApplication.web;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import de.ing.easyfinancing.creditApplication.events.CreditApplicationEnteredEvent;
import de.ing.easyfinancing.creditApplication.events.CreditApplicationScoringDispatcher;
import de.ing.easyfinancing.creditApplication.model.CreditApplication;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CreditApplicationController {
	
	private final CreditApplicationScoringDispatcher creditApplicationScoringDispatcher;
	
	public CreditApplicationController(CreditApplicationScoringDispatcher creditApplicationScoringDispatcher) {
		this.creditApplicationScoringDispatcher = creditApplicationScoringDispatcher;
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/CreditApplicationCreator")
	public String creditApplicationCreator(Model model) {
		model.addAttribute("gruss", "Hallo Welt");
		model.addAttribute("creditApplication", new CreditApplication());
		return "CreditApplicationCreator";
	}
	@PostMapping("/SaveCreditApplication")
	public String saveCreditApplication(@Valid @ModelAttribute("creditApplication") CreditApplication creditApplication,
			BindingResult errors, Model model) {
		CreditApplicationEnteredEvent result = CreditApplicationEnteredEvent
				.builder()
				.creditApplication(creditApplication)
				.build();
		
		Message<CreditApplicationEnteredEvent> message = MessageBuilder.withPayload(result).build();
		creditApplicationScoringDispatcher.creditApplicationOut().send(message); 
		
		return "StatusCreditApplication";
	}
	
	@GetMapping("/CreditApplicationOverview")
	public String creditApplicationOverview( Model model) {
		List<CreditApplication> creditApplications = Arrays.asList(
				
				CreditApplication
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
				.build(),

				CreditApplication
				.builder()
				.applicationDate(LocalDateTime.now())
				.applicationState("Dummy")
				.firstName("Max")
				.city("Köln")
				.monthlyIncome(1000)
				.monthlyExpenditure(200)
				.creditApplicationId("23456")
				.creditSum(20000)
				.lastName("Mustermann")
				.build(),
				
				CreditApplication
				.builder()
				.applicationDate(LocalDateTime.now())
				.applicationState("Dummy")
				.firstName("Max")
				.city("Köln")
				.monthlyIncome(1000)
				.monthlyExpenditure(200)
				.creditApplicationId("9876")
				.creditSum(1000)
				.lastName("Doe")
				.build()
				
		);
			
	model.addAttribute("creditApplications", creditApplications);
		

		return "CreditApplicationOverview";
	}
	
	@GetMapping("/StatusDetail/{id}")
	public String statusDetails(@PathVariable String id, Model model) {
		CreditApplication creditApplication = CreditApplication
		.builder()
		.applicationDate(LocalDateTime.now())
		.applicationState("Dummy")
		.firstName("Max")
		.city("Köln")
		.monthlyIncome(1000)
		.monthlyExpenditure(200)
		.creditApplicationId("9876")
		.creditSum(1000)
		.lastName("Doe")
		.build();
		
		model.addAttribute("creditApplication", creditApplication);
		return "StatusCreditApplication"; 
		
	}
	
	

}
