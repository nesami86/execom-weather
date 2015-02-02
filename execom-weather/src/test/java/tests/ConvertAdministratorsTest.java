package tests;

import main.authentication.ConvertAdministrators;
import main.entities.Administrator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeans.class, ConvertAdministratorsTest.TestConfig.class})
public class ConvertAdministratorsTest {

    @Configuration
    static class TestConfig {
            
        @Bean
        public ConvertAdministrators getConvertAdministratorsInstance() {
            return new ConvertAdministrators();
        }
    }
    
    @Autowired
    private ConvertAdministrators convertAdmin;
    
    @Autowired
    private Administrator admin;
        
    @Test
    public void convertUserTest() {
        when(admin.getAdministratorUsername()).thenReturn("nesa");
        when(admin.getAdministratorPassword()).thenReturn("nesa");
        
        assertEquals("nesa", convertAdmin.convertAdmin(admin).getUsername());
    }
}