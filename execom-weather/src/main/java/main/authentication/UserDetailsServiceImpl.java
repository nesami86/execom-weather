package main.authentication;

import main.database.AdministratorRepository;
import main.entities.Administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Gets converted users from database
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private ConvertAdministrators convertAdministrators;

    @Autowired
    private AdministratorRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Administrator admin = adminRepository.findByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return convertAdministrators.convertAdmin(admin);
    }
}