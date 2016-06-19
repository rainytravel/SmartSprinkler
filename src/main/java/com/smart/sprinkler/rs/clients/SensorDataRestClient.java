package com.smart.sprinkler.rs.clients;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class SensorDataRestClient {

	public static void main(String[] args) {
		postToServer(50L, 0L);
	}

	public static void postToServer(long digitalReading, long analogReading)
	{
		try
		{
			ClientConfig config= new ClientConfig();
			Client client = ClientBuilder.newClient(config);
			WebTarget target = client.target(getBaseURI());
			String request = "{\"id\":\"1\",\"analogValue\":\""+analogReading+"\",\"digitalValue\":\""+digitalReading+"\",\"sensorType\":\"MS\",\"customerId\":\"4000\"} ";
			Response jsonString = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.json(request));
			System.out.println("Response for SensorDataRestClient run:"+jsonString);
		}
		catch(Exception ex)
		{
			System.out.println("Unexpected exception occurred in  SensorDataRestClient:"+ex.getMessage()); 
		}
	}

	private static URI getBaseURI( ) {
		String url = "http://139.59.1.216:8080/api/v1/sensorUnit";
		System.out.println("connecting to SensorDataRestClient:"+url);
		return UriBuilder.fromUri(url).build();
	}


}
