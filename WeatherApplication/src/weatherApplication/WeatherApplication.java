package weatherApplication;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WeatherApplication extends JFrame{
	
	public WeatherApplication(){
		 super("Weather application");
		 
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
		 
		 setSize(800,550);
		 
		 setLocationRelativeTo(null);
		 
		
		 GuiComponentsInit(); 
	}
	private void GuiComponentsInit() {
		
		 Color emoBackground = new Color(84,158,243);
		 JPanel EmoPaneeli = new JPanel();
		 EmoPaneeli.setLayout(null);
		 EmoPaneeli.setBackground(emoBackground);
		 
		 Color weatherPanelBackground = new Color(60,114,175);
		 JPanel weatherPanel = new JPanel();
		 weatherPanel.setBackground(weatherPanelBackground);
		 weatherPanel.setBounds(30,310,715,170);
		 EmoPaneeli.add(weatherPanel);
		 add(EmoPaneeli);
		 ;
		
		 
	 }
	 {

	}
}
