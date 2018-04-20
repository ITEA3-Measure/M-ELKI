package eu.measure.platform.api;

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
	
}
