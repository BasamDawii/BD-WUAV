package BLL;

import BE.Technician;
import DAL.Login_DB;

public class LoginManager {
    private Login_DB loginDb;

    public Technician technicianLogin(String username, String password) throws Exception {
        return loginDb.technicianLogin(username, password);
    }
}
