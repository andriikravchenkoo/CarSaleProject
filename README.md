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

## Profiles passwords

All sellers - **seller**

All customers - **customer**

## Endpoints

http://localhost:8080/car-sale/vehicle/home - home page

http://localhost:8080/car-sale/dealership/create - create dealership

http://localhost:8080/car-sale/dealership/{id} - dealership by id

http://localhost:8080/car-sale/announcement/create - create announcement

http://localhost:8080/car-sale/announcement/{id} - announcement by id

http://localhost:8080/car-sale/announcement/page?pageId={id} - list of announcement

## Contributing

1. Clone the project locally on your machine
2. Run schema.sql script
3. On first run change images:loaded: true to images:loaded: false in application.yml to initialize images to database. After the first run, the value must be returned to true
4. Run data.sql script
5. Run application
