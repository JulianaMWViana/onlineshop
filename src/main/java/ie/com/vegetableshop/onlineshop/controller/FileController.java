package ie.com.vegetableshop.onlineshop.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import ie.com.vegetableshop.onlineshop.config.amazon.AWSS3Service;
import ie.com.vegetableshop.onlineshop.model.Files;
import ie.com.vegetableshop.onlineshop.model.Product;
import ie.com.vegetableshop.onlineshop.repository.FilesRepository;
import ie.com.vegetableshop.onlineshop.repository.ProductRepository;

@RestController
@RequestMapping("/image")
public class FileController {

	// Repository injection
	@Autowired
	private FilesRepository fileRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private AWSS3Service awsS3Service;

	@PreAuthorize("hasAuthority('admin')") // only Admin can request
	@PostMapping("/{productId}")
	public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("image") MultipartFile file,
			@PathVariable String productId, UriComponentsBuilder uriBuilder) {

		// Upload on Amazon Cloud
		String publicURL = awsS3Service.uploadFile(file);

		// update the product image URL
		Product prod = productRepository.getById(Integer.parseInt(productId));

		// Save in the DataBase
		Files f = new Files();
		f.setFileName(file.getOriginalFilename());
		f.setFileType(file.getContentType());
		f.setUrl(publicURL);
		fileRepository.saveAndFlush(f);
		prod.setUrl(publicURL);
		productRepository.saveAndFlush(prod);

		// Build the JSON response
		Map<String, String> response = new HashMap<>();
		response.put("publicURL", publicURL);
		
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('admin')") // only Admin can request
	@PostMapping
	public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("image") MultipartFile file) {
		String publicURL = awsS3Service.uploadFile(file);
		Map<String, String> response = new HashMap<>();
		response.put("publicURL", publicURL);
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
	}

}
