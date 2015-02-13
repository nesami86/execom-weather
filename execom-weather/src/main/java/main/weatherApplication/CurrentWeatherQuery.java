package main.weatherApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import main.database.WeatherOncePerDayRepository;

import org.apache.http.HttpResponse;
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

	@Autowired
	WeatherOncePerDayRepository weatherOncePerDayRepository ;

	private static String jsonPoruka = "";
	private static Integer FIRST_JAN_2015 = 1420070400;

	private static Integer WEEK_IN_SEC = 604800;

	private Integer toTime = FIRST_JAN_2015;
	private Integer fromTimeX;

	private Integer fromTime = (int) (System.currentTimeMillis() / 1000);

	private Integer brIteracija = 0;

	private static List<Integer> cities = new ArrayList<Integer>();

	public void currentQuerry() {
		cities.add(764679); // Minsk
		cities.add(524901); // MOSKVA
		cities.add(2661604); // BASEL
		cities.add(2761369); // Vienne
		cities.add(3117735); // Madrid
		cities.add(264371);  // Athens
		cities.add(2673730); // Stockholm
		cities.add(2950159); // Berlin
		cities.add(2825297); // Stuttgart
		cities.add(745044);  // Istanbul

		Long datex = weatherOncePerDayRepository.findWeatherDate();

		try{
			if(datex.equals(null)){
				System.out.println("NEMA VREDNOSTI U TABELI!!!!!");

			}else{
				System.out.println("###IMA VREDNOSTI U TABELI!!");
				if((datex/1000l)<FIRST_JAN_2015)
					toTime = FIRST_JAN_2015;
				else
					toTime = (int) (datex/1000l);
			}
		}catch(NullPointerException npE){
			System.out.println("Nema podataka u bazi;");
		}


		brIteracija = (fromTime - toTime)/WEEK_IN_SEC;

		System.out.println("BROJ ITERACIJA DO KRAJA:"+ brIteracija);

		for (int i = 0; i < brIteracija; i++) {

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

}
