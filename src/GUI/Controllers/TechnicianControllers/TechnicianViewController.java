package GUI.Controllers.TechnicianControllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class TechnicianViewController {


    public TextArea layoutDrawingField;

    public void uploadPictures() {

    }




    public void uploadButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Gui/Views/technician/Editpic.fxml"));
            Parent editApictureView = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Edit a Picture");
            stage.setScene(new Scene(editApictureView));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SaveButton(ActionEvent event) {

    }
}
