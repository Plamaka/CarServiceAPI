package com.plamaka.car_service_api.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public class ServiceRecordRequestDTO {
	
	@PastOrPresent
	private LocalDate serviceDate;

	@NotEmpty
    private String description;

    @DecimalMin("0.01")
    private double laborCost;

    @NotNull
    private Long carId;

    @NotEmpty
    private List<ServiceRecordPartRequestDTO> parts;

	public LocalDate getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(LocalDate serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getLaborCost() {
		return laborCost;
	}

	public void setLaborCost(double laborCost) {
		this.laborCost = laborCost;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public List<ServiceRecordPartRequestDTO> getParts() {
		return parts;
	}

	public void setParts(List<ServiceRecordPartRequestDTO> parts) {
		this.parts = parts;
	}
    
}
