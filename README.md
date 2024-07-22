# Car Sale Project

## Introduction
An application built on the basis of MVC, which copies a car buying service. The user can view dealers and their advertisements for selling cars, and add them to favorites. The dealer can create car dealerships and add advertisements to the site.
Project is currently under development.

## Features

* Registration and authorization by roles
* Creation of dealerships and advertisements
* View advertisements and add them to favorites
* View a list of advertisements on pages

## Technical details

**Technology stack**:

* JDK 17
* Spring Boot
* Spring JDBC
* Spring Security
* Spring MVC
* Thymeleaf
* PostgreSQL
* Lombok
* Logback
* JUnit 5

## Users profiles

Seller: **email** - `seller@gmail.com`, **password** - `seller`

Customers: **email** - `customer@gmail.com`, **password** - `customer`

## Contributing

1. Clone the project locally on your machine
2. Run `docker compose build` in terminal to build Docker images
3. Run `docker compose up` in terminal to run Docker container
4. Go to this address in your browser: http://localhost:8080/car-sale/authentication/login
