CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    phone_number VARCHAR(15) NOT NULL
);

CREATE TABLE cars (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(50),
    model VARCHAR(50),
    production_year INTEGER,
    vin_number VARCHAR(17) UNIQUE,
    customer_id BIGINT,

    CONSTRAINT fk_car_customer
        FOREIGN KEY(customer_id)
        REFERENCES customers(id)
);

CREATE TABLE service_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_date DATE NOT NULL,
    description VARCHAR(500),
    labor_cost DECIMAL(10,2) NOT NULL,

    car_id BIGINT NOT NULL,

    CONSTRAINT fk_service_record_car
        FOREIGN KEY (car_id)
        REFERENCES cars(id)
);

CREATE TABLE parts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    part_name VARCHAR(100) NOT NULL,
    quantity_in_stock INTEGER NOT NULL,
    price DECIMAL(10,2) NOT NULL
);

CREATE TABLE service_record_parts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_record_id BIGINT NOT NULL,
    part_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,

    CONSTRAINT fk_srp_service_record
        FOREIGN KEY (service_record_id)
        REFERENCES service_records(id),

    CONSTRAINT fk_srp_part
        FOREIGN KEY (part_id)
        REFERENCES parts(id)
);