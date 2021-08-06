package com.webmonitor.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webmonitor.model.ApplicationTable;
import com.webmonitor.model.Applications;
import com.webmonitor.repository.ApplicationsRepository;
import com.webmonitor.repository.ExternalDataSource;

@RestController
@RequestMapping("/appTable")
@CrossOrigin("*")
public class ApplicationTablesController {

	@Autowired
	private ApplicationsRepository applicationRepository;
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<List<ApplicationTable>> listTable(@PathVariable Long id){
		
		Optional<Applications> application = applicationRepository.findById(id);
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ExternalDataSource.getDataSource(application.get().getDbServer(),
				application.get().getDbName(), application.get().getDbUser(), application.get().getDbPsw()));

		String sqlStatement = "Select TableName, RunType, TransferCount, P8PCount, Percentage, Difference, Status From lp_Upd8Info ";

		List<ApplicationTable> applicationTable = jdbcTemplate.query(sqlStatement,new BeanPropertyRowMapper<ApplicationTable>(ApplicationTable.class));

		return new ResponseEntity<List<ApplicationTable>>(applicationTable, HttpStatus.OK);
	}
}
