package GUI.Controllers;

import BE.Employee;
import BE.ProjectManager;
import BE.Salesperson;
import BE.Technician;
import GUI.Controllers.ProjectManagerControllers.ProjectManagerViewController;
import GUI.Controllers.SalespersonControllers.SalespersonViewController;
import GUI.Controllers.TechnicianControllers.TechnicianViewController;
import GUI.Models.FacadeModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameTXT;

    @FXML
    private PasswordField passwordTXT;

    FacadeModel facadeModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            facadeModel = new FacadeModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleLoginButton(ActionEvent event) throws IOException, SQLException {
        String username = usernameTXT.getText();
        String password = passwordTXT.getText();

        Employee employee = facadeModel.employeeLogin(username, password);

        if (employee instanceof Technician) {
            navigateToView("/GUI/Views/technician/technician_view.fxml", employee);
        } else if (employee instanceof ProjectManager) {
            navigateToView("/GUI/Views/project_manager/project_manager_view.fxml", employee);
        } else if (employee instanceof Salesperson) {
            navigateToView("/GUI/Views/salesperson/salesperson_view.fxml", employee);
        } else {

        }
    }

    private void navigateToView(String viewPath, Employee employee) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameTXT.getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        if (employee instanceof Technician) {
            TechnicianViewController technicianViewController = loader.getController();
            technicianViewController.setLoggedInEmployee(employee);
        } else if (employee instanceof ProjectManager) {
            ProjectManagerViewController projectManagerViewController = loader.getController();
            projectManagerViewController.setLoggedInEmployee(employee);
        } else if (employee instanceof Salesperson) {
            SalespersonViewController salespersonViewController = loader.getController();
            salespersonViewController.setLoggedInEmployee(employee);
        }
    }

}
