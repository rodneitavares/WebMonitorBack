	package com.webmonitor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@SequenceGenerator(name = "seq_appno", sequenceName = "seq_appno", allocationSize = 1, initialValue = 1)
public class Applications implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@Column(unique = true, nullable = false)
	private String url;
	
	private String version;
	
	private String jobStatus;
	
	private String countryCode;
	
	private String serverHost; //ex.10.40.72.18
	
	private String extractionJobName; //MyFleet LPNZ - Daily Update
	
	private String currentPeriod;
	
	private Boolean transferAlert;
	
	private String dbServer;
	
	private String dbName;
	
	private String dbUser;
	
	private String dbPsw;
	
	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss", timezone="Europe/Dublin")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastDataUpdate;

	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss", timezone="Europe/Dublin")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastCubeUpdate;
					
	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss", timezone="Europe/Dublin")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastJobStart;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public String getExtractionJobName() {
		return extractionJobName;
	}

	public void setExtractionJobName(String extractionJobName) {
		this.extractionJobName = extractionJobName;
	}

	public String getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(String currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public Boolean getTransferAlert() {
		return transferAlert;
	}

	public void setTransferAlert(Boolean transferAlert) {
		this.transferAlert = transferAlert;
	}

	public String getDbServer() {
		return dbServer;
	}

	public void setDbServer(String dbServer) {
		this.dbServer = dbServer;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPsw() {
		return dbPsw;
	}

	public void setDbPsw(String dbPsw) {
		this.dbPsw = dbPsw;
	}

	public Date getLastDataUpdate() {
		return lastDataUpdate;
	}

	public void setLastDataUpdate(Date lastDataUpdate) {
		this.lastDataUpdate = lastDataUpdate;
	}

	public Date getLastCubeUpdate() {
		return lastCubeUpdate;
	}

	public void setLastCubeUpdate(Date lastCubeUpdate) {
		this.lastCubeUpdate = lastCubeUpdate;
	}

	public Date getLastJobStart() {
		return lastJobStart;
	}

	public void setLastJobStart(Date lastJobStart) {
		this.lastJobStart = lastJobStart;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result + ((currentPeriod == null) ? 0 : currentPeriod.hashCode());
		result = prime * result + ((dbName == null) ? 0 : dbName.hashCode());
		result = prime * result + ((dbPsw == null) ? 0 : dbPsw.hashCode());
		result = prime * result + ((dbServer == null) ? 0 : dbServer.hashCode());
		result = prime * result + ((dbUser == null) ? 0 : dbUser.hashCode());
		result = prime * result + ((extractionJobName == null) ? 0 : extractionJobName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jobStatus == null) ? 0 : jobStatus.hashCode());
		result = prime * result + ((lastCubeUpdate == null) ? 0 : lastCubeUpdate.hashCode());
		result = prime * result + ((lastDataUpdate == null) ? 0 : lastDataUpdate.hashCode());
		result = prime * result + ((lastJobStart == null) ? 0 : lastJobStart.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((serverHost == null) ? 0 : serverHost.hashCode());
		result = prime * result + ((transferAlert == null) ? 0 : transferAlert.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Applications other = (Applications) obj;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		if (currentPeriod == null) {
			if (other.currentPeriod != null)
				return false;
		} else if (!currentPeriod.equals(other.currentPeriod))
			return false;
		if (dbName == null) {
			if (other.dbName != null)
				return false;
		} else if (!dbName.equals(other.dbName))
			return false;
		if (dbPsw == null) {
			if (other.dbPsw != null)
				return false;
		} else if (!dbPsw.equals(other.dbPsw))
			return false;
		if (dbServer == null) {
			if (other.dbServer != null)
				return false;
		} else if (!dbServer.equals(other.dbServer))
			return false;
		if (dbUser == null) {
			if (other.dbUser != null)
				return false;
		} else if (!dbUser.equals(other.dbUser))
			return false;
		if (extractionJobName == null) {
			if (other.extractionJobName != null)
				return false;
		} else if (!extractionJobName.equals(other.extractionJobName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jobStatus == null) {
			if (other.jobStatus != null)
				return false;
		} else if (!jobStatus.equals(other.jobStatus))
			return false;
		if (lastCubeUpdate == null) {
			if (other.lastCubeUpdate != null)
				return false;
		} else if (!lastCubeUpdate.equals(other.lastCubeUpdate))
			return false;
		if (lastDataUpdate == null) {
			if (other.lastDataUpdate != null)
				return false;
		} else if (!lastDataUpdate.equals(other.lastDataUpdate))
			return false;
		if (lastJobStart == null) {
			if (other.lastJobStart != null)
				return false;
		} else if (!lastJobStart.equals(other.lastJobStart))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (serverHost == null) {
			if (other.serverHost != null)
				return false;
		} else if (!serverHost.equals(other.serverHost))
			return false;
		if (transferAlert == null) {
			if (other.transferAlert != null)
				return false;
		} else if (!transferAlert.equals(other.transferAlert))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	
}
