# PetTime 🐾

PetTime is a backend REST API for managing pet services appointments, users, pets, and petshops.  
This project was built as a **portfolio project** with a strong focus on **clean architecture, automated testing, and backend best practices** using Java and Spring Boot.

> This project intentionally focuses on backend architecture and testing.  
> Frontend, authentication, cloud deployment, and microservices are planned as next steps.

---

## 🚀 Project Goals

- Build a real-world backend application using **Spring Boot**
- Apply **clean architecture principles**
- Practice **unit, integration, and repository testing**
- Serve as a solid **backend portfolio project** for Java developer positions

---

## 🛠 Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Web (REST APIs)**
- **Spring Data JPA**
- **Hibernate**
- **H2 (tests)**
- **PostgreSQL (ready for production)**
- **JUnit 5**
- **Mockito**
- **Maven**

---

## 🧱 Architecture Overview

The project follows a **layered architecture**:

- **controller** → REST endpoints
- **service** → business logic
- **repository** → data access (JPA)
- **dto** → request / response objects
- **model** → domain entities
- **exception** → custom business exceptions

Key principles applied:

- Separation of concerns
- DTOs to isolate API contracts
- Custom business exceptions
- Centralized exception handling
- Clear responsibility per layer

### High-level flow

Client
|
Controller
|
Service
|
Repository
|
Database


---

## 📦 Main Features

- User management with roles (**ADMIN**, **CLIENT**, **PETSHOP**)
- Pet management linked to owners
- Appointment scheduling
- Appointment conflict validation
- RESTful API design
- Validation with Jakarta Validation
- Centralized error handling

---

## 📡 API Examples

Some example endpoints exposed by the API:

POST /api/users
GET /api/users
GET /api/users/{id}
GET /api/users/email?email=
POST /appointments


---

## 🧪 Testing Strategy

This project includes **extensive automated tests**, covering multiple layers:

- **Controller tests** using MockMvc
- **Service unit tests** with Mockito
- **Repository integration tests** using H2 in-memory database
- **Model and validation tests**
- **Custom exception tests**

Run all tests with:

```bash
mvn test
▶️ Running the Project
Run the application locally with:

mvn spring-boot:run
Default active profile: dev

🔮 Next Steps (Planned Improvements)
Dockerize the application

Add authentication (JWT / OAuth2)

Fully migrate to PostgreSQL

Introduce microservices concepts

Add Kafka for asynchronous events

Deploy to AWS

Add CI/CD pipeline (GitHub Actions)

👨‍💻 Author
Miqueias Amorim
Backend Java Developer (in progress)

Focused on:

Java & Spring Boot

Clean Architecture

Automated Testing

Scalable backend systems
