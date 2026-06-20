package com.plamaka.car_service_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.plamaka.car_service_api.entity.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
