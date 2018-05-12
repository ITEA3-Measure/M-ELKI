package fr.icam.elki.clustering.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.icam.elki.clustering.clients.ElkiClusteringClient;
import fr.icam.elki.clustering.utils.Cluster;
import fr.icam.elki.clustering.utils.Distance;
import fr.icam.elki.clustering.utils.Instance;

public class KMeansElkiTesting {
	
	private static final Integer LENGTH = 10;
	
	private static final Integer LIMIT = 0;
	
	private ElkiClusteringClient client;
	
	private List<Instance> instances;

	@Test
	public void get() throws Exception {
		Map<String, Object> parameters = client.getKMeans();
		Assert.assertNotNull(parameters);
		Assert.assertNotNull(parameters.get("distance"));
		Assert.assertNotNull(parameters.get("length"));
		Assert.assertNotNull(parameters.get("limit"));
		for (String key : parameters.keySet()) {
			System.out.println(key + ": " + parameters.get(key));
		}
	}

	@Test
	public void put() throws Exception {
		boolean done = client.setKMeans(Distance.SquaredEuclidean, LENGTH.intValue(), LIMIT.intValue());
		Assert.assertTrue(done);
	}

	@Test
	public void post() throws Exception {
		List<Cluster> clusters = client.doKMeans(instances);
		Assert.assertNotNull(clusters);
		Assert.assertNotEquals(0, clusters.size());
	}
	
	@Before
	public void setup() throws Exception {
		client = new ElkiClusteringClient("http", "app.icam.fr", 80, "elki", 0L);
		int length = 10000;
		int size = 2;
		instances = new ArrayList<Instance>(length);
	    for (long i = 0; i < length; i++) {
	    	double[] values = new double[size];
	        for(int j = 0; j < size; j++) {
	        	double v = Math.random() * 100;
	        	values[j] = v;
	        }
	        Instance instance = new Instance("" + i, values);
	        instances.add(instance);
	    }
	}
	
}
