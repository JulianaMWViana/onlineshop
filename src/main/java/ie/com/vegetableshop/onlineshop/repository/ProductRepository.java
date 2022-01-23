package ie.com.vegetableshop.onlineshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.com.vegetableshop.onlineshop.model.Product;
													//model class, type of Id
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
