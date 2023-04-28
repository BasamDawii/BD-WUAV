package DAL;

import BE.ProjectManager;
import BE.Salesperson;
import BE.Technician;
import BE.User;
import DAL.database.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public User login(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection connection = new DBConnector().getConnected();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String role = resultSet.getString("role");
                    int id = resultSet.getInt("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    switch (role) {
                        case "Technician":
                            return new Technician(id, firstName, lastName, email, password);
                        case "Project Manager":
                            return new ProjectManager(id, firstName, lastName, email, password);
                        case "Salesperson":
                            return new Salesperson(id, firstName, lastName, email, password);
                        default:
                            return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
