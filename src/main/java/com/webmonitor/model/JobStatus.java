package com.webmonitor.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

public class JobStatus {

	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss", timezone="Europe/Dublin")
	@Temporal(TemporalType.TIMESTAMP)
	Date lastJobRun;
	
	String jobStatus;
	String lastMonthlyClosure;
	String currentExecutionStatus;
	String lastRunOutcome;
	
	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss", timezone="Europe/Dublin")
	@Temporal(TemporalType.TIMESTAMP)
	Date lastDataUpdate;
	
	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss", timezone="Europe/Dublin")
	@Temporal(TemporalType.TIMESTAMP)
	Date lastCubeUpdate;

	public Date getLastJobRun() {
		return lastJobRun;
	}

	public void setLastJobRun(Date lastJobRun) {
		this.lastJobRun = lastJobRun;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getLastMonthlyClosure() {
		return lastMonthlyClosure;
	}

	public void setLastMonthlyClosure(String lastMonthlyClosure) {
		this.lastMonthlyClosure = lastMonthlyClosure;
	}

	public String getCurrentExecutionStatus() {
		return currentExecutionStatus;
	}

	public void setCurrentExecutionStatus(String currentExecutionStatus) {
		this.currentExecutionStatus = currentExecutionStatus;
	}

	public String getLastRunOutcome() {
		return lastRunOutcome;
	}

	public void setLastRunOutcome(String lastRunOutcome) {
		this.lastRunOutcome = lastRunOutcome;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentExecutionStatus == null) ? 0 : currentExecutionStatus.hashCode());
		result = prime * result + ((jobStatus == null) ? 0 : jobStatus.hashCode());
		result = prime * result + ((lastCubeUpdate == null) ? 0 : lastCubeUpdate.hashCode());
		result = prime * result + ((lastDataUpdate == null) ? 0 : lastDataUpdate.hashCode());
		result = prime * result + ((lastJobRun == null) ? 0 : lastJobRun.hashCode());
		result = prime * result + ((lastMonthlyClosure == null) ? 0 : lastMonthlyClosure.hashCode());
		result = prime * result + ((lastRunOutcome == null) ? 0 : lastRunOutcome.hashCode());
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
		JobStatus other = (JobStatus) obj;
		if (currentExecutionStatus == null) {
			if (other.currentExecutionStatus != null)
				return false;
		} else if (!currentExecutionStatus.equals(other.currentExecutionStatus))
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
		if (lastJobRun == null) {
			if (other.lastJobRun != null)
				return false;
		} else if (!lastJobRun.equals(other.lastJobRun))
			return false;
		if (lastMonthlyClosure == null) {
			if (other.lastMonthlyClosure != null)
				return false;
		} else if (!lastMonthlyClosure.equals(other.lastMonthlyClosure))
			return false;
		if (lastRunOutcome == null) {
			if (other.lastRunOutcome != null)
				return false;
		} else if (!lastRunOutcome.equals(other.lastRunOutcome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JobStatus [lastJobRun=" + lastJobRun + ", jobStatus=" + jobStatus + ", lastMonthlyClosure="
				+ lastMonthlyClosure + ", currentExecutionStatus=" + currentExecutionStatus + ", lastRunOutcome="
				+ lastRunOutcome + ", lastDataUpdate=" + lastDataUpdate + ", lastCubeUpdate=" + lastCubeUpdate + "]";
	}

	
}
