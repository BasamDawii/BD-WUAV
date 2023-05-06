package GUI.Controllers.ProjectManagerControllers;

import BE.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProjectManagerViewController {
    private Employee loggedInEmployee;

    public void setLoggedInEmployee(Employee employee) {
        this.loggedInEmployee = employee;
    }
    @FXML
    private Label loggedInUsername;

    public void setLoggedInUsername(String username) {
        loggedInUsername.setText(username);
    }

}
