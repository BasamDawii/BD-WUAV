package GUI.Controllers.TechnicianControllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Editpic implements Initializable {
    @FXML
    private Button exampleButton, saveimage;

    @FXML
    private Pane imagePane;

    private double mouseX;
    private double mouseY;
    private boolean isDrawing = false;
    private TechnicianViewController technicianViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        technicianViewController = fxmlLoader.getController();
    }

    public void setTechnicianViewController(TechnicianViewController technicianViewController) {
        this.technicianViewController = technicianViewController;
    }

    public void uploadPictures() {
        FileChooser fileChooser = createFileChooser();
        File picture = fileChooser.showOpenDialog(exampleButton.getScene().getWindow());

        if (picture != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(picture);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                ImageView imageView = createImageView(image);
                imagePane.getChildren().add(imageView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveImage(ActionEvent event) {
    }

    public void drawButton(ActionEvent event) {
        toggleDrawingMode();
    }

    private void startDrawing(MouseEvent event) {
        drawCircle(event.getX(), event.getY());
    }

    private void continueDrawing(MouseEvent event) {
        drawCircle(event.getX(), event.getY());
    }

    private void stopDrawing(MouseEvent event) {
    }

    public void saveImageToProgram(ActionEvent event) {
        Image editedImage = getEditedImage();
        if (editedImage != null && technicianViewController != null) {
            technicianViewController.setImagePaneContent(editedImage, imagePane.getChildren());
        }
        closeWindow();
    }

    private FileChooser createFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Pictures");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        return fileChooser;
    }

    private ImageView createImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setOnMouseDragged((MouseEvent event) -> {
            mouseX = event.getX();
            mouseY = event.getY();
            imageView.setX(mouseX);
            imageView.setY(mouseY);
        });
        return imageView;
    }

    private void toggleDrawingMode() {
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

    private void drawCircle(double x, double y) {
        Circle circle = new Circle(x, y, 5, Color.BLACK);
        imagePane.getChildren().add(circle);
    }

    private Image getEditedImage() {
        Image editedImage = null;
        if (!imagePane.getChildren().isEmpty() && imagePane.getChildren().get(0) instanceof ImageView) {
            editedImage = ((ImageView) imagePane.getChildren().get(0)).getImage();
        }
        return editedImage;
    }

    private void closeWindow() {
        Stage stage = (Stage) saveimage.getScene().getWindow();
        stage.close();
    }
}
