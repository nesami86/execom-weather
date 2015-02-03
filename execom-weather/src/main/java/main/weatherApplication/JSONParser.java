package main.weatherApplication;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import main.database.CityRepository;
import main.entities.City;
import main.entities.Weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

@Component
public class JSONParser {

    @Autowired
	private CityRepository cityRepository;
	
	public void parser(String jsonPoruka){
		
		JsonParser jsonParser = new JsonParser();
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
					
		
					System.out.println("ID grada"+city.getId());
					
					cityRepository.save(city);
				
				   }catch(IllegalStateException e){
					   System.out.println("Ono sto smo dobili nije json poruka");
				   }
	}
}
