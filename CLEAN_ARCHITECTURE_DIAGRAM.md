# Clean Architecture Diagram for Stock Monitor Application

```
┌────────────────────────────────────────────────────────────────────────────┐
│                          CLEAN ARCHITECTURE LAYERS                          │
└────────────────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────────────────────┐
│                                                                            │
│  ┌──────────────────────────────────────────────────────────────────────┐  │
│  │                                                                      │  │
│  │  ┌──────────────────────────────────────────────────────────────┐   │  │
│  │  │                                                              │   │  │
│  │  │  ┌──────────────────────────────────────────────────────┐   │   │  │
│  │  │  │                                                      │   │   │  │
│  │  │  │  ┌──────────────────────────────────────────────┐   │   │   │  │
│  │  │  │  │                                              │   │   │   │  │
│  │  │  │  │           ENTERPRISE BUSINESS RULES          │   │   │   │  │
│  │  │  │  │                                              │   │   │   │  │
│  │  │  │  │               ENTITIES LAYER                 │   │   │   │  │
│  │  │  │  │                                              │   │   │   │  │
│  │  │  │  │             • StockPrice.java               │   │   │   │  │
│  │  │  │  │                                              │   │   │   │  │
│  │  │  │  └──────────────────────────────────────────────┘   │   │   │  │
│  │  │  │                                                      │   │   │  │
│  │  │  │           APPLICATION BUSINESS RULES                 │   │   │  │
│  │  │  │                                                      │   │   │  │
│  │  │  │                USE CASES LAYER                       │   │   │  │
│  │  │  │                                                      │   │   │  │
│  │  │  │        • IStockMonitor.java                         │   │   │  │
│  │  │  │        • IDataStorage.java                          │   │   │  │
│  │  │  │        • IAlertSystem.java                          │   │   │  │
│  │  │  │        • BasicStockMonitor.java                     │   │   │  │
│  │  │  │                                                      │   │   │  │
│  │  │  └──────────────────────────────────────────────────────┘   │   │  │
│  │  │                                                              │   │  │
│  │  │              INTERFACE ADAPTERS LAYER                        │   │  │
│  │  │                                                              │   │  │
│  │  │         • SQLiteDataStorage.java                            │   │  │
│  │  │         • Repository implementations                         │   │  │
│  │  │         • Presenters                                         │   │  │
│  │  │         • Controllers                                        │   │  │
│  │  │                                                              │   │  │
│  │  └──────────────────────────────────────────────────────────────┘   │  │
│  │                                                                      │  │
│  │                 FRAMEWORKS & DRIVERS LAYER                           │  │
│  │                                                                      │  │
│  │            • Main.java (JavaFX UI)                                  │  │
│  │            • SQLite database                                         │  │
│  │            • Alpha Vantage API                                       │  │
│  │            • External libraries                                      │  │
│  │                                                                      │  │
│  └──────────────────────────────────────────────────────────────────────┘  │
│                                                                            │
└────────────────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────────────────────┐
│                            DEPENDENCY DIRECTION                             │
└────────────────────────────────────────────────────────────────────────────┘

           FRAMEWORKS & DRIVERS  ──────────┐
                                           │
                                           │
                                           ▼
           INTERFACE ADAPTERS   ──────────┐
                                          │
                                          │
                                          ▼
           USE CASES           ──────────┐
                                         │
                                         │
                                         ▼
           ENTITIES                      

           Dependencies point INWARD toward the center

┌────────────────────────────────────────────────────────────────────────────┐
│                           CONCRETE IMPLEMENTATION                           │
└────────────────────────────────────────────────────────────────────────────┘

1. ENTITIES LAYER:
   - StockPrice.java: Core business entity

2. USE CASES LAYER:
   - IStockMonitor.java: Interface defining stock monitoring use cases
   - IDataStorage.java: Interface defining data storage use cases
   - BasicStockMonitor.java: Implementation of stock monitoring use cases

3. INTERFACE ADAPTERS LAYER:
   - SQLiteDataStorage.java: Adapts between use cases and database
   - Repository implementations: Data access patterns

4. FRAMEWORKS & DRIVERS LAYER:
   - Main.java: JavaFX UI application
   - SQLite database: External persistence framework
   - Alpha Vantage API: External data source
``` 