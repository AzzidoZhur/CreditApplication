package de.ing.easyfinancing.citycheck.events;

import de.ing.easyfinancing.citycheck.model.CreditApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class CityCheckEnteredEventIn extends BaseEvent {

	private static final long serialVersionUID = 223691151420486053L;
	private CreditApplication creditApplication;
}
