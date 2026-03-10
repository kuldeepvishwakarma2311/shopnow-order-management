# ShopNow Order Management Backend

This project is a standalone layered Maven multi-module Spring Boot backend for the ShopNow e-commerce case study.

## Modules

- `order-api`: domain models, DTOs, enums, API contracts
- `order-service`: repositories, business services, orchestration, controller implementations
- `order-app`: Spring Boot bootstrap, runtime configuration, demo seed data

## Architecture Highlights

- Real-time order placement with inventory reservation
- Warehouse routing based on available stock and route priority
- Shipment creation and carrier tracking reference support
- Return and refund initiation flow
- Customer order history lookup
- Sales trend reporting endpoint scaffold

## Package Layers

- `controller`: REST API contracts in `order-api`, REST implementations in `order-service`
- `dto`: request/response models
- `model`: JPA system-of-record entities
- `repository`: persistence access
- `service`: business interfaces
- `service.impl`: business implementations
- `service.orchestration`: order placement workflow and warehouse routing
- `config`: application configuration and seed data in `order-app`

## Build

```bash
mvn -f /Users/ent-00290/Documents/Office/atm-rollout/Backend/shopnow-order-management/pom.xml clean test
```

## Run

```bash
mvn -f /Users/ent-00290/Documents/Office/atm-rollout/Backend/shopnow-order-management/pom.xml -pl order-app spring-boot:run
```
