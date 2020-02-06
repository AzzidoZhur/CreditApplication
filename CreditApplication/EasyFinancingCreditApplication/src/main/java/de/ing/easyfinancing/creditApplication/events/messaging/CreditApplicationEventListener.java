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
		creditApplicationStateService.processScoringNegative(scoringDoneEvent.getCreditApplicationId());
	}

	@StreamListener(CreditApplicationChannels.CREDIT_APPLICATION_SCORING_POSITIVE)
	public void receiveCreditApplicationScoringPositive(@Payload ScoringDoneEvent scoringDoneEvent) {
		creditApplicationStateService.processScoringPositive(scoringDoneEvent.getCreditApplicationId());
	}

	@StreamListener(CreditApplicationChannels.CREDIT_APPLICATION_CITYCHECK_POSITIVE)
	public void receiveCityCheckPositiveIn(@Payload CityCheckDoneEvent cityCheckDoneEvent) {
		creditApplicationStateService.processCityCheckPositive(cityCheckDoneEvent.getCreditApplicationId());
	}

	@StreamListener(CreditApplicationChannels.CREDIT_APPLICATION_CITYCHECK_NEGATIVE)
	public void receiveCityCheckNegativeIn(@Payload CityCheckDoneEvent cityCheckDoneEvent) {
		creditApplicationStateService.processCityCheckNegative(cityCheckDoneEvent.getCreditApplicationId());
	}

}