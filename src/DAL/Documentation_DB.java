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

    public void saveDocumentation(int projectId, byte[] pdfData) {
        String query = "INSERT INTO Documentation (projectId, pdfData, isPrivateCustomer, createdAt, updatedAt) VALUES (?, ?, 0, GETDATE(), GETDATE())";

        try (Connection connection = dbConnector.getConnected();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, projectId);
            preparedStatement.setBytes(2, pdfData);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
