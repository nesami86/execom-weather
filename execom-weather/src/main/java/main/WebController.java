package main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping("/")
    public String welcomePage() {
        return "index";
    }
    
    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @RequestMapping("/adminPage")
    public String adminPage() {
        return "adminPage";
    }
}