package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import main.database.CityRepository;
import main.entities.City;
import main.entities.WeatherOncePerDay;
import main.weatherApplication.JSONParser;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestBeans.class, JSONParser.class})

public class JSONParserTest {
	List<WeatherOncePerDay> listWeather = new ArrayList<WeatherOncePerDay>();
	WeatherOncePerDay day1m1 = new WeatherOncePerDay(-2, 10, 1420070400000L);
	WeatherOncePerDay day1m2 = new WeatherOncePerDay(0, 0, 1420070400000L);
	WeatherOncePerDay day1m3 = new WeatherOncePerDay(-12, 1, 1420070400000L);

	List<WeatherOncePerDay> listWeatherDay2 = new ArrayList<WeatherOncePerDay>();
	WeatherOncePerDay day2m1 = new WeatherOncePerDay(0, 10, 1422144000000L);
	WeatherOncePerDay day2m2 = new WeatherOncePerDay(0, 35, 1422144000000L);
	WeatherOncePerDay day2m3 = new WeatherOncePerDay(-12, 0, 1422144000000L);

	@Configuration
	static class TestConfig {

	}

	@Autowired
	private JSONParser jsonParser;


	@Test
	public void maxMinForDayTest(){
		listWeather.add(day1m1);
		listWeather.add(day1m2);
		listWeather.add(day1m3);

		listWeatherDay2.add(day2m1);
		listWeatherDay2.add(day2m2);
		listWeatherDay2.add(day2m3);

		WeatherOncePerDay result = jsonParser.maxMinForDay(listWeather);
		WeatherOncePerDay test = new WeatherOncePerDay(-12,10,1420070400000L);

		WeatherOncePerDay resultDay2 = jsonParser.maxMinForDay(listWeatherDay2);

		WeatherOncePerDay testDay2 = new WeatherOncePerDay(-12, 35, 1422144000000L);

		assertNotNull(result);

		assertNotNull(result.getDate());
		assertNotNull(result.getTempMax());
		assertNotNull(result.getTempMin());

		assertEquals(test.getDate(),result.getDate());
		assertEquals(test.getTempMax(),result.getTempMax());
		assertEquals(test.getTempMin(),result.getTempMin());


		assertNotNull(resultDay2);

		assertNotNull(resultDay2.getDate());
		assertNotNull(resultDay2.getTempMax());
		assertNotNull(resultDay2.getTempMin());

		assertEquals(testDay2.getDate(),resultDay2.getDate());
		assertEquals(testDay2.getTempMax(),resultDay2.getTempMax());
		assertEquals(testDay2.getTempMin(),resultDay2.getTempMin());
	}



}
