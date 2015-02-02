package tests;

import main.database.AdministratorRepository;
import main.entities.Administrator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

import static org.mockito.Mockito.mock;

@Configuration
public class TestBeans {
    
    @Bean
    public AdministratorRepository getAdministratorRepositoryMock() {
        return mock(AdministratorRepository.class);
    }
    
    @Bean
    public Model getModelMock() {
        return mock(Model.class);
    }
    
    @Bean
    public Administrator getAdministratorMock() {
        return mock(Administrator.class);
    }
}