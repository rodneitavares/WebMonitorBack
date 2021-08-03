package com.webmonitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webmonitor.model.Applications;

public interface ApplicationsRepository extends JpaRepository<Applications, Long> {

//	@Query("select d from Applications d where d.countryCode = ?1")
//	Applications findCountryByCode(String countryCode);
	
}
