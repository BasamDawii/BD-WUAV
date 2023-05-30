package GUI.Models;

import BE.Documentation;
import BE.Project;
import BE.Technician;
import BLL.ProjectManagerManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

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

    public boolean addEmpProject(int pid, int tid){
        return projectManagerManager.addEmpProject(pid,tid);
    }
    public boolean deleteDocumentation(Documentation documentation) {
        return projectManagerManager.deleteDocumentation(documentation);
    }
}
