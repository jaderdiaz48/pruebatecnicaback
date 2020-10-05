package co.nuvu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.nuvu.config.UserDetailsMapper;
import co.nuvu.models.Users;
@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private IUsersService usersService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = usersService.findByUser(username);
		if (user != null) {
			return UserDetailsMapper.build(user);
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}