package ie.com.vegetableshop.onlineshop.dto;

import java.util.List;
import java.util.stream.Collectors;

import ie.com.vegetableshop.onlineshop.model.Product;

public class ProductDTO {

	private Integer id;
	private String name;
	private String description;
	private Double price;
	private Integer quantity;
	private String url;

	public ProductDTO(Product p) {
		this.id = p.getId();
		this.name = p.getName();
		this.description = p.getDescription();
		this.price = p.getPrice();
		this.quantity = p.getQuantity();
		this.url = p.getUrl();
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Double getPrice() {
		return price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public String getUrl() {
		return url;
	}

	/**
	 * Convert entity object to DTO object
	 * 
	 * @param produts
	 * @return
	 */
	public static List<ProductDTO> convert(List<Product> produts) {
		return produts.stream().map(ProductDTO::new).collect(Collectors.toList());
	}

	public static ProductDTO convert(Product product) {
		return new ProductDTO(product);
	}

}
