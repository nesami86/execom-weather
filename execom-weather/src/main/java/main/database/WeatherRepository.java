package main.database;

import java.util.List;

import main.entities.City;
import main.entities.Weather;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

@Component
public interface WeatherRepository extends Repository<Weather, Long>{

	List<Weather> findByCity(City city);

	List<Weather> findByDateGreaterThan(Long date);

	List<Weather> findByDateLessThan(Long date);

	List<Weather> findByCityAndDateBetween(City city, Long date1, Long date2);
	
	@Query("SELECT MAX(date) FROM Weather")
	public Long findWeatherDate();
}