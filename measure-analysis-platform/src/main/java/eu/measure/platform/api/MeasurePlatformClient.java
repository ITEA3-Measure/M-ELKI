package eu.measure.platform.api;

import java.util.List;

import org.apache.http.client.methods.HttpGet;
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
	
	private String getProjectMeasureInstances(String id) throws NullPointerException {
		return this.getAppName() + "/api/project-measure-instances/" + id;
	}

	public List<MeasureInstance> getProjectMeasureInstances(Long id) throws Exception {
        HttpGet request = new HttpGet(this.getProjectMeasureInstances(id.toString()));
        return this.getList(MeasureInstance[].class, request);
	}
	
}
