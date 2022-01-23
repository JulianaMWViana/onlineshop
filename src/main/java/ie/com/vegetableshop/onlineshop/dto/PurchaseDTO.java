package ie.com.vegetableshop.onlineshop.dto;

import java.util.List;
import java.util.stream.Collectors;

import ie.com.vegetableshop.onlineshop.model.EndUser;
import ie.com.vegetableshop.onlineshop.model.Purchase;
import ie.com.vegetableshop.onlineshop.model.RequestedProduct;
import ie.com.vegetableshop.onlineshop.model.Scheduling;
import ie.com.vegetableshop.onlineshop.model.Status;

public class PurchaseDTO {

	private Integer id;
	private Double totalPrice;
	private Status status;
	private List<RequestedProduct> requestedProducts;
	private Scheduling scheduling;
	private EndUser endUser;

	public PurchaseDTO(Purchase p) {
		this.id = p.getId();
		this.totalPrice = p.getTotalPrice();
		this.status = p.getStatus();
		this.requestedProducts = p.getRequestedProduct();
		this.scheduling = p.getScheduling();
		this.endUser = p.getEndUser();
	}

	public Integer getId() {
		return id;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public Status getStatus() {
		return status;
	}

	public List<RequestedProduct> getRequestedProducts() {
		return requestedProducts;
	}

	public Scheduling getScheduling() {
		return scheduling;
	}

	public EndUser getEndUser() {
		return endUser;
	}

	public static List<PurchaseDTO> convert(List<Purchase> list) {
		return list.stream().map(PurchaseDTO::new).collect(Collectors.toList());
	}

}
