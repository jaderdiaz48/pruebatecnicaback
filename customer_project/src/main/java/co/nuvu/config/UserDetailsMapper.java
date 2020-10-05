package co.nuvu.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import co.nuvu.models.Users;

public class UserDetailsMapper {

	public static UserDetails build(Users user) {
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				getAuthorities(user));
	}

	private static Set<? extends GrantedAuthority> getAuthorities(Users user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRol().getName().toUpperCase()));
		return authorities;
	}
}