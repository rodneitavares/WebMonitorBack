package com.webmonitor.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.webmonitor.model.ApplicationSettings;

public class ApplicationSettingMapper implements RowMapper<ApplicationSettings>{

	@Override
	public ApplicationSettings mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ApplicationSettings appSettings = new ApplicationSettings();
	
		appSettings.setName(rs.getString("Name"));
		appSettings.setValue(rs.getString("Value"));
		
		return appSettings;
	}
	
}
