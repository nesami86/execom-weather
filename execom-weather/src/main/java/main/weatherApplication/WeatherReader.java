package main.weatherApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.database.CityRepository;
import main.database.WeatherOncePerDayRepository;
import main.database.WeatherRepository;
import main.entities.City;
import main.entities.Weather;
import main.entities.WeatherOncePerDay;
import main.entities.WeatherPeriod;
import main.entities.WeatherPeriodExtracted;

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
    
    public List<WeatherOncePerDay> getWeatherForecast(WeatherPeriod weatherPeriod) {
//        WeatherPeriodExtracted wpe = extractWeatherPeriod(weatherPeriod);
        
        
        return new ArrayList<WeatherOncePerDay>();
    }
    
    public List<Weather> getWeatherReports(WeatherPeriod weatherPeriod){
        WeatherPeriodExtracted wpe = extractWeatherPeriod(weatherPeriod);
        return weatherRepository.findByCityAndDateBetween(wpe.getCity(), wpe.getStDate(), wpe.getEndDate());
    }
    
    public WeatherPeriodExtracted extractWeatherPeriod(WeatherPeriod weatherPeriod) {
        Integer cityId = Integer.parseInt(weatherPeriod.getCityId());
        City city = cityRepository.findByCityId(cityId);
        long startingDate = getUnixTime(weatherPeriod.getStartingDate());
        long endingDate = getUnixTime(weatherPeriod.getEndingDate()) - 3600000;
        
        return new WeatherPeriodExtracted(city, startingDate, endingDate);
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