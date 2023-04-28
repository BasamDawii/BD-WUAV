package GUI.Models;

import BE.Technician;
import BLL.LoginManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;

public class LoginModel {
    private LoginManager loginManager;
    public LoginModel(){loginManager = new LoginManager();}
    public Technician technicianLogin(String username, String password) throws SQLServerException {
        return loginManager.technicianLogin(username, password);
    }
}
