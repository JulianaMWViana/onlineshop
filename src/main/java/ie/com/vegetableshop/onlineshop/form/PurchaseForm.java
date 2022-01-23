package ie.com.vegetableshop.onlineshop.form;

import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.constraints.NotNull;

import ie.com.vegetableshop.onlineshop.model.EndUser;
import ie.com.vegetableshop.onlineshop.model.Purchase;
import ie.com.vegetableshop.onlineshop.model.Scheduling;
import ie.com.vegetableshop.onlineshop.model.Status;

public class PurchaseForm {

	@NotNull
	private LocalDateTime scheduling;
	@NotNull
	private Map<String, Integer> productIdAndQuantity;

	public LocalDateTime getScheduling() {
		return scheduling;
	}

	public void setScheduling(LocalDateTime scheduling) {
		this.scheduling = scheduling;
	}

	public Map<String, Integer> getProductIdAndQuantity() {
		return productIdAndQuantity;
	}

	public void setProductIdAndQuantity(Map<String, Integer> productIdAndQuantity) {
		this.productIdAndQuantity = productIdAndQuantity;
	}

	public Purchase convert(EndUser u, Double totalPrice) {
		Purchase p = new Purchase();
		p.setStatus(Status.PROCESSING);
		p.setTotalPrice(totalPrice);
		p.setEndUser(u);
		Scheduling s = new Scheduling();
		s.setDateAndTime(scheduling);
		p.setScheduling(s);
		return p;
	}

}
