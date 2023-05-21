package DAL;

import BE.Documentation;
import BE.Project;
import BE.ProjectDetails;
import DAL.database.DBConnector;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class ProjectManager_DB {
    private DBConnector dbConnector;

    public ProjectManager_DB() {
        dbConnector = new DBConnector();
    }


    public void saveDocToDataBase(Documentation documentation){
        String sql = "INSERT INTO Documentation (docName, startDate, endDate, customerName, pdfFile, projectId) VALUES (?, ?, ?, ?,?,?)";
        try (Connection connection = dbConnector.getConnected(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, documentation.getDocName());
            statement.setDate(2, Date.valueOf(documentation.getStartDate()));
            statement.setDate(3, Date.valueOf(documentation.getEndDate()));
            statement.setString(4, documentation.getCostumerName());
            statement.setString(5, documentation.getPdfData());
            statement.setInt(6, documentation.getProjectId());

            statement.executeUpdate();

    } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public ArrayList<ProjectDetails> getData() throws SQLServerException, IOException {
        ArrayList<ProjectDetails> projectDetailsList = new ArrayList<>();

        String query = "SELECT * FROM Documentation";

        try (Connection connection = dbConnector.getConnected();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String projectName = rs.getString("docName");
                LocalDate startDate = rs.getDate("startDate").toLocalDate();
                LocalDate endDate = rs.getDate("endDate").toLocalDate();
                String customerName = rs.getString("customerName");
                System.out.println(projectName);
                ProjectDetails projectDetails = new ProjectDetails(projectName, startDate, endDate, customerName);
                projectDetailsList.add(projectDetails);
            }
        } catch (SQLException e) {
            // Handle the exception appropriately, for example, log it or rethrow it.
            throw new RuntimeException("Error while trying to load project data.", e);
        }

        return projectDetailsList;
    }


    public ArrayList<String> getTechnicianNames() {
        ArrayList<String> arrayList = new ArrayList<>();
        String query = "SELECT username FROM Employee WHERE employeeType = 'Technician'";
        try (Connection connection = dbConnector.getConnected(); Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String username = rs.getString("username");
                arrayList.add(username);
            }
            return arrayList;
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving data.", e);
        }
    }
    public ArrayList<String> getProjectNames() {
        ArrayList<String> arrayList = new ArrayList<>();
        String query = "SELECT projectName FROM Project";
        try (Connection connection = dbConnector.getConnected(); Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String projectName = rs.getString("projectName");
                arrayList.add(projectName);
            }
            return arrayList;
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving data.", e);
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
