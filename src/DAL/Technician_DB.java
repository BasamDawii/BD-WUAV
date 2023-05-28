package DAL;

import BE.Documentation;
import BE.Project;
import BE.Technician;
import DAL.database.DBConnector;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Technician_DB {
    private DBConnector dbConnector;

    public Technician_DB(){
        dbConnector = new DBConnector();
    }
    public Project createNewProject(String projectName, int employeeId) throws Exception {

        String insertSql = "INSERT INTO Project (projectName) VALUES (?)";
        String updateSql = "INSERT INTO Project_Employee (projectId, employeeId) VALUES (?, ?)";

        try (Connection connection = dbConnector.getConnected();
             PreparedStatement insertStmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {

            // Insert project
            insertStmt.setString(1, projectName);
            insertStmt.executeUpdate();

            // Get the generated ID from the DB
            int projectId;
            try (ResultSet rs = insertStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    projectId = rs.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve generated project ID.");
                }
            }

            // Associate project with employee
            updateStmt.setInt(1, projectId);
            updateStmt.setInt(2, employeeId);
            updateStmt.executeUpdate();

            // Create a Project object and return it
            Project project = new Project(projectId, projectName);
            return project;
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



    public List<Project> getAllProjectsByTechnicianId(int technicianId) {
        List < Project > allProjects = new ArrayList < > ();
        String sql = "SELECT Project.* " +
                "FROM Project " +
                "INNER JOIN Project_Employee ON Project.id = Project_Employee.projectId " +
                "WHERE Project_Employee.employeeId = ?;";
        try (Connection connection = dbConnector.getConnected();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, technicianId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String projectName = resultSet.getString("projectName");
                    allProjects.add(new Project(id, projectName));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as per your application's requirements
        }
        return allProjects; // Document not found with the given ID
    }

}
