package DAL;

import BE.Project;
import BE.Technician;
import DAL.database.DBConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProjectManager_DB{
    private DBConnector dbConnector;

    public ProjectManager_DB() {
        dbConnector = new DBConnector();
    }

    public void deleteDocumentation(int documentId) {
        String sql = "DELETE FROM Documentation WHERE id = ?";
        try (Connection connection = dbConnector.getConnected(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, documentId);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting documentation failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while trying to delete documentation.", e);
        }
    }

    public List< Project > getAllProjects() throws SQLException {
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
    public List<Technician> getAllTechnician() throws SQLException {
        List < Technician > allTechnician = new ArrayList < > ();
        try (Connection conn = dbConnector.getConnected()) {
            String sql = "SELECT * FROM Employee WHERE EmployeeType = 'Technician';";
            Statement statement = conn.createStatement();
            //Run the SQL statementgti
            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");

                    allTechnician.add(new Technician(id,username,password));
                }
            }
        }
        return allTechnician;
    }

    public boolean addTechnicianToProject(int pid, int tid){
        String query = "Insert into Project_Employee(projectId, employeeId) VALUES(?,?)";
        try (Connection connection = dbConnector.getConnected(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1,pid);
            statement.setInt(2,tid);
            int row = statement.executeUpdate();
            if (row>0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            // Handle the exception appropriately, for example, log it or rethrow it.
            throw new RuntimeException("Error while Inserting Data.", e);
        }
    }
}
