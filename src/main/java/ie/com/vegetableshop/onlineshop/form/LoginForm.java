package ie.com.vegetableshop.onlineshop.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Class responsible to receive the Login details from the front end form
 *
 * @author Juliana Viana
 */
public class LoginForm {

	@NotNull
	@NotEmpty
	@NotBlank
    private String email;
	@NotNull
	@NotEmpty
	@NotBlank
    private String password;


    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsernamePasswordAuthenticationToken convert() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

}

