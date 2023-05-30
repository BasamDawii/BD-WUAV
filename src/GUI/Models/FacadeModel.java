package GUI.Models;

import BE.Documentation;
import BE.Employee;
import BE.Project;
import BE.Technician;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class FacadeModel {
    private LoginModel loginModel;
    private ProjectManagerModel projectManagerModel;
    private TechnicianModel technicianModel;

    public FacadeModel() throws SQLException {
        loginModel = new LoginModel();
        projectManagerModel = new ProjectManagerModel();
        technicianModel = new TechnicianModel();
    }

    // LoginModel methods
    public Employee employeeLogin(String username, String password) throws SQLServerException {
        return loginModel.employeeLogin(username, password);
    }

    // ProjectManagerModel methods
    public ObservableList<Project> getAllProjects() {
        return projectManagerModel.getAllProjects();
    }

    public ObservableList<Technician> getAllTechnicians() {
        return projectManagerModel.getAllTechnicians();
    }

    public ArrayList<Documentation> loadDocumentationData() throws SQLServerException, IOException {
        return technicianModel.loadDocumentationData();
    }

    public boolean addEmpProject(int pid, int tid) {
        return projectManagerModel.addEmpProject(pid, tid);
    }

    public boolean deleteDocumentation(Documentation documentation) {
        return projectManagerModel.deleteDocumentation(documentation);
    }

    // TechnicianModel methods
    public Project createNewProject(String projectName, int employeeId) throws Exception {
        return technicianModel.createNewProject(projectName, employeeId);
    }

    public Documentation saveDocToDataBase(Documentation documentation) {
        return technicianModel.saveDocToDataBase(documentation);
    }

    public ObservableList<Project> getAllProjectsByTechnicianId(int technicianId) throws SQLException {
        return technicianModel.getAllProjectsByTechnicianId(technicianId);
    }
}
