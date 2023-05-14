DO
$$
    DECLARE
        bmw_description        TEXT;
        lexus_description      TEXT;
        avtopark_description   TEXT;
        volkswagen_description TEXT;
    BEGIN
        bmw_description := 'AVT Bavaria Kyiv is an official dealer of BMW, BMW i, BMW M and BMW Motorrad in the Kyiv region.

Since 1997, the company has been developing intensively and, at this time, is represented by two official dealerships that fully meet BMW standards and provide a full range of services.

In 2017, AVT Bavaria Kyiv opened the largest BMW dealership in Eastern Europe. The area of the center is 7,000 square meters. At the same time, up to 15 new BMW cars can be exhibited in the dealership, which allows you to cover almost the entire model range of BMW. BMW M and BMW i cars are also presented in the respective showroom areas. There is always a wide selection of cars available for a test drive. In addition to the sale of new cars, cars are accepted for trade-in and the BMW Premium Selection program of certified cars with mileage runs.

In August 2018, the completely renovated dealership "AVT Bavaria Kyiv" opened its doors on Vaclav Havel Boulevard. It presents new BMW cars and motorcycles, as well as a wide selection of additional equipment, accessories and motorcycle equipment.

Guests of AVT Bavaria Kyiv dealerships can take advantage of financing and insurance services, repair their cars, and receive qualified maintenance. The technical equipment of the service areas and the qualification of the personnel of our dealerships fully meet the highest standards of the BMW Group and allow diagnostics, maintenance and repair of vehicles in compliance with the strictest requirements of the manufacturer''s factory.

We are always glad to see you in our dealerships.';

        lexus_description := '"Lexus Dnipro" is the only authorized 3S dealership in the city of Dnipro, built in full compliance with the latest standards of the Lexus brand. The spacious showroom represents a space of limitless possibilities, allows for the placement of 8 cars and includes a car drop-off area, without limiting light and space, which inspired design solutions in the dealership center in accordance with the latest Lexus Retail Concept standards.

"Lexus Dnipro" is an integral part of the "Almaz Group" corporation. Today, it is an association of modern dealerships with a total area of 50,000 square meters. meters, service stations that service up to 400 cars per day. "Lexus Dnipro" provides its customers with a full range of services in one place: sale of new cars, including test drive, insurance, leasing, credit, trade-in (exchange of a car with mileage for a new one with an additional cost difference), after-sales service, sale of original spare parts and accessories, body repair.';

        avtopark_description :=
                '"AVTOPARK" is the largest car dealership in Dnipro, which specializes in selling cars with mileage. At your disposal is a huge selection with the most diverse models, with any financial possibilities and taste preferences.';

        volkswagen_description := 'The Volkswagen showroom in Lviv is a great place for car lovers who are looking for high quality, reliability and comfort in one package. Here you can find a wide selection of Volkswagen models, from the classic legendary "Bubba" to modern sports cars.

The Volkswagen showroom in Lviv presents the latest innovations and the best models that meet the highest quality standards. In addition, here you can get professional advice from experts who will help you choose the perfect car, taking into account your needs and budget.';

        INSERT INTO dealerships (name, region, address, phone_number, description)
        VALUES ('BMW AVT Bavaria Kyiv', 'KYIV', 'Vaclav Havel Boulevard, 4', '+3800444907733', bmw_description),
               ('Lexus Dnipro Center', 'DNIPRO', '51, Zaporizhia highway', '+380567221000', lexus_description),
               ('Avtopark', 'DNIPRO', 'Street Naberezhna Peremohy, 2D', '+380984386862', avtopark_description),
               ('Volkswagen Alex Co', 'LVIV', 'Horodotska Street, 306', '+380322323510', volkswagen_description);
    END
$$;

INSERT INTO dealerships_images (dealership_id, image_id)
VALUES (3, 1),
       (3, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (2, 6),
       (2, 7),
       (4, 8),
       (4, 9),
       (4, 10);

INSERT INTO vehicles (vin, brand, model, body_type, year, engine_type, engine_capacity, horsepower, transmission,
                      mileage, color, license_plate, is_used, dealership_id)
VALUES ('WBA31AH020CJ03176', 'BMW', '530d', 'SEDAN', 2015, 'DIESEL', 3.0, 300, 'AUTOMATIC', 150000, 'BLACK',
        'AE 7777 KE', true, 1),
       ('WBA31AH020CJ98476', 'LEXUS', 'ES 250 AT', 'SEDAN', 2023, 'PETROL', 2.0, 212, 'ROBOT', 0, 'BLACK', 'Without',
        false, 2),
       ('WBA31AH020CJ09755', 'Dodge', 'Challenger', 'COUPE', 2023, 'PETROL', 6.4, 700, 'AUTOMATIC', 0, 'RED',
        'Without 2',
        false, 3),
       ('WBA31AH020CJ02572', 'Volkswagen', 'Golf', 'COUPE', 2013, 'PETROL', 2.0, 180, 'ROBOT', 230000, 'WHITE',
        'AE 5267 HK', true, 4);

INSERT INTO users (firstname, lastname, email, password, phone_number, role, dealership_id)
VALUES ('Andrii', 'Seller', 'seller@gmail.com', '$2a$10$eD.HMD5M7R3WeP5NcJvQAO4ESZ4krqLx0TupdCYCdnlJY8M.ohRmW',
        '+380111111111', 'SELLER', 1),
       ('Andrii', 'Seller 2', 'seller2@gmail.com', '$2a$10$eD.HMD5M7R3WeP5NcJvQAO4ESZ4krqLx0TupdCYCdnlJY8M.ohRmW',
        '+380111111112', 'SELLER', 2),
       ('Andrii', 'Seller 3', 'seller3@gmail.com', '$2a$10$eD.HMD5M7R3WeP5NcJvQAO4ESZ4krqLx0TupdCYCdnlJY8M.ohRmW',
        '+380111111113', 'SELLER', 3),
       ('Andrii', 'Seller 4', 'seller4@gmail.com', '$2a$10$eD.HMD5M7R3WeP5NcJvQAO4ESZ4krqLx0TupdCYCdnlJY8M.ohRmW',
        '+380111111114', 'SELLER', 4),
       ('Andrii', 'Customer', 'customer@gmail.com', '$2a$10$kCWYVDu62Fsupf45IRImmuzRJ.mYZ9FwReioGINxflPAYwAfTX1Yq',
        '+380222222222', 'CUSTOMER', null),
       ('Andrii', 'Customer 2', 'customer2@gmail.com', '$2a$10$kCWYVDu62Fsupf45IRImmuzRJ.mYZ9FwReioGINxflPAYwAfTX1Yq',
        '+380222222223', 'CUSTOMER', null);

INSERT INTO announcements (price, created, is_closed, description, user_id, vehicle_id)
VALUES (10000, NOW(), false, 'Some text', 1, 1),
       (56000, NOW(), false, 'Some text', 2, 2),
       (80000, NOW(), false, 'Some text', 3, 3),
       (15500, NOW(), true, 'Some text', 4, 4);

INSERT INTO favorites (user_id, announcement_id)
VALUES (3, 1),
       (3, 3),
       (4, 2);