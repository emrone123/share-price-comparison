package com.stockmonitor.database;

import com.stockmonitor.abstracts.AbstractDatabase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StockDatabase extends AbstractDatabase {
    private static final String DB_URL = "jdbc:sqlite:stock_data.db";

    public StockDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS stocks (symbol TEXT PRIMARY KEY, price REAL)";
                conn.createStatement().execute(createTableSQL);
            }
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    @Override
    public void saveData(String symbol, double price) {
        String insertSQL = "INSERT OR REPLACE INTO stocks (symbol, price) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, symbol);
            pstmt.setDouble(2, price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    @Override
    public String fetchData(String symbol) {
        String selectSQL = "SELECT price FROM stocks WHERE symbol = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
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
} 