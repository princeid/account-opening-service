# Account Opening System

## Description

Account Opening Service for Alexa bank - V2.

## Tools

- Java 17
- Spring boot 3.4.1
- Maven
- MySql
- H2
- Docker
- Openapi - Swagger

## How to run Application locally using H2 Database

- Clone or download zip project - git@github.com:princeid/account-opening-service.git
- Enable Annotation Processors for @lombok. If using Intellij Idea - Goto
  `Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors -> Annotation profile for account-opening-service` ->
  Select `account-opening-service` Check `Obtain processors from project classpath`
- bash mvn spring-boot:run OR click Run on the `AccountOpeningServiceApplication` if using Intellij
- Swagger UI - http://localhost:8080/swagger-ui/index.html. API doc - `http://localhost:8080/v3/api-docs`
- H2 database will be available at `http://localhost:8080/h2-console`. JDBC URL `jdbc:h2:mem:alexa_bank`, User Name =
  `sa`, Password = `password`.

## How to run Application on docker using MySQL Database

### For Windows (x86_64 architecture):

- Follow steps 1-5 below.

### For Mac:

- Skip step 3.

1. Clone or download zip project - git@github.com:princeid/account-opening-service.git
2. Download docker engine or docker desktop https://www.docker.com/products/docker-desktop/
3. edit the tag in `Application container image` in `docker-compose` file from `princeid/accountopeningservice:0.0.1` to
   `princeid/accountopeningservice:0.0.2`
4. cd into project root directory and run on terminal ` docker-compose up `
5. App should be running on `http://localhost:8080/`. For Swagger UI - `http://localhost:8080/swagger-ui/index.html`.
   Api
   docs - `http://localhost:8080/v3/api-docs`

- To drop containers and volumes - `docker-compose down -v`

## Testing

To launch your application's tests, run:

- ` .mvn test `
- Postman collection
  link: https://www.postman.com/galactic-meteor-339782/workspace/public-projects/collection/8809012-eb39ff3d-1c89-475c-80ee-e141f103d803?action=share&creator=8809012
