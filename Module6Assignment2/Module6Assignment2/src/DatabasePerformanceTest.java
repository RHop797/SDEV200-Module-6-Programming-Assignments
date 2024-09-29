import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;

public class DatabasePerformanceTest extends Application {

    private Connection connection;
    private Label resultLabel;

    @Override
    public void start(Stage primaryStage) {
        // Main UI Components
        Button connectButton = new Button("Connect to Database");
        Button insertButton = new Button("Insert Records");
        resultLabel = new Label();

        // Set button actions
        connectButton.setOnAction(e -> showDbConnectionDialog());
        insertButton.setOnAction(e -> insertRecords());

        VBox mainPane = new VBox(10, connectButton, insertButton, resultLabel);
        mainPane.setPadding(new Insets(20));
        Scene scene = new Scene(mainPane, 300, 200);
        primaryStage.setTitle("Database Performance Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showDbConnectionDialog() {
        DBConnectionDialog dialog = new DBConnectionDialog();
        dialog.showAndWait().ifPresent(credentials -> {
            // Connect to the database using the provided credentials
            connection = DatabaseUtil.connectToDatabase(credentials);
        });
    }

    private void insertRecords() {
        if (connection != null) {
            try {
                long timeWithoutBatch = DatabaseUtil.insertRecords(connection, false);
                long timeWithBatch = DatabaseUtil.insertRecords(connection, true);
                resultLabel.setText("Without Batch: " + timeWithoutBatch + "ms, With Batch: " + timeWithBatch + "ms");
            } catch (Exception e) {
                e.printStackTrace();
                resultLabel.setText("Error during insertion: " + e.getMessage());
            }
        } else {
            resultLabel.setText("No database connection. Please connect first.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
