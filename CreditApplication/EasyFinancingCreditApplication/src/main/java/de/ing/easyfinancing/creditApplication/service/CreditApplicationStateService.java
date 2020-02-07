package de.ing.easyfinancing.creditApplication.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import de.ing.easyfinancing.creditApplication.events.CreditApplicationChannels;
import de.ing.easyfinancing.creditApplication.events.CreditApplicationEnteredEvent;
import de.ing.easyfinancing.creditApplication.model.CreditApplication;
import de.ing.easyfinancing.creditApplication.repositories.CreditApplicationRepository;

@Service
public class CreditApplicationStateService {

	private final CreditApplicationRepository creditApplicationRepository;
	private final CreditApplicationChannels creditApplicationChannels;

	public CreditApplicationStateService(CreditApplicationRepository creditApplicationRepository, 
			CreditApplicationChannels creditApplicationChannels) {
		this.creditApplicationRepository = creditApplicationRepository;
		this.creditApplicationChannels = creditApplicationChannels;
	}

	public void processScoringNegative(String id) {
		
		CreditApplication creditApplication = retrieveCreditApplication(id);
		creditApplication.setScoringState("failed");
		creditApplication.setApplicationState("abgelehnt");
		creditApplication.setCityCheckState("---");
	}

	public void processScoringPositive(String id) {
		
		CreditApplication creditApplication = retrieveCreditApplication(id);
		if (creditApplication.getApplicationState().equals("abgelehnt"))
			return;
		creditApplication.setScoringState("OK");
		if (creditApplication.getCityCheckState().equals("OK"))
			creditApplication.setApplicationState("genehmigt");
			createApprovedEvent(creditApplication);
	}

	public void processCityCheckPositive(String id) {
		
		CreditApplication creditApplication = retrieveCreditApplication(id);
		if (creditApplication.getApplicationState().equals("abgelehnt"))
			return;
		creditApplication.setCityCheckState("OK");
		if (creditApplication.getScoringState().equals("OK"))
			creditApplication.setApplicationState("genehmigt");
			createApprovedEvent(creditApplication);
	}

	public void processCityCheckNegative(String id) {
		
		CreditApplication creditApplication = retrieveCreditApplication(id);
		creditApplication.setCityCheckState("failed");
		creditApplication.setApplicationState("abgelehnt");
		creditApplication.setScoringState("---");
	}
	
	private CreditApplication retrieveCreditApplication(String id) {
		CreditApplication creditApplication = creditApplicationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Not found"));
		return creditApplication;
	}
	
	private void createApprovedEvent(CreditApplication creditApplication) {
		CreditApplicationEnteredEvent result = CreditApplicationEnteredEvent.builder()
			.creditApplication(creditApplication).build();
		Message<CreditApplicationEnteredEvent> message = MessageBuilder.withPayload(result).build();
		creditApplicationChannels.creditApplicationOutApproved().send(message);
	}

}
