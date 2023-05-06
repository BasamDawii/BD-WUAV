package GUI.Controllers;

import BE.Employee;
import BE.ProjectManager;
import BE.Salesperson;
import BE.Technician;
import BLL.LoginManager;
import GUI.Controllers.ProjectManagerControllers.ProjectManagerViewController;
import GUI.Controllers.SalespersonControllers.SalespersonViewController;
import GUI.Controllers.TechnicianControllers.TechnicianViewController;
import GUI.Models.LoginModel;
import com.microsoft.sqlserver.jdbc.SQLServerException;
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
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    private TextField usernameTXT;

    @FXML
    private PasswordField passwordTXT;

    private LoginModel loginModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loginModel = new LoginModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleLoginButton(ActionEvent event) throws IOException, SQLServerException {
        String username = usernameTXT.getText();
        String password = passwordTXT.getText();

        Employee employee = loginModel.employeeLogin(username, password);

        if (employee instanceof Technician) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/technician/technician_view.fxml"));
            Parent root = loader.load();
            TechnicianViewController technicianController = loader.getController();
            technicianController.setLoggedInEmployee(employee);
            technicianController.setLoggedInUsername(username); // Add this line
            navigateToView(root);
        } else if (employee instanceof ProjectManager) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/project_manager/project_manager_view.fxml"));
            Parent root = loader.load();
            ProjectManagerViewController projectManagerController = loader.getController();
            projectManagerController.setLoggedInEmployee(employee);
            projectManagerController.setLoggedInUsername(username); // Add this line
            navigateToView(root);
        } else if (employee instanceof Salesperson) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/salesperson/salesperson_view.fxml"));
            Parent root = loader.load();
            SalespersonViewController salespersonController = loader.getController();
            salespersonController.setLoggedInEmployee(employee);
            salespersonController.setLoggedInUsername(username); // Add this line
            navigateToView(root);
        }
    }


    private void navigateToView(Parent root) throws IOException {
        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameTXT.getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
