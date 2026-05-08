# 💳 Payment Service

A microservice responsible for initiating and verifying payments within the platform. It integrates with **Paystack** for payment processing, communicates with the **Notification Service** via Feign Client for post-payment emails, registered on **Eureka** and accessed exclusively through the **API Gateway**.
it has an api that receives a payload of items to be purchased, transactions are stored in a database and can be requested for later
---

## 🏗️ Architecture Overview

```
Client Request
     │
     ▼
 API Gateway  ◄──── Only trusted entry point
     │
     ▼
Payment Service
     │
     ├──► Paystack API          (Payment Initialization)
     │
     ├──► Paystack Webhook      (Payment Verification)
     │
     └──► Notification Service  (Email via Feign Client)
     
Payment Service ──► Eureka Server  (Service Registration & Discovery)
```

---

## ✨ Features

- **Payment Initialization** — Creates a payment session via the Paystack API and returns a checkout URL to the client.
- **Webhook Verification** — Listens for and validates Paystack webhook events to confirm successful transactions.
- **Email Notification** — After payment verification, triggers the Notification (mail) service via Feign Client to send a confirmation email.
- **Service Discovery** — Registers with Netflix Eureka for dynamic service resolution within the microservices network.
- **Gateway-Only Access** — All endpoints are secured and only accessible through the trusted API Gateway.
- **Containerized Deployment** — Docker image is built and pushed to Docker Hub automatically via a GitHub Actions CI/CD pipeline.

---

## 🔄 Payment Flow

```
1. Client sends payment request → API Gateway → Payment Service
2. Payment Service calls Paystack API → returns payment link
3. Customer completes payment on Paystack
4. Paystack sends webhook event to Payment Service
6. On success → Feign Client calls Notification Service
7. Notification Service sends confirmation email to customer
```

---

## 🛠️ Tech Stack

| Layer              | Technology                        |
|--------------------|-----------------------------------|
| Language           | Java 25                           |
| Framework          | Spring Boot 4.0.6                 |
| Cloud              | Spring Cloud 2025.1.1             |
| Service Discovery  | Netflix Eureka Client             |
| Inter-service Comm | OpenFeign                         |
| Payment Gateway    | Paystack                          |
| Database           | PostgreSQL (via Spring Data JPA)  |
| Security           | Spring Security                   |
| HTTP Clients       | RestClient / WebClient            |
| Containerization   | Docker                            |
| CI/CD              | GitHub Actions → Docker Hub       |
| Utilities          | Lombok, Gson, Hutool              |

---

## ⚙️ Configuration

The following environment variables / properties are required:

```yaml
# Paystack
paystack.secret-key=YOUR_PAYSTACK_SECRET_KEY
paystack.base-url=https://api.paystack.co

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/payment_db
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD

# Eureka
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

# Notification Service (Feign)
notification.service.url=http://notification-service
```

---

## 🚀 Running Locally

### Prerequisites

- Java 25+
- Maven
- PostgreSQL
- A running Eureka Server
- A running API Gateway

### Steps

```bash
# Clone the repository
git clone https://github.com/Bigman004/payment_service.git
cd payment_service

# Build the project
./mvnw clean install

# Run the service
./mvnw spring-boot:run
```

### With Docker

```bash
# Build the image
docker build -t payment-service .

# Run the container
docker run -p 8080:8080 \
  -e PAYSTACK_SECRET_KEY=your_key \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host:5432/payment_db \
  payment-service
```

---

## 📦 CI/CD Pipeline

The project uses **GitHub Actions** to automate the build and deployment process.

**On every push to `master`:**

1. Code is checked out
2. Maven builds and tests the project
3. A Docker image is built
4. The image is pushed to **Docker Hub**

> Ensure `DOCKER_USERNAME` and `DOCKER_PASSWORD` are set as GitHub repository secrets.

---

## 🔐 Security

- All API endpoints are **protected by the API Gateway** — direct calls to the service are not permitted.

---

## 📁 Project Structure

```
payment_service/
├── .github/
│   └── workflows/         # GitHub Actions CI/CD pipeline
├── src/
│   └── main/
│       ├── java/          # Application source code
│       └── resources/     # Configuration files
├── Dockerfile             # Container build instructions
└── pom.xml                # Maven dependencies & build config
```

---

## 🔗 Related Services

This service is part of a larger microservices ecosystem:

- **API Gateway** — Routes and secures all incoming traffic
- **Eureka Server** — Service registry and discovery
- **Notification Service** — Handles email delivery after payment confirmation