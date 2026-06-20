package com.plamaka.car_service_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "service_record_parts")
public class ServiceRecordPart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "serviceRecordId")
	private ServiceRecord serviceRecord;
	
	@ManyToOne
	@JoinColumn(name = "PartId")
	private Part part;
	
	private int quantity;
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public ServiceRecord getServiceRecord() {
		return serviceRecord;
	}

	public void setServiceRecord(ServiceRecord serviceRecord) {
		this.serviceRecord = serviceRecord;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}
}
