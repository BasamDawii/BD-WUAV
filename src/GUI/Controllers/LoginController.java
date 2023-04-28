package GUI.Controllers;

import BE.ProjectManager;
import BE.Salesperson;
import BE.Technician;
import BE.User;
import BLL.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private UserService userService;

    @FXML
    private TextField usernameTXT;

    @FXML
    private PasswordField passwordTXT;

    public LoginController() {
        userService = new UserService();
    }

    @FXML
    public void handleLoginButton(ActionEvent actionEvent) {
        String username = usernameTXT.getText();
        String password = passwordTXT.getText();
        User user = userService.login(username, password);
        if (user != null) {
            openAppropriateDashboard(user);
        } else {
            // Show error message, e.g., "Invalid email or password."
        }
    }

    private void openAppropriateDashboard(User user) {
        if (user instanceof Technician) {
            // Open Technician Dashboard
        } else if (user instanceof ProjectManager) {
            // Open Project Manager Dashboard
        } else if (user instanceof Salesperson) {
            // Open Salesperson Dashboard
        } else {
            // Show error message, e.g., "Invalid user type."
        }
    }
}
