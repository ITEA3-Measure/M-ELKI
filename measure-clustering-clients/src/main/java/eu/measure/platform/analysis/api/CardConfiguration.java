package eu.measure.platform.analysis.api;

public class CardConfiguration {

	private String cardUrl;
	
	private String label;

	private Integer preferedWidth;
	
	private Integer preferedHeight;
	
	public String getCardUrl() {
		return cardUrl;
	}

	public void setCardUrl(String cardUrl) {
		this.cardUrl = cardUrl;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getPreferedWidth() {
		return preferedWidth;
	}

	public void setPreferedWidth(Integer preferedWidth) {
		this.preferedWidth = preferedWidth;
	}

	public Integer getPreferedHeight() {
		return preferedHeight;
	}

	public void setPreferedHeight(Integer preferedHeight) {
		this.preferedHeight = preferedHeight;
	}

	public CardConfiguration() {
		
	}

	CardConfiguration(String cardUrl, String label, Integer preferedWidth, Integer preferedHeight) {
		this.cardUrl = cardUrl;
		this.label = label;
		this.preferedWidth = preferedWidth;
		this.preferedHeight = preferedHeight;
	}

}