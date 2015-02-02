package tests;

import main.authentication.UserDetailsServiceImpl;
import main.database.AdministratorRepository;
import main.entities.Administrator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Matchers.anyString;

import static org.mockito.Mockito.spy;
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
    }
    
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
   
    @Autowired
    private AdministratorRepository AdminRepo;
    
    @Autowired
    private UserDetails userDetails;
    
    @Autowired
    private Administrator admin;
    
    @Test
    public void loadUserByUsernameTest1() {
        UserDetailsServiceImpl spy = spy(userDetailsServiceImpl);
        
        when(AdminRepo.findByAdministratorUsername(anyString())).thenReturn(admin);
//        when(spy.loadUserByUsername(anyString())).thenReturn(userDetails);
        spy.loadUserByUsername(anyString());
        
        verify(spy).convertAdmin(admin);
    }
    
    @Test(expected=NullPointerException.class)
    public void loadUserByUsernameTest2() {
        when(AdminRepo.findByAdministratorUsername(anyString())).thenReturn(null);
        userDetailsServiceImpl.loadUserByUsername(anyString());
    }
}