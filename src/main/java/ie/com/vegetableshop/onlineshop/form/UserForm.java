package ie.com.vegetableshop.onlineshop.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ie.com.vegetableshop.onlineshop.model.EndUser;
import ie.com.vegetableshop.onlineshop.model.Roles;
import ie.com.vegetableshop.onlineshop.repository.RolesRepository;

public class UserForm {

	// Bean Validation to check the attributes
	@NotNull
	@NotEmpty
	@NotBlank
	private String name;
	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 3, max = 16)
	private String password;
	@NotNull
	@NotEmpty
	@NotBlank
	private String email;
	@NotNull
	@NotEmpty
	@NotBlank
	private String phone;
	@NotNull
	@NotEmpty
	@NotBlank
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public EndUser convert(RolesRepository roles) {
		List<Roles> rolesList = new ArrayList<>();
		rolesList.add(roles.findByRoleName("customer"));
		String encryptedPassword = new BCryptPasswordEncoder().encode(password);
		return new EndUser(name, encryptedPassword, email, phone, address, rolesList);
	}

}
