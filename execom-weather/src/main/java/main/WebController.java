package main;

import java.io.IOException;
import java.util.List;

import main.beans.WeatherPeriod;
import main.database.AdministratorRepository;
import main.database.CityRepository;
import main.entities.Weather;
import main.entities.WeatherOncePerDay;
import main.weatherApplication.CurrentWeatherQuery;
import main.weatherApplication.WeatherQuery;
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
    private WeatherQuery weatherQuery;
    
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
    
    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @RequestMapping("/adminPage")
    public String adminPage(Model model) {
        model.addAttribute("admin", adminRepo.findByAdministratorUsername(getAuthenticatedUsersName()));
        return "adminPage";
    }
    
    @RequestMapping("/weather/report")
    public @ResponseBody String getWeatherReport(@RequestParam("city") String city) throws IllegalStateException, IOException {
        weatherQuery.returnWeather(city);
        return city;
    }
    
    @RequestMapping("/weather/getData")
    public void getWeatherHistory() throws IllegalStateException, IOException {
        weatherQueryInit.returnWeather();
    }
    
    @RequestMapping("/weather/getFreshData")
    public void getFreshWeatherData() throws IllegalStateException, IOException {
        currentWeatherQuery.currentQuerry();
    }
    
    @RequestMapping("/weather/getPeriod")
    public @ResponseBody List<Weather> getWeatherPeriod(@RequestBody WeatherPeriod weatherPeriod) {
        return weatherReader.getWeatherReports(weatherPeriod);
    }
    
    @RequestMapping("/weather/getForecast")
    public @ResponseBody List<WeatherOncePerDay> getWeatherForecast(@RequestBody WeatherPeriod weatherPeriod) {
        return weatherReader.getWeatherForecast(weatherPeriod);
    }
}
