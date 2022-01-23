package ie.com.vegetableshop.onlineshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ie.com.vegetableshop.onlineshop.model.EndUser;

public interface EndUserRepository extends JpaRepository<EndUser, Integer> {//model class, type of Id

	Optional<EndUser> findByName(String userName);

	//FETCH to force loading of Roles objects because of ManyToMany relationship
	@Query(value = "SELECT u FROM EndUser u LEFT JOIN FETCH u.roles WHERE u.email = :email")
	Optional<EndUser> findByEmail(@Param("email") String email);

	//FETCH to force loading of Roles objects because of ManyToMany relationship
	@Query(value = "SELECT u FROM EndUser u LEFT JOIN FETCH u.roles WHERE u.id = :id")
	Optional<EndUser> findById(@Param("id") Integer id);
}
