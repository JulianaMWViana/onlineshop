package ie.com.vegetableshop.onlineshop.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import ie.com.vegetableshop.onlineshop.dto.ProductDTO;
import ie.com.vegetableshop.onlineshop.form.ProductForm;
import ie.com.vegetableshop.onlineshop.model.Product;
import ie.com.vegetableshop.onlineshop.repository.ProductRepository;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	//Repository injection
	@Autowired
	private ProductRepository productRepository;

	@GetMapping
	public List<ProductDTO> list(){
		List<Product> list = productRepository.findAll();
		
		return ProductDTO.convert(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> list(@PathVariable Integer id){
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()) {
			return ResponseEntity.ok(new ProductDTO(product.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PreAuthorize("hasAuthority('admin')")//only Admin can request
	@PostMapping //@Valid to use the Bean Validation
	@Transactional// to commit automatically in the DB
	public ResponseEntity<ProductDTO> save(@RequestBody @Valid ProductForm productForm, UriComponentsBuilder uriBuilder) {//@RequestBody to get the information in the request body
		Product product = productForm.convert();
		productRepository.save(product);
		
		//build the URI for the new user created in the DB
		URI uri = uriBuilder.path("/product/{id}").buildAndExpand(product.getId()).toUri();
		
		//return the new location of the user created and the representation of the new user
		//HTTP 201 Created
		return ResponseEntity.created(uri).body(new ProductDTO(product));
	}
	
	@PreAuthorize("hasAuthority('admin')")//only Admin can request
	@PutMapping("/{id}")
	@Transactional// to commit automatically in the DB
	public ResponseEntity<ProductDTO> update(@PathVariable Integer id, @RequestBody @Valid ProductForm prodForm){
		Product product = prodForm.update(id, productRepository);
		if(product != null) {
			return ResponseEntity.ok(new ProductDTO(product));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PreAuthorize("hasAuthority('admin')")//only Admin can request
	@DeleteMapping("/{id}")
	@Transactional// to commit automatically in the DB
	public ResponseEntity<?> delete(@PathVariable Integer id){
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()) {
			productRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
