package BLL;

import BE.Technician;
import DAL.Login_DB;
import com.microsoft.sqlserver.jdbc.SQLServerException;

public class LoginManager {

    private Login_DB loginDb;

    public LoginManager() {
        loginDb = new Login_DB();
    }

    public Technician technicianLogin(String username, String password) throws SQLServerException {
        return loginDb.technicianLogin(username, password);
    }

}
