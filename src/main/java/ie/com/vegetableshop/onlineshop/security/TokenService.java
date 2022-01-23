package ie.com.vegetableshop.onlineshop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ie.com.vegetableshop.onlineshop.model.EndUser;

import org.springframework.security.core.Authentication;

/**
 * Class responsable to create the JWT (Java Web Token)
 *
 * @author Juliana Viana
 */
@Service
public class TokenService {

    @Value("${ie.com.vegetableshop.onlineshop.security.jwt.expiration}")//application.properties file
    private String expiration;
    @Value("${ie.com.vegetableshop.onlineshop.security.jwt.secret}")//application.properties file
    private String secret;

    /**
     * Method responsible for creating the JWT and its configuration values
     * 
     * @param authentication
     * @return
     */
    public String createToken(Authentication authentication) {
        EndUser loggedUser = (EndUser) authentication.getPrincipal();
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("API OnlineShop")
                .setSubject(loggedUser.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * Method responsible to get the information inside the token
     * 
     * @param token
     * @return
     */
    boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * return the user id inside the token
     *
     * @param token
     * @return
     */
    public Integer getIdUser(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Integer.parseInt(claims.getSubject());
    }
}
