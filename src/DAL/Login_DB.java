package DAL;

import BE.Employee;
import BE.ProjectManager;
import BE.Salesperson;
import BE.Technician;
import DAL.database.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Login_DB {
    private DBConnector dbConnector;

    public Login_DB() {
        dbConnector = new DBConnector();
    }

    public Employee employeeLogin(String username, String password) {
        String sql = "SELECT * FROM Employee WHERE username = ? AND password = ?";

        try (Connection connection = dbConnector.getConnected(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                UUID id = UUID.fromString(result.getString("id"));
                String employeeType = result.getString("employeeType");

                switch (employeeType) {
                    case "Technician":
                        return new Technician(id, username, password);
                    case "ProjectManager":
                        return new ProjectManager(id, username, password);
                    case "Salesperson":
                        return new Salesperson(id, username, password);
                    default:
                        throw new IllegalStateException("Invalid employee type: " + employeeType);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            // Handle the exception appropriately, for example, log it or rethrow it.
            throw new RuntimeException("Error while trying to login.", e);
        }
    }
}