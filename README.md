# ShopNow Order Management Backend

This repository implements the backend side of the ShopNow order management case study as a Maven multi-module Spring Boot system.

## Modules

- `order-api`: system-of-record entities, DTOs, enums, wrappers, controller interfaces
- `order-service`: repositories, business services, mapper layer, Feign integrations, controller implementations
- `order-app`: Spring Boot startup, security, OpenAPI, cache/async config, seed data, runtime properties

## Backend Coverage

- Order processing with payment validation, inventory reservation, and warehouse allocation
- Inventory tracking by warehouse
- Shipment creation and shipment lookup by tracking number
- Customer order-history access for support use cases
- Return request and refund initiation flow
- Sales trend endpoint for sales and marketing analysis

## Layer Mapping

- `model` -> persistent database entities with uppercase table and column naming
- `dto` -> API payloads for create/update/read operations
- `repository` -> JPA persistence layer
- `service` -> business contracts
- `service.impl` -> business workflow implementations
- `mapper` -> entity/DTO conversion layer
- `controller` -> API contracts
- `controller.impl` -> HTTP endpoint implementations
- `config`, `security`, `mock` -> application startup and operational configuration

## Not In Scope For Backend

- Front-end screens for customers, sales, support, and warehouse staff are not part of this repository.
- Live third-party shipping and payment systems are represented by Feign integration contracts with safe fallback behavior.

## Build

```bash
mvn clean test
```

## Run

```bash
mvn -pl order-app spring-boot:run
```

The application uses MySQL from `application.properties`.

## Authentication

Use `POST /auth/login` to get a JWT token.

Default credentials:

- Username: `shopnow-admin`
- Password: `shopnow123`

## Deployment

Build the jar and Docker image:

```bash
mvn clean package -DskipTests
docker build -t shopnow-order-management:latest .
```

Helm chart files are under [helm-chart](/Users/ent-00290/Documents/Project/shopnow-order-management/helm-chart).

Example install:

```bash
helm install shopnow ./helm-chart
```

## Realistic Scope

Removed demo-only startup code and unused framework wiring:

- no mock seed loader
- no unused async executor config
- no unused cache manager config

Operational support APIs:

- `GET /shipment/order/{orderId}`
- `GET /payments/order/{orderId}`
- `GET /returns`
