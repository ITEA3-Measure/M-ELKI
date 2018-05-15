package eu.measure.platform.analysis.tests;

import java.net.URI;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.measure.platform.analysis.api.AlertData;
import eu.measure.platform.analysis.api.AlertProperty;
import eu.measure.platform.analysis.api.AlertReport;
import eu.measure.platform.analysis.api.EventType;
import eu.measure.platform.analysis.api.MeasureAnalysisPlatformClient;
import eu.measure.platform.analysis.api.ProjectAnalysis;
import eu.measure.platform.api.MeasureInstance;

public class MeasurePlatformTesting {

	private static final String NAME = "elki-clustering-services-on-test";
	private static final String DESC = "This is the web services packaged and deployed by ICAM that provide the clustering algorithms of the ELKI library.";
	private static final String HOST = "http://app.icam.fr/elki?id=0";
	private static final Long TESTID = 13L;
	private static final Long ALGOID = 33L;
	
	private MeasureAnalysisPlatformClient client;
	
	@Before
	public void setup() throws Exception {
		client = new MeasureAnalysisPlatformClient("http", "194.2.241.244", 80, "/measure");
	}

	@Test
	public void test_01_register() throws Exception {
		String result = client.doRegister(URI.create(HOST).toURL(), DESC, NAME);
		System.out.println(result);
	}

	@Test
	public void test_02_configure() throws Exception {
		String result = client.doConfigure(ALGOID, URI.create(HOST).toURL(), URI.create(HOST).toURL());
		System.out.println(result);
	}
	
	@Test
	public void test_03_subscribe() throws Exception {
		String result = client.doSubscribe(NAME, EventType.ANALYSIS_ENABLE.name(), TESTID);
		System.out.println(result);
	}
	
	@Test
	public void test_04_report() throws Exception {
		AlertReport report = client.getAlertReport(NAME);
		System.out.println("number of alerts = " + report.getAlerts().size());
		System.out.println(report.getFrom().toString());
		for (AlertData data : report.getAlerts()) {
			System.out.println("alert type:" + data.getAlertType());
			System.out.println("project id:" + data.getProjectId());
			for (AlertProperty property : data.getProperties()) {
				System.out.println("\tproperty name: :" + property.getProperty());
				System.out.println("\tproperty value: :" + property.getValue());
			}
		}
	}
	
	@Test
	public void test_05_unsubscribe() throws Exception {
		String result = client.doUnsubscribe(NAME, EventType.ANALYSIS_ENABLE.name(), TESTID);
		System.out.println(result);
	}
	
	@Test
	public void test_06_project_measure_instances() throws Exception {
		List<MeasureInstance> instances = client.getProjectMeasureInstances(TESTID);
		System.out.println("project '" + TESTID + "' mesaure instance number: " + instances.size());
	}
	
	@Test
	public void test_07_project_analysis() throws Exception {
		ProjectAnalysis pa = client.getProjectAnalysis(ALGOID);
		System.out.println("project '" + ALGOID + "' analysis: " + pa);
	}
	
}
