package com.plamaka.car_service_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerRequestDTO {
	
		@NotBlank
		@Size(min = 2, max = 50)
		private String firstName;

		@NotBlank
		@Size(min = 2, max = 50)
	    private String lastName;

		@Email
	    private String email;

		@Size(min = 10 ,max = 12)
	    private String phoneNumber;

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone() {
			return phoneNumber;
		}

		public void setPhone(String phone) {
			this.phoneNumber = phone;
		}
}
