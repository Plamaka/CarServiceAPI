package com.plamaka.car_service_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CarRequestDTO {
	
	@NotBlank
	private String brand;
	
	@NotBlank
	private String model;
	
	@NotNull
	private Integer productionYear;
	
	@NotBlank
	@Size(min = 17, max = 17)
	private String vinNumber;
	
	@NotNull
    private Long customerId;

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

	public int getYear() {
		return productionYear;
	}

	public void setYear(int productionYear) {
		this.productionYear = productionYear;
	}

	public String getVinNumber() {
		return vinNumber;
	}

	public void setVinNumber(String vinNumber) {
		this.vinNumber = vinNumber;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
}
