package GUI.Controllers;

import BE.ProjectManager;
import BE.Salesperson;
import BE.Technician;
import GUI.Models.LoginModel;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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


    public void handleLoginButton(ActionEvent event) throws SQLServerException {
        String username = usernameTXT.getText();
        String password = passwordTXT.getText();

        try {
            Technician technician = loginModel.technicianLogin(username, password);
            if (technician != null) {
                // Login successful, navigate to the next screen
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/technician/technician_view.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) usernameTXT.getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}

