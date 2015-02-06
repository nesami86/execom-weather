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
    
    public WeatherOncePerDay getWeatherForecast(WeatherPeriod weatherPeriod) {
        WeatherPeriodExtracted wpe = extractWeatherPeriod(weatherPeriod);        
        return makeForecast(wpe);
    }
    
    public WeatherOncePerDay makeForecast(WeatherPeriodExtracted wpe) {
        List<WeatherOncePerDay> forecastData = getForecastData(wpe);
        Double tempMin = 0.0;
        Double tempMax = 0.0;
        int counter = 1;
        
        for (int i=0; i<forecastData.size(); i++) {
            tempMin += forecastData.get(i).getTempMin();
            tempMax += forecastData.get(i).getTempMax();
            counter = i + 1;
        }
        tempMin = tempMin/counter;
        tempMax = tempMax/counter;
        
        return new WeatherOncePerDay(tempMin, tempMax, wpe.getStDate());
    }
    
    public List<WeatherOncePerDay> getForecastData(WeatherPeriodExtracted wpe) {
        List<WeatherOncePerDay> forecastData = new ArrayList<WeatherOncePerDay>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(wpe.getStDate());
        
        for (int i=0; i<10; i++) {
            WeatherOncePerDay forecastDay = dailyWeatherRepository.findByCityAndDate(wpe.getCity(), calendar.getTimeInMillis());
            
            if (forecastDay != null) {
                forecastData.add(forecastDay);
            }
            calendar.add(Calendar.YEAR, -1);
        } 
        return forecastData;
    }
}