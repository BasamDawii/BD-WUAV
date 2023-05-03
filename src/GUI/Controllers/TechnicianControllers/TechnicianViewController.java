package GUI.Controllers.TechnicianControllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;


public class TechnicianViewController {

    @FXML
    private Pane savedImageArea;
    @FXML
    private TextArea textFieldArea;
    @FXML
    private ImageView uploadedImageView;

    public void uploadButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Gui/Views/technician/Edit-pic.fxml"));
            Parent editPictureView = fxmlLoader.load();

            Editpic editPicController = fxmlLoader.getController();
            editPicController.setTechnicianViewController(this);

            Stage stage = new Stage();
            stage.setTitle("Edit a Picture");
            stage.setScene(new Scene(editPictureView));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setImagePaneContent(Image image, ObservableList<Node> drawingContent) {
        // Set the edited image in the ImageView
        uploadedImageView.setImage(image);

        // Clear the savedImageArea and add the drawing content to it
        savedImageArea.getChildren().clear();
        savedImageArea.getChildren().addAll(drawingContent);
    }
    public void SaveButton(ActionEvent event) {

    }
}
