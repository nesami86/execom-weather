package main;

import java.io.IOException;
import java.util.List;

import main.database.AdministratorRepository;
import main.database.CityRepository;
import main.entities.Weather;
import main.entities.WeatherPeriod;
import main.weatherApplication.WeatherDispatcher;
import main.weatherApplication.WeatherQuery;
import main.weatherApplication.WeatherQueryInit;

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
    private WeatherDispatcher weatherDispatcher;
    
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
    public @ResponseBody String getWeatherReport(@RequestParam("city") String city) throws Exception {
        weatherQuery.returnWeather(city);
        return city;
    }
    
    @RequestMapping("/weather/getData")
    public void getWeatherHistory() throws IllegalStateException, IOException {
        weatherQueryInit.returnWeather();
    }
    
    @RequestMapping("/weather/getPeriod")
    public @ResponseBody List<Weather> getWeatherPeriod(@RequestBody WeatherPeriod weatherPeriod) {
        return weatherDispatcher.getWeatherReports(weatherPeriod);
    }
}