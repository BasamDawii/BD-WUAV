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
    public Project createNewProject(String projectName, int employeeId) throws Exception {
        Project project = technicianManager.createNewProject(projectName, employeeId);
        allProjects.add(project);
        refreshEventListView();
        return project;
    }
    public void refreshEventListView() throws Exception {
        //Update the listview
        allProjects.clear();
        allProjects.setAll(technicianManager.getAllProjects());
    }
    public ObservableList<Project> getAllProjectsByTechnicianId(int technicianId) throws SQLException {
        return FXCollections.observableArrayList(technicianManager.getAllProjectsByTechnicianId(technicianId));
    }

}
