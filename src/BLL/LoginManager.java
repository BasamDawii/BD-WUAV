package BLL;

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

    public Technician technicianLogin(String username, String password) throws SQLServerException {
        return loginDb.technicianLogin(username, password);
    }


    public ProjectManager projectManagerLogin(String username, String password) throws SQLServerException {
        return loginDb.projectManagerLogin(username, password);
    }

    public Salesperson salespersonLogin(String username, String password) throws SQLServerException {
        return loginDb.salespersonLogin(username, password);
    }

}
