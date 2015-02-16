package integrationTest;

import static org.junit.Assert.assertNotNull;

import java.util.Iterator;

import main.database.CityRepository;
import main.entities.City;
import main.weatherApplication.JSONParser;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertGreater;


import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:app-context.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class,
	DbUnitTestExecutionListener.class })
public class JSONPareserIT {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private JSONParser jsonParser;

	@Test
	public void parserTest(){



		String jsonString = "{\"message\":\"\",\"cod\":\"200\",\"city_id\":745044,\"calctime\":2.4534,\"cnt\":160,\"list\":[{\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"main\":{\"temp\":280.87,\"pressure\":1024,\"humidity\":75,\"temp_min\":280.15,\"temp_max\":281.15},\"wind\":{\"speed\":3.1,\"deg\":40,\"var_beg\":10,\"var_end\":100},\"clouds\":{\"all\":40},\"dt\":1357002000}]}";
		DateTime startDate = new DateTime(1356998400 * 1000L);
		DateTime endDate = new DateTime(1357603200 * 1000L);; //1357603200
		//Datume izracunaj
		jsonParser.parser(jsonString, startDate, endDate);
		assertEquals(1, cityRepository.count());


		//City cityResult = 
		Iterable<City> x = cityRepository.findAll();

		for(City cityResult : x){

			assertNotNull(cityResult);
			assertNotNull(cityResult.getId());
			assertNotNull(cityResult.getWeatherList());
			assertNotNull(cityResult.getWeatherOncePerDayList());
			assertEquals(745044, (int)cityResult.getId());
		}
		
		City cityRes2 = cityRepository.findByCityId(745044);
		assertNotNull(cityRes2);
	}
}
