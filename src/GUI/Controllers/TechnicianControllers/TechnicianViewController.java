package GUI.Controllers.TechnicianControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;


public class TechnicianViewController {

    @FXML
    private Pane savedImageArea;
    @FXML
    private TextArea textFieldArea;


    public void uploadButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Gui/Views/technician/Edit-pic.fxml"));
            Parent editPictureView = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit a Picture");
            stage.setScene(new Scene(editPictureView));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SaveButton(ActionEvent event) {

    }
}
