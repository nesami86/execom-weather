package main.database;

import main.entities.City;
import main.entities.WeatherOncePerDay;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

@Component
public interface WeatherOncePerDayRepository extends Repository<WeatherOncePerDay, Long> {

    WeatherOncePerDay findByCityAndDate(City city, long date);
}