package weatherApplication;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WeatherApplication extends JFrame{
	
	public WeatherApplication(){
		 super("Weather application");
		 
		 
		 //Alustetaan ikkuna
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
		 
		 setSize(800,550);
		 
		 //Ikkuna aukeaa aina ruudun keskellä.
		 setLocationRelativeTo(null);
		 
		
		 GuiComponentsInit(); 
	}
	private void GuiComponentsInit() {
		 
		 //Luodaan paneeli, johon kaikki muut komponentit alustetaan
		 Color emoBackground = new Color(84,158,243);
		 JPanel EmoPaneeli = new JPanel();
		 EmoPaneeli.setLayout(null);
		 EmoPaneeli.setBackground(emoBackground);
		 
		 //Paneeli tulevan viikon sääennustetta varten.
		 Color weatherPanelBackground = new Color(60,114,175);
		 JPanel weatherPanel = new JPanel();
		 weatherPanel.setBackground(weatherPanelBackground);
		 weatherPanel.setBounds(30,310,715,170);
		 EmoPaneeli.add(weatherPanel);
		 add(EmoPaneeli);
		 
		 //Luodaan hakukenttä.
		 JTextField searchBar = new JTextField("");
		 searchBar.setBounds(30,20,400,30);
		 EmoPaneeli.add(searchBar);
		 
		 //Luodaan etsi-nappi
		 JButton searchButton = new JButton(imageLoader("src/assets/searchIcon.png"));
		 searchButton.setBounds(430,20,30,30);
		 EmoPaneeli.add(searchButton);
		
		 
	 }
	
	private ImageIcon imageLoader(String filepath){
		
		try {
			BufferedImage img = ImageIO.read(new File(filepath));
			
			return new ImageIcon(img);
		} catch(IOException e) {
			System.out.println("Image not found.");
		return null;
	}
		
	 
}};
