package main.weatherApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeatherQueryInit {
	private static Integer DAY_IN_SEC = 86400;
	private static Integer WEEK_IN_SEC = 604800;
	private static Integer MONTH_IN_SEC = 2629744;
	private static Integer YEAR_IN_SEC =  31536000;

	@Autowired
	JSONParser parser;
	
	private static String jsonPoruka ="";
	private static List<Integer> cities = new ArrayList<Integer>();
	
	@SuppressWarnings("resource")
	public void returnWeather() throws IllegalStateException, IOException{
			cities.add(764679); // Minsk
			cities.add(524901); // MOSKVA
			cities.add(792680); // BEOGRAD
			cities.add(2969284); // Vienne
			cities.add(3996933); // Madrid
			cities.add(4219762); // Rome
			cities.add(2673730); // Stockholm
			cities.add(5245497); // Berlin
			cities.add(2867993); // Stuttgart
			cities.add(745044);  // Istanbul
			Integer fromTime = (int) (System.currentTimeMillis() / 1000);
			
			Integer toTime = fromTime - YEAR_IN_SEC; // krajnjeVreme
			
			Integer fromTimeX = 0;
			
			for (int i = 0; i < 52; i++) {
				
				fromTimeX = toTime + WEEK_IN_SEC;
			
				for (int x : cities) {
					
					@SuppressWarnings("deprecation")
                    HttpClient client = new DefaultHttpClient();
					
					HttpGet request = new HttpGet("http://api.openweathermap.org/data/2.5/history/city?id="
							+ x + "&type=hour&start=" + toTime
							+ "&end=" + fromTimeX
							+ "&APPID=c63f2ee343e3f8ce3a7e452d8f9ad08d");
				
					HttpResponse response = client.execute(request);
					BufferedReader br = new BufferedReader(new InputStreamReader(
							response.getEntity().getContent()));
					
				
					
					 jsonPoruka = br.readLine();
					 
					 //
					 parser.parser(jsonPoruka);
				}
				toTime = fromTimeX;
				
			}
			System.out.println(jsonPoruka);
		//	return jsonPoruka;//+jsonPoruka;
	}
	
}
