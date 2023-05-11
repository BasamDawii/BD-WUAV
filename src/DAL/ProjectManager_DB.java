package DAL;

import BE.Project;
import BE.ProjectDetails;
import DAL.database.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class ProjectManager_DB {
    private DBConnector dbConnector;

    public ProjectManager_DB() {
        dbConnector = new DBConnector();
    }

    public void deletePDF(Project project) {
        String sql = "DELETE FROM Projects WHERE id = ?";

        try (Connection connection = dbConnector.getConnected(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, project.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting project failed, no rows affected.");
            }
        } catch (SQLException e) {
            // Handle the exception appropriately, for example, log it or rethrow it.
            throw new RuntimeException("Error while trying to delete project.", e);
        }
    }
    public ArrayList<ProjectDetails> getData(){
        ArrayList<ProjectDetails> arrayList = new ArrayList<>();
        String query = "SELECT p.projectName, p.projectDescription, p.startDate, p.endDate, c.name " +
                "FROM Project p " +
                "INNER JOIN Customer c ON p.customerId = c.id";
        try (Connection connection = dbConnector.getConnected(); Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String projectName = rs.getString("projectName");
                String projectDesc = rs.getString("projectDescription");
                Date startDate = rs.getDate("startDate");
                Date endDate = rs.getDate("endDate");
                String customerName = rs.getString("name");
                ProjectDetails projectDetails = new ProjectDetails(projectName, projectDesc, startDate, endDate, customerName);
                arrayList.add(projectDetails);
            }
            return arrayList;
        } catch (SQLException e) {
            // Handle the exception appropriately, for example, log it or rethrow it.
            throw new RuntimeException("Error while retrieving Data.", e);
        }
    }
    public ArrayList<Integer> getTechniciansId(){
        ArrayList<Integer> arrayList = new ArrayList<>();
        String query = "SELECT id From Employee where employeeType = 'Technician'";
        try (Connection connection = dbConnector.getConnected(); Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                arrayList.add(id);
            }
            return arrayList;
        } catch (SQLException e) {
            // Handle the exception appropriately, for example, log it or rethrow it.
            throw new RuntimeException("Error while retrieving Data.", e);
        }
    }
    public ArrayList<Integer> getProjectId(){
        ArrayList<Integer> arrayList = new ArrayList<>();
        String query = "SELECT id From Project";
        try (Connection connection = dbConnector.getConnected(); Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                arrayList.add(id);
            }
            return arrayList;
        } catch (SQLException e) {
            // Handle the exception appropriately, for example, log it or rethrow it.
            throw new RuntimeException("Error while retrieving Data.", e);
        }
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
