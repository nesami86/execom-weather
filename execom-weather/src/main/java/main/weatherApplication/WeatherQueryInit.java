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

	private static String jsonPoruka ="";
	private static List<Integer> cities = new ArrayList<Integer>();

	@SuppressWarnings("resource")
	public void returnWeather() throws IllegalStateException, IOException{
		//cities.add(764679); // Minsk
		cities.add(524901); // MOSKVA
		/*cities.add(792680); // BEOGRAD
		cities.add(2969284); // Vienne
		cities.add(3996933); // Madrid
		cities.add(4219762); // Rome
		cities.add(2673730); // Stockholm
		cities.add(5245497); // Berlin
		cities.add(2867993); // Stuttgart
		cities.add(745044);*/  // Istanbul

		Integer toTime = JAN_1_2015 - 2*YEAR_IN_SEC;

		Integer fromTimeX = 0;

		for (int i = 0; i < 104; i++) {

			fromTimeX = toTime + WEEK_IN_SEC;

			for (int x : cities) {

				DateTime startDate = new DateTime(toTime * 1000L);
				DateTime endDate = new DateTime(fromTimeX * 1000L);

				@SuppressWarnings("deprecation")
				HttpClient client = new DefaultHttpClient();

				String query = "http://api.openweathermap.org/data/2.5/history/city?id="+x+"&type=day&start=" + toTime + "&end=" + fromTimeX
						+ "&APPID=c63f2ee343e3f8ce3a7e452d8f9ad08d";

				HttpGet request = new HttpGet(query);

				HttpResponse response = client.execute(request);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));

				String line = null;
				while((line = br.readLine()) != null){
					jsonPoruka += line;
				}

				//String[] stringArray = jsonPoruka.split("{\"message\"");
				String[] stringArray = jsonPoruka.split("}]}");

				for(String s: stringArray){
					parser.parser(s+"}]}", startDate, endDate);
				}
			}
			toTime = fromTimeX;

		}
	}

}
