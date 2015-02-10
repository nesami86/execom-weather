package main.authentication;

import java.util.ArrayList;
import java.util.Collection;

import main.database.AdministratorRepository;
import main.entities.Administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Gets converted ( from Administrator to spring security User ) users from database
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AdministratorRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return convertAdmin(adminRepository.findByAdministratorUsername(username));
	}

	public User convertAdmin(Administrator admin) {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ADMIN"));

		return new User(admin.getAdministratorUsername(), admin.getAdministratorPassword(), authorities);
	}
}