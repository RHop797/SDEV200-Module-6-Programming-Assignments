import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffDatabaseGUI extends Application {
    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/staff";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    // UI Elements
    private TextField tfID = new TextField();
    private TextField tfLastName = new TextField();
    private TextField tfFirstName = new TextField();
    private TextField tfMI = new TextField();
    private TextField tfAddress = new TextField();
    private TextField tfCity = new TextField();
    private TextField tfState = new TextField();
    private TextField tfTelephone = new TextField();
    private TextField tfEmail = new TextField();

    @Override
    public void start(Stage primaryStage) {
        // Create the UI
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(5);
        pane.setVgap(5);
        
        pane.add(new Label("ID:"), 0, 0);
        pane.add(tfID, 1, 0);
        pane.add(new Label("Last Name:"), 0, 1);
        pane.add(tfLastName, 1, 1);
        pane.add(new Label("First Name:"), 0, 2);
        pane.add(tfFirstName, 1, 2);
        pane.add(new Label("MI:"), 0, 3);
        pane.add(tfMI, 1, 3);
        pane.add(new Label("Address:"), 0, 4);
        pane.add(tfAddress, 1, 4);
        pane.add(new Label("City:"), 0, 5);
        pane.add(tfCity, 1, 5);
        pane.add(new Label("State:"), 0, 6);
        pane.add(tfState, 1, 6);
        pane.add(new Label("Telephone:"), 0, 7);
        pane.add(tfTelephone, 1, 7);
        pane.add(new Label("Email:"), 0, 8);
        pane.add(tfEmail, 1, 8);

        Button btnView = new Button("View");
        Button btnInsert = new Button("Insert");
        Button btnUpdate = new Button("Update");
        pane.add(btnView, 0, 9);
        pane.add(btnInsert, 1, 9);
        pane.add(btnUpdate, 2, 9);

        // Set button actions
        btnView.setOnAction(e -> viewRecord());
        btnInsert.setOnAction(e -> insertRecord());
        btnUpdate.setOnAction(e -> updateRecord());

        // Create and set the scene
        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setTitle("Staff Database App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void viewRecord() {
        String id = tfID.getText();

        String query = "SELECT * FROM staff WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                tfLastName.setText(rs.getString("lastName"));
                tfFirstName.setText(rs.getString("firstName"));
                tfMI.setText(rs.getString("mi"));
                tfAddress.setText(rs.getString("address"));
                tfCity.setText(rs.getString("city"));
                tfState.setText(rs.getString("state"));
                tfTelephone.setText(rs.getString("telephone"));
                tfEmail.setText(rs.getString("email"));
            } else {
                showAlert("No record found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertRecord() {
        String query = "INSERT INTO staff (id, lastName, firstName, mi, address, city, state, telephone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, tfID.getText());
            pstmt.setString(2, tfLastName.getText());
            pstmt.setString(3, tfFirstName.getText());
            pstmt.setString(4, tfMI.getText());
            pstmt.setString(5, tfAddress.getText());
            pstmt.setString(6, tfCity.getText());
            pstmt.setString(7, tfState.getText());
            pstmt.setString(8, tfTelephone.getText());
            pstmt.setString(9, tfEmail.getText());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Record inserted successfully.");
            } else {
                showAlert("Insert operation failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateRecord() {
        String query = "UPDATE staff SET lastName = ?, firstName = ?, mi = ?, address = ?, city = ?, state = ?, telephone = ?, email = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, tfLastName.getText());
            pstmt.setString(2, tfFirstName.getText());
            pstmt.setString(3, tfMI.getText());
            pstmt.setString(4, tfAddress.getText());
            pstmt.setString(5, tfCity.getText());
            pstmt.setString(6, tfState.getText());
            pstmt.setString(7, tfTelephone.getText());
            pstmt.setString(8, tfEmail.getText());
            pstmt.setString(9, tfID.getText());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Record updated successfully.");
            } else {
                showAlert("Update operation failed. No record with the specified ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
