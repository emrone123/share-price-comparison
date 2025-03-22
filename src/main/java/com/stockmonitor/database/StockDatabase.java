package com.stockmonitor.database;

import com.stockmonitor.abstracts.AbstractDatabase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// SRP: This class handles only database operations.
//  Single Responsibility Principle (SRP)
// Location: StockDatabase class
// this class is responsible for database operations such as saving and fetching data by focusing on a single resbonslbilty it becomes easier to maintain
public class StockDatabase extends AbstractDatabase {
    private static final String DB_URL = "jdbc:sqlite:stock_data.db";
    private final Connection connection;

    // DIP: Connection is injected here.
    // Dependency Inversion Principle (DIP)
    // Location: Dependency injection in StockDatabase
    // The StockDatabase class gets its Connection object passed to it through the constructor.
    // This means it doesn’t directly create or manage the connection itself, which makes the class more flexible and easier to test.
    // By doing this, you can change the connection details or use a fake connection for testing without modifying the StockDatabase class itself
    //    public StockDatabase(Connection connection) {
    public StockDatabase(Connection connection) {
        this.connection = connection;
        initializeDatabase();
    }

    private void initializeDatabase() {
        // SRP: Handles database initialization.
        //
        try (Statement stmt = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS stocks (symbol TEXT PRIMARY KEY, price REAL)";
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    @Override
    public void saveData(String symbol, double price) {
        // SRP: Handles saving data.   making sure each class has one ressbonsblity
        String insertSQL = "INSERT OR REPLACE INTO stocks (symbol, price) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, symbol);
            pstmt.setDouble(2, price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    @Override
    public String fetchData(String symbol) {
        // SRP: Handles fetching data.
        String selectSQL = "SELECT price FROM stocks WHERE symbol = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, symbol);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return "Price: " + rs.getDouble("price");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }
        return "No data found for symbol: " + symbol;
    }

    // OCP: Can extend functionality without modifying existing code.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
// Open-Closed Principle (OCP)
// Effectiveness: Facilitates adding new features without altering existing code.
// Reasoning: By using interfaces and inheritance, the system can be extended with new functionality, minimizing the risk of introducing errors into existing code.