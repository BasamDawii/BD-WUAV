package GUI.Controllers.TechnicianControllers;

import BE.Employee;
import DAL.ProjectManager_DB;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
public class TechnicianViewController {
    private ProjectManager_DB projectManagerDb;

    public TextField projectNameTXT;
    public TextField projectDisTXT;
    public DatePicker startDateTXT;
    public DatePicker endDateTXT;
    public TextField customerNameTXT;
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
    public TechnicianViewController() {
        projectManagerDb = new ProjectManager_DB();
    }

    public void setLoggedInEmployee(Employee employee) {
        this.loggedInEmployee = employee;
        usernameLabel.setText( employee.getUsername());
        usernameLabel.setTranslateX(65);
    }


    public void setImagePaneContent(Image combinedImage) {
        uploadedImageView.setImage(combinedImage);
    }

    public byte[] generatePdf(String projectDescription, LocalDate startDate, LocalDate endDate, String customerName) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter pdfWriter = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            // Add project information to the PDF
            document.add(new Paragraph("Project Description: " + projectDescription));
            document.add(new Paragraph("Start Date: " + startDate));
            document.add(new Paragraph("End Date: " + endDate));
            document.add(new Paragraph("Customer Name: " + customerName));

            document.close();

            // Save the generated PDF to a file
            Path tempPdfFile = Files.createTempFile("project-", ".pdf");
            try (FileOutputStream fileOutputStream = new FileOutputStream(tempPdfFile.toFile())) {
                outputStream.writeTo(fileOutputStream);
            }
            return outputStream.toByteArray();
        }

    }
    public void saveButton(ActionEvent event) {
        // Get the project information from the input fields
        String projectName = projectNameTXT.getText();
        String projectDescription = projectDisTXT.getText();
        LocalDate startDate = startDateTXT.getValue();
        LocalDate endDate = endDateTXT.getValue();
        String customerName = customerNameTXT.getText();

        // Generate the PDF
        try {
            byte[] pdfData = generatePdf(projectDescription, startDate, endDate, customerName);

            // Save the generated PDF to the database
            projectManagerDb.savePdfToDatabase(projectName, projectDescription, startDate, endDate, customerName, pdfData);

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void SaveLocal(ActionEvent actionEvent) {
        // Create a new PDF document
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            // Save the image and drawings to the PDF
            Image image = uploadedImageView.getImage();
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);
            contentStream.drawImage(pdImage, 50, 450, 500, 300); // Adjust the position and size as needed

            // Save the project information to the PDF
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 400); // Adjust the position as needed
            contentStream.showText("Project Name: " + projectNameTXT.getText());
            contentStream.newLineAtOffset(0, -14); // Adjust the line spacing as needed
            contentStream.showText("Project Description: " + projectDisTXT.getText());
            contentStream.newLineAtOffset(0, -14);
            contentStream.showText("Start Date: " + startDateTXT.getValue());
            contentStream.newLineAtOffset(0, -14);
            contentStream.showText("End Date: " + endDateTXT.getValue());
            contentStream.newLineAtOffset(0, -14);
            contentStream.showText("Customer Name: " + customerNameTXT.getText());
            contentStream.newLineAtOffset(0, -14);

            // Save the text to the PDF
            String text = textFieldArea.getText();
            String[] lines = text.split("\n");
            for (String line : lines) {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -14); // Adjust the line spacing as needed
            }
            contentStream.endText();
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
