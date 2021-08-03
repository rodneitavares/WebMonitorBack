package com.webmonitor.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webmonitor.repository.ExternalDataSource;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*")
public class TestExternalSource {
	
	
	@GetMapping(value="/{id}", produces = "application/json")
	public String getLastUpdate(@PathVariable Long id) {
		
//		return jdbcTemplate.queryForList("select * from dataRefresh").toString();

		
		//ExternalDataSource lastUpd = new ExternalDataSource();
		
		//result = lastUpd.openConnection("localhost","WebApps","sa","73184342","select top 1 * from dataRefresh");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate( ExternalDataSource.getDataSource("localhost","WebApps","sa","73184342") );
		String sqlStatement = "select top 1 * from dataRefresh where id = " + id;
		System.out.println(sqlStatement);
		String result = jdbcTemplate.query( sqlStatement , rs -> {
		            if( rs.next() )
		            {		                
		                return rs.getString("lastRefresh");
		            }
		            return "empty";
		        } );
		
		return result;
	}

}
