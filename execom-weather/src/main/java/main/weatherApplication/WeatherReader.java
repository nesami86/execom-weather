package main.weatherApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import main.database.CityRepository;
import main.database.WeatherRepository;
import main.entities.City;
import main.entities.Weather;
import main.entities.WeatherPeriod;

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
    
    public List<Weather> getWeatherReports(WeatherPeriod weatherPeriod){
        Integer cityId = Integer.parseInt(weatherPeriod.getCityId());
        City city = cityRepository.findByCityId(cityId);
        long startingDate = getUnixTime(weatherPeriod.getStartingDate());
        long endingDate = getUnixTime(weatherPeriod.getEndingDate());
        
        return weatherRepository.findByCityAndDateBetween(city, startingDate, endingDate);
    }
    
    public long getUnixTime(String date) {
        return getDate(date).getTimeInMillis();
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
}