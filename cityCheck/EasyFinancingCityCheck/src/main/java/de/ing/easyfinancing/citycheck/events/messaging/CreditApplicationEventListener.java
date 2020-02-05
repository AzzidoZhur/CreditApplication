package de.ing.easyfinancing.citycheck.events.messaging;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import de.ing.easyfinancing.citycheck.events.CityCheckDoneEvent;
import de.ing.easyfinancing.citycheck.events.CityCheckEnteredEventIn;
import de.ing.easyfinancing.citycheck.events.CreditApplicationCityCheckChannels;
import de.ing.easyfinancing.citycheck.model.CreditApplication;

@Component
public class CreditApplicationEventListener {
	private final CreditApplicationCityCheckChannels creditApplicationCityCheckChannels;

	public CreditApplicationEventListener(CreditApplicationCityCheckChannels creditApplicationCityChannels) {
		this.creditApplicationCityCheckChannels = creditApplicationCityChannels;
	}

	@StreamListener(CreditApplicationCityCheckChannels.CREDIT_APPLICATION_ENTERED)
	public void receiveCreditApplicationEnteredIn(@Payload CityCheckEnteredEventIn creditCheckEnteredEventIn) {
		CreditApplication creditApplication = creditCheckEnteredEventIn.getCreditApplication();
		CityCheckDoneEvent result = CityCheckDoneEvent.builder()
				.creditApplicationId(creditApplication.getCreditApplicationId()).build();
		Message<CityCheckDoneEvent> message = MessageBuilder.withPayload(result).build();
		if (creditApplication.cityCheckProcess()) {
			creditApplicationCityCheckChannels.cityCheckPositiveOut().send(message);
		} else {
			creditApplicationCityCheckChannels.cityCheckNegativeOut().send(message);
		}
		
		
	}
}
