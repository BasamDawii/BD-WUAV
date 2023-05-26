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
        return projectManagerManager.loadData();
    }
    public boolean addEmpProject(int pid, int tid){
        return projectManagerManager.addEmpProject(pid,tid);
    }
    public boolean deleteDocumentation(Documentation documentation) {
        return projectManagerManager.deleteDocumentation(documentation);
    }
}
