package DAL;

import DAL.database.DBConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Documentation_DB {

    private DBConnector dbConnector;

    public Documentation_DB() {
        dbConnector = new DBConnector();
    }

    public void saveDocumentation(int projectId, String layoutDrawing, String textDescription, String setupInformation) {
        String query = "INSERT INTO Documentation (projectId, layoutDrawing, textDescription, setupInformation, isPrivateCustomer, createdAt, updatedAt) VALUES (?, ?, ?, ?, 0, GETDATE(), GETDATE())";

        try (Connection connection = dbConnector.getConnected();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, projectId);
            preparedStatement.setString(2, layoutDrawing);
            preparedStatement.setString(3, textDescription);
            preparedStatement.setString(4, setupInformation);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
