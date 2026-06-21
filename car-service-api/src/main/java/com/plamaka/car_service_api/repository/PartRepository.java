package com.plamaka.car_service_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.plamaka.car_service_api.entity.Part;

@Repository
public interface PartRepository extends JpaRepository<Part,Long> {

	List<Part> findByPartNameContaining(String name);

    List<Part> findByQuantityInStockGreaterThanEqual(int quantityInStock);
    
    List<Part> findByQuantityInStock(int quantityInStock);
    
}
