package JUnit_test;

import BE.Employee;
import BE.Technician;
import DAL.Login_DB;
import DAL.database.DBConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class LoginDBTest {
    private Login_DB login_db;
    private DBConnector dbConnector;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    void setUp() throws SQLException {
        dbConnector = Mockito.mock(DBConnector.class);
        connection = Mockito.mock(Connection.class);
        preparedStatement = Mockito.mock(PreparedStatement.class);
        resultSet = Mockito.mock(ResultSet.class);

        login_db = new Login_DB();
        login_db.dbConnector = this.dbConnector;

        when(dbConnector.getConnected()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    // 1. Correct username and correct password
    @Test
    void employeeLogin_withValidUsernameAndPassword_returnsEmployee() throws SQLException {
        // Arrange
        String expectedUsername = "TestUsername";
        String expectedPassword = "TestPassword";
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("employeeType")).thenReturn("Technician");

        // Act
        Employee actualEmployee = login_db.employeeLogin(expectedUsername, expectedPassword);

        // Assert
        assertEquals(expectedUsername, actualEmployee.getUsername());
        assertEquals(expectedPassword, actualEmployee.getPassword());
        assertTrue(actualEmployee instanceof Technician);
    }

    // 2. Incorrect username and incorrect password
    @Test
    void employeeLogin_withInvalidUsernameAndPassword_returnsNull() throws SQLException {
        // Arrange
        String expectedUsername = "WrongUsername";
        String expectedPassword = "WrongPassword";
        when(resultSet.next()).thenReturn(false);

        // Act
        Employee actualEmployee = login_db.employeeLogin(expectedUsername, expectedPassword);

        // Assert
        assertNull(actualEmployee);
    }

    // 3. Empty username and password
    @Test
    void employeeLogin_withEmptyUsernameAndPassword_returnsNull() throws SQLException {
        // Arrange
        String expectedUsername = "";
        String expectedPassword = "";
        when(resultSet.next()).thenReturn(false);

        // Act
        Employee actualEmployee = login_db.employeeLogin(expectedUsername, expectedPassword);

        // Assert
        assertNull(actualEmployee);
    }

    // 4. Correct username and incorrect password
    @Test
    void employeeLogin_withValidUsernameAndInvalidPassword_returnsNull() throws SQLException {
        // Arrange
        String expectedUsername = "TestUsername";
        String expectedPassword = "WrongPassword";
        when(resultSet.next()).thenReturn(false);

        // Act
        Employee actualEmployee = login_db.employeeLogin(expectedUsername, expectedPassword);

        // Assert
        assertNull(actualEmployee);
    }

    // 5. Incorrect username and correct password
    @Test
    void employeeLogin_withInvalidUsernameAndValidPassword_returnsNull() throws SQLException {
        // Arrange
        String expectedUsername = "WrongUsername";
        String expectedPassword = "TestPassword";
        when(resultSet.next()).thenReturn(false);

        // Act
        Employee actualEmployee = login_db.employeeLogin(expectedUsername, expectedPassword);

        // Assert
        assertNull(actualEmployee);
    }
}