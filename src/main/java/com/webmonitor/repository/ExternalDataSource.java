package com.webmonitor.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

public class ExternalDataSource {

	public String openConnection(String server, String dbName, String user, String _psw, String sqlStatement) {

		String url = "jdbc:sqlserver://" + server.trim() + ";databaseName=" + dbName.trim();
		String username = user;
		String psw = _psw;
		String lastUpdate = "";
		String sql = sqlStatement;

		try {
			Connection connection = DriverManager.getConnection(url, username, psw);
			System.out.println("Connected to SQL");

			Statement query = connection.createStatement();
			ResultSet result = query.executeQuery(sql);

			while (result.next()) {
				lastUpdate = result.getString("lastRefresh");

				System.out.println(lastUpdate);
			}

			System.out.println(lastUpdate);
		} catch (SQLException e) {
			System.out.println("Failed to connect to the server " + server.trim() + ".");
			e.printStackTrace();
		}
		return lastUpdate;
	}

	@Bean()
	public static DataSource getDataSource(String server, String dbName, String user, String _psw) {
		DataSource dataSource = null;

		DataSourceBuilder<?> builder = DataSourceBuilder.create();

		builder.url("jdbc:sqlserver://" + server.trim() + ";databaseName=" + dbName.trim());
		builder.username(user);
		builder.password(_psw);
		builder.driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		try {			
			dataSource = builder.build();
		} catch (Exception e) {
			System.out.println("Failed to connect on " + server);
			System.out.println(e.getMessage());
		}

		return dataSource;
	}

}
