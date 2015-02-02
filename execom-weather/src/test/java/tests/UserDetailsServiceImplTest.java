package tests;

import main.authentication.ConvertAdministrators;
import main.authentication.UserDetailsServiceImpl;
import main.database.AdministratorRepository;
import main.entities.Administrator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.anyString;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeans.class, UserDetailsServiceImplTest.TestConfig.class})
public class UserDetailsServiceImplTest {

    @Configuration
    static class TestConfig {
                
        @Bean
        public UserDetailsServiceImpl getUserDetailsServiceImplInstance() {
            return new UserDetailsServiceImpl();
        }
        
        @Bean
        public ConvertAdministrators getConvertAdministratorsMock() {
            return mock(ConvertAdministrators.class);
        }
    }
    
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    
    @Autowired
    private ConvertAdministrators convertAdmin;
   
    @Autowired
    private AdministratorRepository AdminRepo;
    
    @Autowired
    private Administrator admin;
    
    @Test
    public void loadUserByUsernameTest1() {
        when(AdminRepo.findByAdministratorUsername(anyString())).thenReturn(admin);
        userDetailsServiceImpl.loadUserByUsername(anyString());
        
        verify(convertAdmin).convertAdmin(admin);
    }
    
    @Test(expected=UsernameNotFoundException.class)
    public void loadUserByUsernameTest2() {
        when(AdminRepo.findByAdministratorUsername(anyString())).thenReturn(null);
        userDetailsServiceImpl.loadUserByUsername(anyString());
    }
}