package com.smart.sprinkler.rs.clients;

import java.net.URI;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MoistureSensorRestClient {

	public static void main(String[] args) {
		getMoistureData();
	}
	public static Long getMoistureData()
	{
		Map moistureData=null;
		try
		{
			ClientConfig config= new ClientConfig();
			Client client = ClientBuilder.newClient(config);
			WebTarget target = client.target(getBaseURI());
			String jsonString = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);
			JSONObject json = (JSONObject) new JSONParser().parse(jsonString);
			moistureData = (Map)json.get("variables");
		}
		catch(Exception ex)
		{
			System.out.println("Unexpected exception occurred :"+ex.getMessage()); 
		}

		System.out.println("MoistureData Retrieve :"+(Long)moistureData.get("digitalMoistureLevel"));
		return (Long)moistureData.get("digitalMoistureLevel");
	}

	private static URI getBaseURI( ) {
		String ip = SensorIPRestClient.getConfig();
		String url = "http://"+ip+":80";
		System.out.println("connecting to Moisture :"+url);
		return UriBuilder.fromUri(url).build();
	}
}
