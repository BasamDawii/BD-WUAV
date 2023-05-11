package GUI.Controllers.TechnicianControllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditPic implements Initializable {

    @FXML
    private Button exampleButton, saveimage;

    @FXML
    private Pane imagePane;
    @FXML
    private Pane iconPane;

    private double mouseX;
    private double mouseY;
    private boolean isDrawing = false;
    private TechnicianViewController technicianViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        technicianViewController = fxmlLoader.getController();

        createIcon("mic.png");
        createIcon("projectors.png");
        createIcon("speaker.png");
        // Add more icons as needed
        imagePane.setMouseTransparent(false);
        enableDragAndDrop(); // Add this line to enable drag and drop functionality
    }



    private void enableDrag(ImageView imageView) {
        imageView.setOnDragDetected(event -> {
            Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putImage(imageView.getImage());
            db.setContent(content);

            event.consume();
        });
    }


    private void enableDragAndDrop() {
        imagePane.setOnDragOver(event -> {
            if (event.getGestureSource() != imagePane && event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        imagePane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage()) {
                ImageView imageView;

                if (event.getGestureSource() instanceof ImageView && ((ImageView) event.getGestureSource()).getParent() == iconPane) {
                    // Clone the icon if the drag originated from the iconPane
                    String imagePath = ((ImageView) event.getGestureSource()).getId();
                    URL imageUrl = getClass().getResource("/GUI/Views/Image/" + imagePath);
                    imageView = new ImageView(new Image(imageUrl.toExternalForm()));
                } else {
                    imageView = (ImageView) event.getGestureSource();
                }

                // Set the size of the dropped image
                imageView.setFitWidth(35);
                imageView.setFitHeight(35);

                double dropX = event.getX() - imageView.getFitWidth() / 2;
                double dropY = event.getY() - imageView.getFitHeight() / 2;

                imageView.setLayoutX(dropX);
                imageView.setLayoutY(dropY);

                enableDrag(imageView); // Enable dragging for the dropped ImageView
                imagePane.getChildren().add(imageView);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });



    }

    private void createIcon(String imagePath) {
        URL imageUrl = getClass().getResource("/GUI/Views/Image/" + imagePath);
        ImageView imageView = new ImageView(new Image(imageUrl.toExternalForm()));
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        iconPane.getChildren().add(imageView);

        imageView.setId(imagePath); // Set a unique identifier for each icon
        enableDrag(imageView); // Enable dragging for the icon ImageView
    }





    public void setTechnicianViewController(TechnicianViewController technicianViewController) {
        this.technicianViewController = technicianViewController;
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

                imageView.setOnMousePressed(event -> {
                    mouseX = event.getX();
                    mouseY = event.getY();
                });

                imageView.setOnMouseDragged(event -> {
                    double offsetX = event.getSceneX() - mouseX;
                    double offsetY = event.getSceneY() - mouseY;
                    imageView.setTranslateX(offsetX);
                    imageView.setTranslateY(offsetY);
                });

                imageView.setOnMouseReleased(event -> {
                    imageView.setLayoutX(imageView.getLayoutX() + imageView.getTranslateX());
                    imageView.setLayoutY(imageView.getLayoutY() + imageView.getTranslateY());
                    imageView.setTranslateX(0);
                    imageView.setTranslateY(0);
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



    public void saveImageToProgram(ActionEvent event) {
        // Convert imagePane to a single Image
        WritableImage combinedImage = new WritableImage((int) imagePane.getWidth(), (int) imagePane.getHeight());
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);
        imagePane.snapshot(snapshotParameters, combinedImage);

        // Pass the edited image to TechnicianViewController
        if (technicianViewController != null) {
            technicianViewController.setImagePaneContent(combinedImage);
        }

        // Close the window
        Stage stage = (Stage) saveimage.getScene().getWindow();
        stage.close();
    }

}
