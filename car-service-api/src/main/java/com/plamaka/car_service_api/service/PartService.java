package com.plamaka.car_service_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import com.plamaka.car_service_api.dto.request.PartRequestDTO;
import com.plamaka.car_service_api.dto.response.PartResponseDTO;
import com.plamaka.car_service_api.entity.Part;
import com.plamaka.car_service_api.repository.PartRepository;

@Service
public class PartService {
	
	private final PartRepository partRepository;
	
	public PartService(PartRepository partRepository) {
		this.partRepository = partRepository;
	}
	
	public List<PartResponseDTO> getAllParts(){
		
		List<Part> parts = partRepository.findAll();
		List<PartResponseDTO> partDTOs = new ArrayList<>();
		
		for(var part : parts ) {
			PartResponseDTO dto = new PartResponseDTO();
			
			dto.setId(part.getId());
			dto.setPartName(part.getPartName());
			dto.setPrice(part.getPrice());
			
			
			partDTOs.add(dto);
		}
		
		return partDTOs;
	}
	
	public List<PartResponseDTO> findByPartNameContaining(String partName){

		List<Part> parts = partRepository.findByPartNameContaining(partName);
		
		List<PartResponseDTO> partDTOs = new ArrayList<>();
		
		for(var part : parts ) {
			PartResponseDTO dto = new PartResponseDTO();
			
			dto.setId(part.getId());
			dto.setPartName(part.getPartName());
			dto.setPrice(part.getPrice());
			
			
			partDTOs.add(dto);
		}
		
		return partDTOs;
	}
	
	public List<PartResponseDTO> getAvailableParts(int quantity){

		List<Part> parts = partRepository.findByQuantityGreaterThanEqual(quantity);
		
		List<PartResponseDTO> partDTOs = new ArrayList<>();
		
		for(var part : parts ) {
			PartResponseDTO dto = new PartResponseDTO();
			
			dto.setId(part.getId());
			dto.setPartName(part.getPartName());
			dto.setPrice(part.getPrice());
			
			
			partDTOs.add(dto);
		}
		
		return partDTOs;
	}
	
	public List<PartResponseDTO> getPartsOutOfStock(int quantity){

		List<Part> parts = partRepository.findByQuantity(quantity);
		
		List<PartResponseDTO> partDTOs = new ArrayList<>();
		
		for(var part : parts ) {
			PartResponseDTO dto = new PartResponseDTO();
			
			dto.setId(part.getId());
			dto.setPartName(part.getPartName());
			dto.setPrice(part.getPrice());
			
			
			partDTOs.add(dto);
		}
		
		return partDTOs;
	}
	
	public PartResponseDTO createPart(PartRequestDTO dto) {
		
		Part part = new Part(
				dto.getPartName(),
				dto.getPrice(),
				dto.getQuantity());
		 
		Part savedPart = partRepository.save(part);
		
		PartResponseDTO responseDto = new PartResponseDTO();
		
		responseDto.setId(savedPart.getId());
		responseDto.setPartName(savedPart.getPartName());
		responseDto.setPrice(savedPart.getPrice());
		
		return responseDto;		
	}
	
	public PartResponseDTO updatePart(Long id, PartRequestDTO dto) {
		
		Part part = partRepository.findById(id)
				.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
						ProblemDetail.forStatusAndDetail(
		                HttpStatus.NOT_FOUND,
		                "Part not found with id: " + id
		        ),
		        null));;
		
		part.setPartName(dto.getPartName());
		part.setPrice(dto.getPrice());
		part.setQuantity(dto.getQuantity());
		
		Part savedPart = partRepository.save(part);
		
		PartResponseDTO responseDto = new PartResponseDTO();
		
		responseDto.setId(savedPart.getId());
		responseDto.setPartName(savedPart.getPartName());
		responseDto.setPrice(savedPart.getPrice());
		
		return responseDto;
	}
	
	public void deletePart(Long id) {
		
		
		Part part = partRepository.findById(id)
				.orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,
						ProblemDetail.forStatusAndDetail(
		                HttpStatus.NOT_FOUND,
		                "Part not found with id: " + id
		        ),
		        null));
		
		if(part.getQuantity() != 0) {
			throw new RuntimeException(
	                "Part has quantity in stock");
		}
		
		partRepository.delete(part);
	}
}
