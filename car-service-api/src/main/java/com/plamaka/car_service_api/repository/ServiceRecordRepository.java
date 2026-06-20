package com.plamaka.car_service_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.plamaka.car_service_api.entity.ServiceRecord;

@Repository
public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long>{
	
	List<ServiceRecord> findByCarId(Long carId);
	
	List<ServiceRecord> findByCarVinNumber(String vinNumber);
}
