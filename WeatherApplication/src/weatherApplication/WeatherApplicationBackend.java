package weatherApplication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WeatherApplicationBackend {
	
	 public static JSONObject getWeatherData(String locationName) {
		 
		 JSONArray locationData = (JSONArray) getLocationData(locationName);
		 
		 JSONObject cityData = (JSONObject)locationData.get(0);
		 System.out.println(cityData);
		 
		 System.out.println(cityData.get("country_code"));
		 System.out.println(cityData.get("population"));
		 System.out.println(cityData.get("latitude"));
		 System.out.println(cityData.get("longitude"));
		 
		 
		 return null;
		 
	 }
	 
	 public static JSONArray getLocationData(String locationName)  {
		 String urlString = "https://geocoding-api.open-meteo.com/v1/search?name="+
				 			locationName+"&count=10&language=en&format=json";
		 try{
			 HttpURLConnection conn = fetchApiResponse(urlString);
			 
			 if(conn.getResponseCode()!=200) {
				 System.out.println("Connection to the API failed!");
				 return null;
		 } else {
			 StringBuilder fetchedJson = new StringBuilder();
			 Scanner scanner = new Scanner(conn.getInputStream());
			 
			 while(scanner.hasNext()) {
				 fetchedJson.append(scanner.nextLine());
			 }
			 conn.disconnect();
			 scanner.close();
			 
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
