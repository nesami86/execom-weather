package tests;

import static org.mockito.Mockito.mock;
import main.beans.WeatherPeriod;
import main.database.CityRepository;
import main.database.WeatherOncePerDayRepository;
import main.database.WeatherRepository;
import main.entities.City;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

@Configuration
public class TestBeans {
    
   
    
    @Bean
    public Model getModelMock() {
        return mock(Model.class);
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
    
   
    
}