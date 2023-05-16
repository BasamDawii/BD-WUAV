package DAL;

import BE.Documentation;
import BE.Project;
import BE.ProjectDetails;
import DAL.database.DBConnector;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class ProjectManager_DB {
    private DBConnector dbConnector;

    public ProjectManager_DB() {
        dbConnector = new DBConnector();
    }

    public void savePdfToDatabase(String projectName, String projectDescription, LocalDate startDate, LocalDate endDate, String customerName, byte[] pdfData) {
        String selectCustomerIdSql = "SELECT id FROM Customer WHERE name = ?";
        String insertProjectSql = "INSERT INTO Project (projectName, projectDescription, startDate, endDate, customerId) VALUES (?, ?, ?, ?, ?)";
        String insertDocumentationSql = "INSERT INTO Documentation (docId, custId, pdfFile) VALUES (?, (SELECT id FROM Customer WHERE name = ?), ?)";
        int customerId = -1;

        try (Connection connection = dbConnector.getConnected()) {
            connection.setAutoCommit(false);

            // Fetch the customer ID
            try (PreparedStatement pstmt = connection.prepareStatement(selectCustomerIdSql)) {
                pstmt.setString(1, customerName);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        customerId = rs.getInt(1);
                    } else {
                        throw new SQLException("No customer found with the name: " + customerName);
                    }
                }
            }

            // Insert project details into Project table
            try (PreparedStatement pstmt = connection.prepareStatement(insertProjectSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, projectName);
                pstmt.setString(2, projectDescription);
                pstmt.setDate(3, java.sql.Date.valueOf(startDate));
                pstmt.setDate(4, java.sql.Date.valueOf(endDate));
                pstmt.setInt(5, customerId);

                pstmt.executeUpdate();

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int projectId = generatedKeys.getInt(1);

                        // Insert PDF data into Documentation table
                        try (PreparedStatement pstmt2 = connection.prepareStatement(insertDocumentationSql)) {
                            pstmt2.setInt(1, projectId);
                            pstmt2.setString(2, customerName);
                            pstmt2.setBytes(3, pdfData);

                            pstmt2.executeUpdate();
                        }
                    } else {
                        throw new SQLException("Creating project failed, no ID obtained.");
                    }
                }
            }

            connection.commit();
        } catch (SQLException e) {
            // Handle the exception appropriately, for example, log it or rethrow it.
            throw new RuntimeException("Error while trying to save PDF to the database.", e);
        }
    }
    public void saveDocToDataBase(Documentation documentation){
        String sql = "INSERT INTO Documentation (startDate, endDate, pdfFile, projectId) VALUES (?, ?, ?, ?)";
        try (Connection connection = dbConnector.getConnected(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(documentation.getStartDate()));
            statement.setDate(2, Date.valueOf(documentation.getEndDate()));
            statement.setString(3, documentation.getPdfData());
            statement.setInt(4, documentation.getProjectId());

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
