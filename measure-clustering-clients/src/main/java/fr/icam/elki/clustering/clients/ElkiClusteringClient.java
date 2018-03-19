package fr.icam.elki.clustering.clients;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import fr.icam.elki.clustering.utils.Cluster;
import fr.icam.elki.clustering.utils.Distance;
import fr.icam.elki.clustering.utils.Instance;

public class ElkiClusteringClient {
	
	private static final String DBSCAN = "dbscan";
	
	private static final String KMEANS = "kmeans";
	
	private static final String EM = "em";
	
	private static final String SLINK = "slink";
	
	private Gson mapper;
	
    private HttpHost host;
    
    private CloseableHttpClient client;
    
    private String name;
	
	public ElkiClusteringClient(String protocol, String hostname, int port, String appname) {
		mapper = new Gson();
        host = new HttpHost(hostname, port, protocol);
        client = HttpClients.createDefault();
        name =  appname.startsWith("/") ? appname : "/" + appname;
        name += appname.endsWith("/") ? "" : "/";
	}

	public void setUp() throws Exception {
	}
	
	public void tearDown() throws Exception {
		client.close();
	}

	public Boolean setDBScan(Distance distance, Double epsilon, Integer size) throws Exception {
		URIBuilder builder = new URIBuilder(name + DBSCAN);
		builder.addParameter("distance", distance.toString().toLowerCase());
		builder.addParameter("epsilon", epsilon.toString());
		builder.addParameter("size", size.toString());
        HttpPut request = new HttpPut(builder.build());
        return this.getBoolean(request);
	}

	public Boolean setKMeans(Distance distance, Integer length, Integer limit) throws Exception {
		URIBuilder builder = new URIBuilder(name + KMEANS);
		builder.addParameter("distance", distance.toString().toLowerCase());
		builder.addParameter("length", length.toString());
		builder.addParameter("limit", limit.toString());
        HttpPut request = new HttpPut(builder.build());
        return this.getBoolean(request);
	}

	public Boolean setEM(Integer length, Double delta, Integer limit) throws Exception {
		URIBuilder builder = new URIBuilder(name + EM);
		builder.addParameter("delta", delta.toString());
		builder.addParameter("length", length.toString());
		builder.addParameter("limit", limit.toString());
        HttpPut request = new HttpPut(builder.build());
        return this.getBoolean(request);
	}

	public Boolean setSLink(Distance distance, Integer size) throws Exception {
		URIBuilder builder = new URIBuilder(name + SLINK);
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
		 HttpPost request = new HttpPost(name + algorithm);
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
	
	private <T> List<T> getList(Class<T[]> type, HttpRequestBase request) throws IOException, ClientProtocolException {
		HttpResponse response = client.execute(host, request);
        HttpEntity entity = response.getEntity();
        String message = EntityUtils.toString(entity);
        int status = response.getStatusLine().getStatusCode();
        EntityUtils.consume(entity);
        if (status == 200) {
        	return Arrays.asList(mapper.fromJson(message, type));
        } else {
        	throw new IOException("error code: " + status + " '" + message + "'");
        }
	}
	
	private Boolean getBoolean(HttpRequestBase request) throws IOException, ClientProtocolException {
		HttpResponse response = client.execute(host, request);
		HttpEntity entity = response.getEntity();
		String message = EntityUtils.toString(entity);
		int status = response.getStatusLine().getStatusCode();
		EntityUtils.consume(entity);
		if (status == 200) {
			return Boolean.valueOf(message);
		} else {
			throw new IOException("error code: " + status + " '" + message + "'");
		}
	}

}
