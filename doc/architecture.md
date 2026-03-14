# ShopNow Order Management Architecture

## Modules

- `order-api`: REST contracts, DTOs, wrappers, enums, and JPA entity model.
- `order-service`: controller implementations, orchestration services, repositories, mappers, and Feign integrations.
- `order-app`: Spring Boot launcher, security, OpenAPI, cache, async configuration, and runtime resources.

## Covered Business Capabilities

- Real-time order processing with payment validation and warehouse allocation.
- Inventory tracking by warehouse with stock reservation.
- Shipment generation and carrier tracking integration points.
- Return request and refund workflow.
- Sales trend endpoint for support and sales visibility.
