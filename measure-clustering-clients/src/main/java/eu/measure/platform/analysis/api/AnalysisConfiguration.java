package eu.measure.platform.analysis.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnalysisConfiguration {
	
	private Long projectAnalysisId;
	
	private String viewUrl;
	
	private String configurationUrl;
	
	private List<CardConfiguration> cards ;

	public Long getProjectAnalysisId() {
		return projectAnalysisId;
	}

	public void setProjectAnalysisId(Long projectAnalysisId) {
		this.projectAnalysisId = projectAnalysisId;
	}

	public String getViewUrl() {
		return viewUrl;
	}

	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}

	public String getConfigurationUrl() {
		return configurationUrl;
	}

	public void setConfigurationUrl(String configurationUrl) {
		this.configurationUrl = configurationUrl;
	}

	public List<CardConfiguration> getCards() {
		return cards;
	}

	public void setCards(List<CardConfiguration> cards) {
		this.cards = cards;
	}

	public AnalysisConfiguration() {
		this.setCards(new ArrayList<CardConfiguration>());
	}

	AnalysisConfiguration(Long projectAnalysisId, String viewUrl, String configurationUrl, CardConfiguration... cards) {
		this.projectAnalysisId = projectAnalysisId;
		this.viewUrl = viewUrl;
		this.configurationUrl = configurationUrl;
		this.cards = Arrays.asList(cards);
	}

}