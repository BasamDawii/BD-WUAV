package DAL;

import BE.Documentation;
import BE.Project;
import DAL.database.DBConnector;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class ProjectManager_DB{
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
            statement.setString(4, documentation.getCustomerName());
            statement.setString(5, documentation.getPdfData());
            statement.setInt(6, documentation.getProjectId());

            statement.executeUpdate();

    } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public int getProjectIdByName(String projectName) {
        String sql = "SELECT id FROM Project WHERE projectName = ?";
        try (Connection connection = dbConnector.getConnected();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, projectName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as per your application's requirements
        }
        return -1; // Project not found with the given name
    }

    public Documentation getDocumentById(int documentId) {
        String sql = "SELECT * FROM Documentation WHERE id = ?";
        try (Connection connection = dbConnector.getConnected();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, documentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String documentationName = resultSet.getString("docName");
                    LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
                    LocalDate endDate = resultSet.getDate("endDate").toLocalDate();
                    String customerName = resultSet.getString("customerName");
                    String pdfData = resultSet.getString("pdfFile");
                    int projectId = resultSet.getInt("projectId");

                    return new Documentation(id, documentationName, startDate, endDate, customerName, pdfData, projectId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as per your application's requirements
        }
        return null; // Document not found with the given ID
    }

    public ArrayList<Documentation> getData() throws SQLServerException, IOException {
        ArrayList<Documentation> documentations = new ArrayList<>();

        String query = "SELECT * FROM Documentation";

        try (Connection connection = dbConnector.getConnected();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String docName = rs.getString("docName");
                LocalDate startDate = rs.getDate("startDate").toLocalDate();
                LocalDate endDate = rs.getDate("endDate").toLocalDate();
                String customerName = rs.getString("customerName");
                String pdfData = rs.getString("pdfFile");
                int projectId = rs.getInt("projectId");

                System.out.println(docName);
                Documentation documentation = new Documentation(id, docName, startDate, endDate, customerName, pdfData, projectId);
                documentations.add(documentation);
            }
        } catch (SQLException e) {
            // Handle the exception appropriately, for example, log it or rethrow it.
            throw new RuntimeException("Error while trying to load project data.", e);
        }

        return documentations;
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
