package de.ing.easyfinancing.creditApplication.web;

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
import de.ing.easyfinancing.creditApplication.repositories.CreditApplicationRepository;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CreditApplicationController {

	private final CreditApplicationScoringDispatcher creditApplicationScoringDispatcher;
	private final CreditApplicationRepository repository;

	public CreditApplicationController(CreditApplicationScoringDispatcher creditApplicationScoringDispatcher,
			final CreditApplicationRepository repository) {
		this.creditApplicationScoringDispatcher = creditApplicationScoringDispatcher;
		this.repository = repository;
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
		CreditApplicationEnteredEvent result = CreditApplicationEnteredEvent.builder()
				.creditApplication(creditApplication).build();

		repository.save(creditApplication);

		Message<CreditApplicationEnteredEvent> message = MessageBuilder.withPayload(result).build();
		creditApplicationScoringDispatcher.creditApplicationOut().send(message);
		model.addAttribute("creditApplications", creditApplication);
		return "StatusCreditApplication";
	}

	@GetMapping("/CreditApplicationOverview")
	public String creditApplicationOverview(Model model) {
		List<CreditApplication> creditApplications = repository.findAllAsList();

		model.addAttribute("creditApplications", creditApplications);

		return "CreditApplicationOverview";
	}

	@GetMapping("/StatusDetail/{id}")
	public String statusDetails(@PathVariable String id, Model model) {
		CreditApplication creditApplication = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Not found"));

		model.addAttribute("creditApplication", creditApplication);
		return "StatusCreditApplication";

	}

	@GetMapping("/StatusUpdate/{id}")
	public String statusUpdate(@PathVariable String id, Model model) {
		
		System.out.println("ID before .... " + id);
		CreditApplication creditApplication = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Not found"));
		
		System.out.println("ID after .... " + creditApplication.getCreditApplicationId());

		model.addAttribute("creditApplication", creditApplication);
		return "StatusCreditApplication";

	}
	
	
}
