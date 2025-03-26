package com.stockmonitor.interfaces;

import java.util.List;

/**
 * Interface for sending notifications to users as shown in the diagram
 */
public interface INotificationSystem {
    /**
     * Send a notification to a user
     * @param userId The user ID
     * @param message The notification message
     */
    void sendNotification(String userId, String message);
    
    /**
     * Send a notification about a price alert
     * @param alert The alert that triggered the notification
     */
    void sendAlertNotification(IAlertSystem.Alert alert);
    
    /**
     * Get a list of available notification channels
     * @return List of notification channel names
     */
    List<String> getAvailableChannels();
    
    /**
     * Check the status of a notification
     * @param notificationId The ID of the notification
     * @return The status of the notification
     */
    NotificationStatus checkStatus(String notificationId);
    
    /**
     * Enumeration of possible notification statuses
     */
    enum NotificationStatus {
        PENDING,
        DELIVERED,
        READ,
        FAILED
    }
} 