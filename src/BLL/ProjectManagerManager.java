package BLL;

import BE.Documentation;
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


    public boolean addEmpProject(int pid, int tid){
        ProjectManager_DB projectManager_db = new ProjectManager_DB();
        return projectManager_db.addTechnicianToProject(pid,tid);
    }

    public List<Technician> getAllTechnicians() throws SQLException {
        return projectManager_db.getAllTechnician();
    }

    public boolean deleteDocumentation(Documentation documentation) {
        try {
            // Delete the documentation from the database using the documentation ID
            new ProjectManager_DB().deleteDocumentation(documentation.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
