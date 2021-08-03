package com.webmonitor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webmonitor.model.ApplicationSettings;
import com.webmonitor.repository.ExternalDataSource;

@RestController
@RequestMapping("/appSettings")
@CrossOrigin("*")
public class ApplicationSettingsController {

	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<ApplicationSettings>> getSettings() {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(ExternalDataSource.getDataSource("localhost", "WebApps", "sa", "73184342"));

		String sqlStatement = "select Name, Value from lp_Upd8Settings";

		List<ApplicationSettings> appSettings = jdbcTemplate.query(sqlStatement,
				new BeanPropertyRowMapper<ApplicationSettings>(ApplicationSettings.class));

		return new ResponseEntity<List<ApplicationSettings>>(appSettings, HttpStatus.OK);

	}

	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<ApplicationSettings> updateSettingsByName(@RequestBody ApplicationSettings paramAppSettings) {
	
		JdbcTemplate myJdbc = new JdbcTemplate(ExternalDataSource.getDataSource("localhost", "WebApps", "sa", "73184342"));
		
//		for (int i = 0; i < paramAppSettings.size(); i++) {
			
			String sqlStatement = "Update lp_Upd8Settings Set Value = ? Where name = ?";
//			ApplicationSettings updSetting = paramAppSettings;
			
			myJdbc.update(sqlStatement, paramAppSettings.getValue(), paramAppSettings.getName());
			
//		}
		
		return new ResponseEntity<ApplicationSettings>(HttpStatus.OK);
	}
}
