package com.plamaka.car_service_api.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name = "parts")
public class Part {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String partName;
	
	private double price;
	
	private int quantity;
	
	@OneToMany(mappedBy = "part")
	private List<ServiceRecordPart> ServiceRecordParts;
	
	public Part() {
		
	}
	
	public Part(String partName, double price, int quantity) {
		super();

		this.partName = partName;
		this.price = price;
		this.quantity = quantity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
