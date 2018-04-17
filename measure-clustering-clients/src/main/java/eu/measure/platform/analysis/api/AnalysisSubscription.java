package eu.measure.platform.analysis.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnalysisSubscription {

	private String analysisTool;
	
	private String eventType;
	
	private Long projectId;
	
	private List<AlertProperty> properties;

	public String getAnalysisTool() {
		return analysisTool;
	}

	public void setAnalysisTool(String analysisTool) {
		this.analysisTool = analysisTool;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
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

	public AnalysisSubscription() {
		this.setProperties(new ArrayList<AlertProperty>());
	}

	AnalysisSubscription(String analysisTool, String eventType, Long projectId, AlertProperty... properties) {
		this.analysisTool = analysisTool;
		this.eventType = eventType;
		this.projectId = projectId;
		this.properties = Arrays.asList(properties);
	}
	
}