package de.ing.easyfinancing.citycheck.model;
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
	private String city;
	
	
	public boolean cityCheckProcess() {
		
		return !("Muenchen".equalsIgnoreCase(city) || "München".equalsIgnoreCase(city));
	}
}