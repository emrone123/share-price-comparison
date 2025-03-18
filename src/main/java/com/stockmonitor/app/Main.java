package com.stockmonitor.app;

import com.stockmonitor.core.StockMonitorApplication;
import com.stockmonitor.interfaces.*;
import com.stockmonitor.collectors.DataCollector;
import com.stockmonitor.database.DataBase;
import com.stockmonitor.logging.Logging;
import com.stockmonitor.display.DisplayCharts;
import com.stockmonitor.monitoring.MonitoringRing;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private StockMonitorApplication stockApp;

    @Override
    public void start(Stage primaryStage) {
        // Initialize components
        IDataCollector dataCollector = new DataCollector();
        IDataBase database = new DataBase();
        ILoggingData logger = new Logging();
        IChartDisplay chartDisplay = new DisplayCharts();
        IStockMonitoring stockMonitoring = new MonitoringRing();

        // Initialize the stock monitoring application
        stockApp = new StockMonitorApplication(dataCollector, database, logger, chartDisplay, stockMonitoring);
        
        // Create the main layout
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20; -fx-background-color: #f0f0f0;");
        
        // Add a title
        Label titleLabel = new Label("Stock Monitor");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        root.getChildren().add(titleLabel);
        
        // Connect the stock app to the UI
        stockApp.setRoot(root);
        
        // Create and set the scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Stock Monitor Application");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Run the stock monitoring application
        stockApp.run();
    }

    public static void main(String[] args) {
        launch(args);
    }
}