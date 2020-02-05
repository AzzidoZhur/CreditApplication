package de.ing.easyfinancing.scoring.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditApplication {
	private String creditApplicationId;
	private double monthlyIncome;
	private double monthlyExpenditure;
	private double creditSum;
	
	public boolean scoringProcess() {
		return creditSum < (monthlyIncome-monthlyExpenditure)*10.0;
	}
}
