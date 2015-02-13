package tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.entities.WeatherOncePerDay;
import main.weatherApplication.JSONParser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeans.class, WeatherReaderTest.TestConfig.class})
public class JSONParserTest {
	List<WeatherOncePerDay> listWeather = new ArrayList<WeatherOncePerDay>();
	WeatherOncePerDay day1m1 = new WeatherOncePerDay(-2, 10, 1420070400000L);
	WeatherOncePerDay day1m2 = new WeatherOncePerDay(0, 0, 1420070400000L);
	WeatherOncePerDay day1m3 = new WeatherOncePerDay(-12, 1, 1420070400000L);
	
	
	@Configuration
    static class TestConfig {
	/*	@Bean
        public JSONParser getJSONParserMock() {
            return mock(JSONParser.class);
        }*/
		
		@Bean
		public WeatherOncePerDay getWeatherOncePerDayMock(){
			return mock(WeatherOncePerDay.class);
		}
	}
	
	@Autowired
	private JSONParser jsonParser;
	
	@Autowired
	public WeatherOncePerDay weatherOncePerDay;
	
	
	
	@Test
	public void maxMinForDayTest(){
		listWeather.add(day1m1);
		listWeather.add(day1m2);
		listWeather.add(day1m3);
		
	//	when(listWeather.get(1)).thenReturn(day1m1);
	//	when(listWeather.get(2)).thenReturn(day1m2);
	//	when(listWeather.get(3)).thenReturn(day1m3);
	/*	for(int i=0; i< listWeather.size();i++){
			when(weatherOncePerDay.getDate()).thenReturn(listWeather.get(i).getDate());
			when(weatherOncePerDay.getTempMax()).thenReturn(listWeather.get(i).getTempMax());
			when(weatherOncePerDay.getTempMin()).thenReturn(listWeather.get(i).getTempMin());
		}*/
		
		WeatherOncePerDay result = jsonParser.maxMinForDay(listWeather);
	//	WeatherOncePerDay result = new WeatherOncePerDay(-12,10,1420070400000L);
		WeatherOncePerDay test = new WeatherOncePerDay(-12,10,1420070400000L);
		
		assertEquals(test.getDate(),result.getDate());
		assertEquals(test.getTempMax(),result.getTempMax());
		assertEquals(test.getTempMin(),result.getTempMin());
	}
	
}
