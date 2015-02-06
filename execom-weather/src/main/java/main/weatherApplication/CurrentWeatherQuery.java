package main.weatherApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.Date;
import java.util.TimeZone;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrentWeatherQuery {

	@Autowired
	JSONParser jsonParser;

	private static String jsonPoruka = "";
	private static Integer FIRST_JAN_2015 = 1420070400;

	private static Integer DAY_IN_SEC = 86400;
	private static Integer WEEK_IN_SEC = 604800;
	private static Integer MONTH_IN_SEC = 2629744;

	private Integer toTime = FIRST_JAN_2015;
	private Integer fromTimeX = toTime + MONTH_IN_SEC;// 1420675200;
	// private String cityID = "745044";

	private static List<Integer> cities = new ArrayList<Integer>();

	public void currentQuerry() {
		cities.add(764679); // Minsk
		cities.add(524901); // MOSKVA
		cities.add(792680); // BEOGRAD
		cities.add(2969284); // Vienne
		cities.add(3996933); // Madrid
		cities.add(4219762); // Rome
		cities.add(2673730); // Stockholm
		cities.add(5245497); // Berlin
		cities.add(2867993); // Stuttgart
		cities.add(745044); // Istanbul

		for (int i = 0; i < 4; i++) {

			fromTimeX = toTime + WEEK_IN_SEC;

			for (int cityID : cities) {
				try {
					DateTime startDate = new DateTime(toTime * 1000L);
					DateTime endDate = new DateTime(fromTimeX * 1000L);

					HttpClient client = new DefaultHttpClient();

					System.out.println(startDate);

					String query = "http://api.openweathermap.org/data/2.5/history/city?id="
							+ cityID
							+ "&type=day&start="
							+ toTime
							+ "&end="
							+ fromTimeX
							+ "&APPID=c63f2ee343e3f8ce3a7e452d8f9ad08d";

					HttpGet request = new HttpGet(query);

					HttpResponse response;

					response = client.execute(request);

					BufferedReader br;
					br = new BufferedReader(new InputStreamReader(response
							.getEntity().getContent()));

					jsonPoruka = br.readLine();

					System.out.println(jsonPoruka);

					jsonParser.CurrentMesurementParser(jsonPoruka, startDate,
							endDate);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			toTime = fromTimeX;
		}
	}

	/*
	 * public SimpleDateFormat formater (sd){
	 * 
	 * }
	 */
}
