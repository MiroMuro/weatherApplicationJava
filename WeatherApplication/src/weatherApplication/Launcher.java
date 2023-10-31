package weatherApplication;

import java.awt.EventQueue;

public class Launcher {
	public static void main(String[] argrs) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				new WeatherApplication().setVisible(true);
				WeatherApplicationBackend.getWeatherData("Helsinki");
				
				
			}
		});

	}
}
