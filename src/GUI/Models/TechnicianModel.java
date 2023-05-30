package GUI.Models;

import BE.Documentation;
import BE.Project;
import BLL.TechnicianManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public Documentation saveDocToDataBase(Documentation documentation) {
        return technicianManager.saveDocToDataBase(documentation);
    }

    public ArrayList<Documentation> loadDocumentationData() throws SQLServerException, IOException {
        return technicianManager.getDocumentationData();
    }
}
