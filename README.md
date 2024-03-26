# Stellar Craft Project - API Module

Api module

## Prerequisites

- Tools: Java 21 and Maven 3.9.x installed.

## Stack

- Maven + Spring data JPA

## Test coverage

Test coverage:

- Controller: must be 100%
- Service must be almost 80%

## Api documentation (PDF)

Api documentation using Spring rest docs. (target/generate-docs). It will be generated only using custom maven profile (generate-api)

    mvn -Pgenerate-api-doc clean install

## Compile application local

    ./mvnw clean install

## Run application local profile
 
    ./mvnw spring-boot:run -D"spring-boot.run.profiles"=local

## Access dev database through local interface

Use H2 database provided by Spring Boot, which DB file is setting in local: 
for example:

file:/Users/sabrinaberazategui/proyectos1/stellarcraftcontrol/testdb

## Swagger UI

http://localhost:8080/stellar-craft/swagger-ui/index.html
