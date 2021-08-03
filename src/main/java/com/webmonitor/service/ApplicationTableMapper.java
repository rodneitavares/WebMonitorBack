package com.webmonitor.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.webmonitor.model.ApplicationTable;

public class ApplicationTableMapper implements RowMapper<ApplicationTable>{

	@Override
	public ApplicationTable mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ApplicationTable appTables = new ApplicationTable();
		
		appTables.setTableName(rs.getString("TableName"));
		appTables.setRunType(rs.getString("RunType"));
		appTables.setTransferCount(rs.getLong("TransferCount"));
		appTables.setP8pCount(rs.getLong("P8PCount"));
		appTables.setPercentage(rs.getLong("Percentage"));
		appTables.setDifference(rs.getLong("Difference"));
		appTables.setStatus(rs.getString("Status"));
		
		return appTables;
	}

}
