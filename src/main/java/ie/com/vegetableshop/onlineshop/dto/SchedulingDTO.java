package ie.com.vegetableshop.onlineshop.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import ie.com.vegetableshop.onlineshop.model.Scheduling;

public class SchedulingDTO {

	private Integer id;
	private LocalDateTime dateAndTime;

	public SchedulingDTO(Scheduling s) {
		this.id = s.getId();
		this.dateAndTime = s.getDateAndTime();
	}

	public Integer getId() {
		return id;
	}

	public LocalDateTime getDateAndTime() {
		return dateAndTime;
	}

	public static List<SchedulingDTO> convert(List<Scheduling> list) {
		return list.stream().map(SchedulingDTO::new).collect(Collectors.toList());
	}

}
