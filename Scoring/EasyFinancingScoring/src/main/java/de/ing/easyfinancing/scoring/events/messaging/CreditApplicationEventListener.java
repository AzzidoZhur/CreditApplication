package de.ing.easyfinancing.scoring.events.messaging;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import de.ing.easyfinancing.scoring.events.CreditApplicationEnteredEvent;
import de.ing.easyfinancing.scoring.events.CreditApplicationScoringChannels;
import de.ing.easyfinancing.scoring.events.ScoringDoneEvent;
import de.ing.easyfinancing.scoring.models.CreditApplication;

@Component
public class CreditApplicationEventListener {
	private final CreditApplicationScoringChannels creditApplicationScoringDispatcher;

	public CreditApplicationEventListener(CreditApplicationScoringChannels creditApplicationScoringDispatcher) {
		this.creditApplicationScoringDispatcher = creditApplicationScoringDispatcher;
	}
	
	
	@StreamListener(CreditApplicationScoringChannels.CREDIT_APPLICATION_ENTERED)
	public void receiveCreditApplicationEnteredIn(@Payload CreditApplicationEnteredEvent creditApplicationEnteredEvent) {
		CreditApplication creditApplication = creditApplicationEnteredEvent.getCreditApplication();
		
		System.out.println(creditApplication);
		
		ScoringDoneEvent result = ScoringDoneEvent.builder()
												.creditApplicationId(creditApplication.getCreditApplicationId()).build();
		Message<ScoringDoneEvent> message = MessageBuilder.withPayload(result).build();
		if (creditApplication.scoringProcess()) {
			creditApplicationScoringDispatcher.scoringPositiveOut().send(message);
		} else {
			creditApplicationScoringDispatcher.scoringNegativeOut().send(message);
		}
	}
}
