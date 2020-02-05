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
	private final CreditApplicationScoringDispatcher creditApplicationScoringDispatcher;
    private final CreditApplicationRepository creditApplicationRepository;
    
	public CreditApplicationEventListener(CreditApplicationScoringDispatcher creditApplicationScoringDispatcher, CreditApplicationRepository creditApplicationRepository) {
		this.creditApplicationScoringDispatcher = creditApplicationScoringDispatcher;
		this.creditApplicationRepository = creditApplicationRepository;
	}

	@StreamListener(CreditApplicationScoringDispatcher.CREDIT_APPLICATION_SCORING_NEGATIVE)
	public void receiveCreditApplicationScoringNegative(
			@Payload ScoringDoneEvent scoringDoneEvent) {
		
		updateScoringState(scoringDoneEvent, "failed");

	}

	@StreamListener(CreditApplicationScoringDispatcher.CREDIT_APPLICATION_SCORING_POSITIVE)
	public void receiveCreditApplicationScoringPositive(
			@Payload ScoringDoneEvent scoringDoneEvent) {
		
		updateScoringState(scoringDoneEvent, "OK");
	}

	private void updateScoringState(ScoringDoneEvent scoringDoneEvent, String newState) {
		CreditApplication creditApplication = creditApplicationRepository.findById(scoringDoneEvent.getCreditApplicationId())
				.orElseThrow(() -> new RuntimeException("Not found"));
		
		creditApplication.setScoringState(newState);
	}
}