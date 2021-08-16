package com.webmonitor.component;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class LastDataRefresh {
	
	private String lastDataRefresh = new Date().toString();

	public String getLastDataRefresh() {
		return lastDataRefresh;
	}

	public void setLastDataRefresh(String lastDataRefresh) {
		this.lastDataRefresh = lastDataRefresh;
	}
	
	

}
