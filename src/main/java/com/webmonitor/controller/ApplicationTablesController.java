package com.webmonitor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webmonitor.model.ApplicationTable;
import com.webmonitor.repository.ExternalDataSource;

@RestController
@RequestMapping("/appTable")
@CrossOrigin("*")
public class ApplicationTablesController {

	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<ApplicationTable>> listTable(){
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ExternalDataSource.getDataSource("localhost", "WebApps", "sa", "73184342"));

		String sqlStatement = "Select TableName, RunType, TransferCount, P8PCount, Percentage, Difference, Status From lp_Upd8Info ";

		List<ApplicationTable> applicationTable = jdbcTemplate.query(sqlStatement,new BeanPropertyRowMapper<ApplicationTable>(ApplicationTable.class));

		return new ResponseEntity<List<ApplicationTable>>(applicationTable, HttpStatus.OK);
	}
}
