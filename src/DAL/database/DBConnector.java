package DAL.database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector {

    SQLServerDataSource dataSource;

    //Defining server and database
    public DBConnector() {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setDatabaseName("Event_Tickets_EASV_bar");
        dataSource.setUser("CSe22A_23");
        dataSource.setPassword("DataMatiker");
        dataSource.setTrustServerCertificate(true);
        dataSource.setPortNumber(1433);


    }

    public Connection getConnected() throws SQLServerException {
        return dataSource.getConnection();
    }


    //Only to see if we have connection or not
    public static void main(String[] args) throws SQLServerException {
        DBConnector dbConnector = new DBConnector();
        try(Connection connection =dbConnector.getConnected())
        {
            if(!connection.isClosed())
            {
                System.out.println("Is it opened..?\n" + !connection.isClosed());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
