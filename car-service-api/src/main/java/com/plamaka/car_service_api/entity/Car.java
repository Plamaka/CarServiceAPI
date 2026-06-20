package com.plamaka.car_service_api.entity;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name = "cars")
public class Car {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String brand;
	
	private String model;
	
	private Integer productionYear;
	
	private String vinNumber;
	
	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;
	
	@OneToMany(mappedBy ="car")
	private List<ServiceRecord> ServiceRecords; 

	public Car() {
		
	}
	
	public Car(String brand, String model, Integer productionYear, String vinNumber ) {
		super();
		this.brand = brand;
		this.model = model;
		this.productionYear = productionYear;
		this.vinNumber = vinNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<ServiceRecord> getServiceRecords() {
		return ServiceRecords;
	}

	public void setServiceRecords(List<ServiceRecord> serviceRecords) {
		ServiceRecords = serviceRecords;
	}
}
