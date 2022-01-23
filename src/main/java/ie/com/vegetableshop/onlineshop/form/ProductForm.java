package ie.com.vegetableshop.onlineshop.form;

import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import ie.com.vegetableshop.onlineshop.model.Product;
import ie.com.vegetableshop.onlineshop.repository.ProductRepository;

public class ProductForm {

	// Bean Validation to check the attributes
	@NotNull
	@NotEmpty
	@NotBlank
	private String name;
	@NotNull
	@NotEmpty
	@NotBlank
	private String description;
	@NotNull
	private Double price;
	@NotNull
	private Integer quantity;
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Product convert() {
		return new Product(name, description, price, quantity, url);
	}

	public Product update(Integer id, ProductRepository productRepository) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()) {
			Product p = product.get();
			p.setName(name);
			p.setDescription(description);
			p.setPrice(price);
			p.setQuantity(quantity);
			return p;
		} else {
			return null;
		}
	}

}
