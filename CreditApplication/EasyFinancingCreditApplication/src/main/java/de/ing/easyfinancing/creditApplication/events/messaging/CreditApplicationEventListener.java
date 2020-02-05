package de.ing.easyfinancing.creditApplication.events.messaging;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import de.ing.easyfinancing.creditApplication.events.CreditApplicationScoringDispatcher;
import de.ing.easyfinancing.creditApplication.events.ScoringDoneEvent;

@Component
public class CreditApplicationEventListener {
	private final CreditApplicationScoringDispatcher creditApplicationScoringDispatcher;

	public CreditApplicationEventListener(CreditApplicationScoringDispatcher creditApplicationScoringDispatcher) {
		this.creditApplicationScoringDispatcher = creditApplicationScoringDispatcher;
	}

	@StreamListener(CreditApplicationScoringDispatcher.CREDIT_APPLICATION_SCORING_NEGATIVE)
	public void receiveCreditApplicationScoringNegative(
			@Payload ScoringDoneEvent scoringDoneEvent) {
		
		System.out.println("Scoring Negative");

	}

	@StreamListener(CreditApplicationScoringDispatcher.CREDIT_APPLICATION_SCORING_POSITIVE)
	public void receiveCreditApplicationScoringPositive(
			@Payload ScoringDoneEvent scoringDoneEvent) {
		
		System.out.println("Scoring Postive");
	}
}