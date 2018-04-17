package fr.icam.elki.clustering.clients;

import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.httpclient.commons.AbstractClient;

import fr.icam.elki.clustering.utils.Cluster;
import fr.icam.elki.clustering.utils.Distance;
import fr.icam.elki.clustering.utils.Instance;

public class ElkiClusteringClient extends AbstractClient {
	
	private static final String DBSCAN = "dbscan";
	
	private static final String KMEANS = "kmeans";
	
	private static final String EM = "em";
	
	private static final String SLINK = "slink";
	
	public ElkiClusteringClient(String protocol, String hostname, int port, String appname) {
		super(protocol, hostname, port, appname);
	}

	public void setUp() throws Exception {
	}
	
	public void tearDown() throws Exception {
	}

	public Map<String, Object> getDBScan() throws Exception {
        HttpGet request = new HttpGet(this.getAppName() + DBSCAN);
        return this.getMap(request);
	}

	public Map<String, Object> getKMeans() throws Exception {
        HttpGet request = new HttpGet(this.getAppName() + KMEANS);
        return this.getMap(request);
	}

	public Map<String, Object> getEM() throws Exception {
        HttpGet request = new HttpGet(this.getAppName() + EM);
        return this.getMap(request);
	}

	public Map<String, Object> getSLink() throws Exception {
        HttpGet request = new HttpGet(this.getAppName() + SLINK);
        return this.getMap(request);
	}

	public Boolean setDBScan(Distance distance, Double epsilon, Integer size) throws Exception {
		URIBuilder builder = new URIBuilder(this.getAppName() + DBSCAN);
		builder.addParameter("distance", distance.toString().toLowerCase());
		builder.addParameter("epsilon", epsilon.toString());
		builder.addParameter("size", size.toString());
        HttpPut request = new HttpPut(builder.build());
        return this.getBoolean(request);
	}

	public Boolean setKMeans(Distance distance, Integer length, Integer limit) throws Exception {
		URIBuilder builder = new URIBuilder(this.getAppName() + KMEANS);
		builder.addParameter("distance", distance.toString().toLowerCase());
		builder.addParameter("length", length.toString());
		builder.addParameter("limit", limit.toString());
        HttpPut request = new HttpPut(builder.build());
        return this.getBoolean(request);
	}

	public Boolean setEM(Integer length, Double delta, Integer limit) throws Exception {
		URIBuilder builder = new URIBuilder(this.getAppName() + EM);
		builder.addParameter("delta", delta.toString());
		builder.addParameter("length", length.toString());
		builder.addParameter("limit", limit.toString());
		HttpPut request = new HttpPut(builder.build());
		return this.getBoolean(request);
	}

	public Boolean setSLink(Distance distance, Integer size) throws Exception {
		URIBuilder builder = new URIBuilder(this.getAppName() + SLINK);
		builder.addParameter("distance", distance.toString().toLowerCase());
		builder.addParameter("size", size.toString());
        HttpPut request = new HttpPut(builder.build());
        return this.getBoolean(request);
	}

	public List<Cluster> doDBScan(List<Instance> instances) throws Exception {
		 return this.doProcess(DBSCAN, instances);
	}

	public List<Cluster> doKMeans(List<Instance> instances) throws Exception {
		 return this.doProcess(KMEANS, instances);
	}

	public List<Cluster> doEM(List<Instance> instances) throws Exception {
		 return this.doProcess(EM, instances);
	}

	public List<Cluster> doSLink(List<Instance> instances) throws Exception {
		 return this.doProcess(SLINK, instances);
	}

	private List<Cluster> doProcess(String algorithm, List<Instance> instances) throws Exception {
		 HttpPost request = new HttpPost(this.getAppName() + algorithm);
		 String string = this.getContent(instances);
		 StringEntity entity = new StringEntity(string);
		 request.setEntity(entity);
         return this.getList(Cluster[].class, request);
	}
	
	private String getContent(List<Instance> instances) {
		int length = instances.size();
		Instance item = instances.get(0);
		int size = item.size();
		StringBuilder builder = new StringBuilder(length * size * 5);
		builder.append(length);
		builder.append(",");
		builder.append(size);
		for (Instance instance : instances) {
			builder.append("\n");
			builder.append(instance.getId());
			for (Double value : instance) {
				builder.append(",");
				builder.append(value);
			}
		}
		return builder.toString();
	}

}
