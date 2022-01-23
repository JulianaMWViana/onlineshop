package ie.com.vegetableshop.onlineshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.com.vegetableshop.onlineshop.model.Roles;


public interface RolesRepository extends JpaRepository<Roles, Integer>{

	Roles findByRoleName(String name);

}
