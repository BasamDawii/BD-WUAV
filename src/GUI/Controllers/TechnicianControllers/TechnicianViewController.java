package GUI.Controllers.TechnicianControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class TechnicianViewController {
    @FXML
    private TextArea layoutDrawingField;
    @FXML
    private TextArea textDescriptionField;
    @FXML
    private Button uploadButton;
    @FXML
    private Button saveButton;

    private List<File> pictures;

    public void uploadPictures() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Pictures");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        pictures = fileChooser.showOpenMultipleDialog(uploadButton.getScene().getWindow());
    }

    public void saveDocumentation() {
        String layoutDrawing = layoutDrawingField.getText();
        String textDescription = textDescriptionField.getText();

        // Save layout drawing, text description, and pictures to the database
        // For example, you can use the BLL package classes to handle data saving

        // Show a confirmation message or an error message if there was an issue while saving the data
    }

    public void uploadButton(ActionEvent event) {
    }

    public void SaveButton(ActionEvent event) {
    }
}
