import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class DatabaseUtil {

    public static Connection connectToDatabase(DatabaseCredentials credentials) {
        try {
            return DriverManager.getConnection(credentials.getUrl(), credentials.getUsername(), credentials.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to connect to the database: " + e.getMessage());
            alert.show();
            return null;
        }
    }

    public static long insertRecords(Connection connection, boolean useBatch) throws SQLException {
        String sql = "INSERT INTO Temp (num1, num2, num3) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        Random random = new Random();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            statement.setDouble(1, random.nextDouble());
            statement.setDouble(2, random.nextDouble());
            statement.setDouble(3, random.nextDouble());

            if (useBatch) {
                statement.addBatch();
            } else {
                statement.executeUpdate();
            }
        }

        if (useBatch) {
            statement.executeBatch();
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
