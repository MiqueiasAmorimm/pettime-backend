# PetTime 🐾

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-brightgreen)
![Build](https://img.shields.io/badge/build-Maven-success)
![Tests](https://img.shields.io/badge/tests-JUnit%20%7C%20Mockito-success)
![Architecture](https://img.shields.io/badge/architecture-layered-blueviolet)
![Status](https://img.shields.io/badge/status-active_development-yellow)

PetTime is a backend REST API designed to manage pet service scheduling,
including users, pets, petshops, and appointments.

The project emphasizes clean architecture, testability, and scalable backend
design patterns commonly used in real-world Spring Boot applications.
It was built as a backend-focused portfolio project to demonstrate solid
software engineering practices using Java.

Frontend, authentication, cloud deployment, and distributed components are
planned as future improvements.

---

## 🚀 Project Goals

- Build a real-world backend application using Spring Boot
- Apply clean architecture and layered design principles
- Implement business rules isolated from HTTP concerns
- Practice automated testing across multiple layers
- Serve as a strong backend portfolio project for Java developer positions

---

## 🛠 Tech Stack

- Java 17
- Spring Boot 3
- Spring Web (REST APIs)
- Spring Data JPA
- Hibernate
- H2 (integration and repository tests)
- PostgreSQL (production-ready)
- JUnit 5
- Mockito
- Maven

---

## 🧱 Architecture Overview

The project follows a layered architecture with clear responsibility
boundaries between components:

- **controller** → REST endpoints and request handling
- **service** → business logic and validations
- **repository** → data access layer (JPA)
- **dto** → request and response contracts
- **model** → domain entities
- **exception** → custom business exceptions and error handling

### Key principles applied

- Separation of concerns
- DTO-based API contracts
- Business rules isolated in the service layer
- Centralized exception handling
- Clear responsibility per layer

---

```md
## 🧩 Architecture Diagram
## 🧩 Architecture Diagram

```mermaid
flowchart LR
    Client["Client / API Consumer"]

    subgraph Web_Layer["Web Layer"]
        Controller["REST Controllers"]
    end

    subgraph Application_Layer["Application Layer"]
        Service["Business Services"]
        DTO["DTOs"]
    end

    subgraph Domain_Layer["Domain Layer"]
        Entity["Domain Entities"]
        Exception["Business Exceptions"]
    end

    subgraph Infrastructure_Layer["Infrastructure Layer"]
        Repository["JPA Repositories"]
        Database[("Database")]
    end

    Client --> Controller
    Controller --> DTO
    Controller --> Service
    Service --> Entity
    Service --> Repository
    Repository --> Database
    Service --> Exception

```md
---

🔄 High-Level Flow
Client sends an HTTP request to a REST controller

Controller validates input and maps requests to DTOs

Service layer executes business rules and domain validations

Repository layer interacts with the database via JPA

Domain entities are persisted and mapped back to response DTOs

Centralized exception handling ensures consistent error responses

Client → Controller → Service → Repository → Database
📦 Main Features
User management with roles (ADMIN, CLIENT, PETSHOP)

Pet management linked to owners

Appointment scheduling

Appointment conflict validation per petshop

RESTful API design

Input validation with Jakarta Validation

Centralized and consistent error handling

📡 API Examples
Create User
POST /api/users

Request body:

{
  "name": "John Doe",
  "email": "john@example.com",
  "role": "CLIENT"
}
Response:

{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "CLIENT"
}
Get User by ID
GET /api/users/{id}

Response:

{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "CLIENT"
}
🧪 Testing Strategy
The project includes extensive automated tests covering multiple layers:

Controller tests using MockMvc

Service unit tests using Mockito

Repository integration tests using H2 in-memory database

Model and validation tests

Custom business exception tests

Run all tests with:

mvn test
▶️ Running the Project
Run the application locally with:

mvn spring-boot:run
Default active profile: dev

🔮 Planned Improvements
Dockerize the application

Add authentication (JWT / OAuth2)

Fully migrate to PostgreSQL

Introduce microservices concepts

Add Kafka for asynchronous events

Deploy to AWS

Add CI/CD pipeline using GitHub Actions

👨‍💻 Author
Miqueias Amorim
Backend Java Developer

Focused on:

Java & Spring Boot

Clean Architecture

Automated Testing

Scalable backend systems



