package ie.com.vegetableshop.onlineshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ie.com.vegetableshop.onlineshop.model.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {//model class, type of Id

	//Spring Data naming automatically generating SQL
	List<Purchase> findByEndUserName(String userName);

	//same query result above, but declared by hand
	@Query("SELECT p FROM Purchase p WHERE p.endUser.name = :userName")
	List<Purchase> getPurchaseByUserName(@Param("userName") String userName);
	
	List<Purchase> findByEndUserId(Integer id);
}
