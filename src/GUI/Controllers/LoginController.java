package GUI.Controllers;

import BE.User;
import BLL.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private UserService userService = new UserService();

    public void login() {
        String email = emailField.getText();
        String password = passwordField.getText();
        User user = userService.login(email, password);

        if (user != null) {
            try {
                Stage stage = (Stage) emailField.getScene().getWindow();
                Parent root;
                String role = user.getRole();
                switch (role) {
                    case "Technician":
                        root = FXMLLoader.load(getClass().getResource("technician_view.fxml"));
                        break;
                    case "Project Manager":
                        root = FXMLLoader.load(getClass().getResource("project_manager_view.fxml"));
                        break;
                    case "Salesperson":
                        root = FXMLLoader.load(getClass().getResource("salesperson_view.fxml"));
                        break;
                    default:
                        errorLabel.setText("Invalid role");
                        return;
                }
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Invalid email or password");
        }
    }
}
