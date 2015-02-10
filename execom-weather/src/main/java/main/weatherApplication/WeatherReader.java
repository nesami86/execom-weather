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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

	public List<WeatherOncePerDay> getWeatherForecast(WeatherPeriod weatherPeriod) {
		List<WeatherOncePerDay> daysPredicted = new ArrayList<WeatherOncePerDay>();

		List<Calendar> days = getDaysForPrediction(getUnixTime(weatherPeriod.getStartingDate()), 
				getUnixTime(weatherPeriod.getEndingDate()));

		City city = cityRepository.findByCityId((Integer.parseInt(weatherPeriod.getCityId())));
		
		for (Calendar day : days) {
			WeatherOncePerDay dailyWeather = makeForecast(city, day.getTimeInMillis());
			daysPredicted.add(dailyWeather);
		}
		return daysPredicted;
	}

	public List<Calendar> getDaysForPrediction(Long startDate, Long endDate) {
		List<Calendar> days = new ArrayList<Calendar>();

		for (long date = startDate; date < endDate; date += 86400000) {
			Calendar day = Calendar.getInstance();
			day.setTimeInMillis(date);
			days.add(day);
		}
		return days;
	}

	public WeatherOncePerDay makeForecast(City city, long date) {
		List<WeatherOncePerDay> forecastData = getForecastData(city, date);
		int tempMin = 0;
		int tempMax = 0;
		int counter = 1;

		for (int i=0; i<forecastData.size(); i++) {
			tempMin += forecastData.get(i).getTempMin();
			tempMax += forecastData.get(i).getTempMax();
			counter = i + 1;
		}
		tempMin = Math.round(tempMin/counter);
		tempMax = Math.round(tempMax/counter);

		return new WeatherOncePerDay(tempMin, tempMax, date);
	}

	public List<WeatherOncePerDay> getForecastData(City city, long date) {
		List<WeatherOncePerDay> forecastData = new ArrayList<WeatherOncePerDay>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);

		for (int i=0; i<10; i++) {
			WeatherOncePerDay forecastDay = dailyWeatherRepository.findByCityAndDate(city, calendar.getTimeInMillis());

			if (forecastDay != null) {
				forecastData.add(forecastDay);
			}
			calendar.add(Calendar.YEAR, -1);
		} 
		return forecastData;
	}
}