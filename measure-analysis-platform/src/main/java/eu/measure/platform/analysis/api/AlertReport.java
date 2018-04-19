package eu.measure.platform.analysis.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlertReport {
	
	private Date from;
	
	private List<AlertData> alerts;
	
	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public List<AlertData> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<AlertData> alerts) {
		this.alerts = alerts;
	}

	public AlertReport() {
		this.setAlerts(new ArrayList<AlertData>());
	}
	
}