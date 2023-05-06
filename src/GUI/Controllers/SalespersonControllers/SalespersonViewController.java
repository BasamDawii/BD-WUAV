package GUI.Controllers.SalespersonControllers;

import BE.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class SalespersonViewController {
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
