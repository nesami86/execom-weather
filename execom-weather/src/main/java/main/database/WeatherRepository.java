package main.database;

import java.util.List;

import main.entities.City;
import main.entities.Weather;

import org.springframework.stereotype.Component;
import org.springframework.data.repository.CrudRepository;

@Component
public interface WeatherRepository extends CrudRepository<Weather, Long>{

    List<Weather> findByCity(City city);
    
    List<Weather> findByDate(Long date);
    
    List<Weather> findByCityAndDate(City city, Long date);
}