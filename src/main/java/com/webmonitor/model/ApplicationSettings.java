package com.webmonitor.model;

public class ApplicationSettings {
	
	private String Name;
	private String Value;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime * result + ((Value == null) ? 0 : Value.hashCode());
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
		ApplicationSettings other = (ApplicationSettings) obj;
		if (Name == null) {
			if (other.Name != null)
				return false;
		} else if (!Name.equals(other.Name))
			return false;
		if (Value == null) {
			if (other.Value != null)
				return false;
		} else if (!Value.equals(other.Value))
			return false;
		return true;
	}	
	
}
