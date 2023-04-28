package DAL;

import BE.Technician;
import DAL.database.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login_DB {
    private DBConnector dbConnector;

    public Login_DB() {
        dbConnector = new DBConnector();
    }

    public Technician technicianLogin(String username, String password) {
        String sql = "SELECT * FROM Technician WHERE username = ? AND password = ?";

        try (Connection connection = dbConnector.getConnected(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int id = result.getInt("id");
                username = result.getString("username");
                password = result.getString("password");

                return new Technician(id, username, password);
            } else {
                return null;
            }
        } catch (SQLException e) {
            // Handle the exception appropriately, for example, log it or rethrow it.
            throw new RuntimeException("Error while trying to login.", e);
        }
    }
}
