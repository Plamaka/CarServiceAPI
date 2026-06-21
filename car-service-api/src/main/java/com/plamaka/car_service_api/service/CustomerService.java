package com.plamaka.car_service_api.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import com.plamaka.car_service_api.dto.request.CustomerRequestDTO;
import com.plamaka.car_service_api.dto.response.CarResponseDTO;
import com.plamaka.car_service_api.dto.response.CustomerResponseDTO;
import com.plamaka.car_service_api.dto.response.ServiceRecordPartResponseDTO;
import com.plamaka.car_service_api.dto.response.ServiceRecordResponseDTO;
import com.plamaka.car_service_api.entity.Car;
import com.plamaka.car_service_api.entity.Customer;
import com.plamaka.car_service_api.entity.Part;
import com.plamaka.car_service_api.entity.ServiceRecord;
import com.plamaka.car_service_api.entity.ServiceRecordPart;
import com.plamaka.car_service_api.repository.CarRepository;
import com.plamaka.car_service_api.repository.CustomerRepository;
import com.plamaka.car_service_api.repository.ServiceRecordPartRepository;
import com.plamaka.car_service_api.repository.ServiceRecordRepository;

@Service
public class CustomerService {
	
	private final CustomerRepository customerRepository;
	private final CarRepository carRepository;
	private final ServiceRecordRepository serviceRecordRepository;
	
	private final ServiceRecordPartRepository serviceRecordPartRepository;
	
	public CustomerService(CustomerRepository customerRepository, CarRepository carRepository,ServiceRecordRepository serviceRecordRepository, ServiceRecordPartRepository serviceRecordPartRepository) {
		this.customerRepository = customerRepository;
		this.carRepository = carRepository;
		this.serviceRecordRepository = serviceRecordRepository;
		this.serviceRecordPartRepository = serviceRecordPartRepository;
	}

	public List<CustomerResponseDTO> getAllCustomers(){
		
		List<Customer> customers = customerRepository.findAll();
		List<CustomerResponseDTO> customerDTOs = new ArrayList<>();
		
		for(var customer : customers ) {
			CustomerResponseDTO dto = new CustomerResponseDTO();
			
			dto.setId(customer.getId());
			dto.setFullName(customer.getFirstName() + " " + customer.getLastName());
			dto.setEmail(customer.getEmail());
			
			customerDTOs.add(dto);
		}
		
		return customerDTOs;
	}
	
	public CustomerResponseDTO getCustomerById(Long id) {
		
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
						ProblemDetail.forStatusAndDetail(
		                HttpStatus.NOT_FOUND,
		                "Customer not found with id: " + id
		        ),
		        null));
		
		CustomerResponseDTO dto = new CustomerResponseDTO();
		
		dto.setId(customer.getId());
		dto.setFullName(customer.getFirstName() + " " + customer.getLastName());
		dto.setEmail(customer.getEmail());
		
		return dto;
	}
	
	public CustomerResponseDTO createCustumer(CustomerRequestDTO dto) {
		
		Customer customer = new Customer(
				dto.getFirstName(),
				dto.getLastName(),
				dto.getEmail(),
				dto.getPhone());
		
		Customer saved = customerRepository.save(customer);
		
		CustomerResponseDTO responseDto = new CustomerResponseDTO();
		
		responseDto.setId(saved.getId());
		responseDto.setFullName(saved.getFirstName() + " " + saved.getLastName());
		responseDto.setEmail(saved.getEmail());
		
		return responseDto;		
	}
	
	public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO dto) {
			
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
						ProblemDetail.forStatusAndDetail(
		                HttpStatus.NOT_FOUND,
		                "Customer not found with id: " + id
		        ),
		        null));
		
		customer.setFirstName(dto.getFirstName());
		customer.setLastName(dto.getLastName());
		customer.setEmail(dto.getEmail());
		customer.setPhoneNumber(dto.getPhone());
		
		Customer saved = customerRepository.save(customer);
		
		CustomerResponseDTO responseDto = new CustomerResponseDTO();
		responseDto.setId(saved.getId());
		responseDto.setFullName(saved.getFirstName() + " " + saved.getLastName());
		responseDto.setEmail(saved.getEmail());				

		return responseDto;
		
	}
	
	public void deleteCustomer(Long id) {
		
		 List<Car> cars = carRepository.findByCustomerId(id);

	    if (!cars.isEmpty()) {
	    	throw new ErrorResponseException(HttpStatus.NOT_ACCEPTABLE,
					ProblemDetail.forStatusAndDetail(
			                HttpStatus.NOT_ACCEPTABLE,
			                "Customer has assigned cars."
			        ),
			        null);
	    }
	
	    Customer customer = customerRepository.findById(id)
	    		.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
						ProblemDetail.forStatusAndDetail(
		                HttpStatus.NOT_FOUND,
		                "Customer not found with id: " + id
		        ),
		        null));
	    
		customerRepository.delete(customer);
	}
	
	public List<CarResponseDTO> getCustomerCars(Long id){
		List<Car> cars = carRepository.findByCustomerId(id);
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
						ProblemDetail.forStatusAndDetail(
		                HttpStatus.NOT_FOUND,
		                "Customer not found with id: " + id
		        ),
		        null));
		
		List<CarResponseDTO> responseDtos = new ArrayList<>();

	    for (var car : cars) {

	        CarResponseDTO dto = new CarResponseDTO();

	        dto.setId(car.getId());
	        dto.setBrand(car.getBrand());
	        dto.setModel(car.getModel());
	        dto.setVinNumber(car.getVinNumber());
	        dto.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
	        responseDtos.add(dto);
	    }

	    return responseDtos;
	}
	
	public List<ServiceRecordResponseDTO> getCustomerServiceHistory(Long id){
		List<Car> cars = carRepository.findByCustomerId(id);
		List<ServiceRecord> serviceHistory = new ArrayList<ServiceRecord>();
		List<ServiceRecordResponseDTO> responseDtos = new ArrayList<ServiceRecordResponseDTO>();
		
		for(var car : cars) {
			List<ServiceRecord> serviceRecords = serviceRecordRepository.findByCarId(car.getId());
			
			serviceHistory.addAll(serviceRecords);
			
			for(var service : serviceHistory) {
				ServiceRecordResponseDTO dto = new ServiceRecordResponseDTO();
				
				dto.setId(service.getId());
				dto.setBrand(car.getBrand());
				dto.setModel(car.getModel());
				dto.setVinNumber(car.getVinNumber());
				dto.setServiceDate(service.getServiceDate());
				dto.setDescription(service.getDescription());
				dto.setParts(partsMapper(service.getId()));
				dto.setLaborCost(service.getLaborCost());
				dto.setPartsCost(totalPartCost(service.getId()));
				dto.setTotalPrice(dto.getLaborCost() + dto.getPartsCost());
				
				responseDtos.add(dto);
			}
			
			serviceHistory.clear();
		}
		
		return responseDtos;
	}
	
	public double totalPartCost(Long id) {
		double total = 0;
		
		List<ServiceRecordPart> serviceRecorParts = serviceRecordPartRepository.findByServiceRecordId(id);
		
		for(var srp : serviceRecorParts) {
			Part p = srp.getPart();
			total += (p.getPrice() * srp.getQuantity());
		}
		
		return total;
	}
	
	public List<ServiceRecordPartResponseDTO> partsMapper(Long id){
		
		
		List<ServiceRecordPart> serviceRecorParts = serviceRecordPartRepository.findByServiceRecordId(id);
		List<ServiceRecordPartResponseDTO> serviceRecordPart = new ArrayList<ServiceRecordPartResponseDTO>();
		
		for(var srp : serviceRecorParts) {
			Part part = srp.getPart();
			ServiceRecordPartResponseDTO dto = new ServiceRecordPartResponseDTO();
			
			dto.setPartName(part.getPartName());
			dto.setPrice(part.getPrice());
			dto.setQuantity(srp.getQuantity());
			
			serviceRecordPart.add(dto);
		}
		
		return serviceRecordPart;
	}
}
