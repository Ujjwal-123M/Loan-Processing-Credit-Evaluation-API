# Loan Processing & Credit Evaluation API

A Spring Boot based backend application that simulates a Loan Processing and Credit Evaluation system used in FinTech environments.

This service handles applicant onboarding, loan application processing, and rule-based credit evaluation.

---

## 🚀 Overview

This project demonstrates a backend system responsible for:

- Applicant registration
- Loan application submission
- Rule-based loan approval/rejection
- Status tracking of loan applications
- Transactional processing
- Clean layered architecture

The system evaluates loan eligibility based on predefined business rules such as credit score, income, and loan amount thresholds.

---

## 🏗 Architecture

The project follows a clean layered architecture:

com.creditengine
 ├── controller
 ├── service
 ├── service.impl
 ├── repository
 ├── entity
 ├── dto
 ├── exception
 └── config

### Layers

- Controller Layer – Handles REST API requests
- Service Layer – Contains business logic and credit evaluation rules
- Repository Layer – Database interaction using Spring Data JPA
- Entity Layer – JPA entity definitions
- DTO Layer – Request/Response objects
- Exception Layer – Global exception handling

---

## 🛠 Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- Lombok
- Jakarta Validation

---

## 📦 Core Entities

### Applicant
- id
- name
- email
- monthlyIncome
- creditScore
- createdAt

### LoanApplication
- id
- loanAmount
- tenureMonths
- interestRate
- status (PENDING, APPROVED, REJECTED, DISBURSED)
- appliedDate
- Applicant (ManyToOne relationship)

---

## ⚙ Business Rules

A loan is APPROVED if:

- creditScore >= 700  
- monthlyIncome >= 30000  
- loanAmount <= monthlyIncome × 20  

Otherwise, the loan is REJECTED.

---

## 🔗 REST Endpoints

### Applicant APIs

POST   /api/applicants  
GET    /api/applicants/{id}  

### Loan APIs

POST   /api/loans/apply  
PUT    /api/loans/{id}/review  
GET    /api/loans/{id}  
GET    /api/loans/applicant/{applicantId}  

---

## 🗄 Database Configuration

Add the following in application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/credit_engine  
spring.datasource.username=your_username  
spring.datasource.password=your_password  

spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true  

---

## ▶ Running the Application

1. Clone the repository  
2. Configure MySQL database  
3. Run:

mvn spring-boot:run  

Application runs at:

http://localhost:8080  

---

## 📌 Key Concepts Demonstrated

- Layered Architecture
- RESTful API Design
- DTO Pattern
- JPA Entity Relationships
- Enum-based State Management
- Business Rule Processing
- Transaction Management
- Global Exception Handling
- Validation
