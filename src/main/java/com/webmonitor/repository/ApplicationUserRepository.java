package com.webmonitor.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webmonitor.model.ApplicationUser;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
	
	@Query("select u from ApplicationUser u where u.username = ?1")
	ApplicationUser findUserByUsername(String username);
}
