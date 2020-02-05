package de.ing.easyfinancing.scoring.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CreditApplicationScoringDispatcher {
	String CREDIT_APPLICATION_ENTERED = "creditApplicationEnteredIn";
	String CREDIT_APPLICATION_SCORING_POSITIVE = "scoringPositiveOut";
	String CREDIT_APPLICATION_SCORING_NEGATIVE = "scoringNegativeOut";

	@Output
	MessageChannel scoringNegativeOut();

	@Output
	MessageChannel scoringPositiveOut();

	@Input
	SubscribableChannel creditApplicationEnteredIn();
}
