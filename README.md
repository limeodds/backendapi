# ![Turing Challenge - Backend API]

> ### Java 11 + Spring boot + MySql

This codebase implements REST API endpoints as described in the swagger documentation
https://backendapi.turing.com/docs/ 

# Prerequisites

* Java 11 https://jdk.java.net/java-se-ri/11
* Gradle https://gradle.org/install/
* MySQL Community database https://dev.mysql.com/downloads/mysql/

# How it works

* The application uses Spring boot (Web, Security)
* Lombok is heavily used throughout the code (https://projectlombok.org/)

The code is organized as follows:

There are three layers:
1. 'repository' layer in charge to database data manipulation
2. 'service' layer where all teh business logic is done
3. 'controller' layer that expose data through the REST API endpoints

You can check the APIs provided trough the swagger interface: 

    http://localhost:8081/swagger-ui.html

# Security

Integration with Spring Security and add other filter for jwt token process.

# Getting started

You need Java installed.

    ./gradlew bootRun
    open http://localhost:8081