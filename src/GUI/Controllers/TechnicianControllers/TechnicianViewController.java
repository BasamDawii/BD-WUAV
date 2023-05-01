package GUI.Controllers.TechnicianControllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class TechnicianViewController {

    @FXML
    private TextArea layoutDrawingField;

    @FXML
    private Button uploadBtn, saveBtn;
    @FXML
    private FlowPane imagePane;
    private List<File> pictures;

    public void uploadPictures() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Pictures");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        pictures = fileChooser.showOpenMultipleDialog(uploadBtn.getScene().getWindow());

        if (pictures != null) {
            for (File picture : pictures) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(picture);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(150);
                    imageView.setFitHeight(150);
                    imagePane.getChildren().add(imageView);

                    // Save the picture to the "Pictures" package
                    File destination = new File("src/Pictures/" + picture.getName());
                    Files.copy(picture.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveDocumentation() {
        String layoutDrawing = layoutDrawingField.getText();
        // Save layout drawing, text description, and pictures to the database
        // For example, you can use the BLL package classes to handle data saving

        // Show a confirmation message or an error message if there was an issue while saving the data
    }

    public void uploadButton(ActionEvent event) {
        uploadPictures();
    }

    public void SaveButton(ActionEvent event) {
        saveDocumentation();
    }
}
