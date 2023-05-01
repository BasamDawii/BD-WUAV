package BLL;

import BE.Employee;
import BE.ProjectManager;
import BE.Salesperson;
import BE.Technician;
import DAL.Login_DB;
import com.microsoft.sqlserver.jdbc.SQLServerException;

public class LoginManager {

    private Login_DB loginDb;

    public LoginManager() {
        loginDb = new Login_DB();
    }

    public Employee employeeLogin(String username, String password) throws SQLServerException {
        return loginDb.employeeLogin(username, password);
    }
}
