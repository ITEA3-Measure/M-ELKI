package fr.icam.elki.platform;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import eu.measure.platform.analysis.api.AlertData;
import eu.measure.platform.analysis.api.AlertProperty;
import eu.measure.platform.analysis.api.AlertReport;
import eu.measure.platform.analysis.api.EventType;
import eu.measure.platform.analysis.api.MeasureAnalysisPlatformClient;
import eu.measure.platform.analysis.api.PropertyType;
import fr.icam.elki.identifiers.ElkiConfiguration;

public class MeasurePlatformConnection extends HttpServlet implements Runnable {

	private static final long serialVersionUID = 201804191520001L;

	private static final String NAME = "M·ELKI";
	
	private static final String DESC = "Clustering Algorithms dedicated to the MEASURE Platform and based on the ELKI Java Library";
	
	private static final String CONF = "http://emit.icam.fr/elki/settings/?id=";
	
	private static final String VIEW = "http://emit.icam.fr/elki?id=";
	
	private URI getConf(Long id) {
		return URI.create(CONF + id);
	}
	
	private URI getView(Long id) {
		return URI.create(VIEW + id);
	}
	
	private ScheduledExecutorService executor;
		
	private MeasureAnalysisPlatformClient client;
	
	private Map<Long, ElkiConfiguration> identifiers;
	
	private void doInsert(Long id) {
		ElkiConfiguration cfg = identifiers.get(0L);
		identifiers.put(id, new ElkiConfiguration(cfg));
	}
	
	private void doDelete(Long id) {
		identifiers.remove(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		super.init();
		identifiers = (Map<Long, ElkiConfiguration>) this.getServletContext().getAttribute("identifiers");
		executor = Executors.newSingleThreadScheduledExecutor();
		try {
			client = new MeasureAnalysisPlatformClient("http", "194.2.241.244", 80, "/measure");
			client.setUp();
			client.doRegister(this.getConf(0L).toURL(), DESC, NAME);
			executor.scheduleAtFixedRate(this, 0, 60, TimeUnit.SECONDS);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	@Override
	public void destroy() {
		executor.shutdown();
	}

	public void run() {
		try { this.doProcess(); } 
		catch (Exception e) { e.printStackTrace(); }
	}
	
	private void doProcess() throws Exception {
		AlertReport report = client.getAlertReport(NAME);
		for (AlertData alert : report.getAlerts()) {
			this.doAlert(alert);
		}
	}

	private void doAlert(AlertData alert) throws Exception {
		String alertType = alert.getAlertType();
		Long projectId = Long.valueOf(alert.getProjectId());
		if (alertType.equals(EventType.ANALYSIS_ENABLE.name())) {
			Long analysisId = this.getAnalysisId(alert);
			this.onAnalysisEnabled(projectId, analysisId);
		} else if (alertType.equals(EventType.ANALYSIS_DESABLE.name())) {
			Long analysisId = this.getAnalysisId(alert);
			this.onAnalysisDisabled(projectId, analysisId);
		} else if (alertType.equals(EventType.MEASURE_ADDED.name())) {
			Long measureId = this.getMeasureId(alert);
			this.onMeasureAdded(projectId, measureId);
		} else if (alertType.equals(EventType.MEASURE_REMOVED.name())) {
			Long measureId = this.getMeasureId(alert);
			this.onMeasureRemoved(projectId, measureId);
		} else if (alertType.equals(EventType.MEASURE_SCHEDULED.name())) {
			Long measureId = this.getMeasureId(alert);
			this.onMeasureScheduled(projectId, measureId);
		} else if (alertType.equals(EventType.MEASURE_UNSCHEDULED.name())) {
			Long measureId = this.getMeasureId(alert);
			this.onMeasureUnscheduled(projectId, measureId);
		}
	}

	private void onAnalysisEnabled(Long projectId, Long analysisId) throws Exception, MalformedURLException {
		this.doInsert(analysisId);
		client.doConfigure(analysisId, this.getView(analysisId).toURL(), this.getConf(analysisId).toURL());
	}

	private void onAnalysisDisabled(Long projectId, Long analysisId) throws Exception, MalformedURLException {
		this.doDelete(analysisId);
	}

	private void onMeasureAdded(Long projectId, Long measureId) throws Exception, MalformedURLException {
	}

	private void onMeasureRemoved(Long projectId, Long measureId) throws Exception, MalformedURLException {
	}
	
	private void onMeasureScheduled(Long projectId, Long measureId) throws Exception, MalformedURLException {
	}
	
	private void onMeasureUnscheduled(Long projectId, Long measureId) throws Exception, MalformedURLException {
	}

	private Long getAnalysisId(AlertData alert) {
		for (AlertProperty property : alert.getProperties()) {
			String name = property.getProperty();
			String value = property.getValue();
			if (name.equals(PropertyType.ANALYSISID.name())) {
				return Long.valueOf(value);
			}
		}
		return null;
	}

	private Long getMeasureId(AlertData alert) {
		for (AlertProperty property : alert.getProperties()) {
			String name = property.getProperty();
			String value = property.getValue();
			if (name.equals(PropertyType.MEASUREID.name())) {
				return Long.valueOf(value);
			}
		}
		return null;
	}
	
}
