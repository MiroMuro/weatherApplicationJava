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
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WeatherApplication extends JFrame{
	
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
		//JPanel EmoPaneeli = new JPanel();
		//EmoPaneeli.setLayout(null);
		 
		 
		 try {
				BufferedImage tausta = ImageIO.read(new File("src/assets/space.jpeg"));
				JLabel taustaLabel = new JLabel(new ImageIcon(tausta));
				taustaLabel.setBounds(0,0,800,550);
				motherPane.add(taustaLabel,1,0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 
		 //Paneeli tulevan viikon sääennustetta varten.
		 Color weatherPanelBackground = new Color(60,114,175,150);
		 JPanel weatherPanel = new JPanel();
		 weatherPanel.setBackground(weatherPanelBackground);
		 weatherPanel.setBounds(30,310,715,170);
		 motherPane.add(weatherPanel,2,0);
		 
		 
		 
		 //Luodaan hakukenttä.
		 
		 JTextField searchBar = new JTextField("");
		 searchBar.setBounds(30,20,400,30);
		 motherPane.add(searchBar,2,0);
		 
		 //Luodaan etsi-nappi
		 JButton searchButton = new JButton(imageLoader("src/assets/searchIcon.png"));
		 searchButton.setBounds(430,20,30,30);
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
		
	 
}};
