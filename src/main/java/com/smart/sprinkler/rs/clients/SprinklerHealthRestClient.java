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

public class SprinklerHealthRestClient {


	public static void main(String[] args) {
		postToServer();
	}

	public static void postToServer()
	{
		try
		{
			ClientConfig config= new ClientConfig();
			Client client = ClientBuilder.newClient(config);
			WebTarget target = client.target(getBaseURI());
			String request = "{\"id\":\"1\",\"customerId\":\"4000\"}";
			Response jsonString = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.json(request));
			System.out.println("Response for SprinklerHealthRestClient:"+jsonString);
		}
		catch(Exception ex)
		{
			System.out.println("Unexpected exception occurred in  SprinklerHealthRestClient:"+ex.getMessage()); 
		}
	}


	private static URI getBaseURI( ) {
		String url = "http://139.59.1.216:8080/api/v1/sprinklerHealth";
		System.out.println("connecting to SprinklerHealthRestClient:"+url);
		return UriBuilder.fromUri(url).build();
	}
}
