package GUI.Controllers.TechnicianControllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

public class Editpic implements Initializable {
    public Button saveimage;
    @FXML
    private Button exampleButton;

    @FXML
    private Pane imagePane;

    private double mouseX;
    private double mouseY;

    @FXML
    private ImageView uploadedImageView;
    private boolean isDrawing = false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void uploadPictures() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Pictures");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

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

    public void drawButton(ActionEvent event) {
        isDrawing = !isDrawing; // Toggle the drawing flag

        if (isDrawing) {
            // Enable drawing
            imagePane.setOnMousePressed(this::startDrawing);
            imagePane.setOnMouseDragged(this::continueDrawing);
            imagePane.setOnMouseReleased(this::stopDrawing);
        } else {
            // Disable drawing
            imagePane.setOnMousePressed(null);
            imagePane.setOnMouseDragged(null);
            imagePane.setOnMouseReleased(null);
        }
    }
    private void startDrawing(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        Circle circle = new Circle(x, y, 5, Color.BLACK);
        imagePane.getChildren().add(circle);
    }

    private void continueDrawing(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        Circle circle = new Circle(x, y, 5, Color.BLACK);
        imagePane.getChildren().add(circle);
    }

    private void stopDrawing(MouseEvent event) {
        // You can add any additional logic here if needed
    }
}