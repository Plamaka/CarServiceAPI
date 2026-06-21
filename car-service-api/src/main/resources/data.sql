INSERT INTO customers(first_name, last_name, email, phone_number)
VALUES ('Ivan', 'Petrov', 'ivan@gmail.com', '0891477943');

INSERT INTO customers(first_name, last_name, email, phone_number)
VALUES ('Georgi', 'Ivanov', 'georgi@gmail.com', '0987688650');

INSERT INTO cars(brand,model,production_year,vin_number,customer_id) 
VALUES ('Fiat', 'Grande Punto', 2008, 'ZFA19900000000001',1);

INSERT INTO service_records(service_date,description,labor_cost,car_id)
VALUES (CURRENT_DATE, 'Oil and filters replacement', 80.00, 1);

INSERT INTO parts(part_name, quantity_in_stock, price)
VALUES ('Oil Filter', 20, 15.50);

INSERT INTO parts(part_name, quantity_in_stock, price)
VALUES ('Air Filter', 10, 25.00);

INSERT INTO parts(part_name, quantity_in_stock, price)
VALUES ('Spark Plug', 50, 8.50);

INSERT INTO service_record_parts(service_record_id,part_id,quantity)
VALUES (1, 1, 1);

INSERT INTO service_record_parts(service_record_id,part_id,quantity)
VALUES (1, 2, 1);