package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.WebController;
import main.beans.WeatherPeriod;
import main.entities.Administrator;
import main.entities.City;
import main.entities.Weather;
import main.entities.WeatherOncePerDay;
import main.weatherApplication.CurrentWeatherQuery;
import main.weatherApplication.JSONParser;
import main.weatherApplication.WeatherQuery;
import main.weatherApplication.WeatherQueryInit;
import main.weatherApplication.WeatherReader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeans.class, WebControllerTest.TestConfig.class})
public class WebControllerTest {

    @Configuration
    static class TestConfig {
            
        @Bean
        public WebController getWebControllerInstance() {
            return new WebController();
        }
        
        @Bean
        public WeatherQuery getWeatherQueryMock() {
            return mock(WeatherQuery.class);
        }
        
        @Bean
        public WeatherQueryInit getWeatherQueryInitMock() {
            return mock(WeatherQueryInit.class);
        }
        
        @Bean
        public WeatherReader getWeatherDispatcherMock() {
            return mock(WeatherReader.class);
        }
        
        @Bean
        public JSONParser getJSONParserMock() {
            return mock(JSONParser.class);
        }
        
        @Bean
        public CurrentWeatherQuery getCurrentWeatherQueryMock() {
            return mock(CurrentWeatherQuery.class);
        }
    }
    
    @Autowired
    private WebController webController;
    
    @Autowired
    private Model model;
    
    @Autowired
    private Administrator admin;
    
    @Autowired
    private City city;
   
    @Autowired
    private WeatherQueryInit weatherQueryInit;
    
    @Autowired
    private WeatherReader weatherReader;
    
    @Autowired
    private WeatherPeriod weatherPeriod;
    
    @Autowired
    private WeatherQuery weatherQuery;
    
    @Autowired
    private CurrentWeatherQuery currentWeatherQuery;
    
    @Before
    public void setUp() {      
        Authentication auth = new UsernamePasswordAuthenticationToken(admin,null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
    
    @Test
    public void welcomePageTest() throws IllegalStateException, IOException {
        assertEquals("index", webController.welcomePage(model));
    }
    
    @Test
    public void loginPageTest() {
        assertEquals("login", webController.loginPage());
    }
    
    @Test
    public void adminPageTest() {
        assertEquals("adminPage", webController.adminPage(model));
    }
    
    @Test
    public void getWeatherReportTest() throws IllegalStateException, IOException {
        assertEquals("Novi Sad", webController.getWeatherReport("Novi Sad"));
    }
    
    @Test
    public void getFreshWeatherData() throws IllegalStateException, IOException {
        webController.getWeatherHistory();
        verify(weatherQueryInit).returnWeather();
    }
    
    @Test
    public void getFreshWeatherDataTest() throws IllegalStateException, IOException {
        webController.getFreshWeatherData();
        verify(currentWeatherQuery).currentQuerry();
    }
    
    @Test
    public void getWeatherPeriodTest() {
        List<Weather> weather = new ArrayList<Weather>();      
        when(weatherReader.getWeatherReports(weatherPeriod)).thenReturn(weather);
        
        assertEquals(weather,  webController.getWeatherPeriod(weatherPeriod));
    }
    
    @Test
    public void getWeatherForecastTest() {
        List<WeatherOncePerDay> forecast = new ArrayList<WeatherOncePerDay>();      
        when(weatherReader.getWeatherForecast(weatherPeriod)).thenReturn(forecast);
        
        assertEquals(forecast,  webController.getWeatherForecast(weatherPeriod));
    }
}