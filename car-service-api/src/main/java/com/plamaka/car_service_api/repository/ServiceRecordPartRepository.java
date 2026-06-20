package com.plamaka.car_service_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.plamaka.car_service_api.entity.ServiceRecordPart;

@Repository
public interface ServiceRecordPartRepository extends JpaRepository<ServiceRecordPart, Long>{
	
	List<ServiceRecordPart> findByServiceRecordId(Long serviceRecordId);
}
