package ie.com.vegetableshop.onlineshop.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Double totalPrice;
	@Enumerated(EnumType.STRING)
	private Status status = Status.PROCESSING;
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private EndUser endUser;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "purchase")
	@JsonManagedReference
	private List<RequestedProduct> requestedProduct = new ArrayList<>();
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Scheduling scheduling;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public EndUser getEndUser() {
		return endUser;
	}

	public void setEndUser(EndUser endUser) {
		this.endUser = endUser;
	}

	public Scheduling getScheduling() {
		return scheduling;
	}

	public void setScheduling(Scheduling scheduling) {
		this.scheduling = scheduling;
	}

	public List<RequestedProduct> getRequestedProduct() {
		return requestedProduct;
	}

	public void setRequestedProduct(List<RequestedProduct> requestedProduct) {
		this.requestedProduct = requestedProduct;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endUser == null) ? 0 : endUser.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((requestedProduct == null) ? 0 : requestedProduct.hashCode());
		result = prime * result + ((scheduling == null) ? 0 : scheduling.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((totalPrice == null) ? 0 : totalPrice.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Purchase other = (Purchase) obj;
		if (endUser == null) {
			if (other.endUser != null)
				return false;
		} else if (!endUser.equals(other.endUser))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (requestedProduct == null) {
			if (other.requestedProduct != null)
				return false;
		} else if (!requestedProduct.equals(other.requestedProduct))
			return false;
		if (scheduling == null) {
			if (other.scheduling != null)
				return false;
		} else if (!scheduling.equals(other.scheduling))
			return false;
		if (status != other.status)
			return false;
		if (totalPrice == null) {
			if (other.totalPrice != null)
				return false;
		} else if (!totalPrice.equals(other.totalPrice))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", totalPrice=" + totalPrice + ", status=" + status + ", endUser=" + endUser
				+ ", requestedProduct=" + requestedProduct + ", scheduling=" + scheduling + "]";
	}

	
}
