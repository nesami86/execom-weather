package main.database;

import main.entities.City;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface CityRepository extends CrudRepository<City, Long>{
//public interface CityRepository extends JpaRepository<City, Long>{
	City findByCityId(Integer cityId);
}