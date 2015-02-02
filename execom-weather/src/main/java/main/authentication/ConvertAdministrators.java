package main.authentication;

import java.util.ArrayList;
import java.util.Collection;

import main.entities.Administrator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Translates Administrators from entities to spring security users
 */
@Component
public class ConvertAdministrators {

    @Transactional(readOnly = true)
    public User convertAdmin(Administrator admin) {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));

        return new User(admin.getAdministratorUsername(), admin.getAdministratorPassword(), authorities);
    }
}