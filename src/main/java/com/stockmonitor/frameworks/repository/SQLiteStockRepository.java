package com.stockmonitor.frameworks.repository;

import com.stockmonitor.domain.StockPrice;
import com.stockmonitor.interfaces.repository.IStockRepository;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// SRP: Responsible only for database operations
public class SQLiteStockRepository implements IStockRepository {
    private static final String DB_URL = "jdbc:sqlite:stock_data.db";
    private Connection connection;
    
    public SQLiteStockRepository() {
        initializeDatabase();
    }
    
    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            String createTableSQL = "CREATE TABLE IF NOT EXISTS stocks (" +
                "symbol TEXT, " +
                "price REAL, " +
                "date TEXT, " +
                "PRIMARY KEY (symbol, date))";
            connection.createStatement().execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
    
    @Override
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
    
    @Override
    public List<StockPrice> getStockPrices(String symbol, LocalDate startDate, LocalDate endDate) {
        List<StockPrice> stockPrices = new ArrayList<>();
        String selectSQL = "SELECT * FROM stocks WHERE symbol = ? AND date BETWEEN ? AND ?";
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
    
    @Override
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
} 