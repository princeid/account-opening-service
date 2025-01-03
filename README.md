# Account Opening System

## Description

Account Opening Service for Alexa bank - V2.

## Tools

- Java 21
- Spring boot 3.4.1
- MySql
- Docker
- Openapi - Swagger
- Spring Security & OAUTH 2.0

## How to run the API on docker

- Download docker engine or docker desktop https://www.docker.com/products/docker-desktop/
- Create `.env` file in project root directory with the following db credentials
  (MYSQL_DATABASE_URL, MYSQL_DATABASE_SCHEMA, MYSQL_ROOT_USER, MYSQL_ROOT_PASSWORD)
- run `mvn clean install`
- From project root directory terminal run `docker-compose up --build`. App should be running on
  `http://localhost:8080/`

## Drop containers AND/OR volumes

- run `docker-compose down -v`

## Testing

To launch your application's tests, run:

- ` .mvn test `
