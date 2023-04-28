package DAL;

import BE.Project;
import DAL.database.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProjectManager_DB {
    private DBConnector dbConnector;

    public ProjectManager_DB(){dbConnector = new DBConnector();
    }

    public void deletePDF ( Project event){


    }
}
