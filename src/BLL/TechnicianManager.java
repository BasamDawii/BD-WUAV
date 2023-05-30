package BLL;

import BE.Documentation;
import BE.Project;
import DAL.Technician_DB;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TechnicianManager {
    private Technician_DB technician_db;

    public TechnicianManager() {
        technician_db = new Technician_DB();
    }


    public List<Project> getAllProjects() throws SQLException {
        return technician_db.getAllProjects();
    }
    public List<Project> getAllProjectsByTechnicianId(int technicianId) throws SQLException {
        return technician_db.getAllProjectsByTechnicianId(technicianId);
    }

    public Project createNewProject(String projectName, int employeeId) throws Exception {
        return technician_db.createNewProject(projectName, employeeId);
    }

    public Documentation saveDocToDataBase(Documentation documentation) {
            return technician_db.saveDocToDataBase(documentation);
    }

    public ArrayList<Documentation> getDocumentationData() throws SQLServerException, IOException {
        return technician_db.getDocumentationData();
    }

}
