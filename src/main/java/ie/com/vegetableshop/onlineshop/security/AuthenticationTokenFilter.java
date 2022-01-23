package ie.com.vegetableshop.onlineshop.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import ie.com.vegetableshop.onlineshop.model.EndUser;
import ie.com.vegetableshop.onlineshop.repository.EndUserRepository;

/**
 * Class responsable the intercept the requests and validate the token
 *
 * @author Juliana Viana
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private EndUserRepository endUserRepository;

	public AuthenticationTokenFilter(TokenService tokenService, EndUserRepository endUserRepository) {
		this.tokenService = tokenService;
		this.endUserRepository = endUserRepository;
	}

	/**
	 * Do the validation of the header token
	 *
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = recoverToken(request);
		
		boolean validUser = tokenService.isValidToken(token);
		if (validUser) {
			authenticateUser(token);
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * Gives access to the user that needs the request
	 */
	private void authenticateUser(String token) {
		Integer idUser = tokenService.getIdUser(token);
		EndUser user = endUserRepository.findById(idUser).get();
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
				user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	/**
	 * Token header validation
	 *
	 * @param request
	 * @return
	 */
	private String recoverToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());// to remove Bearer
	}

}
