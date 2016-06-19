package com.smart.sprinkler.rs.clients;

import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MotorRequestRestClient {
	public static void main(String[] args) {
		getConfig();
	}
	public static String getConfig()
	{
		String motorFlag = null;
		try
		{
			ClientConfig config= new ClientConfig();
			Client client = ClientBuilder.newClient(config);
			WebTarget target = client.target(getBaseURI());
			String jsonString = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);
			JSONObject json = (JSONObject) new JSONParser().parse(jsonString);
			motorFlag = (String)json.get("motorFlag");
			System.out.println(motorFlag);
		}
		catch(Exception ex)
		{
			System.out.println("Unexpected exception occurred in  MotorRequestRestClient:"+ex.getMessage()); 
		}
		return motorFlag;
	}
	private static URI getBaseURI( ) {
		String url = "http://139.59.1.216:8080/api/v1/motor/1";
		System.out.println("connecting to MotorRequestRestClient:"+url);
		return UriBuilder.fromUri(url).build();
	}
}
