package com.plamaka.car_service_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.plamaka.car_service_api.dto.request.CustomerRequestDTO;
import com.plamaka.car_service_api.dto.response.CarResponseDTO;
import com.plamaka.car_service_api.dto.response.CustomerResponseDTO;
import com.plamaka.car_service_api.dto.response.ServiceRecordResponseDTO;
import com.plamaka.car_service_api.service.CustomerService;

import jakarta.validation.Valid;

@RestController
public class CustomerController {
	
	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping(path = "/customers")
	public List<CustomerResponseDTO> getAll(){
		return customerService.getAllCustomers();
	}
	

	@GetMapping(path = "/customers/{id}")
	public CustomerResponseDTO getCustomer(@PathVariable Long id){
		return customerService.getCustomerById(id);
	}
	
	@GetMapping(path = "/customers/{id}/cars")
	public List<CarResponseDTO> getCustomerAllCars(@PathVariable Long id){
		return customerService.getCustomerCars(id);
	}
	
	@GetMapping(path = "/customers/{id}/service-record")
	public  List<ServiceRecordResponseDTO> getServiceHistory(@PathVariable Long id){
		return customerService.getCustomerServiceHistory(id);
	}
	
	@PostMapping(path = "/customers")
	public CustomerResponseDTO addCustomer(@Valid @RequestBody CustomerRequestDTO dto){
		return customerService.createCustumer(dto);
	}
	
	@PutMapping(path = "/customers/{id}")
	public CustomerResponseDTO putCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequestDTO dto){
		return customerService.updateCustomer(id, dto);
	}
	
	@DeleteMapping(path = "/customers/{id}")
	public void removeCustomer(@PathVariable Long id){
		 customerService.deleteCustomer(id);
	}
	
	
	
}
