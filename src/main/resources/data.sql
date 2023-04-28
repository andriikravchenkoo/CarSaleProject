INSERT INTO dealerships (name, region, address, phone_number, description)
VALUES ('BMW Dnipro', 'DNIPRO', 'Topol 1, 17-B', '+380660567777', 'Some text'),
       ('Volkswagen Group', 'KYIV', 'Shevchenko 15-A', '+380660567277', 'Some text');

INSERT INTO vehicles (vin, brand, model, body_type, year, engine_type, engine_capacity, horsepower, transmission, mileage, color, license_plate, is_used, dealership_id)
VALUES ('WBA31AH020CJ03176','BMW', '530d', 'SEDAN', 2015, 'DIESEL', 3.0, 300, 'AUTOMATIC', 150000, 'BLACK', 'AE 7777 KE', true, 1),
       ('WBA31AH020CJ02572','Volkswagen', 'Golf', 'COUPE', 2013, 'PETROL', 2.0, 180, 'ROBOT', 230000, 'WHITE', 'AE 5267 HK', true, 2),
       ('WBA31AH020CJ09755','Dodge', 'Challenger', 'COUPE', 2023, 'PETROL', 6.4, 700, 'AUTOMATIC', 0, 'RED', 'Without', false, 1);

INSERT INTO users (firstname, lastname, email, password, phone_number, role, dealership_id)
VALUES ('Andrii', 'Seller', 'seller@gmail.com', '$2a$10$eD.HMD5M7R3WeP5NcJvQAO4ESZ4krqLx0TupdCYCdnlJY8M.ohRmW', '+380111111111', 'SELLER', 1),
       ('Andrii', 'Seller 2', 'seller2@gmail.com', '$2a$10$eD.HMD5M7R3WeP5NcJvQAO4ESZ4krqLx0TupdCYCdnlJY8M.ohRmW', '+380111111112', 'SELLER', 2),
       ('Andrii', 'Customer', 'customer@gmail.com', '$2a$10$kCWYVDu62Fsupf45IRImmuzRJ.mYZ9FwReioGINxflPAYwAfTX1Yq', '+380222222222', 'CUSTOMER', null),
       ('Andrii', 'Customer 2', 'customer2@gmail.com', '$2a$10$kCWYVDu62Fsupf45IRImmuzRJ.mYZ9FwReioGINxflPAYwAfTX1Yq', '+380222222223', 'CUSTOMER', null);

INSERT INTO announcements (price, created, is_closed, description, user_id, vehicle_id)
VALUES (10000, NOW(), false, 'Some text', 1, 3),
       (26000, NOW(), false, 'Some text', 2, 2),
       (15500, NOW(), true, 'Some text', 1, 1);

INSERT INTO favorites (user_id, announcement_id)
VALUES (3, 1),
       (3, 3),
       (4, 2);