package com.stockmonitor.database;

import com.stockmonitor.domain.StockPrice;
import com.stockmonitor.interfaces.IDataStorage;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of IDataStorage using SQLite
 * 
 * CLEAN ARCHITECTURE PRINCIPLE: Frameworks & Drivers Layer
 * This class belongs to the outermost layer that deals with frameworks and tools.
 * It implements the IDataStorage interface from the interfaces layer but contains
 * database-specific code that shouldn't leak into inner layers.
 * 
 * CLEAN ARCHITECTURE PRINCIPLE: Dependency Rule
 * This outer layer depends on the inner domain layer (StockPrice) and interface layer (IDataStorage),
 * but those inner layers don't depend on this class, adhering to the dependency rule.
 */
public class SQLiteDataStorage implements IDataStorage {
    // CLEAN ARCHITECTURE PRINCIPLE: Infrastructure Details Isolation
    // Database-specific details are contained here and don't leak to domain layer
    private static final String DB_URL = "jdbc:sqlite:stock_data.db";
    private Connection connection;
    
    public SQLiteDataStorage() {
        try {
            initializeDatabase();
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
    
    /**
     * Initialize the database connection and tables
     * 
     * CLEAN ARCHITECTURE PRINCIPLE: Separation of Concerns
     * Database initialization logic is separated from business logic.
     */
    private void initializeDatabase() throws SQLException {
        try {
            // Explicitly load the SQLite driver
            Class.forName("org.sqlite.JDBC");
            
            connection = DriverManager.getConnection(DB_URL);
            
            // Create stocks table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS stocks (" +
                "symbol TEXT, " +
                "price REAL, " +
                "date TEXT, " +
                "PRIMARY KEY (symbol, date))";
            connection.createStatement().execute(createTableSQL);
            
            // Create archive table if it doesn't exist
            String createArchiveTableSQL = "CREATE TABLE IF NOT EXISTS archived_stocks (" +
                "symbol TEXT, " +
                "price REAL, " +
                "date TEXT, " +
                "archived_date TEXT, " +
                "PRIMARY KEY (symbol, date))";
            connection.createStatement().execute(createArchiveTableSQL);
            
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found: " + e.getMessage());
            throw new SQLException("Driver not found", e);
        }
    }
    
    /**
     * CLEAN ARCHITECTURE PRINCIPLE: Interface Adapter
     * This method adapts the domain entity (StockPrice) to the persistence format
     * required by the database, serving as a bridge between layers.
     */
    @Override
    public void saveStockData(StockPrice data) {
        String insertSQL = "INSERT OR REPLACE INTO stocks (symbol, price, date) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, data.getSymbol());
            pstmt.setDouble(2, data.getPrice());
            pstmt.setString(3, data.getDate().toString());
            pstmt.executeUpdate();
            System.out.println("Saved data for " + data.getSymbol() + " on " + data.getDate());
        } catch (SQLException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
    
    /**
     * CLEAN ARCHITECTURE PRINCIPLE: Interface Adapter
     * This method converts database records back to domain entities (StockPrice),
     * maintaining the separation between the domain and the database.
     */
    @Override
    public List<StockPrice> retrieveStockData(String symbol, LocalDate startDate) {
        List<StockPrice> stockPrices = new ArrayList<>();
        String selectSQL = "SELECT * FROM stocks WHERE symbol = ? AND date >= ? ORDER BY date";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, symbol);
            pstmt.setString(2, startDate.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                stockPrices.add(new StockPrice(
                    rs.getString("symbol"),
                    rs.getDouble("price"),
                    LocalDate.parse(rs.getString("date"))
                ));
            }
            System.out.println("Retrieved " + stockPrices.size() + " records for " + symbol);
        } catch (SQLException e) {
            System.err.println("Error retrieving data: " + e.getMessage());
        }
        return stockPrices;
    }
    
    @Override
    public void archiveData(LocalDate olderThan) {
        // CLEAN ARCHITECTURE PRINCIPLE: Use Case Implementation
        // This complex business logic for archiving data is implemented here but
        // defined by the interface in the inner layer
        try {
            // Start a transaction
            connection.setAutoCommit(false);
            
            // Move data to archive table
            String moveSQL = "INSERT INTO archived_stocks (symbol, price, date, archived_date) " +
                             "SELECT symbol, price, date, ? FROM stocks WHERE date < ?";
            try (PreparedStatement moveStmt = connection.prepareStatement(moveSQL)) {
                moveStmt.setString(1, LocalDate.now().toString());
                moveStmt.setString(2, olderThan.toString());
                int rowsMoved = moveStmt.executeUpdate();
                
                // Delete moved data from main table
                String deleteSQL = "DELETE FROM stocks WHERE date < ?";
                try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL)) {
                    deleteStmt.setString(1, olderThan.toString());
                    int rowsDeleted = deleteStmt.executeUpdate();
                    
                    if (rowsMoved == rowsDeleted) {
                        connection.commit();
                        System.out.println("Archived " + rowsMoved + " records older than " + olderThan);
                    } else {
                        connection.rollback();
                        System.err.println("Archive operation failed: moved " + rowsMoved + " but deleted " + rowsDeleted);
                    }
                }
            }
            
            // Reset auto-commit
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Error during rollback: " + ex.getMessage());
            }
            System.err.println("Error archiving data: " + e.getMessage());
        }
    }
    
    @Override
    public Map<String, Object> getStorageStats() {
        Map<String, Object> stats = new HashMap<>();
        
        try {
            // Count total records
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM stocks");
                if (rs.next()) {
                    stats.put("totalRecords", rs.getInt(1));
                }
            }
            
            // Count unique symbols
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT COUNT(DISTINCT symbol) FROM stocks");
                if (rs.next()) {
                    stats.put("uniqueSymbols", rs.getInt(1));
                }
            }
            
            // Count archived records
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM archived_stocks");
                if (rs.next()) {
                    stats.put("archivedRecords", rs.getInt(1));
                }
            }
            
            // Get earliest and latest dates
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT MIN(date), MAX(date) FROM stocks");
                if (rs.next()) {
                    String minDate = rs.getString(1);
                    String maxDate = rs.getString(2);
                    if (minDate != null && maxDate != null) {
                        stats.put("earliestDate", minDate);
                        stats.put("latestDate", maxDate);
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting storage stats: " + e.getMessage());
        }
        
        return stats;
    }
    
    // CLEAN ARCHITECTURE PRINCIPLE: Resource Management
    // Clean up resources properly to maintain system integrity
    @Override
    protected void finalize() throws Throwable {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        } finally {
            super.finalize();
        }
    }
} 