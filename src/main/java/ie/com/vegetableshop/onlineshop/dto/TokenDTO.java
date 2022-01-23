package ie.com.vegetableshop.onlineshop.dto;

/**
 * DTO (Data Transfer Object) Design Pattern to represent Token Class
 *
 * @author Juliana Viana
 */
public class TokenDTO {

    private String token;
    private String type;
    private boolean isAdmin;

    public TokenDTO(String token, String type, boolean isAdmin) {
        this.token = token;
        this.type = type;
        this.isAdmin = isAdmin;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

	public boolean isAdmin() {
		return isAdmin;
	}

}
