package weatherApplication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WeatherApplicationBackend {
	 //This method is called from the application.
	 public static ArrayList<JSONObject> getWeatherData(String locationName) {
		 
		 //The arraylist of JSONObjects that shall hold current weather data, weather data for the
		 //upcoming 4 days and city data.
		 ArrayList<JSONObject> weatherDataObjects = new ArrayList();
		 //First we must fetch latitude and longitude with our parameter given.
		 JSONArray locationData = (JSONArray) getCityLocationData(locationName);
		 //JSONObject that holds latitude, longitude, country code and the population of the searched city
		 JSONObject cityData = (JSONObject)locationData.get(0);
		 
		 //Geodata for the city.
		 double latitude = (double)cityData.get("latitude");
		 double longitude = (double) cityData.get("longitude");
		 //City population.
		 long population = (long) cityData.get("population");
		 //Country code for fetching the image of the countrys flag.
		 String CC = (String) cityData.get("country_code");
		 
		 //Url that returns weather info based on previously fetched latitude and longitude.
		 String weatherUrlString = "https://api.open-meteo.com/v1/forecast?latitude="+latitude+"&longitude="+
		 longitude+"&current=temperature_2m,relativehumidity_2m,weathercode,windspeed_10m&daily=weathercode,apparent_temperature_max,apparent_temperature_min,sunrise,sunset&timezone=Europe%2FMoscow";
		 
		 //Second, lets call the weather api and gather the results.
		 try {	
			HttpURLConnection conn = fetchApiResponse(weatherUrlString);
			//response code 200 = success.
			if(conn.getResponseCode()!= 200) {
				 System.out.println("API fetch error.");
				 return null;
			 }else {
				 //Read the data stream (JSON) from the connection with a scanner and create a new StringBuilder object out of it
				 StringBuilder fetchedJson = new StringBuilder();
				 Scanner scanner = new Scanner(conn.getInputStream());
				 
				 while(scanner.hasNextLine()) {
					 fetchedJson.append(scanner.nextLine());
				 }
				 scanner.close();
				 conn.disconnect();
				 
				 
				 JSONParser parser = new JSONParser();
				 //The string must be parsed to JSONObject.
				 JSONObject resultsJsonObj = (JSONObject) parser.parse(String.valueOf(fetchedJson));
				 
				 //This JSONObject holds current hour weather data for the city name given as a parameter
				 JSONObject currentData = (JSONObject)resultsJsonObj.get("current");
				 JSONObject dailyData = (JSONObject)resultsJsonObj.get("daily");
				 //We append the current weather, the weather for the next days and city data into an array that we return to the class handling the GUI.
				 weatherDataObjects.add(currentData);
				 weatherDataObjects.add(cityData);
				 weatherDataObjects.add(dailyData);
				 return weatherDataObjects;
				 
			 }
		} catch (IOException | ParseException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		 
	 }
	 
	 public static JSONArray getCityLocationData(String locationName)  {
		 String urlString = "https://geocoding-api.open-meteo.com/v1/search?name="+
				 			locationName+"&count=10&language=en&format=json";
		 try{
			 HttpURLConnection conn = fetchApiResponse(urlString);
			 
			 if(conn.getResponseCode()!=200) {
				 System.out.println("Connection to the API failed!");
				 return null;
		 } else {
			 //Read the data stream (JSON) from the connection with a scanner and create a new StringBuilder object out of it
			 StringBuilder fetchedJson = new StringBuilder();
			 Scanner scanner = new Scanner(conn.getInputStream());
			 
			 while(scanner.hasNext()) {
				 fetchedJson.append(scanner.nextLine());
			 }
			 conn.disconnect();
			 scanner.close();
			 
			 //Parsing the stringbuilder object into an JSONArray for easier manipulation.
			 JSONParser parser = new JSONParser();
			 JSONObject resultsJsonObj = (JSONObject) parser.parse(String.valueOf(fetchedJson));
			 JSONArray resultsJSON = (JSONArray) resultsJsonObj.get("results");
			 
			 return resultsJSON;
			 
		 }
		 }catch(IOException | ParseException e) {
			 e.printStackTrace();
		 }
		 return null;
	 }
	 
	 private static HttpURLConnection fetchApiResponse(String urlString) {
		 //Methdod for connecting to and url endpoint with GET-method.
		 //Returns a connection whose input stream contains the data.
		 try {
			 URL url = new URL(urlString);
			 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			 conn.setRequestMethod("GET");
			 
			 conn.connect();
			 return conn;
		 } catch(IOException e) {
			 e.printStackTrace();
		 }
		 return null;
	 }
	
	
}
