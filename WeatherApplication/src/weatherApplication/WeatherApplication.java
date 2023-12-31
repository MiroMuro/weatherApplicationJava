package weatherApplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WeatherApplication extends JFrame{
	//This variable holds current weather data
	private JSONObject currentWeatherData;
	//This varialbe holds data about the city
	private JSONObject cityData;
	//This variable holds data for the upcoming weather,
	private JSONObject upcomingWeatherData;
	//Labels for displaying information about upcoming weather.
	private ArrayList weekDays;
	
	public WeatherApplication(){
		 super("Weather application");
		 
		 
		 //Alustetaan ikkuna
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
		 
		 setSize(900,650);
		 
		 setResizable(false);
		 //Ikkuna aukeaa aina ruudun keskellä.
		 setLocationRelativeTo(null);
		
		 GuiComponentsInit(); 
	}
	private void GuiComponentsInit() {
		//Luodaan motherPaneeli, johon kaikki muut komponentit alustetaan
		JLayeredPane motherPane = new JLayeredPane();
		//Background pane.
		 try {
				BufferedImage tausta = ImageIO.read(new File("src/assets/bg.jpg"));
				JLabel taustaLabel = new JLabel(new ImageIcon(tausta));
				taustaLabel.setBounds(0,0,900,650);
				motherPane.add(taustaLabel,1,0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		 

		 
		 //Nykyhetken säätilan näyttäminen
		 JLabel currentWeatherIcon = new JLabel(imageLoader("src/assets/sunny.png"));
		 currentWeatherIcon.setBounds(30,130,150,150);
		 motherPane.add(currentWeatherIcon,2,0);
		 
		 //Nykyisen kaupungin nimi
		 JLabel cityName = new JLabel("");
		 cityName.setBounds(120,20,250,100);
		 cityName.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		 cityName.setForeground(Color.white);
		 motherPane.add(cityName,2,0);
		 
		 //Population txt
		 JLabel cityPopulation = new JLabel("");
		 cityPopulation.setBounds(120,40,250,100);
		 cityPopulation.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		 cityPopulation.setForeground(Color.white);
		 motherPane.add(cityPopulation,2,0);
		 
		 //Current country flag icon
		 JLabel flagIcon = new JLabel("");
		 flagIcon.setBounds(50,50,64,64);
		 motherPane.add(flagIcon,2,0);
		 
		 //Current temp. text field.
		 JLabel degreesText = new JLabel("");
		 degreesText.setFont(new Font("Times New Roman", Font.BOLD, 18));
		 degreesText.setForeground(Color.white);
		 degreesText.setBounds(230,100,100,100);
		 motherPane.add(degreesText,2,0);
		 
		 //Temp. icon
		 JLabel degreesIcon = new JLabel(imageLoader("src/assets/temp.png"));
		 degreesIcon.setBounds(180,130,50,50);
		 motherPane.add(degreesIcon,2,0);
		 
		 
		 //Air moisture text field
		 JLabel humidity = new JLabel("");
		 humidity.setFont(new Font("Times New Roman", Font.BOLD, 18));
		 humidity.setForeground(Color.white);
		 humidity.setBounds(230,150,140,100);
		 motherPane.add(humidity,2,0);
		 
		 //Air moisture icon
		 JLabel humidityIcon = new JLabel(imageLoader("src/assets/wd50.png"));
		 humidityIcon.setBounds(176,180,50,50);
		 motherPane.add(humidityIcon,2,0);
		 
		 //Winspeed text field
		 JLabel windspeed = new JLabel("");
		 windspeed.setFont(new Font("Times New Roman",Font.BOLD, 18));
		 windspeed.setForeground(Color.white);
		 windspeed.setBounds(230,200,100,100);
		 motherPane.add(windspeed,2,0);
		 
		 //Winspeed icon
		 JLabel windspeedIcon = new JLabel(imageLoader("src/assets/wind.png"));
		 windspeedIcon.setBounds(180,230,50,50);
		 motherPane.add(windspeedIcon,2,0);
		 
		 //Luodaan hakukenttä.
		 JTextField searchBar = new JTextField("");
		 searchBar.setBounds(30,20,400,30);
		 motherPane.add(searchBar,2,0);
		 
		 //Luodaan etsi-nappi
		 JButton searchButton = new JButton(imageLoader("src/assets/searchIcon.png"));
		 searchButton.setBounds(430,20,30,30);
		 searchButton.addActionListener(new ActionListener(){
			//Method for handling the click of the search button.
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = searchBar.getText();
				//Dont proceed if input is blank
				if(input.replaceAll("\\s", "").length()<=0) {
					return;
				}
				//Replace whitespace with plus sign in input for api. 
				//for example new york = new+york. Keep the old one for the city name Jlabel
				String Newinput = input.replaceAll(" ", "+");
				ArrayList weatherData = WeatherApplicationBackend.getWeatherData(Newinput);
				currentWeatherData = (JSONObject) weatherData.get(0);
				cityData = (JSONObject) weatherData.get(1);
				//This
				upcomingWeatherData = (JSONObject) weatherData.get(2);
				//JSONArray aids = (JSONArray)upcomingWeatherData.get("time");
				
				//Updating JLabels with current weather data.
				degreesText.setText("<html>Temperature: </br>"+currentWeatherData.get("temperature_2m")+"°C </br> </html>");
				humidity.setText(("<html>Air humidity: </br>"+currentWeatherData.get("relativehumidity_2m")+"% </html>"));
				windspeed.setText("<html>Windspeed: </br>"+currentWeatherData.get("windspeed_10m")+" km/h");
				//Set city name and population
				cityName.setText((String) cityData.get("country")+", "+(String) cityData.get("name"));
				//Add spaces between zeroes for easier reading.
				String population = String.format("%,d",cityData.get("population"));
				cityPopulation.setText("Population: "+population);
				
				//Translate the weathercode into a string and fetch and place the corresponding image.
				String weatherCondition = translateWeatherCode((long) currentWeatherData.get("weathercode"));
				try {
					currentWeatherIcon.setIcon(imageLoaderUrl(weatherCondition));
					flagIcon.setIcon(imageLoaderUrl("https://flagsapi.com/"+(String)cityData.get("country_code")+"/flat/64.png"));
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				motherPane.add(displayWeatherForNextDays(upcomingWeatherData),2,0);
				//Clear the search bar after every search.
				searchBar.setText("");
			}
			 
		 });
		 motherPane.add(searchButton,2,0);
		 
		 add(motherPane);
		 
		
		 
	 }
	//Used for downloading offline images
	private ImageIcon imageLoader(String filepath){
		
		try {
			BufferedImage img = ImageIO.read(new File(filepath));
			
			return new ImageIcon(img);
		} catch(IOException e) {
			System.out.println("Image not found.");
		return null;
	}	 
}		//Used for downloading images from the web.
	private ImageIcon imageLoaderUrl(String link) throws MalformedURLException {
		try {
			URL url = new URL(link);
			BufferedImage img;
			img = ImageIO.read(url);
			ImageIcon icon = new ImageIcon(img);
			return icon;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	private JPanel displayWeatherForNextDays(JSONObject data) {
		
		//Return a panel with weather information for the next three days.
		JPanel panel = new JPanel();
		Color weatherPanelBackground = new Color(60,114,175,200);
		panel.setBackground(weatherPanelBackground);
		panel.setBounds(30,310,815,270);
		
		//Dates to be translated into weekdays.
		JSONArray days = (JSONArray) data.get("time");
		//Weathercodes to be translated into weather icon links
		JSONArray weatherCodes = (JSONArray) data.get("weathercode");
		JSONArray apparentTemperaturesMin = (JSONArray) data.get("apparent_temperature_min");
		JSONArray apparentTemperatureMax = (JSONArray) data.get("apparent_temperature_max");
		System.out.println(data);
		panel.setLayout(null);
		
		//Weekday jlables.		
		JLabel firstDay = new JLabel(getWeekdayAsString((days.get(1).toString())));
		firstDay.setBounds(120,0,150,50);
		firstDay.setFont(new Font("Arial Black",Font.ITALIC,18));
		firstDay.setForeground(Color.white);
		
		JLabel secondDay = new JLabel(getWeekdayAsString((days.get(2).toString())));
		secondDay.setBounds(350,0,150,50);
		secondDay.setFont(new Font("Arial Black",Font.ITALIC,18));
		secondDay.setForeground(Color.white);
		
		JLabel thirdDay = new JLabel(getWeekdayAsString(days.get(3).toString()));
		thirdDay.setBounds(580,0,150,50);
		thirdDay.setFont(new Font("Arial Black", Font.ITALIC, 18));
		thirdDay.setForeground(Color.white);
		
		//Temperature labes for the next days
		JLabel firstDayTemp = new JLabel(apparentTemperaturesMin.get(1)+"°C..   .."+apparentTemperatureMax.get(1)+"°C");
		firstDayTemp.setBounds(110,200,120,50);
		firstDayTemp.setFont(new Font("Arial Black",Font.BOLD,13));
		firstDayTemp.setForeground(Color.white);
		
		JLabel secondDayTemp = new JLabel(apparentTemperaturesMin.get(2)+"°C..   .."+apparentTemperatureMax.get(2)+"°C");
		secondDayTemp.setBounds(340,200,120,50);
		secondDayTemp.setFont(new Font("Arial Black", Font.BOLD, 13));
		secondDayTemp.setForeground(Color.white);
		
		JLabel thirdDayTemp = new JLabel(apparentTemperaturesMin.get(3)+"°C..   .."+apparentTemperatureMax.get(3)+"°C");
		thirdDayTemp.setBounds(570,200,120,50);
		thirdDayTemp.setFont(new Font("Arial Black", Font.BOLD, 13));
		thirdDayTemp.setForeground(Color.white);
		
		
		panel.add(secondDayTemp);
		panel.add(firstDayTemp);
		panel.add(thirdDayTemp);
		try {
			JLabel firstDayWeatherIcon = new JLabel((imageLoaderUrl(translateWeatherCode((long) weatherCodes.get(1)))));
			firstDayWeatherIcon.setBounds(90,50,144,144);
			JLabel secondDayWeatherIcon = new JLabel((imageLoaderUrl(translateWeatherCode((long) weatherCodes.get(2)))));
			secondDayWeatherIcon.setBounds(325,50,144,144);
			JLabel thirdDayWeatherIcon = new JLabel((imageLoaderUrl(translateWeatherCode((long) weatherCodes.get(3)))));
			thirdDayWeatherIcon.setBounds(565,50,144,144);
			
			panel.add(firstDayWeatherIcon);
			panel.add(secondDayWeatherIcon);
			panel.add(thirdDayWeatherIcon);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panel.add(firstDay);
		panel.add(secondDay);
		panel.add(thirdDay);
		return panel;
		
	};
	private String getWeekdayAsString (String datum) {
		LocalDate date = LocalDate.parse(datum);
		return date.getDayOfWeek().toString();	
	}
	//Returns a corresponding image link to a weather code.
	private String translateWeatherCode(long weatherCode) {
	
	switch((int)weatherCode) {
		case 0:
			return "https://img.icons8.com/color/144/sun--v1.png";
			//Sunny
		case 1:
			return "https://img.icons8.com/fluency/144/weather.png";
			//Mainly clear
		case 2:
			return "https://img.icons8.com/fluency/144/partly-cloudy-day.png";
			//Partly cloudy
		case 3:
			return "https://img.icons8.com/fluency/144/clouds--v3.png";
			//Overcast
		case 45:
		case 48:
			return "https://img.icons8.com/emoji/144/fog.png";
			//fog
		case 51:
		case 53:
		case 55:
			return "https://img.icons8.com/color/144/light-rain--v1.png";
			//drizzle
		case 56:
		case 57:
			return "https://img.icons8.com/fluency/144/sleet.png";
			//freezing drizzle
		case 61:
			return "https://img.icons8.com/officel/144/light-rain.png";
			//light rain
		case 63:
			return "https://img.icons8.com/office/144/moderate-rain.png";
			//moderate rain
		case 65:
			return "https://img.icons8.com/color/144/downpour--v1.png";
			//
		case 66:
		case 67:
			return "https://img.icons8.com/badges/144/sleet.png";
			//freezing rain.
		case 71:
		case 77:
			return "https://img.icons8.com/color/144/light-snow--v1.png";
			//light snow
		case 73:
			return "https://img.icons8.com/office/144/snow.png";
			//moderate snow
		case 75:
			return "https://img.icons8.com/external-smashingstocks-basic-outline-smashing-stocks/144/external-Snowstorm-natural-disaster-icon-smashingstocks-basic-outline-smashing-stocks.png";
			//heavy snow
		case 80:
		case 81:
		case 82:
			return "https://img.icons8.com/color/144/torrential-rain.png";
			//rain shower
		case 85:
		case 86:
			return "https://img.icons8.com/ios/144/snow.png";
			//snow shower
		case 95:
		case 96:
		case 99:
			return "https://img.icons8.com/officel/144/cloud-lighting.png";
			//thunder
		
			
		}
	return null;
			
		
}
	
};
