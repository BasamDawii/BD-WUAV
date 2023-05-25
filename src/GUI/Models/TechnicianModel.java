package GUI.Models;

import BE.Project;
import BLL.TechnicianManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class TechnicianModel {
    private TechnicianManager technicianManager;
    private ObservableList<Project> allProjects;

    public TechnicianModel() throws SQLException {
        technicianManager = new TechnicianManager();
        allProjects = FXCollections.observableArrayList();
        allProjects.addAll(technicianManager.getAllProjects());
        //allProjects = FXCollections.observableArrayList();
    }
    public void createNewProject(String projectName) throws Exception {
        Project project = technicianManager.createNewProject(projectName);
        allProjects.add(project);
        refreshEventListView();

    }

    public void refreshEventListView() throws Exception {
        //Update the listview
        allProjects.clear();
        allProjects.setAll(technicianManager.getAllProjects());
    }

    public ObservableList<Project> getAllProjects(){
        return allProjects;
    }

    public ObservableList<Project> getAllProjectsByTechnicianId(int technicianId) throws SQLException {
        return FXCollections.observableArrayList(technicianManager.getAllProjectsByTechnicianId(technicianId));
    }

}
