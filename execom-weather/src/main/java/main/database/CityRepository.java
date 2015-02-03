package main.database;

import main.entities.City;

import org.springframework.stereotype.Component;
import org.springframework.data.repository.CrudRepository;

@Component
public interface CityRepository extends CrudRepository<City, Long>{

	City findByCityID(String CityID);
}
