# 🏪 Store Management API

A simple REST API built with **Java 17**, **Spring Boot 3**, and **Maven**.  
The project simulates a small store management system, allowing basic product operations and secured access via roles.

---

## 🚀 Features

- Add new products
- Find product by ID
- Change product price
- Basic Authentication with roles (`USER`, `ADMIN`)
- Role-based access control for endpoints
- Exception handling and logging
- Unit tests with JUnit 5 and Mockito
- Uses Lombok for cleaner entities
- Built with Java 17 (records, concise syntax)

---

## 🧩 Tech Stack

| Layer | Technology |
|--------|-------------|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Build Tool | Maven |
| Security | Spring Security (Basic Auth) |
| Persistence | Spring Data JPA |
| Database | H2 (in-memory, for demo) |
| Testing | JUnit 5, Mockito |
| Logging | SLF4J / Logback |
| Helpers | Lombok |

---

## 📦 Endpoints

| Method | Endpoint | Description | Role |
|--------|-----------|--------------|------|
| `POST` | `/api/products` | Add new product | `ADMIN` |
| `GET` | `/api/products/{id}` | Get product by ID | `USER` or `ADMIN` |
| `PUT` | `/api/products/{id}/price?price=VALUE` | Update product price | `ADMIN` |

Example request:
```bash
POST /api/products
Content-Type: application/json
Authorization: Basic YWRtaW46YWRtaW4xMjM=

{
  "name": "Laptop",
  "price": 1200.0
}
