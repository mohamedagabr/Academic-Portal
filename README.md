# Performance Management System API - Academic Portal
## Spring Boot + Oracle DB

## Tech Stack

- Language: Java 21
- Framework: Spring Boot 3.5.6
    - Spring Web
    - Spring Data JPA
    - Spring Validation
- Build Tool: Maven (Maven Wrapper included)
- Database: Oracle DB
- Libraries:
    - Lombok (Boilerplate reduction)
    - MapStruct (DTO mapping)
- API Documentation:
    - springdoc-openapi (Swagger UI)

---
## API Overview
Base path: /api
Swagger UI:
http://localhost:8080/swagger-ui/index.html

## Security
Authentication: JWT-based Security
Access Token: short-lived (5 minutes)
Refresh Token: short-lived  (10 minutes)
Stateless REST API
Spring Security Filter Chain

## Academic Portal Roadmap
EPIC 1 — Project Setup & Infrastructure
    US-1.1 — Create Backend Project
            Initialize Spring Boot project
            Configure Maven dependencies
            Setup base package structure
    US-1.2 — Database & Liquibase Setup
            Oracle DB connection setup
            Schema initialization
            Base entity creation
    US-1.3 — Security Integration (JWT)
            JWT generation & validation
            Access & Refresh token flow
            Spring Security configuration

EPIC 2 — Master Data Management
    US-2.1 — Courses Management
            CRUD operations for courses
            Validation rules
            DTO mapping using MapStruct
    US-2.2 — Users & Roles Management
            User registration & management
            Role-based access control
            Department assignment logic

EPIC 3 — Business Management
    Student Course Registration
    Each student can register only one record per course
    Prevent duplicate entries
    Allow status updates (registered  / cancelled)

Design Notes
    Clean layered architecture
    DTO-based API communication
    Centralized exception handling
    JWT security filter
    Stateless backend design
    Oracle-compatible timestamp handling
