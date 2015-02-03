package tests;

import java.io.IOException;

import main.WebController;
import main.database.AdministratorRepository;
import main.entities.Administrator;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeans.class, WebControllerTest.TestConfig.class})
public class WebControllerTest {

    @Configuration
    static class TestConfig {
            
        @Bean
        public WebController getWebControllerInstance() {
            return new WebController();
        }
    }
    
    @Autowired
    private WebController webController;
    
    @Autowired
    private AdministratorRepository adminRepo;
    
    @Autowired
    private Model model;
    
    @Autowired
    private Administrator admin;
    
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
}