package GUI.Controllers;

import BE.Employee;
import BE.ProjectManager;
import BE.Salesperson;
import BE.Technician;
import BLL.LoginManager;
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
            navigateToView("/GUI/Views/technician/technician_view.fxml");
        } else if (employee instanceof ProjectManager) {
            navigateToView("/GUI/Views/project_manager/project_manager_view.fxml");
        } else if (employee instanceof Salesperson) {
            navigateToView("/GUI/Views/salesperson/salesperson_view.fxml");
        } else {
            // Show an error message if the login is unsuccessful
            // ...
        }
    }

    private void navigateToView(String viewPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameTXT.getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
