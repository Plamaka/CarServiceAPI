package com.plamaka.car_service_api.dto.response;

import java.time.LocalDate;
import java.util.List;

public class ServiceRecordResponseDTO {
	
	private Long id;
	
	private String brand;
	
	private String model;
	
	private String vinNumber;
	
	private LocalDate serviceDate;
	
	private String description;
	
	private List<ServiceRecordPartResponseDTO> parts;
	
	private double laborCost;
	
	private double partsCost;
	
	private double totalPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVinNumber() {
		return vinNumber;
	}

	public void setVinNumber(String vinNumber) {
		this.vinNumber = vinNumber;
	}

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

	public List<ServiceRecordPartResponseDTO> getParts() {
		return parts;
	}

	public void setParts(List<ServiceRecordPartResponseDTO> parts) {
		this.parts = parts;
	}

	public double getLaborCost() {
		return laborCost;
	}

	public void setLaborCost(double laborCost) {
		this.laborCost = laborCost;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getPartsCost() {
		return partsCost;
	}

	public void setPartsCost(double partsCost) {
		this.partsCost = partsCost;
	}
	
}
