package BLL;

import BE.Project;

import DAL.Technician_DB;

import java.sql.SQLException;
import java.util.List;

public class TechnicianManager {
    private Technician_DB technician_db;

    public TechnicianManager() {
        technician_db = new Technician_DB();
    }


    public List<Project> getAllProjects() throws SQLException {
        return technician_db.getAllProjects();
    }

    public Project createNewProject(String projectName) throws Exception {
        return technician_db.createNewProject(projectName);
    }

}
