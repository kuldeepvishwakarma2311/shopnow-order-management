# Code Layer Explanation

This file explains one representative file from each layer of the project in simple language.

You can use this if your lead asks:

- why this file is there
- why this layer was created
- what responsibility each class has

## 1. Controller Interface

File:
[OrderController.java](/Users/ent-00290/Documents/Project/shopnow-order-management/order-api/src/main/java/com/shopnow/order/controller/OrderController.java)

### What it does

This file defines the order-related API contract.

It declares:

- create order
- get order by id
- get all orders
- update order status
- cancel order

### Why I wrote it

I wrote this file in the `order-api` module because I wanted API definitions to stay separate from implementation.

This helps because:

- API structure is clean and reusable
- the contract remains stable even if implementation changes
- it follows enterprise modular design

### Short explanation to tell lead

`OrderController` is the API contract layer for order operations. I kept it as an interface so the request mappings and endpoint definitions remain separated from controller implementation logic.

## 2. Controller Implementation

File:
[OrderControllerImpl.java](/Users/ent-00290/Documents/Project/shopnow-order-management/order-service/src/main/java/com/shopnow/order/controller/impl/OrderControllerImpl.java)

### What it does

This class implements the API contract defined in `OrderController`.

It:

- receives API requests
- calls the service layer
- wraps responses inside `ApiResponse`

### Why I wrote it

I wrote this class to keep controllers thin.

The controller should not contain business logic. Its job is only:

- accept request
- call service
- return response

### Short explanation to tell lead

`OrderControllerImpl` is the REST entry point implementation. I kept it lightweight so business rules stay in the service layer and the controller only manages request-response handling.

## 3. Service Interface

File:
[OrderService.java](/Users/ent-00290/Documents/Project/shopnow-order-management/order-service/src/main/java/com/shopnow/order/service/OrderService.java)

### What it does

This file defines the operations supported by the order business layer.

It declares methods for:

- create order
- fetch order
- update status
- cancel order
- fetch customer orders

### Why I wrote it

I wrote this interface to keep business contracts separate from their implementation.

This helps because:

- logic can change without affecting controller code
- implementation stays replaceable
- code is easier to test and maintain

### Short explanation to tell lead

`OrderService` is the business contract for order processing. I created it so the controller depends on an abstraction, not directly on implementation.

## 4. Service Implementation

File:
[OrderServiceImpl.java](/Users/ent-00290/Documents/Project/shopnow-order-management/order-service/src/main/java/com/shopnow/order/service/impl/OrderServiceImpl.java)

### What it does

This is the main business logic class for order management.

It handles:

- customer validation
- payment validation
- warehouse selection
- inventory reservation
- order item creation
- shipment creation
- status transition validation
- inventory release on cancellation
- inventory finalization when order is shipped

### Why I wrote it

I wrote this file because this is the real business layer of the system.

This is where the order lifecycle rules belong, not in controller or repository.

This class is important because the case study is mainly about solving:

- real-time order processing
- inventory-based warehouse routing
- shipment generation
- return-safe order lifecycle handling

### Short explanation to tell lead

`OrderServiceImpl` is the core order orchestration class. I wrote it to centralize the complete order lifecycle, including payment check, warehouse routing, stock reservation, shipment creation, status control, and cancellation handling.

## 5. Repository

File:
[OrderRepository.java](/Users/ent-00290/Documents/Project/shopnow-order-management/order-service/src/main/java/com/shopnow/order/repository/OrderRepository.java)

### What it does

This file provides database access for the `ORDERS` table through Spring Data JPA.

It supports:

- standard CRUD through `JpaRepository`
- custom lookup of orders by customer id

### Why I wrote it

I wrote this repository so database access stays separate from business logic.

The service should describe what needs to happen, and repository should handle data access.

### Short explanation to tell lead

`OrderRepository` is the data access layer for orders. I used `JpaRepository` to reduce boilerplate and added a customer-based query needed for support and order-history use cases.

## 6. Mapper

File:
[OrderMapper.java](/Users/ent-00290/Documents/Project/shopnow-order-management/order-service/src/main/java/com/shopnow/order/mapper/OrderMapper.java)

### What it does

This file converts internal entity data into response DTOs.

It maps:

- `Order`
- `OrderItem`
- `Shipment`

into one `OrderResponseDTO`.

### Why I wrote it

I wrote this mapper to avoid returning JPA entities directly from APIs.

This helps because:

- API response stays clean
- internal entity structure stays protected
- response formatting remains centralized

### Short explanation to tell lead

`OrderMapper` is used to convert database entities into API response objects. I created it to keep response-building logic separate from service logic and to avoid exposing entity models directly.

## 7. DTO

File:
[OrderRequestDTO.java](/Users/ent-00290/Documents/Project/shopnow-order-management/order-api/src/main/java/com/shopnow/order/dto/OrderRequestDTO.java)

### What it does

This DTO represents the request payload for order creation.

It contains:

- customer id
- list of ordered items
- payment details

### Why I wrote it

I wrote this DTO because API input should be different from database entity design.

The client should send only the required request data, not a full entity object.

### Short explanation to tell lead

`OrderRequestDTO` is the input model for creating an order. I used a DTO instead of the entity so the API remains clean, validated, and independent from database structure.

## 8. Entity Model

File:
[Order.java](/Users/ent-00290/Documents/Project/shopnow-order-management/order-api/src/main/java/com/shopnow/order/model/Order.java)

### What it does

This class represents the `ORDERS` table in MySQL.

It stores:

- customer reference
- order date
- order status
- total amount
- payment status

### Why I wrote it

I wrote this entity to model the system-of-record database exactly as required by the case study.

The order entity is central because most other modules connect to it:

- payments
- shipments
- returns
- order items

### Short explanation to tell lead

`Order` is the core persistence model of the system. I created it as the main transaction entity because payment, shipment, inventory, and return flows all revolve around the order record.

## 9. Exception Handler

File:
[ApiExceptionHandler.java](/Users/ent-00290/Documents/Project/shopnow-order-management/order-service/src/main/java/com/shopnow/order/generic/ApiExceptionHandler.java)

### What it does

This class handles exceptions globally and converts them into standard API responses.

It handles:

- resource not found
- validation errors
- business errors
- generic server errors

### Why I wrote it

I wrote this file so the API gives clean and consistent error responses.

Without this, every controller would need repeated try-catch logic.

### Short explanation to tell lead

`ApiExceptionHandler` gives centralized error handling. I added it to make the API response format consistent and to keep controllers free from repetitive exception code.

## 10. Integration Client

File:
[ShippingCarrierClient.java](/Users/ent-00290/Documents/Project/shopnow-order-management/order-service/src/main/java/com/shopnow/order/integration/ShippingCarrierClient.java)

### What it does

This is a Feign client contract for external shipping integration.

It represents how the system can call a carrier tracking API.

### Why I wrote it

I wrote this because the case study clearly mentions third-party shipping carrier integration.

Even if we are not using a full real carrier today, the backend structure is ready for it.

### Short explanation to tell lead

`ShippingCarrierClient` is the external integration contract for shipment tracking. I added it so the project is ready for real carrier API integration without changing the core order module design.

## 11. Security Configuration

File:
[SecurityConfig.java](/Users/ent-00290/Documents/Project/shopnow-order-management/order-app/src/main/java/com/shopnow/order/security/SecurityConfig.java)

### What it does

This class configures application security.

It:

- allows login endpoint and Swagger without token
- protects all business APIs
- enables stateless JWT security
- creates the in-memory application user
- registers password encoder and authentication manager

### Why I wrote it

I wrote this because open business APIs are not realistic for enterprise systems.

JWT-based protection makes the project more practical and closer to real implementation.

### Short explanation to tell lead

`SecurityConfig` controls API security for the application. I added JWT-based stateless authentication so only authenticated users can access business operations, while keeping login and documentation endpoints open.

## 12. Application Bootstrap

File:
[OrderApplication.java](/Users/ent-00290/Documents/Project/shopnow-order-management/order-app/src/main/java/com/shopnow/order/OrderApplication.java)

### What it does

This is the Spring Boot startup class.

It enables:

- application bootstrapping
- Feign clients
- JPA auditing
- package scanning

### Why I wrote it

I wrote this file because the application module should be responsible for startup and runtime wiring.

This keeps boot configuration separate from business logic.

### Short explanation to tell lead

`OrderApplication` is the main startup entry point of the backend. I used it to bootstrap Spring Boot, enable external integration clients, and activate JPA auditing in one central application layer.

## 13. How To Explain The Overall Design

If your lead asks why the project is layered like this, you can say:

I separated the code so each layer has one responsibility. The `order-api` module defines contracts, DTOs, and entities. The `order-service` module contains the business logic, repositories, mappings, exception handling, and external integration contracts. The `order-app` module is only for startup, security, Swagger, and runtime configuration. This keeps the project easier to maintain, easier to test, and easier to extend later.

## 14. Short Version If Asked Quickly

I used a layered modular structure so the API contract, business logic, database access, integration, and application configuration are all separated. This was done to make the order management backend clean, enterprise-style, and easier to enhance for payment, shipment, return, and warehouse workflows.
