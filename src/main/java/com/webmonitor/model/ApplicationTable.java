package com.webmonitor.model;

public class ApplicationTable {

	String tableName;
	String runType;
	Long p8pCount;
	Long transferCount;
	Long percentage;
	Long difference;
	String status;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getRunType() {
		return runType;
	}
	public void setRunType(String runType) {
		this.runType = runType;
	}
	public Long getP8pCount() {
		return p8pCount;
	}
	public void setP8pCount(Long p8pCount) {
		this.p8pCount = p8pCount;
	}
	public Long getTransferCount() {
		return transferCount;
	}
	public void setTransferCount(Long transferCount) {
		this.transferCount = transferCount;
	}
	public Long getPercentage() {
		return percentage;
	}
	public void setPercentage(Long percentage) {
		this.percentage = percentage;
	}
	public Long getDifference() {
		return difference;
	}
	public void setDifference(Long difference) {
		this.difference = difference;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((difference == null) ? 0 : difference.hashCode());
		result = prime * result + ((p8pCount == null) ? 0 : p8pCount.hashCode());
		result = prime * result + ((percentage == null) ? 0 : percentage.hashCode());
		result = prime * result + ((runType == null) ? 0 : runType.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
		result = prime * result + ((transferCount == null) ? 0 : transferCount.hashCode());
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
		ApplicationTable other = (ApplicationTable) obj;
		if (difference == null) {
			if (other.difference != null)
				return false;
		} else if (!difference.equals(other.difference))
			return false;
		if (p8pCount == null) {
			if (other.p8pCount != null)
				return false;
		} else if (!p8pCount.equals(other.p8pCount))
			return false;
		if (percentage == null) {
			if (other.percentage != null)
				return false;
		} else if (!percentage.equals(other.percentage))
			return false;
		if (runType == null) {
			if (other.runType != null)
				return false;
		} else if (!runType.equals(other.runType))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		if (transferCount == null) {
			if (other.transferCount != null)
				return false;
		} else if (!transferCount.equals(other.transferCount))
			return false;
		return true;
	}
	
	
}
