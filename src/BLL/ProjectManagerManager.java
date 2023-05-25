package BLL;

import BE.Project;
import BE.Technician;
import DAL.ProjectManager_DB;

import java.sql.SQLException;
import java.util.List;

public class ProjectManagerManager {
    public ProjectManagerManager(){
        projectManager_db = new ProjectManager_DB();
    }
    ProjectManager_DB projectManager_db;
    public List<Project> getAllProjects() throws SQLException {
        return projectManager_db.getAllProjects();
    }
    public List<Technician> getAllTechnicians() throws SQLException {
        return projectManager_db.getAllTechnician();
    }
}
