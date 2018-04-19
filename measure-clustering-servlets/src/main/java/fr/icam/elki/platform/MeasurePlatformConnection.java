package fr.icam.elki.platform;

import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import eu.measure.platform.analysis.api.MeasurePlatformClient;

public class MeasurePlatformConnection extends HttpServlet {

	private static final long serialVersionUID = 201804191520001L;

	private static final String NAME = "M·ELKI";
	
	private static final String DESC = "Clustering Algorithms dedicated to the MEASURE Platform and based on the ELKI Java Library";
	
	private static final String HOST = "http://emit.icam.fr/elki";

	
	private MeasurePlatformClient client;
	
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			System.out.println("configuring ...");
			client = new MeasurePlatformClient("http", "194.2.241.244", 80, "/measure");
			System.out.println("connecting ...");
			client.setUp();
			System.out.println("connected ...");
			System.out.println("registering ...");
			client.doRegister(URI.create(HOST).toURL(), DESC, NAME);
			System.out.println("registered ...");
			System.out.println("configured ...");
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
}
