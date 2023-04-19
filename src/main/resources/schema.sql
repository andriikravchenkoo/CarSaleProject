CREATE TYPE body_type AS ENUM ('SEDAN', 'COUPE', 'HATCHBACK', 'STATION_WAGON', 'SUV', 'CROSSOVER', 'VAN', 'PICKUP_TRUCK', 'CONVERTIBLE', 'ROADSTER', 'MINIVAN', 'BUS', 'TRUCK');

CREATE TYPE engine_type AS ENUM ('PETROL', 'DIESEL', 'GAS', 'HYBRID', 'ELECTRIC');

CREATE TYPE transmission AS ENUM ('MANUAL', 'AUTOMATIC', 'TIPTRONIC', 'ROBOT', 'VARIATOR');

CREATE TYPE color AS ENUM ('WHITE', 'BLACK', 'BEIGE', 'BLUE', 'BROWN', 'GREEN', 'GREY', 'ORANGE', 'VIOLET', 'RED', 'YELLOW');

CREATE TABLE IF NOT EXISTS vehicles
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    vin             VARCHAR(255) UNIQUE,
    brand           VARCHAR(255),
    model           VARCHAR(255),
    body_type       body_type,
    year            BIGINT,
    engine_type     engine_type,
    engine_capacity DOUBLE PRECISION                        NOT NULL,
    horsepower      BIGINT                                  NOT NULL,
    transmission    transmission,
    mileage         BIGINT                                  NOT NULL,
    color           color,
    license_plate   VARCHAR(255) UNIQUE,
    is_used         BOOLEAN
);