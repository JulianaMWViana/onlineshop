package ie.com.vegetableshop.onlineshop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ie.com.vegetableshop.onlineshop.model.EndUser;
import ie.com.vegetableshop.onlineshop.repository.EndUserRepository;

import java.util.Optional;

/**
 * The authentication logic is defined in this class using UserDetailsService that gets the UserDetails interface.
 *
 * @author Juliana Viana
 */
@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private EndUserRepository endUserRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<EndUser> u = null;
		u = endUserRepository.findByEmail(email);
		if (u != null && u.isPresent()) {
			return u.get();
		}
		throw new UsernameNotFoundException("User name was not found");
	}

}
