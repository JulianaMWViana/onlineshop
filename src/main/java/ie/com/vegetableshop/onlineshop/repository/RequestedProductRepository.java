package ie.com.vegetableshop.onlineshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.com.vegetableshop.onlineshop.model.RequestedProduct;

public interface RequestedProductRepository extends JpaRepository<RequestedProduct, Integer> {//model class, type of Id

}
