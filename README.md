```md
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
- Spring Web
- Spring Data JPA
- Hibernate
- H2 (tests)
- PostgreSQL (production-ready)
- JUnit 5
- Mockito
- Maven

---

## 🧱 Architecture Overview

- **controller** → REST endpoints  
- **service** → business logic  
- **repository** → data access  
- **dto** → API contracts  
- **model** → domain entities  
- **exception** → centralized error handling  

---

## 🧩 Architecture Diagram

```mermaid
flowchart LR
    Client["Client / API Consumer"]

    subgraph Web["Web Layer"]
        Controller["REST Controllers"]
    end

    subgraph App["Application Layer"]
        Service["Business Services"]
        DTO["DTOs"]
    end

    subgraph Domain["Domain Layer"]
        Entity["Entities"]
        Exception["Business Exceptions"]
    end

    subgraph Infra["Infrastructure Layer"]
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

1. Client sends an HTTP request
2. Controller validates input and maps DTOs
3. Service executes business rules
4. Repository interacts with the database
5. Entities are persisted and mapped to responses
6. Exceptions are handled centrally

Client → Controller → Service → Repository → Database

📦 Main Features

* User management with roles
* Pet management
* Appointment scheduling
* Conflict validation
* RESTful API design
* Centralized error handling

📡 API Examples
POST /api/users

{
  "name": "John Doe",
  "email": "john@example.com",
  "role": "CLIENT"
}
🧪 Testing Strategy

* Controller tests (MockMvc)
* Service tests (Mockito)
* Repository tests (H2)

Run:
mvn test

▶️ Running the Project
mvn spring-boot:run

👨‍💻 Author
Miqueias Amorim
Backend Java Developer


