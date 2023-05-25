package GUI.Models;

import BE.Documentation;
import BE.Project;
import BE.Technician;
import BLL.ProjectManagerManager;
import BLL.TechnicianManager;
import DAL.ProjectManager_DB;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectManagerModel {
    private ProjectManagerManager projectManagerManager;
    private ObservableList<Project> allProjects;
    private ObservableList<Technician> allTechnicians;
    public ProjectManagerModel() throws SQLException {
        projectManagerManager = new ProjectManagerManager();
        allProjects = FXCollections.observableArrayList();
        allProjects.addAll(projectManagerManager.getAllProjects());

        allTechnicians = FXCollections.observableArrayList();
        allTechnicians.addAll(projectManagerManager.getAllTechnicians());
        //allProjects = FXCollections.observableArrayList();
    }
    public ObservableList<Project> getAllProjects(){
        return allProjects;
    }
    public ObservableList<Technician> getAllTechnicians(){
        return allTechnicians;
    }
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
    public boolean deleteDocumentation(Documentation documentation) {
        // Implement the logic to delete the documentation from the database
        // You can use your existing deletePDF method in the ProjectManager_DB class

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
