package main;

import java.io.IOException;
import java.util.List;

import main.beans.WeatherPeriod;
import main.database.AdministratorRepository;
import main.database.CityRepository;
import main.entities.Weather;
import main.entities.WeatherOncePerDay;
import main.weatherApplication.CurrentWeatherQuery;
import main.weatherApplication.WeatherQueryInit;
import main.weatherApplication.WeatherReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController extends WebControllerAddMethods {

    @Autowired
    private AdministratorRepository adminRepo;
    
    @Autowired
    private CityRepository cityRepository;
       
    @Autowired
    private WeatherQueryInit weatherQueryInit;
    
    @Autowired
    private WeatherReader weatherReader;
    
    @Autowired
    private CurrentWeatherQuery currentWeatherQuery;
    
    @RequestMapping("/")
    public String welcomePage(Model model) throws IllegalStateException, IOException {
        model.addAttribute("cities", cityRepository.findAll());
        return "index";
    }
    
    @RequestMapping("/weather/getData")
    public String getWeatherHistory() throws IllegalStateException, IOException {
        weatherQueryInit.returnWeather();
        return "index";
    }
    
    @RequestMapping("/weather/getFreshData")
    public String getFreshWeatherData() throws IllegalStateException, IOException {
        currentWeatherQuery.currentQuerry();
        System.out.println("kraj!");
        return "index";
    }
    
    @RequestMapping("/weather/getPeriod")
    public @ResponseBody List<Weather> getWeatherPeriod(@RequestBody WeatherPeriod weatherPeriod) {
        return weatherReader.getWeatherReports(weatherPeriod);
    }
    
    @RequestMapping("/weather/getPeriodJson")
    public @ResponseBody String getWeatherPeriodJSON(@RequestParam("cityId") String cityId,@RequestParam("startingDate") String startingDate,@RequestParam("endingDate") String endingDate) {
        return weatherReader.getWeatherReportsJSON(cityId, startingDate, endingDate);
    }
    
    @RequestMapping("/weather/getForecast")
    public @ResponseBody List<WeatherOncePerDay> getWeatherForecast(@RequestBody WeatherPeriod weatherPeriod) {
        return weatherReader.getWeatherForecast(weatherPeriod);
    }
    @RequestMapping("/weather/getForecastJson")
    public @ResponseBody String getWeatherForecastJson(@RequestParam("cityId") String cityId,@RequestParam("startingDate") String startingDate,@RequestParam("endingDate") String endingDate) {
        return weatherReader.getWeatherForecastJSON(cityId, startingDate, endingDate);
    }
}
