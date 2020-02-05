package de.ing.easyfinancing.creditApplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.ing.easyfinancing.creditApplication.model.CreditApplication;

public interface CreditApplicationRepository extends CrudRepository<CreditApplication, String> {

	@Query("Select c from CreditApplication c")
	List<CreditApplication> findAllAsList();
}
