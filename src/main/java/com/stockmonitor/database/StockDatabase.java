package com.stockmonitor.database;

import com.stockmonitor.domain.StockPrice;
import com.stockmonitor.interfaces.IDataBase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// SRP: This class handles only database operations.
//  Single Responsibility Principle (SRP)
// Location: StockDatabase class
// this class is responsible for database operations such as saving and fetching data by focusing on a single resbonslbilty it becomes easier to maintain
public class StockDatabase implements IDataBase {
    private static final String DB_URL = "jdbc:sqlite:stock_data.db";
    private Connection connection;

    // DIP: Connection is injected here.
    // Dependency Inversion Principle (DIP)
    // Location: Dependency injection in StockDatabase
    // The StockDatabase class gets its Connection object passed to it through the constructor.
    // This means it doesn't directly create or manage the connection itself, which makes the class more flexible and easier to test.
    // By doing this, you can change the connection details or use a fake connection for testing without modifying the StockDatabase class itself
    //    public StockDatabase(Connection connection) {
    public StockDatabase() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        // SRP: Handles database initialization.
        //
        try {
            // Explicitly load the SQLite driver
            Class.forName("org.sqlite.JDBC");
            
            connection = DriverManager.getConnection(DB_URL);
            String createTableSQL = "CREATE TABLE IF NOT EXISTS stocks (" +
                "symbol TEXT, " +
                "price REAL, " +
                "date TEXT, " +
                "PRIMARY KEY (symbol, date))";
            connection.createStatement().execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found: " + e.getMessage());
        }
    }

    @Override
    public void saveData(String symbol, double price) {
        saveStockPrice(new StockPrice(symbol, price, LocalDate.now()));
    }

    @Override
    public String fetchData(String symbol) {
        List<StockPrice> prices = getStockPrices(symbol, LocalDate.now().minusMonths(1), LocalDate.now());
        if (prices.isEmpty()) {
            return "No data found for symbol: " + symbol;
        }
        return "Latest price for " + symbol + ": " + prices.get(0).getPrice();
    }

    public void saveStockPrice(StockPrice stockPrice) {
        String insertSQL = "INSERT OR REPLACE INTO stocks (symbol, price, date) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, stockPrice.getSymbol());
            pstmt.setDouble(2, stockPrice.getPrice());
            pstmt.setString(3, stockPrice.getDate().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    public List<StockPrice> getStockPrices(String symbol, LocalDate startDate, LocalDate endDate) {
        List<StockPrice> stockPrices = new ArrayList<>();
        String selectSQL = "SELECT * FROM stocks WHERE symbol = ? AND date BETWEEN ? AND ? ORDER BY date DESC";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, symbol);
            pstmt.setString(2, startDate.toString());
            pstmt.setString(3, endDate.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                StockPrice stockPrice = new StockPrice(
                    rs.getString("symbol"),
                    rs.getDouble("price"),
                    LocalDate.parse(rs.getString("date"))
                );
                stockPrices.add(stockPrice);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }
        return stockPrices;
    }

    public boolean hasStockData(String symbol, LocalDate startDate, LocalDate endDate) {
        String countSQL = "SELECT COUNT(*) FROM stocks WHERE symbol = ? AND date BETWEEN ? AND ?";
        try (PreparedStatement pstmt = connection.prepareStatement(countSQL)) {
            pstmt.setString(1, symbol);
            pstmt.setString(2, startDate.toString());
            pstmt.setString(3, endDate.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking data: " + e.getMessage());
        }
        return false;
    }

    // OCP: Can extend functionality without modifying existing code.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
// Open-Closed Principle (OCP)
// Effectiveness: Facilitates adding new features without altering existing code.
// Reasoning: By using interfaces and inheritance, the system can be extended with new functionality, minimizing the risk of introducing errors into existing code.