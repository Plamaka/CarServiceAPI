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

import com.plamaka.car_service_api.dto.request.PartRequestDTO;
import com.plamaka.car_service_api.dto.response.PartResponseDTO;
import com.plamaka.car_service_api.service.PartService;

import jakarta.validation.Valid;

@RestController
public class PartController {
	
	private final PartService partService;
	
	public PartController(PartService partService) {
		this.partService = partService;
	}

	@GetMapping(path = "/parts")
	public List<PartResponseDTO> getAll(){
		return partService.getAllParts();
	}
	
	@GetMapping(path = "/parts/available-parts")
	public List<PartResponseDTO> getAvailableParts(@RequestParam("quantity") int quantity){
		return partService.getAvailableParts(quantity);
	}
	
	@GetMapping(path = "/parts/search")
	public List<PartResponseDTO> getByPartNameContaining(@RequestParam("part-name") String name){
		return partService.findByPartNameContaining(name);
	}
	
	@GetMapping(path = "/parts/out-of-stock")
	public List<PartResponseDTO> getPartsOutOfStock(){
		return partService.getPartsOutOfStock(0);
	}
	
	@PostMapping(path = "/parts")
	public PartResponseDTO addPart(@Valid @RequestBody PartRequestDTO dto){
		return partService.createPart(dto);
	}
	
	@PutMapping(path = "/parts/{id}")
	public PartResponseDTO putCar(@PathVariable Long id, @Valid @RequestBody PartRequestDTO dto){
		return partService.updatePart(id, dto);
	}
	
	@DeleteMapping(path = "/parts/{id}")
	public void removePart(@PathVariable Long id){
		partService.deletePart(id);
	}
}
