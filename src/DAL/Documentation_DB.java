package DAL;

import DAL.database.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> searchProjectsByName(String projectName) throws SQLException {
        String selectProjectSQL = "SELECT projectName FROM Project WHERE projectName LIKE ?";
        List<String> projectNames = new ArrayList<>();

        try (Connection connection = dbConnector.getConnected();
             PreparedStatement preparedStatement = connection.prepareStatement(selectProjectSQL)) {

            preparedStatement.setString(1, "%" + projectName + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    projectNames.add(resultSet.getString("projectName"));
                }
            }
        }

        return projectNames;
    }

}
