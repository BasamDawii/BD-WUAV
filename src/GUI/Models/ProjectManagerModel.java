package GUI.Models;

import BE.Documentation;
import DAL.ProjectManager_DB;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.util.ArrayList;

public class ProjectManagerModel {


    public ArrayList<Documentation> loadData() throws SQLServerException, IOException {
        ProjectManager_DB projectManager_db = new ProjectManager_DB();
        return projectManager_db.getData();
    }
    public ArrayList<String> loadProjectNames() {
        ProjectManager_DB projectManager_db = new ProjectManager_DB();
        return projectManager_db.getProjectNames();
    }

    public ArrayList<String> loadTechnicianNames() {
        ProjectManager_DB projectManager_db = new ProjectManager_DB();
        return projectManager_db.getTechnicianNames();
    }
    public boolean addEmpProject(int pid, int tid){
        ProjectManager_DB projectManager_db = new ProjectManager_DB();
        return projectManager_db.addTechnicianToProject(pid,tid);
    }
}
