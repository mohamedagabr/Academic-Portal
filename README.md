# Performance Management System API - Academic Portal (Spring Boot + Oracle + Liquibase)

## Tech Stack

- Language: Java 21
- Framework: Spring Boot 3.5.6 (Web, Data JPA, Validation)
- Build/Package Manager: Maven (with Maven Wrapper)
- Annotation processing: Lombok , mapstruct
- API Documentation: springdoc-openapi (Swagger UI)


-Export Jar file (with ignore test)
- mvn clean package -DskipTests


- API base URL: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html` (or `/swagger-ui/index.html`)
- OpenAPI spec (JSON): `http://localhost:8080/v3/api-docs`

## API Overview

Base path: `/api`

## Academic-Portal-Project Roadmap

# EPIC 1 — Project Setup & Infrastructure

- US-1.1 — Create Backend Project
- US-1.2 — Database & Liquibase Setup
- US-1.3 — Security Integration (JWT)

# EPIC 2 — Master Data Management

- US-2.1 Courses Management  (CRUD courses)
- US-2.2 Users & Roles Admin       (Link User to Management)

# EPIC 2 — Business Management
- Student can register single record per student-course 
       and update its status to avoid duplication.





