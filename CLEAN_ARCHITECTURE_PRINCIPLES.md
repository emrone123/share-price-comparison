# Clean Architecture Principles in Stock Monitor Application

This document provides a comprehensive overview of how Clean Architecture principles are implemented throughout the Stock Monitor application, with specific references to code files and patterns.

## 1. Dependency Rule

**Definition**: Dependencies always point inward. Inner layers do not know about outer layers.

**Implementation Examples**:
- `module-info.java`: The module descriptor follows the dependency rule by explicitly requiring external frameworks but keeping the core domain independent.
- `IStockMonitor.java`: The interface defines business rules without knowledge of UI or database implementations.
- `Main.java`: Depends on interfaces (`IStockMonitor`, `IDataStorage`) rather than concrete implementations.

## 2. Clean Separation of Layers

**Definition**: System divided into concentric layers with clear boundaries.

**Implementation Examples**:
- **Entities Layer**: `StockPrice.java` - Core business objects independent of application-specific use cases.
- **Use Cases Layer**: `IStockMonitor.java`, `IDataStorage.java` - Business rules specific to the application.
- **Interface Adapters Layer**: `SQLiteDataStorage.java` - Converts data between use cases and external formats.
- **Frameworks & Drivers Layer**: `Main.java` (JavaFX UI) - External frameworks and tools.

## 3. Interface Segregation Principle

**Definition**: Clients should not be forced to depend on interfaces they do not use.

**Implementation Examples**:
- **Domain-specific interfaces**: `IStockMonitor.java`, `IAlertSystem.java`, `IChartSystem.java`, etc. - Each focuses on a specific responsibility.
- **Granular service interfaces**: `IDataStorage.java` focuses only on data persistence operations.
- **Repository interfaces**: Clean separation between data access and business logic.

## 4. Single Responsibility Principle

**Definition**: Each class has only one reason to change.

**Implementation Examples**:
- `StockPrice.java`: Only responsible for representing stock price data structure.
- `SQLiteDataStorage.java`: Only responsible for SQLite-specific data persistence.
- `BasicStockMonitor.java`: Only responsible for stock monitoring business logic.
- `Main.java`: UI concerns are separated from business logic.

## 5. Dependency Inversion Principle

**Definition**: High-level modules don't depend on low-level modules. Both depend on abstractions.

**Implementation Examples**:
- `Main.java`: Depends on `IStockMonitor` and `IDataStorage` interfaces rather than concrete implementations.
- Business rules defined in interfaces (`IStockMonitor`, etc.) that are implemented by concrete classes.
- Instantiation with `stockMonitor = new BasicStockMonitor()` and `dataStorage = new SQLiteDataStorage()` demonstrates dependency injection.

## 6. Entities as Domain Objects

**Definition**: Business objects that embody core business rules independent of application.

**Implementation Examples**:
- `StockPrice.java`: Pure domain entity without dependencies on frameworks or external libraries.
- Immutable core properties (`symbol`, `price`, `date`) with encapsulated behavior.

## 7. Use Cases as Business Logic

**Definition**: Application-specific business rules that orchestrate the flow of data.

**Implementation Examples**:
- `IStockMonitor.java`: Defines operations for monitoring stocks.
- `BasicStockMonitor.java`: Implements business logic for stock monitoring.
- `fetchAndDisplayStockData()` in `Main.java`: Represents a specific use case.

## 8. Interface Adapters as Converters

**Definition**: Convert data between formats suitable for use cases and external agencies.

**Implementation Examples**:
- `SQLiteDataStorage.java`: Converts domain objects to database records and vice versa.
- `saveStockData()` and `retrieveStockData()` methods in `SQLiteDataStorage.java` adapt between domain and persistence formats.

## 9. Frameworks and Drivers as External Details

**Definition**: External frameworks, tools, and delivery mechanisms.

**Implementation Examples**:
- JavaFX UI components in `Main.java`.
- SQLite database access in `SQLiteDataStorage.java`.
- Alpha Vantage API interaction in `fetchStockData()` method of `Main.java`.

## 10. Separation of Concerns

**Definition**: Different aspects of the system are addressed by different components.

**Implementation Examples**:
- **UI concerns**: Handled in `Main.java`.
- **Business logic**: Defined in interfaces and implemented in classes like `BasicStockMonitor.java`.
- **Data access**: Encapsulated in `SQLiteDataStorage.java`.
- **External API interaction**: Isolated in specific methods.

## 11. Encapsulation

**Definition**: Implementation details are hidden from other parts of the system.

**Implementation Examples**:
- `StockPrice.java`: Private fields with public accessors.
- `BasicStockMonitor.java`: Private helper methods like `generateRandomPrice()`.
- `SQLiteDataStorage.java`: Database connection details hidden from business logic.

## 12. Testability

**Definition**: System components designed to be easily testable in isolation.

**Implementation Examples**:
- Interface-based design allows for mock implementations in tests.
- Clear boundaries between system components facilitate unit testing.
- Business logic separated from external dependencies.

## 13. Framework Independence

**Definition**: The core business logic is independent of UI frameworks and external tools.

**Implementation Examples**:
- Domain entities and business rules have no dependencies on JavaFX, SQLite, or other frameworks.
- External framework dependencies explicitly declared in `module-info.java` but isolated from core components.

## 14. Open-Closed Principle

**Definition**: Software entities should be open for extension but closed for modification.

**Implementation Examples**:
- Interface-based design allows adding new implementations without modifying existing code.
- `IStockMonitor` can have multiple implementations beyond `BasicStockMonitor`.
- `IDataStorage` can support different database technologies without changing business logic.

## 15. Plugin Architecture

**Definition**: System components can be easily replaced with alternative implementations.

**Implementation Examples**:
- `Main.java` uses dependency injection to load concrete implementations of interfaces.
- Different UI, database, or API integration implementations can be swapped without affecting core business logic.

## Summary of Implementation Patterns

1. **Interface-Based Programming**: Core business rules defined as interfaces.
2. **Dependency Injection**: Concrete implementations injected at runtime.
3. **Domain-Driven Design**: Core entities model the business domain.
4. **Layered Architecture**: Clear separation of system layers.
5. **Adapter Pattern**: Interfaces between components with different interfaces.
6. **Repository Pattern**: Data access abstracted behind interfaces.
7. **Factory Pattern**: Creation of objects delegated to specialized methods.
8. **Strategy Pattern**: Different algorithms encapsulated in different implementations.

This comprehensive implementation of Clean Architecture principles ensures the application is:
- **Maintainable**: Components can be modified independently.
- **Testable**: Components can be tested in isolation.
- **Extensible**: New features can be added without disrupting existing functionality.
- **Independent of Frameworks**: Core business logic doesn't depend on external frameworks.
- **Independent of UI**: Business logic doesn't depend on UI choices.
- **Independent of Database**: Business logic doesn't depend on database technology.
- **Independent of External Agencies**: Business logic doesn't depend on external APIs. 