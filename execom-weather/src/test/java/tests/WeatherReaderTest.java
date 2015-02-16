package tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.WebController;
import main.beans.WeatherPeriod;
import main.entities.Weather;
import main.weatherApplication.CurrentWeatherQuery;
import main.weatherApplication.JSONParser;
import main.weatherApplication.WeatherQueryInit;
import main.weatherApplication.WeatherReader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeans.class, WeatherReaderTest.TestConfig.class})
public class WeatherReaderTest {

    @Configuration
    static class TestConfig {
        
        @Bean
        public WeatherReader getWeatherReaderInstance() {
            return new WeatherReader();
        }
        
        @Bean
        public WebController getWebControllerMock() {
            return mock(WebController.class);
        }
                
        @Bean
        public WeatherQueryInit getWeatherQueryInitMock() {
            return mock(WeatherQueryInit.class);
        }
        
        @Bean
        public JSONParser getJSONParserMock() {
            return mock(JSONParser.class);
        }
        
        @Bean
        public CurrentWeatherQuery getCurrentWeatherQueryMock() {
            return mock(CurrentWeatherQuery.class);
        }
    }
    
    @Autowired
    private WeatherReader weatherReader;
    
    @Autowired
    private WeatherPeriod weatherPeriod;
    
    @Test
    public void getWeatherReportsTest() {
        List<Weather> weather = new ArrayList<Weather>();
        
        when(weatherPeriod.getCityId()).thenReturn("1");
        when(weatherPeriod.getStartingDate()).thenReturn("2015-02-05");
        when(weatherPeriod.getEndingDate()).thenReturn("2015-02-05");
        
        assertEquals(weather, weatherReader.getWeatherReports(weatherPeriod));
    }
    
    @Test
    public void getUnixTimeTest() {
        assertEquals(1423094400000L, weatherReader.getUnixTime("2015-02-05"));
    }
    
    @Test
    public void getDateTest() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            calendar.setTime(sdf.parse("2015-02-05"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
       
        assertEquals(calendar.getTimeInMillis(), weatherReader.getDate("2015-02-05").getTimeInMillis());
    }
}