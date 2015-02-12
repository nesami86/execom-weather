package main.weatherApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.beans.WeatherPeriod;
import main.database.CityRepository;
import main.database.WeatherOncePerDayRepository;
import main.database.WeatherRepository;
import main.entities.City;
import main.entities.Weather;
import main.entities.WeatherOncePerDay;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;

/**
 * Gets weather data from database
 */
@Component
public class WeatherReader {

	@Autowired
	private WeatherRepository weatherRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private WeatherOncePerDayRepository dailyWeatherRepository;

	public List<Weather> getWeatherReports(WeatherPeriod weatherPeriod){
		City city = cityRepository.findByCityId((Integer.parseInt(weatherPeriod.getCityId())));
		return weatherRepository.findByCityAndDateBetween(city, getUnixTime(weatherPeriod.getStartingDate()),
				getUnixTime(weatherPeriod.getEndingDate()));
	}

	public long getUnixTime(String date) {
		return getDate(date).getTimeInMillis() + 3600000;
	}

	public String getWeatherReportsJSON(String cityId, String startingDate, String endingDate){
		System.out.println("CityID je!!!:"+cityId);
		String retValue = "it did not work";
		City city = cityRepository.findByCityId((Integer.parseInt(cityId)));
		JSONArray jsonArray = new JSONArray(weatherRepository.findByCityAndDateBetween(city, Long.parseLong(startingDate),
				Long.parseLong(endingDate)));
		try {
			 retValue = jsonArray.toString(2);
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retValue;
		}
	
	public Calendar getDate(String date) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			calendar.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
	}

	public long adjustEndingDate(long endingDate, long startingDate) {
		if (endingDate == startingDate) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(endingDate);
			calendar.add(Calendar.DAY_OF_YEAR, +1);
			endingDate = calendar.getTimeInMillis();
		}        
		return endingDate;
	}
	
	public List<WeatherOncePerDay> getWeatherForecast(WeatherPeriod weatherPeriod) {    // ovo pozivamo sa html stranice
		List<WeatherOncePerDay> daysPredicted = new ArrayList<WeatherOncePerDay>();  //init za listu predikcija

		List<Calendar> days = getDaysForPrediction(getUnixTime(weatherPeriod.getStartingDate()), //formiramo listu Calendar parametara na osnovu ucitanih podataka iz forme 
				getUnixTime(weatherPeriod.getEndingDate()));

		City city = cityRepository.findByCityId(Integer.parseInt(weatherPeriod.getCityId())); //formiramo City objekat na osnovu
																							  //ulaznog parametara sa forme

		for (Calendar day : days) {      //Za listu Calendar objekata sto smo pravili 
			WeatherOncePerDay dailyWeather = makeForecast(city, day.getTimeInMillis());//pozivamo fuju makeForecast
			daysPredicted.add(dailyWeather);	// rezultat dodamo u listu predikcija
		}
		
		return daysPredicted;  // vraca listu WeatherOncePerDay objekata
	}
	
	public String getWeatherForecastJSON(String cityId, String startingDate, String endingDate) {    // ovo pozivamo sa html stranice
		List<WeatherOncePerDay> daysPredicted = new ArrayList<WeatherOncePerDay>();  //init za listu predikcija
		String returnJson="";
		List<Calendar> days = getDaysForPrediction(Long.parseLong(startingDate), //formiramo listu Calendar parametara na osnovu ucitanih podataka iz forme 
				Long.parseLong(endingDate));

		City city = cityRepository.findByCityId(Integer.parseInt(cityId)); //formiramo City objekat na osnovu
																							  //ulaznog parametara sa forme
	//	List<JsonArray> testJSONARRAy ;
		for (Calendar day : days) {      //Za listu Calendar objekata sto smo pravili 
			WeatherOncePerDay dailyWeather = makeForecast(city, day.getTimeInMillis());//pozivamo fuju makeForecast
		//	testJSONARRAy.add(new JsonArray(makeForecast(city, day.getTimeInMillis())));
			daysPredicted.add(dailyWeather);	// rezultat dodamo u listu predikcija
		}
		JSONArray returnJArray = new JSONArray(daysPredicted);
		
		try {
			returnJson = returnJArray.toString(2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnJson;  // vraca listu WeatherOncePerDay objekata
	}

	public List<Calendar> getDaysForPrediction(Long startDate, Long endDate) {
		List<Calendar> days = new ArrayList<Calendar>(); // iniciramo listu objekata tipa Calendar

		for (long date = startDate; date < endDate; date += 86400000) { // povecavamo counter za jedan dan u sekundama
			Calendar day = Calendar.getInstance(); 						// pravimo objekat tipa canlendar
			day.setTimeInMillis(date);									//setujemo mu date u milisekundama
			days.add(day);												//dodamo ga u listu gradova
		}
		return days;
	}

	public WeatherOncePerDay makeForecast(City city, long date) {
		List<WeatherOncePerDay> forecastData = getForecastData(city, date);  //ovde pozivamo get forecast za ulazne parametre
		int tempMin = 0;
		int tempMax = 0;
		int counter = 1;

		for (int i=0; i<forecastData.size(); i++) {          // U petlji prelazimo kroz elemente forecast liste.   
			tempMin += forecastData.get(i).getTempMin();	 // formiramo sumu min temperatura za trazen period.
			tempMax += forecastData.get(i).getTempMax();	 // formiramo sumu max temperatura za trazen period.
			counter = i + 1;								 // brojac koji cemo koristiti u racunu.
		}
		tempMin = Math.round(tempMin/counter);				 // izracunamo srednju vrednost za min temperaturu
		tempMax = Math.round(tempMax/counter);				 // izracunamo srednju vrednost za max temperaturu

		return new WeatherOncePerDay(tempMin, tempMax, date); //wratimo objekat tipa WeatherOncePerDay.
	}

	public List<WeatherOncePerDay> getForecastData(City city, long date) {		
		List<WeatherOncePerDay> forecastData = new ArrayList<WeatherOncePerDay>(); // formiramo listu objekata tipa weatherOncePerDay
		Calendar calendar = Calendar.getInstance();								   // kreiramo kalendar objekat
		calendar.setTimeInMillis(date);											   // setujemo mu datum

		for (int i=0; i<10; i++) {								// citamo deset godina unazad
			WeatherOncePerDay forecastDay = dailyWeatherRepository.findByCityAndDate(city, calendar.getTimeInMillis()); //citamo iz baze dnevni min i max

			if (forecastDay != null) {			// ako iscitana vrednost nije null onda:
				forecastData.add(forecastDay);	// dodaj forecastDay u listu forecast data
			}
			calendar.add(Calendar.YEAR, -1);	// umanjujemu godinu za godinu dana
		} 
		return forecastData;
	}
}