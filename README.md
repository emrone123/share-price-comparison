# Stock Price Comparison Web Application

A web-based system for comparing stock prices over time, designed with modern architectural styles and domain-independent patterns.

## Features

- Compare stock prices from different companies over customizable date ranges
- Interactive charts for visual comparison
- Tabular data display with pricing information
- RESTful API for programmatic access to stock data
- Responsive web design for desktop and mobile

## Architecture Overview

This application implements multiple domain-independent architectural styles to create a reusable, scalable, and maintainable system:

### 1. Layered Architecture
The application is structured in clear layers with separation of concerns:
- **Presentation Layer**: Controllers and views that handle user interaction
- **Service Layer**: Business logic and external service integration
- **Data Access Layer**: Repository interfaces for data persistence
- **Domain Layer**: Core business entities and value objects

### 2. Model-View-Controller (MVC)
The Spring MVC framework is used to implement the classic MVC pattern:
- **Model**: Domain objects (StockPrice) and services
- **View**: Thymeleaf templates for HTML rendering
- **Controller**: Web controllers handling HTTP requests

### 3. Service-Oriented Architecture (SOA)
The application embraces SOA principles:
- **Service Modularity**: Self-contained services with specific responsibilities
- **Service Interoperability**: Standard protocols (HTTP/REST) and data formats (JSON)
- **Service Abstraction**: Implementation details hidden behind interfaces
- **Service Reusability**: Generic interfaces allowing different implementations
- **Service Composability**: Services can be combined to create higher-level functionality

### 4. N-Tier Architecture
The system follows a multi-tier approach:
- **Presentation Tier**: Web UI and REST API
- **Business Logic Tier**: Service implementations
- **Data Tier**: JPA repositories and SQLite database

### 5. Adapter Pattern
- YahooFinanceStockDataService adapts external API to domain model
- Controllers adapt HTTP requests to service calls

### 6. Pipes and Filters
- Data processing flows through distinct stages
- Each component processes data independently

### 7. Blackboard (Repository Pattern)
- Central repository for accessing and storing stock data
- Multiple components read from and write to this shared knowledge base

## Technology Stack

- **Backend**: Java 17, Spring Boot 3
- **Database**: SQLite with Spring Data JPA
- **Frontend**: Thymeleaf, Bootstrap 5, Chart.js
- **Build Tool**: Maven

## Getting Started

### Prerequisites
- Java JDK 17 or higher
- Maven 3.6 or higher

### Running the Application
1. Clone the repository
2. Navigate to the project directory
3. Run the application using Maven:
   ```
   mvn spring-boot:run
   ```
4. Access the application at http://localhost:8080/stockmonitor

## API Documentation

The application exposes a RESTful API:

### Get Stock Data
`GET /stockmonitor/api/stocks/{symbol}?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD`

### Get Latest Stock Price
`GET /stockmonitor/api/stocks/{symbol}/latest`

### Compare Stocks
`GET /stockmonitor/api/stocks/compare?symbol1=AAPL&symbol2=MSFT&startDate=YYYY-MM-DD&endDate=YYYY-MM-DD`

## SOA Principles

This application emphasizes SOA principles through:

1. **Service Modularity**: Services are focused on specific business capabilities
2. **Interoperability**: Standard formats and protocols for communication
3. **Loose Coupling**: Dependencies managed through interfaces
4. **Abstraction**: Implementation details hidden from consumers
5. **Reusability**: Services designed for reuse across multiple contexts
6. **Statelessness**: Services don't maintain client state between requests 