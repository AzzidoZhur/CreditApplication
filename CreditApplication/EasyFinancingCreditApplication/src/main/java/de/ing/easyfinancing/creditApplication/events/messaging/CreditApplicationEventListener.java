package de.ing.easyfinancing.creditApplication.events.messaging;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.ing.easyfinancing.creditApplication.events.CityCheckDoneEvent;
import de.ing.easyfinancing.creditApplication.events.CreditApplicationChannels;
import de.ing.easyfinancing.creditApplication.events.ScoringDoneEvent;
import de.ing.easyfinancing.creditApplication.service.CreditApplicationStateService;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Component
public class CreditApplicationEventListener {

	private final CreditApplicationChannels creditApplicationScoringDispatcher;
	private final CreditApplicationStateService creditApplicationStateService;

	public CreditApplicationEventListener(final CreditApplicationChannels creditApplicationScoringDispatcher,
			CreditApplicationStateService creditApplicationStateService) {
		this.creditApplicationScoringDispatcher = creditApplicationScoringDispatcher;
		this.creditApplicationStateService = creditApplicationStateService;
	}

	@StreamListener(CreditApplicationChannels.CREDIT_APPLICATION_SCORING_NEGATIVE)
		public void receiveCreditApplicationScoringNegative(@Payload ScoringDoneEvent scoringDoneEvent) {
		System.out.println("ScoringNegativeStart");
		
		creditApplicationStateService.processScoringNegative(scoringDoneEvent.getCreditApplicationId());

		System.out.println("ScoringNegativeStop");
	}

	@StreamListener(CreditApplicationChannels.CREDIT_APPLICATION_SCORING_POSITIVE)
	public void receiveCreditApplicationScoringPositive(@Payload ScoringDoneEvent scoringDoneEvent) {
		System.out.println("ScoringPositiveStart");
		creditApplicationStateService.processScoringPositive(scoringDoneEvent.getCreditApplicationId());
		System.out.println("ScoringPositiveStop");
	}

	@StreamListener(CreditApplicationChannels.CREDIT_APPLICATION_CITYCHECK_POSITIVE)
	public void receiveCityCheckPositiveIn(@Payload CityCheckDoneEvent cityCheckDoneEvent) {
		System.out.println("CityCheckPositiveStart");
		creditApplicationStateService.processCityCheckPositive(cityCheckDoneEvent.getCreditApplicationId());
		System.out.println("CityCheckPositiveStop");
	}

	@StreamListener(CreditApplicationChannels.CREDIT_APPLICATION_CITYCHECK_NEGATIVE)
	public void receiveCityCheckNegativeIn(@Payload CityCheckDoneEvent cityCheckDoneEvent) {

		creditApplicationStateService.processCityCheckNegative(cityCheckDoneEvent.getCreditApplicationId());
	}

}