package GUI.Controllers.TechnicianControllers;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.imgscalr.Scalr;

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
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(25, 700);

            String text = textFieldArea.getText();
            contentStream.showText(text);

            contentStream.endText();

            // Save the image to the PDF
            if (uploadedImageView.getImage() != null) {
                Image image = uploadedImageView.getImage();
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

                // Scale the image to 150x150 pixels
                BufferedImage scaledImage = Scalr.resize(bufferedImage, 150, 150);

                // Calculate image position
                float x = (page.getMediaBox().getWidth() - scaledImage.getWidth()) / 2;
                float y = (page.getMediaBox().getHeight() - scaledImage.getHeight()) / 2;

                PDImageXObject pdImage = LosslessFactory.createFromImage(document, scaledImage);
                contentStream.drawImage(pdImage, x, y, scaledImage.getWidth(), scaledImage.getHeight());
            }

            contentStream.close();

            // Save the PDF document
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(savedImageArea.getScene().getWindow());
            if (file != null) {
                document.save(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
