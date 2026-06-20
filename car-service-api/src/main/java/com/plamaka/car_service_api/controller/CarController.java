package com.plamaka.car_service_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plamaka.car_service_api.dto.request.CarRequestDTO;
import com.plamaka.car_service_api.dto.response.CarResponseDTO;
import com.plamaka.car_service_api.service.CarService;

import jakarta.validation.Valid;

@RestController
public class CarController {
	
	private final CarService carService;
	
	public CarController(CarService carService) {
		this.carService = carService;
	}

	@GetMapping(path = "/cars")
	public List<CarResponseDTO> getAll(){
		return carService.getAllCars();
	}
	
	@GetMapping(path = "/cars/{vinNumber}")
	public CarResponseDTO getCarByVinNumber(@RequestParam String vinNumber){
		return carService.getCarByVin(vinNumber);
	}
	
	@PostMapping(path = "/cars")
	public CarResponseDTO addCar(@Valid @RequestBody CarRequestDTO dto){
		return carService.createCar(dto);
	}
	
	@PutMapping(path = "/cars/{id}")
	public CarResponseDTO putCar(@Valid @PathVariable Long id, @RequestBody CarRequestDTO dto){
		return carService.updateCar(id, dto);
	}
	
	@PatchMapping(path = "/cars/{id}")
	public CarResponseDTO putCar( @PathVariable Long id, @Valid @RequestBody Long newCustomerId){
		return carService.assignNewCustomer(id, newCustomerId);
	}
	
	@DeleteMapping(path = "/cars/{id}")
	public void removeCar(@PathVariable Long id){
		carService.deleteCar(id);
	}
}
