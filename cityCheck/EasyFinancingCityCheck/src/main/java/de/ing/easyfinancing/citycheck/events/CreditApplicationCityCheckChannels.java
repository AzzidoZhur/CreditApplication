package de.ing.easyfinancing.citycheck.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CreditApplicationCityCheckChannels {
	String CREDIT_APPLICATION_ENTERED = "creditApplicationEnteredIn";
	String CREDIT_APPLICATION_CITYCHECK_POSITIVE = "cityCheckPositiveOut";
	String CREDIT_APPLICATION_CITYCHECK_NEGATIVE = "cityCheckNegativeOut";

	@Output
	MessageChannel cityCheckNegativeOut();

	@Output
	MessageChannel cityCheckPositiveOut();

	@Input
	SubscribableChannel creditApplicationEnteredIn();
}
