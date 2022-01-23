package ie.com.vegetableshop.onlineshop.dto;

import java.util.List;
import java.util.stream.Collectors;

import ie.com.vegetableshop.onlineshop.model.EndUser;

public class EndUserDTO {

	private Integer id;
	private String name;
	private String email;
	private String phone;
	private String address;

	public EndUserDTO(EndUser u) {
		this.id = u.getId();
		this.name = u.getName();
		this.email = u.getEmail();
		this.phone = u.getPhone();
		this.address = u.getAddress();
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	/**
	 * Convert entity object to DTO object
	 * 
	 * @param list
	 * @return
	 */
	public static List<EndUserDTO> convert(List<EndUser> list) {
		return list.stream().map(EndUserDTO::new).collect(Collectors.toList());
	}

	public static EndUserDTO convert(EndUser endUser) {
		return new EndUserDTO(endUser);
	}

}
