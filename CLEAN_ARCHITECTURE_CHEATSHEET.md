# Clean Architecture Principles - Quick Reference Guide

This quick reference guide maps Clean Architecture principles to specific files in the codebase for easy evaluation.

## Core Clean Architecture Principles

| Principle | Description | Key File References |
|-----------|-------------|---------------------|
| **Dependency Rule** | Dependencies point inward | `module-info.java` (lines 6-16), `Main.java` (lines 39-41) |
| **Layer Separation** | System divided into concentric layers | All interfaces in `interfaces/` package, implementations in respective packages |
| **Entities** | Core business objects | `StockPrice.java` (lines 3-20) |
| **Use Cases** | Application business rules | `IStockMonitor.java`, `BasicStockMonitor.java` |
| **Interface Adapters** | Convert data between formats | `SQLiteDataStorage.java` (lines 58-96) |
| **Frameworks & Drivers** | External details | `Main.java` (UI components, lines 48-91) |

## SOLID Principles

| Principle | Description | Key File References |
|-----------|-------------|---------------------|
| **Single Responsibility** | One reason to change | `StockPrice.java`, `BasicStockMonitor.java` |
| **Open-Closed** | Open for extension, closed for modification | Interface hierarchy in `interfaces/` package |
| **Liskov Substitution** | Subtypes replaceable | `SQLiteDataStorage` implements `IDataStorage` |
| **Interface Segregation** | Focused interfaces | `IStockMonitor.java`, `IAlertSystem.java`, etc. |
| **Dependency Inversion** | High-level modules don't depend on low-level | `Main.java` depends on interfaces (lines 39-41) |

## Design Patterns Implementing Clean Architecture

| Pattern | Description | Key File References |
|---------|-------------|---------------------|
| **Repository** | Data access abstraction | `IDataStorage.java`, `SQLiteDataStorage.java` |
| **Strategy** | Different algorithm implementations | `BasicStockMonitor.java` |
| **Adapter** | Convert interfaces | `SQLiteDataStorage.java` (adapts domain to SQLite) |
| **Dependency Injection** | Inject dependencies | `Main.java` (lines 49-50) |

## Clean Architecture Layers - File Mapping

### Entities Layer
- `src/main/java/com/stockmonitor/domain/StockPrice.java`

### Use Cases Layer
- `src/main/java/com/stockmonitor/interfaces/IStockMonitor.java`
- `src/main/java/com/stockmonitor/interfaces/IDataStorage.java`
- `src/main/java/com/stockmonitor/interfaces/IAlertSystem.java`
- `src/main/java/com/stockmonitor/interfaces/IChartSystem.java`
- `src/main/java/com/stockmonitor/interfaces/IHistoricalAnalysis.java`
- `src/main/java/com/stockmonitor/interfaces/INotificationSystem.java`
- `src/main/java/com/stockmonitor/monitor/BasicStockMonitor.java`

### Interface Adapters Layer
- `src/main/java/com/stockmonitor/database/SQLiteDataStorage.java`
- `src/main/java/com/stockmonitor/frameworks/repository/*`
- Repository, presenter, and controller implementations

### Frameworks & Drivers Layer
- `src/main/java/com/stockmonitor/app/Main.java`
- External libraries (JavaFX, SQLite)
- `module-info.java` (framework dependencies)

## Clean Code Principles - Consistent Implementation

| Principle | Implementation |
|-----------|----------------|
| **Clear naming** | Descriptive method and variable names throughout |
| **Comments** | Clean Architecture principle comments at class and method levels |
| **Small methods** | Methods have single responsibility with clear purpose |
| **Error handling** | Proper exception handling in `SQLiteDataStorage.java` |
| **Testing potential** | Interface-based design enables mocking for tests | 