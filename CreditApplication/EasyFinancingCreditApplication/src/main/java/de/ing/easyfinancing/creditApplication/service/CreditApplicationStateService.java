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

		System.out.println("processScoringNegative");
		CreditApplication creditApplication = creditApplicationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Not found"));

		creditApplication.setScoringState("failed");

		creditApplication.setApplicationState("abgelehnt");
		creditApplication.setCityCheckState("---");
	}

	public void processScoringPositive(String id) {
		System.out.println("processScoringPositive");
		CreditApplication creditApplication = creditApplicationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Not found"));
		if (creditApplication.getApplicationState().equals("abgelehnt"))

			return;

		creditApplication.setScoringState("OK");

		if (creditApplication.getCityCheckState().equals("OK"))

			creditApplication.setApplicationState("genehmigt");
	}

	public void processCityCheckPositive(String id) {
		System.out.println("processCityCheckPositive");
		CreditApplication creditApplication = creditApplicationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Not found"));

		if (creditApplication.getApplicationState().equals("abgelehnt"))

			return;

		creditApplication.setCityCheckState("OK");

		if (creditApplication.getScoringState().equals("OK"))
			creditApplication.setApplicationState("genehmigt");

	}

	public void processCityCheckNegative(String id) {
		System.out.println("processCityCheckNegative");
		CreditApplication creditApplication = creditApplicationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Not found"));

		creditApplication.setCityCheckState("failed");

		creditApplication.setApplicationState("abgelehnt");
		creditApplication.setScoringState("---");

	}

}
