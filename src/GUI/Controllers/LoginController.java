package GUI.Controllers;

import BE.ProjectManager;
import BE.Salesperson;
import BE.Technician;
import BE.User;
import BLL.UserService;
import javafx.event.ActionEvent;

public class LoginController {
    private UserService userService;

    public LoginController() {
        userService = new UserService();
    }

    public void login(String username, String password) {
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
    public void LoginButton(ActionEvent event) {

    }
}
