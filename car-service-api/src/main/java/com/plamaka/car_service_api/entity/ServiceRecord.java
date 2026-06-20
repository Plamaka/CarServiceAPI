package com.plamaka.car_service_api.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name = "service_records")
public class ServiceRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate serviceDate;
	
	private String description;
	
	private double laborCost;
	
	@ManyToOne
	@JoinColumn(name = "carId")
	private Car car;
	
	@OneToMany(mappedBy = "serviceRecord")
	private List<ServiceRecordPart> serviceRecordParts;

	public ServiceRecord() {
		
	}
	
	public ServiceRecord(LocalDate serviceDate, String description, double laborCost) {
		super();
		this.serviceDate = serviceDate;
		this.description = description;
		this.laborCost = laborCost;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public double getLaborCost() {
		return laborCost;
	}

	public void setLaborCost(double laborCost) {
		this.laborCost = laborCost;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public List<ServiceRecordPart> getServiceRecordParts() {
		return serviceRecordParts;
	}

	public void setServiceRecordParts(List<ServiceRecordPart> serviceRecordParts) {
		this.serviceRecordParts = serviceRecordParts;
	}
}
