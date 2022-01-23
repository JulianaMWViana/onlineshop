package ie.com.vegetableshop.onlineshop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import ie.com.vegetableshop.onlineshop.dto.PurchaseDTO;
import ie.com.vegetableshop.onlineshop.form.PurchaseForm;
import ie.com.vegetableshop.onlineshop.form.PurchaseUpdateForm;
import ie.com.vegetableshop.onlineshop.model.EndUser;
import ie.com.vegetableshop.onlineshop.model.Product;
import ie.com.vegetableshop.onlineshop.model.Purchase;
import ie.com.vegetableshop.onlineshop.model.RequestedProduct;
import ie.com.vegetableshop.onlineshop.repository.EndUserRepository;
import ie.com.vegetableshop.onlineshop.repository.ProductRepository;
import ie.com.vegetableshop.onlineshop.repository.PurchaseRepository;
import ie.com.vegetableshop.onlineshop.repository.RequestedProductRepository;
import ie.com.vegetableshop.onlineshop.security.TokenService;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

	// Repository injection
	@Autowired
	private PurchaseRepository purchaseRepository;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private EndUserRepository endUserRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private RequestedProductRepository  requestedProductRepository;

	@PreAuthorize("hasAuthority('admin')")//only Admin can request
	@GetMapping
	public List<PurchaseDTO> list() {
		List<Purchase> list = purchaseRepository.findAll();
		return PurchaseDTO.convert(list);
	}
	
	@PostMapping //@Valid to use the Bean Validation
	@Transactional// to commit automatically in the DB
	public ResponseEntity<?> save(@RequestBody @Valid PurchaseForm userForm, 
			UriComponentsBuilder uriBuilder, HttpServletRequest request) {//@RequestBody to get the information in the request body
		
		//find the user by token
		String recoveredToken = recoverToken(request);
		Integer idUser = tokenService.getIdUser(recoveredToken);
		Double totalPrice = 0.0;
		
		//find the products and the total price
		List<RequestedProduct> requestedProducts = new ArrayList<>();
		
		for (Entry<String, Integer> map : userForm.getProductIdAndQuantity().entrySet()) {
			Product prod = productRepository.findById(Integer.parseInt(map.getKey())).get();
			
			if(prod.getQuantity() < map.getValue()) {// when product quantity is not enough
				return ResponseEntity.badRequest().body(
						"The current quantity of the " + prod.getName() + " is " + prod.getQuantity() + ". Please, redo your order.");
			} else {
				prod.setQuantity(prod.getQuantity() - map.getValue());
			}
			
			totalPrice += prod.getPrice() * map.getValue();
			RequestedProduct r = new RequestedProduct();
			r.setProduct(prod);
			r.setQuantity(map.getValue());
			requestedProducts.add(r);
        }
		
		EndUser u = endUserRepository.getById(idUser);
		Purchase purchase = userForm.convert(u, totalPrice);
		//purchase is saved in DB
		purchaseRepository.saveAndFlush(purchase);
		
		//requested products saved in DB
		for (RequestedProduct r : requestedProducts) {
			r.setPurchase(purchase);
			requestedProductRepository.saveAndFlush(r);
		}
		
		purchase.setRequestedProduct(requestedProducts);
		
		//build the URI for the new user created in the DB
		URI uri = uriBuilder.path("/enduser/{id}").buildAndExpand(purchase.getId()).toUri();
		
		//return the new location of the user created and the representation of the new user
		//HTTP 201 Created
		return ResponseEntity.created(uri).body(new PurchaseDTO(purchase));
	}
	
	@GetMapping("/{id}")
	public PurchaseDTO detail(@PathVariable Integer id) {//@PathVariable get variable after /
		Purchase purchase = purchaseRepository.getById(id);
		return new PurchaseDTO(purchase);
	}
	
	@GetMapping("/orders")
	public List<PurchaseDTO> getCustomerOrders(HttpServletRequest request){
		String recoveredToken = recoverToken(request);
		Integer idUser = tokenService.getIdUser(recoveredToken);
		List<Purchase> list = purchaseRepository.findByEndUserId(idUser);
		return PurchaseDTO.convert(list);
	}
	
	private static String recoverToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());// to remove Bearer
	}
	
	@PreAuthorize("hasAuthority('admin')")//only Admin can request
	@PutMapping("/{id}")
	@Transactional// to commit automatically in the DB
	public ResponseEntity<PurchaseDTO> updateStatus(@PathVariable Integer id, @RequestBody @Valid PurchaseUpdateForm purchase){
		Purchase p = purchase.update(id, purchaseRepository);
		if(p != null) {
			return ResponseEntity.ok(new PurchaseDTO(p));
		}
		return ResponseEntity.notFound().build();
	}
}
