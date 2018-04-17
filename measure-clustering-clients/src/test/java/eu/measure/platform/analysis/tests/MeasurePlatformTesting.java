package eu.measure.platform.analysis.tests;

import java.net.URI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.measure.platform.analysis.api.AlertReport;
import eu.measure.platform.analysis.api.EventType;
import eu.measure.platform.analysis.api.MeasurePlatformClient;

public class MeasurePlatformTesting {

	private static final String NAME = "elki-clustering-services-on-test";
	private static final String DESC = "This is the web services packaged and deployed by ICAM that provide the clustering algorithms of the ELKI library.";
	private static final String HOST = "http://emit.icam.fr/elki";
	private static final Long TESTID = 11L;
	
	private MeasurePlatformClient client;
	
	@Before
	public void setup() throws Exception {
		client = new MeasurePlatformClient("http", "194.2.241.244", 80, "/measure");
	}

	@Test
	public void test_01_register() throws Exception {
		String result = client.doRegister(URI.create(HOST).toURL(), DESC, NAME);
		System.out.println(result);
	}

	@Test
	public void test_02_configure() throws Exception {
		String result = client.doConfigure(TESTID, URI.create(HOST).toURL(), URI.create(HOST).toURL());
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
	}
	
	@Test
	public void test_05_unsubscribe() throws Exception {
		String result = client.doUnsubscribe(NAME, EventType.ANALYSIS_ENABLE.name(), TESTID);
		System.out.println(result);
	}
	
}
