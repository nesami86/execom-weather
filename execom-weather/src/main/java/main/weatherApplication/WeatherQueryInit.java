package main.weatherApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import main.database.WeatherRepository;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeatherQueryInit {

	private static Integer WEEK_IN_SEC = 604800;
	private static Integer YEAR_IN_SEC =  31536000;
	private static Integer JAN_1_2015 = 1420070400;

	@Autowired
	JSONParser parser;
	
	@Autowired
	WeatherRepository weatherReposirory;

	private Integer brIteracija = 0;
	private static String jsonPoruka ="";
	private static List<Integer> cities = new ArrayList<Integer>();

	
	
	
	@SuppressWarnings("resource")
	public void returnWeather() throws IllegalStateException, IOException{
		cities.add(764679); // Minsk
		cities.add(524901); // MOSKVA
		cities.add(2661604); // BASEL
		cities.add(2761369); // Vienne
		cities.add(3117735); // Madrid
		cities.add(264371); // Athens
		cities.add(2673730); // Stockholm
		cities.add(2950159); // Berlin
		cities.add(2825297); // Stuttgart
		cities.add(745044);  // Istanbul
		Long datex = weatherReposirory.findWeatherDate();
		
		
		Integer toTime = JAN_1_2015 - 2*YEAR_IN_SEC;

		Integer fromTimeX = 0;

		try{
			if(datex.equals(null)){
				System.out.println("NEMA VREDNOSTI U TABELI!!!!!");

			}else{
				System.out.println("###IMA VREDNOSTI U TABELI!!");
				toTime = (int) (datex/1000l);
			}
		}catch(NullPointerException npE){
			System.out.println("Nema podataka u bazi;");
		}

		brIteracija = (JAN_1_2015 - toTime)/WEEK_IN_SEC;
		System.out.println(brIteracija);
		
		for (int i = 0; i < brIteracija; i++) {

			fromTimeX = toTime + WEEK_IN_SEC;

			for (int x : cities) {

				DateTime startDate = new DateTime(toTime * 1000L);
				DateTime endDate = new DateTime(fromTimeX * 1000L);

				@SuppressWarnings("deprecation")
				HttpClient client = new DefaultHttpClient();
				
				Integer fromTimeXHelp = fromTimeX - 3600;
				
				
				String query = "http://api.openweathermap.org/data/2.5/history/city?id="+x+"&type=day&start=" + toTime + "&end=" + fromTimeXHelp
						+ "&APPID=c63f2ee343e3f8ce3a7e452d8f9ad08d";

				HttpGet request = new HttpGet(query);

				HttpResponse response = client.execute(request);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));

				String line = null;
		
				jsonPoruka = br.readLine();
				
				parser.parser(jsonPoruka, startDate, endDate);
			}
			toTime = fromTimeX;

		}
	}

}
