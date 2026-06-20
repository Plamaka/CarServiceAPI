package com.plamaka.car_service_api.dto.request;

public class ServiceRecordPartRequestDTO {
	
	
	private Long partId;

    private int quantity;

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
