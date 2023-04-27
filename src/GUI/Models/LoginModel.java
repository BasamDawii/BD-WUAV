package GUI.Models;

import BE.Technician;
import BLL.LoginManager;

public class LoginModel {

        private LoginManager loginManager;
        public LoginModel() {
        loginManager = new LoginManager();
    }

        public Technician technicianLogin(String username, String password) throws Exception {
        return loginManager.technicianLogin(username, password);
        }
}
