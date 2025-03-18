# Stock Monitor Business Concept Model

```mermaid
classDiagram
    class Stock {
        +String symbol
        +String name
        +Double currentPrice
        +Double previousClose
        +Integer volume
        +Double marketCap
    }
    
    class User {
        +String id
        +String username
        +String email
        +Enum role
        +Object preferences
    }
    
    class Portfolio {
        +String id
        +String name
        +Double totalValue
        +Date creationDate
        +Date lastUpdated
    }
    
    class Transaction {
        +String id
        +Enum type
        +String stockSymbol
        +Integer quantity
        +Double price
        +Date date
        +Double fee
    }
    
    class Alert {
        +String id
        +Enum type
        +String stockSymbol
        +Double targetPrice
        +Enum status
        +Date creationDate
        +Boolean triggered
    }
    
    class MarketData {
        +Date timestamp
        +String stockSymbol
        +Double openPrice
        +Double highPrice
        +Double lowPrice
        +Double closePrice
        +Integer volume
    }
    
    class WatchList {
        +String id
        +String name
        +Array stockSymbols
        +Date creationDate
    }
    
    class Report {
        +String id
        +Enum type
        +Date generationDate
        +Object data
        +String format
    }
    
    User "1" -- "0..*" Portfolio : owns
    User "1" -- "0..*" WatchList : manages
    User "1" -- "0..*" Alert : configures
    
    Portfolio "1" -- "0..*" Transaction : contains
    Portfolio "1" -- "0..*" Stock : tracks
    
    WatchList "1" -- "0..*" Stock : monitors
    
    Stock "1" -- "0..*" MarketData : generates
    Stock "1" -- "0..*" Alert : triggers
    
    Report "0..*" -- "1" Portfolio : analyzes
    Report "0..*" -- "1" User : belongs to
```

## Business Concepts Explanation

### Core Entities

1. **Stock**
   - Represents financial instruments traded on exchanges
   - Contains current market data and identifiers

2. **User**
   - System users who interact with the application
   - May have different roles (investor, analyst, admin)

3. **Portfolio**
   - Collection of stocks owned by a user
   - Tracks total value and performance metrics

4. **Transaction**
   - Records of buying or selling activities
   - Contains price, quantity, and timing information

5. **Alert**
   - Notification triggers based on price or event conditions
   - Can be configured by users for specific stocks

6. **MarketData**
   - Historical and real-time pricing information
   - OHLC (Open, High, Low, Close) data points

7. **WatchList**
   - Curated lists of stocks users want to monitor
   - Does not imply ownership, only interest

8. **Report**
   - Generated analysis of portfolio performance
   - Can be scheduled or created on-demand

### Key Relationships

- Users **own** Portfolios, which track their investments
- Portfolios **contain** Transactions that record trading activity
- Users **manage** WatchLists to monitor stocks of interest
- Stocks **generate** MarketData as prices change over time
- Users **configure** Alerts that are **triggered** by Stocks reaching certain conditions
- Reports **analyze** Portfolios and **belong to** Users 