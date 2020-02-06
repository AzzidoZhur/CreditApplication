package de.ing.easyfinancing.creditApplication.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CreditApplicationChannels {
	String CREDIT_APPLICATION_OUT = "creditApplicationOut";
	String CREDIT_APPLICATION_SCORING_POSITIVE = "scoringPositiveIn";
	String CREDIT_APPLICATION_SCORING_NEGATIVE = "scoringNegativeIn";
	String CREDIT_APPLICATION_CITYCHECK_POSITIVE = "cityCheckPositiveIn";
	String CREDIT_APPLICATION_CITYCHECK_NEGATIVE = "cityCheckNegativeIn";
	
	@Input
	SubscribableChannel scoringNegativeIn();

	@Input
	SubscribableChannel scoringPositiveIn();

	@Output
	MessageChannel creditApplicationOut();
	
	
	@Input                
	SubscribableChannel cityCheckNegativeIn();
	
	@Input
	SubscribableChannel cityCheckPositiveIn();
}
