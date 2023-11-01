package weatherApplication;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.simple.JSONObject;

public class WeatherApplication extends JFrame{
	private JSONObject currentWeatherData;
	public WeatherApplication(){
		 super("Weather application");
		 
		 
		 //Alustetaan ikkuna
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
		 
		 setSize(800,550);
		 
		 setResizable(false);
		 //Ikkuna aukeaa aina ruudun keskellä.
		 setLocationRelativeTo(null);
		 
		
		 GuiComponentsInit(); 
	}
	private void GuiComponentsInit() {
	  //Luodaan motherPaneeli, johon kaikki muut komponentit alustetaan
		
		JLayeredPane motherPane = new JLayeredPane();
		
		 
		 
		 try {
				BufferedImage tausta = ImageIO.read(new File("src/assets/bg.jpg"));
				JLabel taustaLabel = new JLabel(new ImageIcon(tausta));
				taustaLabel.setBounds(0,0,800,550);
				motherPane.add(taustaLabel,1,0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		 
		 
		 //Paneeli tulevan viikon sääennustetta varten.
		 Color weatherPanelBackground = new Color(60,114,175,150);
		 JPanel weatherPanel = new JPanel();
		 weatherPanel.setBackground(weatherPanelBackground);
		 weatherPanel.setBounds(30,310,715,170);
		 motherPane.add(weatherPanel,2,0);
		 
		 //Nykyhetken säätilan näyttäminen
		 JLabel currentWeatherDataWeatherIcon = new JLabel(imageLoader("src/assets/sunny.png"));
		 currentWeatherDataWeatherIcon.setBounds(30,130,150,150);
		 motherPane.add(currentWeatherDataWeatherIcon,2,0);
		 
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
		 
		 
		 /*windspeed gif
		 JLabel gifLabel = new JLabel("");
		 ImageIcon gif = new ImageIcon("src/assets/newsun.gif");
		 gifLabel.setIcon(gif);
		 gifLabel.setBounds(100,100,100,100);
		 motherPane.add(gifLabel,2,0);
		 */
		 
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
				ArrayList weatherData = WeatherApplicationBackend.getWeatherData(input);
				//This variable holds current weather data
				currentWeatherData = (JSONObject) weatherData.get(0);
				//Updating JLabels with current weather data.
				degreesText.setText("<html>Temperature: </br>"+currentWeatherData.get("temperature_2m")+"°C </br> </html>");
				humidity.setText(("<html>Air humidity: </br>"+currentWeatherData.get("relativehumidity_2m")+"% </html>"));
				windspeed.setText("<html>Windspeed: </br>"+currentWeatherData.get("windspeed_10m")+" km/h");
				
				
				
				// TODO Auto-generated method stub
				
			}
			 
		 });
		 motherPane.add(searchButton,2,0);
		 
		 add(motherPane);
		 
		
		 
	 }
	
	private ImageIcon imageLoader(String filepath){
		
		try {
			BufferedImage img = ImageIO.read(new File(filepath));
			
			return new ImageIcon(img);
		} catch(IOException e) {
			System.out.println("Image not found.");
		return null;
	}	 
}	private void setCurrentWeatherInformation (JSONObject weatherData) {
		
		
}
	
};
