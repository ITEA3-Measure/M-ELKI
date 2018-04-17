package eu.measure.platform.analysis.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlertData {
	
	private String alertType;
	
	private Long projectId;
	
	private List<AlertProperty> properties;

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public List<AlertProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<AlertProperty> properties) {
		this.properties = properties;
	}

	public AlertData() {
		this.setProperties(new ArrayList<AlertProperty>());
	}

	AlertData(String alertType, Long projectId, AlertProperty... properties) {
		this.alertType = alertType;
		this.projectId = projectId;
		this.properties = Arrays.asList(properties);
	}
	
}