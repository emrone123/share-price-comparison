# Stock Monitor System Interfaces Documentation

## Module Structure
The StockMonitor application is organized as a modular Java application with the following module exports:

```java
module com.stockmonitor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    exports com.stockmonitor.app;
    exports com.stockmonitor.core;
    exports com.stockmonitor.interfaces;
    exports com.stockmonitor.abstracts;
    exports com.stockmonitor.collectors;
    exports com.stockmonitor.database;
    exports com.stockmonitor.display;
    exports com.stockmonitor.logging;
    exports com.stockmonitor.monitoring;
    exports com.stockmonitor.pricing;
}
```

## Core Interfaces

### IApplication
Represents the main application entry point.
- Methods:
  - `void run()`: Starts the application and initializes all components

### IDataCollector
Handles data collection from external sources.
- Methods:
  - `void collectData()`: Retrieves stock data from external APIs

### IDataBase
Manages data persistence operations.
- Methods:
  - `void saveData(String data)`: Stores data in the database
  - `String fetchData()`: Retrieves data from the database

### IStockMonitoring
Handles stock price monitoring activities.
- Methods:
  - `void monitorStockPrices()`: Initiates the stock price monitoring process

### IChartDisplay
Manages the visualization of stock data.
- Methods:
  - `void displayChart()`: Shows visual representation of stock data

### ILoggingData
Handles system logging.
- Methods:
  - `void log(String message)`: Records system events and messages

### IStockPrices
Manages stock price information.
- Methods:
  - `void updateStockPrice(double price)`: Updates the current price of a stock
  - `double getCurrentPrice()`: Retrieves the current price of a stock

## Implementation Structure

The system follows an interface-abstract class-concrete implementation pattern:

1. **Interfaces**: Define the contract for each component
2. **Abstract Classes**: Provide partial implementations and common functionality
3. **Concrete Classes**: Implement the specific behavior for each component

### Implementation Example:

```java
// Interface
interface IDataCollector {
    void collectData();
}

// Abstract implementation
abstract class AbstractDataCollector implements IDataCollector {
    protected String source;
    
    public AbstractDataCollector(String source) {
        this.source = source;
    }
}

// Concrete implementation
class StockDataCollector extends AbstractDataCollector {
    public StockDataCollector(String source) {
        super(source);
    }
    
    @Override
    public void collectData() {
        System.out.println("Collecting data from: " + source);
    }
}
```

## Component Interactions

The main application orchestrates all components:

1. **Application Initialization**: `StockMonitorApplication` implements `IApplication`
2. **Data Collection**: `StockDataCollector` implements `IDataCollector`
3. **Data Storage**: `StockDatabase` implements `IDataBase`
4. **Stock Monitoring**: `StockPriceMonitor` implements `IStockMonitoring`
5. **Data Visualization**: `StockChartDisplay` implements `IChartDisplay`
6. **System Logging**: `StockLogger` implements `ILoggingData`
7. **Price Tracking**: `StockPriceTracker` implements `IStockPrices`

## UI Components

The application uses JavaFX for the user interface:
- VBox layout with centered alignment
- Label for displaying welcome text
- Button for user interaction
- Controlled by HelloController class

## System Flow

1. The application starts with the `main` method
2. The `StockMonitorApplication` initializes and runs
3. Components are created and initialized in sequence
4. Each component performs its specific function
5. The UI is rendered using JavaFX
6. User interactions are handled through the controller 