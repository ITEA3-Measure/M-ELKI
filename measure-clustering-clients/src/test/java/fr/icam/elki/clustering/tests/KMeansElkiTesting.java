package fr.icam.elki.clustering.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import de.lmu.ifi.dbs.elki.data.Clustering;
import de.lmu.ifi.dbs.elki.data.model.KMeansModel;
import de.lmu.ifi.dbs.elki.database.Database;
import fr.icam.elki.clustering.KMeansElkiClustering;
import fr.icam.elki.clustering.clients.ElkiClusteringClient;
import fr.icam.elki.clustering.utils.Cluster;
import fr.icam.elki.clustering.utils.Distance;
import fr.icam.elki.clustering.utils.Instance;

public class KMeansElkiTesting extends KMeansElkiClustering {

	private static final long serialVersionUID = 20180305140000L;
	
	private static final Integer LENGTH = 10;
	
	private static final Integer LIMIT = 0;
	
	private ElkiClusteringClient client;
	
	private Gson gson;
	
	private String string;
	
	private List<Instance> instances;
	
	public List<Cluster> local() throws Exception {
		InputStream input = new ByteArrayInputStream(string.getBytes());
		Database database = this.getDatabase(input);
		Clustering<KMeansModel> clusters = this.doProcess(database);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		this.doPrint(database, clusters, output);
		String string = new String(output.toByteArray());
		Cluster[] array = gson.fromJson(string, Cluster[].class);
		return Arrays.asList(array);
	}
	
	public List<Cluster> remote() throws Exception {
		boolean done = client.setKMeans(Distance.SquaredEuclidean, LENGTH, LIMIT);
		Assert.assertTrue(done);
		List<Cluster> clusters = client.doKMeans(instances);
		Assert.assertNotNull(clusters);
		Assert.assertNotEquals(0, clusters.size());
		return clusters;
	}
	
	@Test
	public void test() {
		try {
			List<Cluster> lClusters = local();
			List<Cluster> rClusters = remote();
			int lSize = lClusters.size();
			int rSize = rClusters.size();
			System.out.println(lSize + " == " + rSize);
			Assert.assertEquals(lSize, rSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void setup() throws Exception {
		client = new ElkiClusteringClient("http", "172.21.50.1", 8080, "elki");
		gson = new Gson();
		this.init();
		this.setDistance(Distance.SquaredEuclidean.toString());
		this.setLength(LENGTH.toString());
		this.setLimit(LIMIT.toString());
		int length = 10000;
		int size = 2;
		instances = new ArrayList<Instance>(length);
	    string = this.setData(length, size);
	}

	private String setData(int length, int size) {
		StringBuilder builder = new StringBuilder(length * size * 5);
		builder.append(length);
		builder.append(",");
		builder.append(size);
		builder.append("\n");
	    for (long i = 0; i < length; i++) {
	    	builder.append(i);
	    	double[] values = new double[size];
	        for(int j = 0; j < size; j++) {
	        	builder.append(",");
	        	double v = Math.random() * 100;
	        	builder.append(v);
	        	values[j] = v;
	        }
	        builder.append("\n");
	        Instance instance = new Instance("" + i, values);
	        instances.add(instance);
	    }
		return builder.toString();
	}
	
}
