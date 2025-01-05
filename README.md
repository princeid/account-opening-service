# Account Opening System

## Description

Account Opening Service for Alexa bank - V2.

## Tools

- Java 21
- Spring boot 3.4.1
- Maven
- MySql
- H2
- Docker
- Openapi - Swagger

## How to run Application locally using H2 Database

### Clone or download zip project

Use Maven to build and run the application:

- bash mvn clean install
- bash mvn spring-boot:run OR click Run on the `AccountOpeningServiceApplication` if using IntelliJ Idea

## How to run Application on docker using MySQL Database

- Clone or download zip project - git@github.com:princeid/account-opening-service.git
- Download docker engine or docker desktop https://www.docker.com/products/docker-desktop/
- cd into project root directory and run on terminal ` docker-compose up `. App should be running on
  `http://localhost:8080/`. For Swagger UI - `http://localhost:8080/swagger-ui/index.html`

## Drop containers AND/OR volumes

- run `docker-compose down -v`

## Testing

To launch your application's tests, run:

- ` .mvn test `
- Postman collection
  link: https://www.postman.com/galactic-meteor-339782/workspace/public-projects/collection/8809012-eb39ff3d-1c89-475c-80ee-e141f103d803?action=share&creator=8809012
