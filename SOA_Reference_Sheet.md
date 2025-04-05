# SOA Principles Reference Sheet

This document provides a comprehensive reference of Service-Oriented Architecture (SOA) principles implemented throughout the Stock Monitor application.

## 1. Service Contract

**Definition**: Well-defined interfaces that establish clear contracts between service providers and consumers.

**Implementation Examples**:
- `StockDataService.java`: Defines a clear contract for stock data services without exposing implementation details
- `StockApiController.java`: Exposes well-defined API contract with standardized HTTP methods and endpoints
- `StockViewController.java`: Provides a clear contract for accessing the home page view

## 2. Service Abstraction

**Definition**: Services hide their internal implementations and expose only what's necessary through interfaces.

**Implementation Examples**:
- `YahooFinanceStockDataService.java`: Encapsulates the complexity of external stock data retrieval
- `StockDataService.java`: Abstracts underlying stock data retrieval mechanisms
- `StockApiController.java`: Abstracts implementation details with a simplified interface
- `StockViewController.java`: Abstracts web presentation layer from service layer

## 3. Service Autonomy

**Definition**: Services operate independently with clear boundaries.

**Implementation Examples**:
- `YahooFinanceStockDataService.java`: Operates independently and maintains its own state and logic
- `StockDataService.java`: Each method represents a self-contained operation with clearly defined inputs and outputs
- `StockMonitorApplication.java`: Each service manages its own state and logic

## 4. Service Loose Coupling

**Definition**: Services depend on abstractions rather than concrete implementations.

**Implementation Examples**:
- `StockDataService.java`: Ensures loose coupling as consumers depend on the abstraction
- `YahooFinanceStockDataService.java`: Scheduled methods operate independently without direct dependencies
- `StockViewController.java`: Uses dependency injection for loose coupling with service implementation

## 5. Service Reusability

**Definition**: Services are designed to be used across different parts of the application or by external clients.

**Implementation Examples**:
- `YahooFinanceStockDataService.java`: Provides reusable functionality that can be used by multiple consumers
- `StockDataService.java`: Operations designed to be reusable across different application parts
- `StockApiController.java`: API endpoints can be reused by multiple client applications
- `StockViewController.java`: Controller methods provide reusable capabilities via standard web protocols

## 6. Service Composability

**Definition**: Services can be composed together to create new functionality.

**Implementation Examples**:
- `YahooFinanceStockDataService.java`: Composed with other services through dependency injection
- `StockApiController.java`: Composes with StockDataService through dependency injection
- `StockViewController.java`: Composes with StockDataService to create higher-level service capability

## 7. Service Statelessness

**Definition**: Services do not maintain client state between requests.

**Implementation Examples**:
- `StockApiController.java`: Endpoint is stateless with all necessary information passed in each request
- `StockMonitorApplication.java`: RESTful endpoints are stateless with all needed context passed in requests

## 8. Service Discoverability

**Definition**: Services and their capabilities are easily discoverable.

**Implementation Examples**:
- `StockDataService.java`: Clear method naming and purpose make the service easy to discover
- `YahooFinanceStockDataService.java`: Scheduled method automatically discovered by Spring's component scanning
- `StockViewController.java`: Endpoint has clear URL path making it easily discoverable

## 9. Service Interoperability

**Definition**: Services can be consumed by various clients regardless of platform or technology.

**Implementation Examples**:
- `StockApiController.java`: RESTful endpoint provides standard HTTP interface consumable by any client
- `StockMonitorApplication.java`: REST API endpoints can be consumed by any HTTP-capable client
- `StockViewController.java`: Controller integrates with service layer and transforms data for the view layer

## 10. Service Orchestration

**Definition**: Coordinating multiple service calls to provide a cohesive functionality.

**Implementation Examples**:
- `StockViewController.java`: Orchestrates multiple service calls to provide a cohesive view for stock comparison

## SOA Principles Implementation Matrix

| Component | Contract | Abstraction | Autonomy | Loose Coupling | Reusability | Composability | Statelessness | Discoverability | Interoperability | Orchestration |
|-----------|:--------:|:-----------:|:--------:|:--------------:|:-----------:|:-------------:|:-------------:|:---------------:|:----------------:|:-------------:|
| **StockDataService** | ✓ | ✓ | ✓ | ✓ | ✓ | - | - | ✓ | - | - |
| **YahooFinanceStockDataService** | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | - | ✓ | - | - |
| **StockApiController** | ✓ | ✓ | - | - | ✓ | ✓ | ✓ | - | ✓ | - |
| **StockViewController** | ✓ | ✓ | - | ✓ | ✓ | ✓ | - | ✓ | ✓ | ✓ |
| **StockMonitorApplication** | - | - | ✓ | - | - | - | ✓ | - | ✓ | - |

## Component Relationship Map

The Stock Monitor application follows a layered SOA architecture with clear service boundaries:

```
┌─────────────────────────────────────────────────────────────┐
│                      Client Layer                            │
│  (Web Browsers, Mobile Apps, External Systems)               │
└───────────────────────────┬─────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                   Presentation Layer                         │
│  ┌───────────────────┐           ┌────────────────────┐     │
│  │ StockViewController│◄─────────►│ StockApiController │     │
│  └─────────┬─────────┘           └──────────┬─────────┘     │
└────────────┼────────────────────────────────┼───────────────┘
             │                                │
             │           Invokes              │
             ▼                                ▼
┌─────────────────────────────────────────────────────────────┐
│                     Service Layer                            │
│  ┌───────────────────────────────────────────────────┐      │
│  │              StockDataService (Interface)          │      │
│  └───────────────────────┬───────────────────────────┘      │
│                          │ Implements                        │
│                          ▼                                   │
│  ┌───────────────────────────────────────────────────┐      │
│  │         YahooFinanceStockDataService              │      │
│  └───────────────────────┬───────────────────────────┘      │
└────────────────────────┬─┼────────────────────────────────┬─┘
                         │ │                                │
                         │ │ Uses                           │
                         ▼ ▼                               ▼
┌─────────────────────────────────────────────┐ ┌────────────────────┐
│            Data Access Layer                 │ │   Domain Model     │
│  ┌───────────────────────────────────┐      │ │ ┌────────────────┐ │
│  │      StockPriceRepository         │      │ │ │   StockPrice   │ │
│  └─────────────────┬─────────────────┘      │ │ └────────────────┘ │
└─────────────────────────────────────────────┘ └────────────────────┘
```

### Key Relationships:

1. **Client Layer to Presentation Layer**: Clients interact with the application through web interfaces or API calls

2. **Presentation Layer to Service Layer**: Controllers depend on the `StockDataService` interface (not implementation)

3. **Service Layer Implementation**: `YahooFinanceStockDataService` implements the `StockDataService` interface

4. **Service to Data Access Layer**: The service implementation uses the repository to persist and retrieve data

5. **Service to Domain Model**: Services operate on the `StockPrice` domain model

This architecture demonstrates:
- **Loose Coupling**: Components depend on abstractions (interfaces)
- **Clear Service Boundaries**: Each layer has well-defined responsibilities
- **Separation of Concerns**: Presentation, business logic, and data access are separated

