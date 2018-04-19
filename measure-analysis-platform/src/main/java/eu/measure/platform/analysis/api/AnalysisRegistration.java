package eu.measure.platform.analysis.api;

public class AnalysisRegistration {
	
	private String configurationURL;
	
	private String description;
	
	private String name;

	public String getConfigurationURL() {
		return configurationURL;
	}

	public void setConfigurationURL(String configurationURL) {
		this.configurationURL = configurationURL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AnalysisRegistration() {
		
	}

	AnalysisRegistration(String configurationURL, String description, String name) {
		this.configurationURL = configurationURL;
		this.description = description;
		this.name = name;
	}
	
}