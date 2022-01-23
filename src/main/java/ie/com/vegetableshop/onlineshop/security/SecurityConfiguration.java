package ie.com.vegetableshop.onlineshop.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import ie.com.vegetableshop.onlineshop.repository.EndUserRepository;

/**
 * Class responsable to load the security configuration
 *
 * @author Juliana Viana
 */
@EnableWebSecurity//Security mode from Spring Boot
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EndUserRepository endUserRepository;
    
    @Override
    @Bean//spring knows that it returns authenticationManager for inject in the autheticationController
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * Check the authentication settings
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());//to encrypt the password
    }

    /**
     * Check the authorization settings. URLs available or restricted
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //to avoid CORS problems
    	http.cors().and().authorizeRequests()
    	.antMatchers(HttpMethod.POST, "/auth").permitAll()
    	.antMatchers(HttpMethod.GET, "/product").permitAll()
    	.antMatchers(HttpMethod.GET, "/product/*").permitAll()
    	.antMatchers(HttpMethod.POST, "/enduser").permitAll()
    	.antMatchers(HttpMethod.GET, "/product/*").permitAll()
    	.anyRequest().authenticated()
    	.and().csrf().disable()//CSRF disabled;
    	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//login stateless
    	.and().addFilterBefore(new AuthenticationTokenFilter(tokenService, endUserRepository), UsernamePasswordAuthenticationFilter.class);
    	
    	//to access the DB in the Browser
//        http.authorizeRequests().antMatchers("/").permitAll().and()
//        .authorizeRequests().antMatchers("/console/**").permitAll();
//        http.csrf().disable();
//        http.headers().frameOptions().disable();
    }

    /**
     * Responsible to check the static resources (css, img, js...)
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//        return source;
    	CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
//        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
