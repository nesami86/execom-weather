package main;

import main.database.AdministratorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController extends WebControllerAddMethods {

    @Autowired
    private AdministratorRepository adminRepo;
    
    @RequestMapping("/")
    public String welcomePage() {
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
    public @ResponseBody String getWeatherReport(@RequestParam("city") String city) {
        
        return city;
    }
}