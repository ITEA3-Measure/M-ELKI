package eu.measure.platform.analysis.api;

import eu.measure.platform.api.Project;

public class ProjectAnalysis {

    private Long id;

    private String analysisToolId;
      
    private String analysisToolDescription;
    
    private String dashboardName;
    
    private String configurationUrl;
    
    private String viewUrl;
 
    private Project project;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnalysisToolId() {
		return analysisToolId;
	}

	public void setAnalysisToolId(String analysisToolId) {
		this.analysisToolId = analysisToolId;
	}

	public String getAnalysisToolDescription() {
		return analysisToolDescription;
	}

	public void setAnalysisToolDescription(String analysisToolDescription) {
		this.analysisToolDescription = analysisToolDescription;
	}

	public String getDashboardName() {
		return dashboardName;
	}

	public void setDashboardName(String dashboardName) {
		this.dashboardName = dashboardName;
	}

	public String getConfigurationUrl() {
		return configurationUrl;
	}

	public void setConfigurationUrl(String configurationUrl) {
		this.configurationUrl = configurationUrl;
	}

	public String getViewUrl() {
		return viewUrl;
	}

	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
       
}