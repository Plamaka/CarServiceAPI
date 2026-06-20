package com.plamaka.car_service_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plamaka.car_service_api.dto.request.ServiceRecordRequestDTO;
import com.plamaka.car_service_api.dto.response.ServiceRecordResponseDTO;
import com.plamaka.car_service_api.service.ServiceRecordService;

import jakarta.validation.Valid;

@RestController
public class ServiceRecordController {
	
private final ServiceRecordService serviceRecordService;
	
	public ServiceRecordController(ServiceRecordService serviceRecordService) {
		this.serviceRecordService = serviceRecordService;
	}
	
	@GetMapping(path = "/service-record")
	public List<ServiceRecordResponseDTO> getAll(){
		return serviceRecordService.getAllServiceRecord();
	}
	
	@GetMapping(path = "/service-record/search-history")
	public List<ServiceRecordResponseDTO> getServiceRecordHistoryByVin(@RequestParam("by-vinnember") String vinNumber){
		return serviceRecordService.getServiceRecordByVinNumber(vinNumber);
	}
	
	@PostMapping(path = "/service-record")
	public ServiceRecordResponseDTO createServiceRecord(@Valid @RequestBody ServiceRecordRequestDTO dto){
		return serviceRecordService.createServiceRecord(dto);
	}
	
	@PutMapping(path = "/service-record/{id}")
	public ServiceRecordResponseDTO updateServiceRecord(@PathVariable Long id, @Valid @RequestBody ServiceRecordRequestDTO dto){
		return serviceRecordService.updateServiceRecord(id, dto);
	}
	
	@DeleteMapping(path = "/service-record/{id}")
	public void deleteServiceRecord(@PathVariable Long id){
		serviceRecordService.deleteServiceRecord(id);
	}
	
	
}
