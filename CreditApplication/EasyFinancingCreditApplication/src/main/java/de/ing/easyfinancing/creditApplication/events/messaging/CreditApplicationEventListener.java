package de.ing.easyfinancing.creditApplication.events.messaging;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import de.ing.easyfinancing.creditApplication.events.CreditApplicationScoringDispatcher;
import de.ing.easyfinancing.creditApplication.events.ScoringDoneEvent;
import de.ing.easyfinancing.creditApplication.model.CreditApplication;
import de.ing.easyfinancing.creditApplication.repositories.CreditApplicationRepository;

@Component
@Transactional
public class CreditApplicationEventListener {

	private final CreditApplicationRepository creditApplicationRepository;
	private final CreditApplicationScoringDispatcher creditApplicationScoringDispatcher;
	

	public CreditApplicationEventListener(final CreditApplicationScoringDispatcher creditApplicationScoringDispatcher,
			CreditApplicationRepository creditApplicationRepository) {
		this.creditApplicationScoringDispatcher = creditApplicationScoringDispatcher;
		this.creditApplicationRepository = creditApplicationRepository;
	}

	@StreamListener(CreditApplicationScoringDispatcher.CREDIT_APPLICATION_SCORING_NEGATIVE)
	public void receiveCreditApplicationScoringNegative(@Payload ScoringDoneEvent scoringDoneEvent) {

		CreditApplication creditApplication = creditApplicationRepository
				.findById(scoringDoneEvent.getCreditApplicationId())
				.orElseThrow(() -> new RuntimeException("Not found"));

		creditApplication.setScoringState("failed");

		creditApplication.setApplicationState("abgelehnt");

	}

	@StreamListener(CreditApplicationScoringDispatcher.CREDIT_APPLICATION_SCORING_POSITIVE)
	public void receiveCreditApplicationScoringPositive(@Payload ScoringDoneEvent scoringDoneEvent) {

		CreditApplication creditApplication = creditApplicationRepository
				.findById(scoringDoneEvent.getCreditApplicationId())
				.orElseThrow(() -> new RuntimeException("Not found"));
		if (creditApplication.getApplicationState().equals("abgelehnt"))

			return;

		creditApplication.setScoringState("OK");

		if (creditApplication.getCityCheckState().equals("OK"))

			creditApplication.setApplicationState("genehmigt");

	}

}