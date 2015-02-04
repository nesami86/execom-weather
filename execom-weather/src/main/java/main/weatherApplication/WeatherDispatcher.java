package main.weatherApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import main.entities.WeatherPeriod;

import org.springframework.stereotype.Component;

@Component
public class WeatherDispatcher {
    
    public void getWeatherReports(WeatherPeriod weatherPeriod){
        Integer cityId = Integer.parseInt(weatherPeriod.getCityId());
        long startingDate = getUnixTime(weatherPeriod.getStartingDate());
        long endingDate = getUnixTime(weatherPeriod.getEndingDate());
        
        
    }
    
    public long getUnixTime (String date) {
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