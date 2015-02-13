package main.database;

import java.util.List;

import main.entities.City;
import main.entities.Weather;
import main.entities.WeatherOncePerDay;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

@Component
public interface WeatherOncePerDayRepository extends Repository<WeatherOncePerDay, Long> {

	WeatherOncePerDay findByCityAndDate(City city, long date);

	List<WeatherOncePerDay> findByCityAndDateBetween(City city, Long date1, Long date2);
	
	@Query("SELECT MAX(date) FROM WeatherOncePerDay")
	public Long findWeatherDate();
}