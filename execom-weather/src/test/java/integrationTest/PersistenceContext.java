package integrationTest;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class PersistenceContext {
	
	 @Autowired
	    private Environment environment;
	 
	 @Bean
	    public DataSource dataSource() {
	        BoneCPDataSource dataSource = new BoneCPDataSource();
	 
	        dataSource.setDriverClass(environment.getRequiredProperty("testdb.driver"));
	        dataSource.setJdbcUrl(environment.getRequiredProperty("testdb.url"));
	        dataSource.setUsername(environment.getRequiredProperty("testdb.username"));
	        dataSource.setPassword(environment.getRequiredProperty("testdb.password"));
	 
	        return dataSource;
	    }

}
