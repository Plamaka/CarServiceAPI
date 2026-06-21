package com.plamaka.car_service_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import com.plamaka.car_service_api.dto.request.ServiceRecordPartRequestDTO;
import com.plamaka.car_service_api.dto.request.ServiceRecordRequestDTO;
import com.plamaka.car_service_api.dto.response.ServiceRecordPartResponseDTO;
import com.plamaka.car_service_api.dto.response.ServiceRecordResponseDTO;
import com.plamaka.car_service_api.entity.Car;
import com.plamaka.car_service_api.entity.Part;
import com.plamaka.car_service_api.entity.ServiceRecord;
import com.plamaka.car_service_api.entity.ServiceRecordPart;
import com.plamaka.car_service_api.repository.CarRepository;
import com.plamaka.car_service_api.repository.PartRepository;
import com.plamaka.car_service_api.repository.ServiceRecordPartRepository;
import com.plamaka.car_service_api.repository.ServiceRecordRepository;

import jakarta.transaction.Transactional;

@Service
public class ServiceRecordService {
	
	private final CarRepository carRepository;
	private final ServiceRecordRepository serviceRecordRepository;
	private final PartRepository partRepository;
	private final ServiceRecordPartRepository serviceRecordPartRepository;
	
	public ServiceRecordService(CarRepository carRepository,
			ServiceRecordRepository serviceRecordRepository,
			PartRepository partRepository, 
			ServiceRecordPartRepository serviceRecordPartRepository) {
		
		this.carRepository = carRepository;
		this.serviceRecordRepository = serviceRecordRepository;
		this.partRepository = partRepository;
		this.serviceRecordPartRepository = serviceRecordPartRepository;
	}
	
	public List<ServiceRecordResponseDTO> getAllServiceRecord() {
		
		List<ServiceRecord> serviceRecords = serviceRecordRepository.findAll();
		List<ServiceRecordResponseDTO> dtos = new ArrayList<>();
		
		for(var serviceRecord : serviceRecords) {
			Car car = serviceRecord.getCar();
			
			ServiceRecordResponseDTO dto = new ServiceRecordResponseDTO();
			
			dto.setId(serviceRecord.getId());
			dto.setBrand(car.getBrand());
			dto.setModel(car.getModel());
			dto.setVinNumber(car.getVinNumber());
			dto.setServiceDate(serviceRecord.getServiceDate());
			dto.setDescription(serviceRecord.getDescription());
			dto.setParts(partsMapper(serviceRecord.getId()));
			dto.setPartsCost(totalPartCost(serviceRecord.getId()));
			dto.setLaborCost(serviceRecord.getLaborCost());
			dto.setTotalPrice(dto.getPartsCost() + dto.getLaborCost());
			
			dtos.add(dto);
		}	
		
		return dtos;
	}
	
	public List<ServiceRecordResponseDTO> getServiceRecordByVinNumber(String vinNumber) {
		
		List<ServiceRecord> serviceRecords = serviceRecordRepository.findByCarVinNumber(vinNumber);
		
		List<ServiceRecordResponseDTO> dtos = new ArrayList<>();
		
		for(var serviceRecord : serviceRecords) {
			Car car = serviceRecord.getCar();
			
			ServiceRecordResponseDTO dto = new ServiceRecordResponseDTO();
			
			dto.setId(serviceRecord.getId());
			dto.setBrand(car.getBrand());
			dto.setModel(car.getModel());
			dto.setVinNumber(car.getVinNumber());
			dto.setServiceDate(serviceRecord.getServiceDate());
			dto.setDescription(serviceRecord.getDescription());
			dto.setParts(partsMapper(serviceRecord.getId()));
			dto.setPartsCost(totalPartCost(serviceRecord.getId()));
			dto.setLaborCost(serviceRecord.getLaborCost());
			dto.setTotalPrice(dto.getPartsCost() + dto.getLaborCost());
			
			dtos.add(dto);
		}	
		
		return dtos;
	}
	
	@Transactional
	public ServiceRecordResponseDTO createServiceRecord(ServiceRecordRequestDTO dto) {
		Car car = carRepository.findById(dto.getCarId())
				.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
						ProblemDetail.forStatusAndDetail(
		                HttpStatus.NOT_FOUND,
		                "Car not found with id: " + dto.getCarId()
		        ),
		        null));
		
		ServiceRecord serviceRecord = new ServiceRecord(
				dto.getServiceDate(),
				dto.getDescription(),
				dto.getLaborCost());
		
		serviceRecord.setCar(car);
		
		List<ServiceRecordPart> serviceRecordParts = new ArrayList<>();
		
		for(var parts : dto.getParts()) {
			ServiceRecordPart serviceRecordPart = new ServiceRecordPart();
			Part part = partRepository.findById(parts.getPartId())
					.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
							ProblemDetail.forStatusAndDetail(
			                HttpStatus.NOT_FOUND,
			                "Part not found with id: " + parts.getPartId()
			        ),
			        null));
			
			
			
			serviceRecordPart.setPart(part);
			serviceRecordPart.setServiceRecord(serviceRecord);
			
			if(parts.getQuantity() > part.getQuantityInStock()) {
				throw new ErrorResponseException(HttpStatus.NOT_ACCEPTABLE,
						ProblemDetail.forStatusAndDetail(
				                HttpStatus.NOT_ACCEPTABLE,
				                "Not enough parts: " + part.getPartName() + ". In stock: " + part.getQuantityInStock()
				        ),
				        null);
			}
			
			
			serviceRecordPart.setQuantity(parts.getQuantity());
			
			serviceRecordParts.add(serviceRecordPart);
		}
			
		reduceStock(dto.getParts());
		
		serviceRecord.setServiceRecordParts(serviceRecordParts);
		
		ServiceRecord savedServiceRecord =
		        serviceRecordRepository.save(serviceRecord);

		for(ServiceRecordPart srp : serviceRecordParts) {

		    srp.setServiceRecord(savedServiceRecord);

		    serviceRecordPartRepository.save(srp);
		}
		
		ServiceRecordResponseDTO responseDto = new ServiceRecordResponseDTO();
		
		responseDto.setId(savedServiceRecord.getId());
		responseDto.setBrand(car.getBrand());
		responseDto.setModel(car.getModel());
		responseDto.setVinNumber(car.getVinNumber());
		responseDto.setServiceDate(savedServiceRecord.getServiceDate());
		responseDto.setDescription(savedServiceRecord.getDescription());
		responseDto.setParts(partsMapper(savedServiceRecord.getId()));
		responseDto.setPartsCost(totalPartCost(savedServiceRecord.getId()));
		responseDto.setLaborCost(savedServiceRecord.getLaborCost());
		responseDto.setTotalPrice(responseDto.getPartsCost() + responseDto.getLaborCost());
		
		return responseDto;
	}
	
	@Transactional
	public ServiceRecordResponseDTO updateServiceRecord(Long id, ServiceRecordRequestDTO dto) {

	    ServiceRecord serviceRecord = serviceRecordRepository.findById(id)
	    		.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
						ProblemDetail.forStatusAndDetail(
		                HttpStatus.NOT_FOUND,
		                "ServiceRecord not found with id: " + id
		        ),
		        null));

	    Car car = carRepository.findById(dto.getCarId())
	    		.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
						ProblemDetail.forStatusAndDetail(
		                HttpStatus.NOT_FOUND,
		                "Car not found with id: " + dto.getCarId()
		        ),
		        null));

	    serviceRecord.setServiceDate(dto.getServiceDate());
	    serviceRecord.setDescription(dto.getDescription());
	    serviceRecord.setLaborCost(dto.getLaborCost());
	    serviceRecord.setCar(car);

	    for (ServiceRecordPart oldPart : serviceRecord.getServiceRecordParts()) {
	        Part part = oldPart.getPart();

	        part.setQuantityInStock(part.getQuantityInStock() + oldPart.getQuantity());
	        partRepository.save(part);
	    }

	    serviceRecord.getServiceRecordParts().clear();

	    List<ServiceRecordPart> newParts = new ArrayList<>();

	    for (var p : dto.getParts()) {

	        Part part = partRepository.findById(p.getPartId())
	        		.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
							ProblemDetail.forStatusAndDetail(
			                HttpStatus.NOT_FOUND,
			                "Part not found with id: " + p.getPartId()
			        ),
			        null));

	        if (part.getQuantityInStock() < p.getQuantity()) {
	            throw new ErrorResponseException(HttpStatus.NOT_ACCEPTABLE,
						ProblemDetail.forStatusAndDetail(
				                HttpStatus.NOT_ACCEPTABLE,
				                "Not enough parts: " + part.getPartName() + ". In stock: " + part.getQuantityInStock()
				        ),
				        null);
	        }

	        ServiceRecordPart serviceRecordPart = new ServiceRecordPart();
	        serviceRecordPart.setServiceRecord(serviceRecord);
	        serviceRecordPart.setPart(part);
	        serviceRecordPart.setQuantity(p.getQuantity());

	        newParts.add(serviceRecordPart);
	    }
	    
	    reduceStock(dto.getParts());
	    
	    serviceRecord.setServiceRecordParts(newParts);

	    ServiceRecord saved = serviceRecordRepository.save(serviceRecord);

	    ServiceRecordResponseDTO responseDto = new ServiceRecordResponseDTO();

	    responseDto.setId(saved.getId());
	    responseDto.setBrand(car.getBrand());
	    responseDto.setModel(car.getModel());
	    responseDto.setVinNumber(car.getVinNumber());
	    responseDto.setServiceDate(saved.getServiceDate());
	    responseDto.setDescription(saved.getDescription());
	    responseDto.setParts(partsMapper(saved.getId()));
	    responseDto.setPartsCost(totalPartCost(saved.getId()));
	    responseDto.setLaborCost(saved.getLaborCost());
	    responseDto.setTotalPrice(responseDto.getPartsCost() + responseDto.getLaborCost());

	    return responseDto;
	}
	
	@Transactional
	public void deleteServiceRecord(Long id) {

	    ServiceRecord serviceRecord = serviceRecordRepository.findById(id)
	    		.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
						ProblemDetail.forStatusAndDetail(
		                HttpStatus.NOT_FOUND,
		                "ServiceRecord not found with id: " + id
		        ),
		        null));

	    for (ServiceRecordPart ServiceRecordPart : serviceRecord.getServiceRecordParts()) {

	        Part part = ServiceRecordPart.getPart();

	        part.setQuantityInStock(
	                part.getQuantityInStock() + ServiceRecordPart.getQuantity()
	        );

	        partRepository.save(part);
	    }

	    serviceRecordPartRepository.deleteAll(serviceRecord.getServiceRecordParts());
	    serviceRecordRepository.delete(serviceRecord);
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
	
	public void reduceStock(List<ServiceRecordPartRequestDTO> dto) {
		for(var parts : dto) {
			Part part = partRepository.findById(parts.getPartId())
					.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
							ProblemDetail.forStatusAndDetail(
			                HttpStatus.NOT_FOUND,
			                "Part not found with id: " + parts.getPartId()
			        ),
			        null));

		
			part.setQuantityInStock(part.getQuantityInStock() - parts.getQuantity());

			partRepository.save(part);
		
		}
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
