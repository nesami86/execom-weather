package main.weatherApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.database.CityRepository;
import main.entities.City;
import main.entities.Weather;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class WeatherApplication {
	private static Integer DAY_IN_SEC = 86400;
	private static Integer WEEK_IN_SEC = 604800;
	private static Integer MONTH_IN_SEC = 2629744;
	private static Long YEAR_IN_SEC = 3144960052l;
	@Autowired
	static CityRepository cr ;
	
	public static void writCity(City city){
		try{
		cr.save(city);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws ClientProtocolException,
	IOException, SQLException, ClassNotFoundException {/*
		
		List<Integer> gradovi = new ArrayList<Integer>();
		gradovi.add(764679); // Minsk
	/*	gradovi.add(524901); // MOSKVA
		gradovi.add(792680); // BEOGRAD
		gradovi.add(3054643); // BUDAPEST
		gradovi.add(2969284); // Vienne
		gradovi.add(3996933); // Madrid
		gradovi.add(4219762); // Rome

		gradovi.add(2673730); // Stockholm
		gradovi.add(5245497); // Berlin
		gradovi.add(2867993); // Stuttgart
		gradovi.add(745044);*/ // Istanbul
	
	/*
		Integer fromTime = (int) (System.currentTimeMillis() / 1000);
		
		Integer toTime = fromTime - MONTH_IN_SEC; // krajnjeVreme
		
		Integer fromTimeX = 0;
		
		for (int i = 0; i < 4; i++) {
			
			fromTimeX = toTime + WEEK_IN_SEC;
		
			for(int x: gradovi){
				
				HttpClient client = new DefaultHttpClient();
				
				HttpGet request = new HttpGet("http://api.openweathermap.org/data/2.5/history/city?id="
						+ x + "&type=hour&start=" + toTime
						+ "&end=" + fromTimeX
						+ "&APPID=c63f2ee343e3f8ce3a7e452d8f9ad08d");
			
				HttpResponse response = client.execute(request);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));
				
				JsonParser jsonParser = new JsonParser();
				
				String jsonPoruka = br.readLine();
				System.out.println(jsonPoruka);
				
				
				   try{
				 JsonReader jsonReader = new JsonReader(new StringReader(jsonPoruka));  // mozda ovo resava problem
				   jsonReader.setLenient(true);
				   //jsonReader.
				   JsonObject glavni = jsonParser.parse(jsonReader).getAsJsonObject();
				   
				   
				   int cod = glavni.get("cod").getAsInt();
				   int cityID = glavni.get("city_id").getAsInt();

				
					City city = new City(cityID, cod);

					System.out
							.println("  PODACI O GRADU NA OSNOVU PARSIRANIH PODATAK : "
									+ city);

					JsonArray measurements = jsonParser.parse(jsonPoruka)
							.getAsJsonObject().getAsJsonArray("list");

					List<Weather> weatherx = new ArrayList<Weather>();
					for (JsonElement listEl : measurements) {
						JsonObject main = (JsonObject) listEl.getAsJsonObject()
								.get("main");
						
						Double temp = main.get("temp").getAsDouble() - 273.15;
						Double humidity = main.get("humidity").getAsDouble();
						Double pressure = main.get("pressure").getAsDouble();
						Double temp_min = main.get("temp_min").getAsDouble() - 273.15;
						Double temp_max = main.get("temp_max").getAsDouble() - 273.15;

						JsonElement dtj = listEl.getAsJsonObject().get("dt");

						Long dt = dtj.getAsLong(); // ovo bi ovako bilo u sekundama
						dt = dt * 1000;
						Weather wx = new Weather(dt, temp, humidity, pressure,
								temp_min, temp_max);

						weatherx.add(wx);
					}


					for (Weather wPom : weatherx) {
						city.addWeather(wPom);
					}
					
			/*		SessionFactory sessionFactory = new AnnotationConfiguration()
					.configure().buildSessionFactory();
					Session session = sessionFactory.openSession();
					session.beginTransaction();

					session.saveOrUpdate(city);

					session.getTransaction().commit();
					session.close();
					sessionFactory.close();*/
			/*		System.out.println("ID grada"+city.getId());
					WeatherApplication.writCity(city);
				
				   }catch(IllegalStateException e){
					   System.out.println("Ono sto smo dobili nije json poruka");
				   }
			}
			toTime = fromTimeX;
		}
		
		
		*/
		System.out.println(WeatherQuery.returnWeather());
	}
	
	
}
