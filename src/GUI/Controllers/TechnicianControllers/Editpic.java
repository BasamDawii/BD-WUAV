package GUI.Controllers.TechnicianControllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Editpic {
    public Button saveimage;
    @FXML
    private Button exampleButton;

    @FXML
    private Pane imagePane;

    private double mouseX;
    private double mouseY;
    @FXML
    private Slider imageSizeSlider;

    @FXML
    private ImageView uploadedImageView;

    @FXML
    private void initialize() {
        // You can access and modify the exampleButton here.
    }

    public void uploadPictures() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Pictures");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", ".jpeg", ".gif"));
        File picture = fileChooser.showOpenDialog(exampleButton.getScene().getWindow());

        if (picture != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(picture);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);

                imageView.setOnMouseDragged((MouseEvent event) -> {
                    mouseX = event.getX();
                    mouseY = event.getY();
                    imageView.setX(mouseX);
                    imageView.setY(mouseY);
                });

                imagePane.getChildren().add(imageView);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveImage(ActionEvent event) {
    }
}