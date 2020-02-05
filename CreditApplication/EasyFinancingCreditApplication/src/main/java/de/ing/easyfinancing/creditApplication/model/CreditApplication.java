package de.ing.easyfinancing.creditApplication.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table (name = "tbl_credit_applications")

public class CreditApplication implements Serializable {
	/*
	 * wandelt das Object in einen Bytestrom
	 */
	private static final long serialVersionUID = 8144682724082102640L;
	@Builder.Default
	@Size(min = 36, max = 36)
	@NonNull
	@Id
	@Column(length = 36, nullable = false)
	private String creditApplicationId = UUID.randomUUID().toString();
	@Size(min = 2, max = 51)
	@NonNull
	@Column(length = 51, nullable = false)
	private String firstName;
	@Size(min = 2, max = 51)
	@NonNull
	@Column(length = 51, nullable = false)
	private String lastName;
	@Size(min = 2, max = 51)
	@NonNull
	@Column(length = 51, nullable = false)
	private String city; 
	
	@Builder.Default
	private LocalDateTime applicationDate = LocalDateTime.now(); 
	
	@Min(value = 0)
	private double monthlyIncome; 
	@Min(value = 0)
	private double monthlyExpenditure; 
	@Min(value = 0)
	private double creditSum; 
	
	@Builder.Default
	@Column(length = 20, nullable = false)
	private String scoringState = "in progress";
	@Builder.Default
	@Column(length = 20, nullable = false)
	private String cityCheckState = "in progress"; 
	@Builder.Default
	@Column(length = 20, nullable = false)
	private String applicationState = "in progress"; 
	
	

}
