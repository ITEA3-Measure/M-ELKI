package org.apache.httpclient.commons;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public abstract class AbstractClient {
	
	private Gson mapper;
    
    protected Gson getMapper() {
    	return mapper;
    }
	
    private HttpHost host;
    
    private CloseableHttpClient client;
    
    private String appname;
    
    protected String getAppName() throws NullPointerException {
    	return appname;
    }
    
	public AbstractClient(String protocol, String hostname, int port, String appname) {
		this.mapper = new Gson();
        this.host = new HttpHost(hostname, port, protocol);
        this.client = HttpClients.createDefault();
        this.appname =  appname.startsWith("/") ? appname : "/" + appname;
        this.appname += this.appname.endsWith("/") ? "" : "/";
	}

	public void setUp() throws Exception {
	}
	
	public void tearDown() throws Exception {
		client.close();
	}

	protected Map<String, Object> getMap(HttpRequestBase request) throws IOException, ClientProtocolException {
		HttpResponse response = client.execute(host, request);
		HttpEntity entity = response.getEntity();
		String message = EntityUtils.toString(entity);
		int status = response.getStatusLine().getStatusCode();
		EntityUtils.consume(entity);
		if (status == 200) { 
			Type type = new TypeToken<Map<String, Object>>() { }.getType();
			return mapper.fromJson(message, type);
		} else {
			throw new IOException("error code: " + status + " '" + message + "'");
		}
	}
	
	protected <T> List<T> getList(Class<T[]> type, HttpRequestBase request) throws IOException, ClientProtocolException {
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
	
	protected <T> T getObject(HttpRequestBase request, Class<T> type) throws IOException, ClientProtocolException {
		HttpResponse response = client.execute(host, request);
		HttpEntity entity = response.getEntity();
		String message = EntityUtils.toString(entity);
		int status = response.getStatusLine().getStatusCode();
		EntityUtils.consume(entity);
		if (status == 200) {
			return mapper.fromJson(message, type);
		} else {
			throw new IOException("error code: " + status + " '" + message + "'");
		}
	}
	
	protected Boolean getBoolean(HttpRequestBase request) throws IOException, ClientProtocolException {
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
	
	protected String getString(HttpRequestBase request) throws IOException, ClientProtocolException {
		HttpResponse response = client.execute(host, request);
		HttpEntity entity = response.getEntity();
		String message = EntityUtils.toString(entity);
		int status = response.getStatusLine().getStatusCode();
		EntityUtils.consume(entity);
		if (status == 200) {
			return message;
		} else {
			throw new IOException("error code: " + status + " '" + message + "'");
		}
	}

}
