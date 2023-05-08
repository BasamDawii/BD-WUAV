package GUI.Controllers.TechnicianControllers;

import BE.Employee;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.*;
public class TechnicianViewController {

    @FXML
    private Pane savedImageArea;
    @FXML
    private TextArea textFieldArea;
    @FXML
    private ImageView uploadedImageView;
    private List<Node> imagePaneNodes;
    @FXML
    private Label usernameLabel;
    private Employee loggedInEmployee;

    public void uploadButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Gui/Views/technician/Edit-pic.fxml"));
            Parent editPictureView = fxmlLoader.load();

            EditPic editPicController = fxmlLoader.getController();
            editPicController.setTechnicianViewController(this);

            Stage stage = new Stage();
            stage.setTitle("Edit a Picture");
            stage.setScene(new Scene(editPictureView));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setLoggedInEmployee(Employee employee) {
        this.loggedInEmployee = employee;
        usernameLabel.setText( employee.getUsername());
        usernameLabel.setTranslateX(65);
    }


    public void setImagePaneContent(Image combinedImage) {
        uploadedImageView.setImage(combinedImage);
    }


    public void SaveButton(ActionEvent event) {
//        PDDocument document = new PDDocument();
//        PDPage page = new PDPage(PDRectangle.A4);
//        document.addPage(page);
//
//        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
//            PDFont font = PDType1Font.HELVETICA;
//            contentStream.setFont(PDType1Font.HELVETICA, 12);
//
//            // Save images and drawings to the PDF
//            if (imagePaneNodes != null && !imagePaneNodes.isEmpty()) {
//                for (Node node : imagePaneNodes) {
//                    if (node instanceof ImageView) {
//                        ImageView imageView = (ImageView) node;
//
//                        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
//                        float x = (float) imageView.getX();
//                        float y = page.getMediaBox().getHeight() - (float) imageView.getY() - (float) imageView.getFitHeight();
//
//                        PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);
//                        contentStream.drawImage(pdImage, x, y, (float) imageView.getFitWidth(), (float) imageView.getFitHeight());
//                    } else if (node instanceof Circle) {
//                        Circle circle = (Circle) node;
//
//                        float x = (float) circle.getCenterX();
//                        float y = page.getMediaBox().getHeight() - (float) circle.getCenterY();
//                        float radius = (float) circle.getRadius();
//                        float k = 0.5522847498f;
//
//                        contentStream.setNonStrokingColor(javaFXColorToAWTColor((javafx.scene.paint.Color) circle.getFill()));
//                        contentStream.moveTo(x + radius, y);
//                        contentStream.curveTo(x + radius, y + radius * k, x + radius * k, y + radius, x, y + radius);
//                        contentStream.curveTo(x - radius * k, y + radius, x - radius, y + radius * k, x - radius, y);
//                        contentStream.curveTo(x - radius, y - radius * k, x - radius * k, y - radius, x, y - radius);
//                        contentStream.curveTo(x + radius * k, y - radius, x + radius, y - radius * k, x + radius, y);
//                        contentStream.fill();
//                    }
//                }
//            }
//
//            // Save the text to the PDF (At the bottom)
//            contentStream.beginText();
//            contentStream.setFont(PDType1Font.HELVETICA, 12);
//
//            String text = textFieldArea.getText();
//            String[] lines = text.split("\n");
//            float margin = 25;
//            float lineHeight = 14;
//            float yPosition = 50;
//
//            for (int i = 0; i < lines.length; i++) {
//                contentStream.newLineAtOffset(margin, yPosition - (i * lineHeight));
//                contentStream.showText(lines[i]);
//                contentStream.newLineAtOffset(-margin, 0);
//            }
//
//            contentStream.endText();
//            contentStream.close();
//
//            // Save the PDF document
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("Save PDF");
//            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
//            File file = fileChooser.showSaveDialog(savedImageArea.getScene().getWindow());
//            if (file != null) {
//                document.save(file);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            document.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private Color javaFXColorToAWTColor(javafx.scene.paint.Color fxColor) {
        return new Color((float) fxColor.getRed(), (float) fxColor.getGreen(), (float) fxColor.getBlue(), (float) fxColor.getOpacity());
    }

    public void handleLogoutButton(ActionEvent event) throws IOException, SQLServerException {
        navigateToView("/GUI/Views/LoginView.fxml", event);
    }
    private void navigateToView(String viewPath, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
        Parent root = loader.load(getClass().getResource(viewPath));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
