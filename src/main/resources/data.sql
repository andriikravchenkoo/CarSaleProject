INSERT INTO vehicles (vin, brand, model, body_type, year, engine_type, engine_capacity, horsepower, transmission, mileage, color, license_plate, is_used)
VALUES ('WBA31AH020CJ03176','BMW', '530d', 'SEDAN', 2015, 'DIESEL', 3.0, 300, 'AUTOMATIC', 150000, 'BLACK', 'AE 7777 KE', true),
       ('WBA31AH020CJ02572','Volkswagen', 'Golf', 'COUPE', 2013, 'PETROL', 2.0, 180, 'ROBOT', 230000, 'WHITE', 'AE 5267 HK', true),
       ('WBA31AH020CJ09755','Dodge', 'Challenger', 'COUPE', 2023, 'PETROL', 6.4, 700, 'AUTOMATIC', 0, 'RED', 'Without', false);

INSERT INTO users (firstname, lastname, email, password, phone_number, role)
VALUES ('Andrii', 'Seller', 'seller@gmail.com', '$2a$10$eD.HMD5M7R3WeP5NcJvQAO4ESZ4krqLx0TupdCYCdnlJY8M.ohRmW', '+380111111111', 'SELLER'),
       ('Andrii', 'Customer', 'customer@gmail.com', '$2a$10$kCWYVDu62Fsupf45IRImmuzRJ.mYZ9FwReioGINxflPAYwAfTX1Yq', '+380222222222', 'CUSTOMER');