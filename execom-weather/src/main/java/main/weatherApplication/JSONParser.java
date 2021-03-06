package main.weatherApplication;

import java.io.StringReader;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

import main.database.CityRepository;
import main.entities.City;
import main.entities.Weather;
import main.entities.WeatherOncePerDay;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

@Component
public class JSONParser {

	@Autowired
	private CityRepository cityRepository;

	public void parser(String jsonPoruka, DateTime startDate,
			DateTime endDate) {

		JsonParser jsonParser = new JsonParser();

		System.out.println(jsonPoruka);
		JsonReader jsonReader = new JsonReader(new StringReader(jsonPoruka)); // mozda

		jsonReader.setLenient(true);

		try{
			JsonObject fullMessageJson = jsonParser.parse(jsonReader).getAsJsonObject();

			int cod = fullMessageJson.get("cod").getAsInt();
			int cityID = fullMessageJson.get("city_id").getAsInt();

			City city = new City(cityID, cod);

			JsonArray measurements = null;

			measurements = jsonParser.parse(jsonPoruka)
					.getAsJsonObject().getAsJsonArray("list");


			List<Weather> weatherx = new ArrayList<Weather>();

			DateTime dayOne = startDate;
			DateTime dayTwo = startDate.plusDays(1);
			DateTime dayTree = startDate.plusDays(2);
			DateTime dayFour = startDate.plusDays(3);
			DateTime dayFive = startDate.plusDays(4);
			DateTime daySix = startDate.plusDays(5);
			DateTime daySeven = startDate.plusDays(6);
			DateTime dayEight = startDate.plusDays(7);

			List<WeatherOncePerDay> dayOneList = new ArrayList<WeatherOncePerDay>();
			List<WeatherOncePerDay> dayTwoList = new ArrayList<WeatherOncePerDay>();
			List<WeatherOncePerDay> dayTreeList = new ArrayList<WeatherOncePerDay>();
			List<WeatherOncePerDay> dayFourList = new ArrayList<WeatherOncePerDay>();
			List<WeatherOncePerDay> dayFiveList = new ArrayList<WeatherOncePerDay>();
			List<WeatherOncePerDay> daySixList = new ArrayList<WeatherOncePerDay>();
			List<WeatherOncePerDay> daySevenList = new ArrayList<WeatherOncePerDay>();

			List<WeatherOncePerDay> weeklyExtreme = new ArrayList<WeatherOncePerDay>();

			for (JsonElement listEl : measurements) {
				JsonObject main = (JsonObject) listEl.getAsJsonObject().get(
						"main");

				int temp = main.get("temp").getAsInt() - 273;

				int humidity = -100; 

				try{
					humidity = main.get("humidity").getAsInt();
				} catch(NullPointerException npe){
					//	System.out.println("\nNo value returned for humidity.");
				}

				int	pressure = main.get("pressure").getAsInt();

				int	temp_min = main.get("temp_min").getAsInt() - 273;

				int	temp_max = main.get("temp_max").getAsInt() - 273;


				JsonElement dtj = listEl.getAsJsonObject().get("dt");

				Long dt = dtj.getAsLong();
				dt = dt * 1000L;
				Weather wx = new Weather(dt, temp, humidity, pressure);


				DateTime testDate = new DateTime(dt);
				weatherx.add(wx);

				if (testDate.isAfter(dayOne) && testDate.isBefore(dayTwo)) {
					WeatherOncePerDay weatherOncePerDay = new WeatherOncePerDay(
							temp_min, temp_max, dayOne.getMillis());
					dayOneList.add(weatherOncePerDay);
				}

				if (testDate.isAfter(dayTwo) && testDate.isBefore(dayTree)) {
					WeatherOncePerDay weatherOncePerDay = new WeatherOncePerDay(
							temp_min, temp_max, dayTwo.getMillis());
					dayTwoList.add(weatherOncePerDay);
				}

				if (testDate.isAfter(dayTree) && testDate.isBefore(dayFour)) {
					WeatherOncePerDay weatherOncePerDay = new WeatherOncePerDay(
							temp_min, temp_max, dayTree.getMillis());
					dayTreeList.add(weatherOncePerDay);
				}

				if (testDate.isAfter(dayFour) && testDate.isBefore(dayFive)) {
					WeatherOncePerDay weatherOncePerDay = new WeatherOncePerDay(
							temp_min, temp_max, dayFour.getMillis());
					dayFourList.add(weatherOncePerDay);
				}

				if (testDate.isAfter(dayFive) && testDate.isBefore(daySix)) {
					WeatherOncePerDay weatherOncePerDay = new WeatherOncePerDay(
							temp_min, temp_max, dayFive.getMillis());
					dayFiveList.add(weatherOncePerDay);
				}

				if (testDate.isAfter(daySix) && testDate.isBefore(daySeven)) {
					WeatherOncePerDay weatherOncePerDay = new WeatherOncePerDay(
							temp_min, temp_max, daySix.getMillis());
					daySixList.add(weatherOncePerDay);
				}

				if (testDate.isAfter(daySeven) && testDate.isBefore(dayEight)) {
					WeatherOncePerDay weatherOncePerDay = new WeatherOncePerDay(
							temp_min, temp_max, daySeven.getMillis());
					daySevenList.add(weatherOncePerDay);
				}

			}
			if(dayOneList.size()>0)
				weeklyExtreme.add(this.maxMinForDay(dayOneList));
			if(dayTwoList.size()>0)
				weeklyExtreme.add(this.maxMinForDay(dayTwoList));
			if(dayTreeList.size()>0)		//Tree je drvo
				weeklyExtreme.add(this.maxMinForDay(dayTreeList));
			if(dayFourList.size()>0)
				weeklyExtreme.add(this.maxMinForDay(dayFourList));
			if(dayFiveList.size()>0)
				weeklyExtreme.add(this.maxMinForDay(dayFiveList));
			if(daySixList.size()>0)
				weeklyExtreme.add(this.maxMinForDay(daySixList));
			if(daySevenList.size()>0)
				weeklyExtreme.add(this.maxMinForDay(daySevenList));

			for (Weather wPom : weatherx) {
				city.addWeather(wPom);
			}

			for (WeatherOncePerDay wPom : weeklyExtreme) {
				city.AddWeatherOncePerDay(wPom);
			}
			
				cityRepository.save(city);

		}
		catch(Exception e){
		//	e.printStackTrace();
		}

	}

	public void CurrentMesurementParser(String jsonPoruka, DateTime startDate,
			DateTime endDate) {
		JsonParser jsonParser = new JsonParser();
		try {
			JsonReader jsonReader = new JsonReader(new StringReader(jsonPoruka));
			jsonReader.setLenient(true);

			JsonObject glavni = jsonParser.parse(jsonReader).getAsJsonObject();

			int cod = glavni.get("cod").getAsInt();
			int cityID = glavni.get("city_id").getAsInt();

			City city = new City(cityID, cod);

			JsonArray measurements = jsonParser.parse(jsonPoruka)
					.getAsJsonObject().getAsJsonArray("list");


			List<Weather> weatherx = new ArrayList<Weather>();

			DateTime dayOne = startDate;
			DateTime dayTwo = startDate.plusDays(1);
			DateTime dayTree = startDate.plusDays(2);
			DateTime dayFour = startDate.plusDays(3);
			DateTime dayFive = startDate.plusDays(4);
			DateTime daySix = startDate.plusDays(5);
			DateTime daySeven = startDate.plusDays(6);
			DateTime dayEight = startDate.plusDays(7);

			List<WeatherOncePerDay> dayOneList = new ArrayList<WeatherOncePerDay>();
			List<WeatherOncePerDay> dayTwoList = new ArrayList<WeatherOncePerDay>();
			List<WeatherOncePerDay> dayTreeList = new ArrayList<WeatherOncePerDay>();
			List<WeatherOncePerDay> dayFourList = new ArrayList<WeatherOncePerDay>();
			List<WeatherOncePerDay> dayFiveList = new ArrayList<WeatherOncePerDay>();
			List<WeatherOncePerDay> daySixList = new ArrayList<WeatherOncePerDay>();
			List<WeatherOncePerDay> daySevenList = new ArrayList<WeatherOncePerDay>();

			List<WeatherOncePerDay> weeklyExtreme = new ArrayList<WeatherOncePerDay>();

			for (JsonElement listEl : measurements) {
				JsonObject main = (JsonObject) listEl.getAsJsonObject().get(
						"main");



				int temp_min = main.get("temp_min").getAsInt() - 273;
				int temp_max = main.get("temp_max").getAsInt() - 273;


				int temp = main.get("temp").getAsInt() - 273;

				int humidity = -100; 

				try{
					humidity = main.get("humidity").getAsInt();
				} catch(NullPointerException npe){
					//	System.out.println("\nNo value returned for humidity.");
				}

				int	pressure = main.get("pressure").getAsInt();



				JsonElement dtj = listEl.getAsJsonObject().get("dt");

				Long dt = dtj.getAsLong();
				dt = dt * 1000L;
				Weather wx = new Weather(dt, temp, humidity, pressure);

				DateTime testDate = new DateTime(dt);

				weatherx.add(wx);

				if (testDate.isAfter(dayOne) && testDate.isBefore(dayTwo)) {
					WeatherOncePerDay weatherOncePerDay = new WeatherOncePerDay(
							temp_min, temp_max, dayOne.getMillis());
					dayOneList.add(weatherOncePerDay);
				}

				if (testDate.isAfter(dayTwo) && testDate.isBefore(dayTree)) {
					WeatherOncePerDay weatherOncePerDay = new WeatherOncePerDay(
							temp_min, temp_max, dayTwo.getMillis());
					dayTwoList.add(weatherOncePerDay);
				}

				if (testDate.isAfter(dayTree) && testDate.isBefore(dayFour)) {
					WeatherOncePerDay weatherOncePerDay = new WeatherOncePerDay(
							temp_min, temp_max, dayTree.getMillis());
					dayTreeList.add(weatherOncePerDay);
				}

				if (testDate.isAfter(dayFour) && testDate.isBefore(dayFive)) {
					WeatherOncePerDay weatherOncePerDay = new WeatherOncePerDay(
							temp_min, temp_max, dayFour.getMillis());
					dayFourList.add(weatherOncePerDay);
				}

				if (testDate.isAfter(dayFive) && testDate.isBefore(daySix)) {
					WeatherOncePerDay weatherOncePerDay = new WeatherOncePerDay(
							temp_min, temp_max, dayFive.getMillis());
					dayFiveList.add(weatherOncePerDay);
				}

				if (testDate.isAfter(daySix) && testDate.isBefore(daySeven)) {
					WeatherOncePerDay weatherOncePerDay = new WeatherOncePerDay(
							temp_min, temp_max, daySix.getMillis());
					daySixList.add(weatherOncePerDay);
				}

				if (testDate.isAfter(daySeven) && testDate.isBefore(dayEight)) {
					WeatherOncePerDay weatherOncePerDay = new WeatherOncePerDay(
							temp_min, temp_max, daySeven.getMillis());
					daySevenList.add(weatherOncePerDay);
				}

			}

			weeklyExtreme.add(this.maxMinForDay(dayOneList));
			weeklyExtreme.add(this.maxMinForDay(dayTwoList));
			weeklyExtreme.add(this.maxMinForDay(dayTreeList));
			weeklyExtreme.add(this.maxMinForDay(dayFourList));
			weeklyExtreme.add(this.maxMinForDay(dayFiveList));
			weeklyExtreme.add(this.maxMinForDay(daySixList));
			weeklyExtreme.add(this.maxMinForDay(daySevenList));


			for (Weather wPom : weatherx) {
				city.addWeather(wPom);
			}

			for (WeatherOncePerDay wPom : weeklyExtreme) {
				city.AddWeatherOncePerDay(wPom);

			}

			cityRepository.save(city);

		} catch (Exception e) {
			//System.out.println("The message that we got is not a json message");
		}
	}

	public WeatherOncePerDay maxMinForDay(List<WeatherOncePerDay> listW) {

		int minTemp = 1000000;
		int maxTemp = -1000;
		Long date = 0l;
		for (WeatherOncePerDay help : listW) {
			date = help.getDate();
			if (maxTemp < help.getTempMax()) {
				maxTemp = help.getTempMax();

			}

			if (minTemp > help.getTempMin()) {
				minTemp = help.getTempMin();

			}
		}

		return new WeatherOncePerDay(minTemp, maxTemp, date);
	}

}
