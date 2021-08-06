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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webmonitor.model.ApplicationSettings;
import com.webmonitor.model.Applications;
import com.webmonitor.repository.ApplicationsRepository;
import com.webmonitor.repository.ExternalDataSource;

@RestController
@RequestMapping("/appSettings")
@CrossOrigin("*")
public class ApplicationSettingsController {

	@Autowired
	private ApplicationsRepository applicationRepository;

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<List<ApplicationSettings>> getSettingsById(@PathVariable Long id) {

		Optional<Applications> application = applicationRepository.findById(id);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(ExternalDataSource.getDataSource(application.get().getDbServer(),
				application.get().getDbName(), application.get().getDbUser(), application.get().getDbPsw()));

		String sqlStatement = "select Name, Value from lp_Upd8Settings";

		List<ApplicationSettings> appSettings = jdbcTemplate.query(sqlStatement,
				new BeanPropertyRowMapper<ApplicationSettings>(ApplicationSettings.class));

		return new ResponseEntity<List<ApplicationSettings>>(appSettings, HttpStatus.OK);

	}

	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<ApplicationSettings> updateSettingsByName(@PathVariable Long id, @RequestBody ApplicationSettings paramAppSettings) {

		Optional<Applications> application = applicationRepository.findById(id);
		
		JdbcTemplate myJdbc =  new JdbcTemplate(ExternalDataSource.getDataSource(application.get().getDbServer(),
				application.get().getDbName(), application.get().getDbUser(), application.get().getDbPsw()));

		String sqlStatement = "Update lp_Upd8Settings Set Value = ? Where name = ?";

		myJdbc.update(sqlStatement, paramAppSettings.getValue(), paramAppSettings.getName());

		return new ResponseEntity<ApplicationSettings>(HttpStatus.OK);
	}
}
