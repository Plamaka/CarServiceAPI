package com.plamaka.car_service_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import com.plamaka.car_service_api.dto.request.CarRequestDTO;
import com.plamaka.car_service_api.dto.response.CarResponseDTO;
import com.plamaka.car_service_api.entity.Car;
import com.plamaka.car_service_api.entity.Customer;
import com.plamaka.car_service_api.entity.ServiceRecord;
import com.plamaka.car_service_api.repository.CarRepository;
import com.plamaka.car_service_api.repository.CustomerRepository;
import com.plamaka.car_service_api.repository.PartRepository;
import com.plamaka.car_service_api.repository.ServiceRecordRepository;

@Service
public class CarService {
	
	private final CustomerRepository customerRepository;
	private final CarRepository carRepository;
	private final ServiceRecordRepository serviceRecordRepository;
	
	public CarService(CustomerRepository customerRepository, CarRepository carRepository,ServiceRecordRepository serviceRecordRepository, PartRepository partRepository) {
		this.customerRepository = customerRepository;
		this.carRepository = carRepository;
		this.serviceRecordRepository = serviceRecordRepository;
	}
	
	public List<CarResponseDTO> getAllCars(){
		
		List<Car> cars = carRepository.findAll();
		List<CarResponseDTO> carDTOs = new ArrayList<>();
		
		for(var car : cars ) {
			CarResponseDTO dto = new CarResponseDTO();
			Customer customer = car.getCustomer();
			
			dto.setId(car.getId());
			dto.setBrand(car.getBrand());
			dto.setModel(car.getModel());
			dto.setVinNumber(car.getVinNumber());
			dto.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
			
			
			carDTOs.add(dto);
		}
		
		return carDTOs;
	}
	
	public CarResponseDTO getCarByVin(String vinNumber) {
		Car car = carRepository.findByVinNumber(vinNumber)
				.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
						ProblemDetail.forStatusAndDetail(
		                HttpStatus.NOT_FOUND,
		                "Not found vinNumber:" + vinNumber
		        ),
		        null));
		
		Customer customer = car.getCustomer();
		
		CarResponseDTO dto = new CarResponseDTO();
		
		dto.setId(car.getId());
		dto.setBrand(car.getBrand());
		dto.setModel(car.getModel());
		dto.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
		dto.setVinNumber(car.getVinNumber());
		
		return dto;
	}
	
	public CarResponseDTO createCar(CarRequestDTO dto) {
		
		Customer customer = customerRepository.findById(dto.getCustomerId())
				.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
						ProblemDetail.forStatusAndDetail(
		                HttpStatus.NOT_FOUND,
		                "Customer not found with id: " + dto.getCustomerId()
		        ),
		        null));
		
		Car car = new Car(
				dto.getBrand(),
				dto.getModel(),
				dto.getYear(),
				dto.getVinNumber());
		
		 car.setCustomer(customer);
		 
		 Car savedCar = carRepository.save(car);
		
		CarResponseDTO responseDto = new CarResponseDTO();
		
		responseDto.setId(savedCar.getId());
		responseDto.setBrand(savedCar.getBrand());
		responseDto.setModel(savedCar.getModel());
		responseDto.setVinNumber(savedCar.getVinNumber());
		responseDto.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
		
		return responseDto;		
	}
	
	public CarResponseDTO updateCar(Long id, CarRequestDTO dto) {
			
		Customer customer = customerRepository.findById(dto.getCustomerId())
				.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
						ProblemDetail.forStatusAndDetail(
		                HttpStatus.NOT_FOUND,
		                "Customer not found with id: " + dto.getCustomerId()
		        ),
		        null));
		
		Car car = carRepository.findById(id)
				.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
						ProblemDetail.forStatusAndDetail(
		                HttpStatus.NOT_FOUND,
		                "Car not found with id: " + id
		        ),
		        null));
		
		car.setBrand(dto.getBrand());
		car.setModel(dto.getModel());
		car.setYear(dto.getYear());
		car.setVinNumber(dto.getVinNumber());
		car.setCustomer(customer);
		
		Car saved = carRepository.save(car);
		
		CarResponseDTO responseDto = new CarResponseDTO();
		responseDto.setId(saved.getId());
		responseDto.setBrand(saved.getBrand());
		responseDto.setModel(saved.getModel());
		responseDto.setVinNumber(saved.getVinNumber());
		responseDto.setCustomerName(customer.getFirstName() + " " + customer.getLastName());		

		return responseDto;
		
	}
	
	public CarResponseDTO assignNewCustomer(Long id, Long customerId) {
		Car car = carRepository.findById(id)
				.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
						ProblemDetail.forStatusAndDetail(
		                HttpStatus.NOT_FOUND,
		                "Car not found with id: " + id
		        ),
		        null));
		
		Customer oldCustomer = car.getCustomer();
		
		if (oldCustomer != null) {
		    oldCustomer.getCars().remove(car);
		}		
		
		
		Customer newCustomer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
						ProblemDetail.forStatusAndDetail(
		                HttpStatus.NOT_FOUND,
		                "Customer not found with id: " + customerId
		        ),
		        null));
		
		newCustomer.getCars().add(car);
		
		car.setCustomer(newCustomer);
		
		Car savedCar = carRepository.save(car);
		
		CarResponseDTO dto = new CarResponseDTO();
		
		dto.setBrand(savedCar.getBrand());
		dto.setModel(savedCar.getModel());
		dto.setVinNumber(savedCar.getVinNumber());
		dto.setCustomerName(newCustomer.getFirstName() + " " + newCustomer.getLastName());		

		return dto;
	}
	
	public void deleteCar(Long id) {
		
		List<ServiceRecord> records =
		        serviceRecordRepository.findByCarId(id);

		if (!records.isEmpty()) {
			throw new ErrorResponseException(HttpStatus.NOT_ACCEPTABLE,
					ProblemDetail.forStatusAndDetail(
			                HttpStatus.NOT_ACCEPTABLE,
			                "Car has service history"
			        ),
			        null);
		}
		
		Car car = carRepository.findById(id)
				.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
						ProblemDetail.forStatusAndDetail(
		                HttpStatus.NOT_FOUND,
		                "Car not found with id: " + id
		        ),
		        null));
		
		carRepository.delete(car);
	}
}
