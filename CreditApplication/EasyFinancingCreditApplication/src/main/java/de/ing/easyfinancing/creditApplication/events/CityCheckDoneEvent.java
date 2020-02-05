package de.ing.easyfinancing.creditApplication.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class CityCheckDoneEvent extends BaseEvent {

	private static final long serialVersionUID = 7285438287976753712L;
		private String creditApplicationId;
	}

