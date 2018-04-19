package eu.measure.platform.analysis.api;

import java.net.URL;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.httpclient.commons.AbstractClient;

public class MeasurePlatformClient extends AbstractClient {

	public MeasurePlatformClient(String protocol, String hostname, int port, String appname) {
		super(protocol, hostname, port, appname);
	}

	public void setUp() throws Exception {
		super.setUp();
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
	}

	private String getRegister() throws NullPointerException {
		return this.getAppName() + "/api/analysis/register";
	}
	
	private String getAlert(String name) throws NullPointerException {
		return this.getAppName() + "/api/analysis/alert/list/?id=" + name;
	}
	
	private String getConfigure() throws NullPointerException {
		return this.getAppName() + "/api/analysis/configure";
	}
	
	private String getSubscribe() throws NullPointerException {
		return this.getAppName() + "/api/analysis/alert/subscribe";
	}
	
	private String getUnsuscribe() throws NullPointerException {
		return this.getAppName() + "/api/analysis/alert/unsubscribe";
	}
	
	public AnalysisRegistration getRegistration(URL configurationUrl, String description, String name) {
		return new AnalysisRegistration(configurationUrl.toString(), description, name);
	}
	
	public AlertProperty getProperty(String property, String value) {
		return new AlertProperty(property, value);
	}
	
	public CardConfiguration getCard(URL cardUrl, String label, Integer preferedWidth, Integer preferedHeight) {
		return new CardConfiguration(cardUrl.toString(), label, preferedWidth, preferedHeight);
	}
	
	public AnalysisConfiguration getConfiguration(Long projectAnalysisId, URL viewUrl, URL configurationUrl, CardConfiguration... cards) {
		return new AnalysisConfiguration(projectAnalysisId, viewUrl.toString(), configurationUrl.toString(), cards);
	}
	
	public AnalysisSubscription getSubscription(String analysisTool, String eventType, Long projectId, AlertProperty... properties) {
		return new AnalysisSubscription(analysisTool, eventType, projectId, properties);
	}
	
	public String doRegister(URL configurationUrl, String description, String name) throws Exception {
		AnalysisRegistration registration = this.getRegistration(configurationUrl, description, name);
		return this.doRegister(registration);
	}
	
	public String doRegister(AnalysisRegistration registration) throws Exception {
		String json = this.getMapper().toJson(registration);
		HttpPut request = new HttpPut(this.getRegister());
		StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
		request.setEntity(entity);
		return this.getString(request);
	}

	public AlertReport getAlertReport(String name) throws Exception {
        HttpGet request = new HttpGet(this.getAlert(name));
        return this.getObject(request, AlertReport.class);
	}
	
	public String doConfigure(Long projectAnalysisId, URL viewUrl, URL configurationUrl, CardConfiguration... cards) throws Exception {
		AnalysisConfiguration configuration = this.getConfiguration(projectAnalysisId, viewUrl, configurationUrl, cards);
		return this.doConfigure(configuration);
	}
	
	public String doConfigure(AnalysisConfiguration configuration) throws Exception {
		String json = this.getMapper().toJson(configuration);
		HttpPut request = new HttpPut(this.getConfigure());
		StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
		request.setEntity(entity);
		return this.getString(request);
	}
	
	public String doSubscribe(String analysisTool, String eventType, Long projectId, AlertProperty... properties) throws Exception {
		AnalysisSubscription subscription = this.getSubscription(analysisTool, eventType, projectId, properties);
		return this.doSubscribe(subscription);
	}
	
	public String doSubscribe(AnalysisSubscription subscription) throws Exception {
		String json = this.getMapper().toJson(subscription);
		HttpPut request = new HttpPut(this.getSubscribe());
		StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
		request.setEntity(entity);
		return this.getString(request);
	}
	
	public String doUnsubscribe(String analysisTool, String eventType, Long projectId, AlertProperty... properties) throws Exception {
		AnalysisSubscription subscription = this.getSubscription(analysisTool, eventType, projectId, properties);
		return this.doUnsubscribe(subscription);
	}
	
	public String doUnsubscribe(AnalysisSubscription subscription) throws Exception {
		String json = this.getMapper().toJson(subscription);
		HttpPut request = new HttpPut(this.getUnsuscribe());
		StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
		request.setEntity(entity);
		return this.getString(request);
	}

}
