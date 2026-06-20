package com.plamaka.car_service_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.plamaka.car_service_api.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car,Long>{
	
//	@Query("SELECT * FROM Car c WHERE c.customer_id = :customerId")
	List<Car> findByCustomerId(Long customerId);
	
	Optional<Car> findByVinNumber(String vinNumber);

    List<Car> findByBrand(String brand);
}
