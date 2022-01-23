package ie.com.vegetableshop.onlineshop.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import ie.com.vegetableshop.onlineshop.dto.EndUserDTO;
import ie.com.vegetableshop.onlineshop.form.UserForm;
import ie.com.vegetableshop.onlineshop.model.EndUser;
import ie.com.vegetableshop.onlineshop.repository.EndUserRepository;
import ie.com.vegetableshop.onlineshop.repository.RolesRepository;

@RestController
@RequestMapping("/enduser")
public class EndUserController {
	
	//Repository injection
	@Autowired
	private EndUserRepository endUserRepository;
	@Autowired
	private RolesRepository rolesRepository;

	@GetMapping
	public List<EndUserDTO> list(){
		List<EndUser> list = endUserRepository.findAll();
		
		return EndUserDTO.convert(list);
	}
	
	@GetMapping("/{id}")
	public EndUserDTO list(@PathVariable Integer id){
		Optional<EndUser> user = endUserRepository.findById(id);
		if(user.isPresent()) {
			return EndUserDTO.convert(user.get());
		}
		return null;
	}
	
	@PostMapping //@Valid to use the Bean Validation
	@Transactional// to commit automatically in the DB
	public ResponseEntity<?> save(@RequestBody @Valid UserForm userForm, UriComponentsBuilder uriBuilder) {//@RequestBody to get the information in the request body
		
		if (emailAlreadyRegistered(userForm.getEmail())) {
			return ResponseEntity.badRequest().body("This email address is already being used. Please, login.");
		}
		
		EndUser endUser = userForm.convert(rolesRepository);
		endUserRepository.save(endUser);
		
		//build the URI for the new user created in the DB
		URI uri = uriBuilder.path("/enduser/{id}").buildAndExpand(endUser.getId()).toUri();
		
		//return the new location of the user created and the representation of the new user
		//HTTP 201 Created
		return ResponseEntity.created(uri).body(new EndUserDTO(endUser));
	}

	//Check if there is this email saved in the Database
	private boolean emailAlreadyRegistered(String email) {
		return endUserRepository.findByEmail(email).isPresent();
	}

	public Optional<EndUser> findByName(String userName) {
		return endUserRepository.findByName(userName);
	}

	public Optional<EndUser> findEndUserById(String idUser) {
		return endUserRepository.findById(Integer.parseInt(idUser));
	}
}
