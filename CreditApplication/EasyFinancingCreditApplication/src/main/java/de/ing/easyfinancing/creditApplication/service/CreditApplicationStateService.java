package de.ing.easyfinancing.creditApplication.service;

import org.springframework.stereotype.Service;

import de.ing.easyfinancing.creditApplication.model.CreditApplication;
import de.ing.easyfinancing.creditApplication.repositories.CreditApplicationRepository;

@Service
public class CreditApplicationStateService {

	private final CreditApplicationRepository creditApplicationRepository;

	public CreditApplicationStateService(CreditApplicationRepository creditApplicationRepository) {
		this.creditApplicationRepository = creditApplicationRepository;
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
	}

	public void processCityCheckPositive(String id) {
		
		CreditApplication creditApplication = retrieveCreditApplication(id);
		if (creditApplication.getApplicationState().equals("abgelehnt"))
			return;
		creditApplication.setCityCheckState("OK");
		if (creditApplication.getScoringState().equals("OK"))
			creditApplication.setApplicationState("genehmigt");

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

}
