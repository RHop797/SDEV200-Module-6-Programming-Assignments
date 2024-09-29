import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class DBConnectionDialog extends Dialog<DatabaseCredentials> {
    private TextField dbUrlField;
    private TextField usernameField;
    private PasswordField passwordField;

    public DBConnectionDialog() {
        setTitle("Database Connection");

        // Dialog Components
        Label urlLabel = new Label("Database URL:");
        dbUrlField = new TextField("jdbc:mysql://localhost:3306/temp");
        Label usernameLabel = new Label("Username:");
        usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();

        VBox dialogPane = new VBox(10, urlLabel, dbUrlField, usernameLabel, usernameField, passwordLabel, passwordField);
        getDialogPane().setContent(dialogPane);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Result converter to capture user input
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new DatabaseCredentials(dbUrlField.getText(), usernameField.getText(), passwordField.getText());
            }
            return null;
        });
    }
}
