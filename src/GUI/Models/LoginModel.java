package GUI.Models;

import BE.Employee;
import BE.ProjectManager;
import BE.Salesperson;
import BE.Technician;
import BLL.LoginManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;

public class LoginModel {
    private LoginManager loginManager;

    public LoginModel() {
        loginManager = new LoginManager();
    }

    public Employee employeeLogin(String username, String password) throws SQLServerException {
        return loginManager.employeeLogin(username, password);
    }

}
