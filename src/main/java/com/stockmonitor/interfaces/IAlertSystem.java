package com.stockmonitor.interfaces;

import java.util.List;

/**
 * Interface for setting and managing price alerts as shown in the diagram
 */
public interface IAlertSystem {
    /**
     * Set a price alert for a specific stock
     * @param symbol The stock symbol
     * @param threshold The price threshold that triggers the alert
     * @return The ID of the created alert
     */
    String setPriceAlert(String symbol, double threshold);
    
    /**
     * Get all active alerts
     * @return List of active alerts
     */
    List<Alert> getActiveAlerts();
    
    /**
     * Remove an alert using its ID
     * @param alertId The ID of the alert to remove
     */
    void removeAlert(String alertId);
    
    /**
     * Configure notification preferences for alerts
     */
    void configureNotificationPreferences();
    
    /**
     * Alert data structure for storing alert information
     */
    class Alert {
        private String id;
        private String symbol;
        private double threshold;
        private boolean isActive;
        
        public Alert(String id, String symbol, double threshold) {
            this.id = id;
            this.symbol = symbol;
            this.threshold = threshold;
            this.isActive = true;
        }
        
        public String getId() { return id; }
        public String getSymbol() { return symbol; }
        public double getThreshold() { return threshold; }
        public boolean isActive() { return isActive; }
        public void setActive(boolean active) { isActive = active; }
    }
} 