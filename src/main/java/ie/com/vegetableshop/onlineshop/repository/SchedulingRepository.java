package ie.com.vegetableshop.onlineshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.com.vegetableshop.onlineshop.model.Scheduling;

public interface SchedulingRepository extends JpaRepository<Scheduling, Integer> {//model class, type of Id

}
