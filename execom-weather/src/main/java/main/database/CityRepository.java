package main.database;

import main.entities.City;

import org.springframework.stereotype.Component;
import org.springframework.data.repository.CrudRepository;

@Component
public interface CityRepository extends CrudRepository<City, Long>{

	//City findByCityID(Integer id);  //mora se zvat isto kao i polje u entitetu
}
