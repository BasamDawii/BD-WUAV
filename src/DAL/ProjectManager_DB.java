package DAL;

import BE.Project;
import DAL.database.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProjectManager_DB {
    private DBConnector dbConnector;

    public ProjectManager_DB(){dbConnector = new DBConnector();
    }

    public void deletePDF ( Project event){

        String sql = "DELETE FROM Event WHERE Id = ?";
        try (Connection connection = dbConnector.getConnected()){

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, event.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
