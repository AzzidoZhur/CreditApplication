package de.ing.easyfinancing.creditApplication.events;

import de.ing.easyfinancing.creditApplication.model.CreditApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplicationEnteredEvent extends BaseEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4285916407513235283L;
	private CreditApplication creditApplication;
}
