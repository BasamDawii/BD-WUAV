package GUI.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameTXT;
    @FXML
    private PasswordField passwordTXT;
    @FXML
    private Button loginTXT;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
