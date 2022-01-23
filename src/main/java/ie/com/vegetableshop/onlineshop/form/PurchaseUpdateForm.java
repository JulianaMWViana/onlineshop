package ie.com.vegetableshop.onlineshop.form;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import ie.com.vegetableshop.onlineshop.model.Purchase;
import ie.com.vegetableshop.onlineshop.model.Status;
import ie.com.vegetableshop.onlineshop.repository.PurchaseRepository;

public class PurchaseUpdateForm {
	
	@NotNull
	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Purchase update(Integer id, PurchaseRepository purchaseRepository) {
		Optional<Purchase> p = purchaseRepository.findById(id);
		if(p != null) {
			Purchase purchase = p.get();
			purchase.setStatus(status);
			return purchase;
		} else {
			return null;
		}
	}

}
