package com.smart.sprinkler.weather;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.client.*;

import org.glassfish.jersey.client.ClientConfig;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


@SuppressWarnings("unused")
public class WeatherAPI {
	
	public static String getChanceOfRain(String location) {
		// TODO Auto-generated method stub
		String chanceOfRain=null;
		try{
			boolean indicator=false;

			ClientConfig config = new ClientConfig();
			Client client = ClientBuilder.newClient(config);
			
			WebTarget target = client.target(getBaseURI(location));
			//System.out.println(target.path("data").request().accept(MediaType.APPLICATION_JSON).get(String.class));
			String jsonString = target.path("data").request().accept(MediaType.APPLICATION_JSON).get(String.class);




			JSONObject json = (JSONObject) new JSONParser().parse(jsonString);
			//System.out.println("JSON:"+json);
			Map weatherMap = (Map)json.get("data");
			JSONArray  weather = (JSONArray ) weatherMap.get("weather");
			//System.out.println("key==>"+weather);


			for(int j=0;j<2;j++){

				JSONObject hoourly = (JSONObject)weather.get(j);


				JSONArray timearray1=(JSONArray)hoourly.get("hourly");


				SimpleDateFormat timeformat=new SimpleDateFormat("HH:mm");

				String currentTime= timeformat.format(new Date());

				//System.out.println(currentTime);


				String timespilt[]=currentTime.split(":");

				//System.out.println("CurrentHour="+timespilt[0]);
				// System.out.println("CurrentMinute="+timespilt[1]);

				int currentHour=Integer.parseInt(timespilt[0]);
				int currentMinute=Integer.parseInt(timespilt[1]);

				if(j==1)
				{

					JSONObject rain1 = (JSONObject) timearray1.get(0);

					System.out.println("Rain==" + rain1.get("chanceofrain"));
					chanceOfRain = (String)rain1.get("chanceofrain");
					break;

				}


				for(int i=0;i<timearray1.size();i++)
				{
					JSONObject rain1=(JSONObject)timearray1.get(i);



					String time=(String)rain1.get("time");
					//System.out.println("Time="+time);
					// System.out.println(time.substring(time.length()-2,time.length()));
					//System.out.println(time.substring(0, time.length()-2));



					int jsonHour=Integer.parseInt(time.substring(0, time.length()-2));
					int jsonMinute=Integer.parseInt(time.substring(time.length()-2,time.length()));

					if (currentHour > jsonHour) {

						// Do nothing keep quiet

					}

					else if (currentHour < jsonHour) {

						//System.out.println("Rain=" + rain1.get("chanceofrain"));
						chanceOfRain = (String)rain1.get("chanceofrain");
						indicator=true;
						break;

					}

					else if (currentHour == jsonHour) {
						if (currentMinute > jsonMinute) {
							// Do nothing keep quiet

						} else if (currentMinute < jsonMinute) {

							//System.out.println("Rain=" + rain1.get("chanceofrain"));
							chanceOfRain = (String)rain1.get("chanceofrain");
							indicator=true;
							break;

						}

						else if (currentMinute == jsonMinute) {
							// pick the next JsonHour
							// Do Nothing

						}
					}

				}

				if(indicator)
				{
					break;
				}

			}
			
		}
		catch(Exception exp)
		{
			System.out.println("Exception in WeatherAPI"+exp.getMessage());
			exp.printStackTrace();
		}
		return chanceOfRain;
	     
	}
	
	private static URI getBaseURI(String location) {
	    return UriBuilder.fromUri("https://api.worldweatheronline.com/free/v2/weather.ashx?q="+location+"&format=json&key=e6d42628af0a8eca8a6266ba8fdbc").build();
	  }

}