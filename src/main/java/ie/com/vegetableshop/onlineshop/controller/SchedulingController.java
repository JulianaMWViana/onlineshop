package ie.com.vegetableshop.onlineshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.com.vegetableshop.onlineshop.dto.SchedulingDTO;
import ie.com.vegetableshop.onlineshop.model.Scheduling;
import ie.com.vegetableshop.onlineshop.repository.SchedulingRepository;

@RestController
@RequestMapping("/scheduling")
public class SchedulingController {

	// Repository injection
	@Autowired
	private SchedulingRepository schedulingRepository;

	@GetMapping
	public List<SchedulingDTO> list(){
		List<Scheduling> list = schedulingRepository.findAll();
		return SchedulingDTO.convert(list);
	}

}
