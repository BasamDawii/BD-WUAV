package DAL;

import BE.Project;
import BE.Technician;
import DAL.database.DBConnector;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Technician_DB {
    private DBConnector dbConnector;

    public Technician_DB(){
        dbConnector = new DBConnector();
    }
    public Project createNewProject(String projectName) throws Exception {
        // Creates an SQL command
        String sql = "INSERT INTO Project (projectName) VALUES (?);";
        // Get connection to database
        try (Connection connection = dbConnector.getConnected()) {
            // Creates a statement
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // Bind parameters
            stmt.setString(1, projectName);

            stmt.executeUpdate();
            // Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            // Create a BarEvent object and send up the layers
            Project project = new Project(id, projectName);
            return project;
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List < Project > getAllProjects() throws SQLException {
        List < Project > allProjects = new ArrayList < > ();
        try (Connection conn = dbConnector.getConnected()) {
            String sql = "SELECT * FROM Project;";
            Statement statement = conn.createStatement();
            //Run the SQL statementgti
            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String projectName = resultSet.getString("projectName");

                    allProjects.add(new Project(id, projectName));
                }
            }
        }
        return allProjects;
    }
}
