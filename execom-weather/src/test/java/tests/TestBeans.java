package tests;

import java.util.List;

import main.beans.WeatherPeriod;
import main.database.AdministratorRepository;
import main.database.CityRepository;
import main.database.WeatherOncePerDayRepository;
import main.database.WeatherRepository;
import main.entities.Administrator;
import main.entities.City;
import main.entities.WeatherOncePerDay;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

import static org.mockito.Mockito.mock;

@Configuration
public class TestBeans {
    
    @Bean
    public AdministratorRepository getAdministratorRepositoryMock() {
        return mock(AdministratorRepository.class);
    }
    
    @Bean
    public Model getModelMock() {
        return mock(Model.class);
    }
    
    @Bean
    public Administrator getAdministratorMock() {
        return mock(Administrator.class);
    }
    
    @Bean
    public City getCityMock() {
        return mock(City.class);
    }
    
    @Bean
    public CityRepository getCityRepositoryMock() {
        return mock(CityRepository.class);
    }
    
    @Bean
    public WeatherRepository getWeatherRepositoryMock() {
        return mock(WeatherRepository.class);
    }
    
    @Bean
    public WeatherPeriod getWeatherPeriodMock() {
        return mock(WeatherPeriod.class);
    }
    
    @Bean
    public WeatherOncePerDayRepository getWeatherOncePerDayRepositoryMock() {
        return mock(WeatherOncePerDayRepository.class);
    }
    
    //
    @Bean
    public WeatherOncePerDay getWeatherOncePerDayMock() {
        return mock(WeatherOncePerDay.class);
    }
    
}