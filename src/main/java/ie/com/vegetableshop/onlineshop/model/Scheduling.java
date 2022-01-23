package ie.com.vegetableshop.onlineshop.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Scheduling {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDateTime dateAndTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(LocalDateTime dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

//	public Purchase getPurchase() {
//		return purchase;
//	}
//
//	public void setPurchase(Purchase purchase) {
//		this.purchase = purchase;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateAndTime == null) ? 0 : dateAndTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		result = prime * result + ((purchase == null) ? 0 : purchase.hashCode());
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
		Scheduling other = (Scheduling) obj;
		if (dateAndTime == null) {
			if (other.dateAndTime != null)
				return false;
		} else if (!dateAndTime.equals(other.dateAndTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
//		if (purchase == null) {
//			if (other.purchase != null)
//				return false;
//		} else if (!purchase.equals(other.purchase))
//			return false;
		return true;
	}

}
