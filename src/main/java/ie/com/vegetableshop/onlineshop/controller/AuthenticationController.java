package ie.com.vegetableshop.onlineshop.controller;


import ie.com.vegetableshop.onlineshop.dto.TokenDTO;
import ie.com.vegetableshop.onlineshop.form.LoginForm;
import ie.com.vegetableshop.onlineshop.security.TokenService;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Juliana Viana
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired//AuthenticationManager injected by authenticationManager() in SecurityConfig
    private AuthenticationManager authManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> authentication(@RequestBody @Valid LoginForm form) {
        UsernamePasswordAuthenticationToken login = form.convert();

        try {
            Authentication auth = authManager.authenticate(login);
            String token = tokenService.createToken(auth);
            String role = "admin";
            boolean isAdmin = userHasAuthority(role, auth);
            return ResponseEntity.ok(new TokenDTO(token, "Bearer", isAdmin));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    private boolean userHasAuthority(String role, Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (role.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
	}

}

