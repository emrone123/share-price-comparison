package com.stockmonitor.interfaces;

/**
 * Database interface
 * Manages data persistence operations
 */
public interface IDataBase {
    /**
     * Stores data in the database
     * @param data The data to be stored
     */
    void saveData(String data);
    
    /**
     * Retrieves data from the database
     * @return The retrieved data
     */
    String fetchData(); 
}  
