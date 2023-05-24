package GUI.Controllers.TechnicianControllers;

import BE.Documentation;
import BE.Employee;
import BE.Project;
import DAL.ProjectManager_DB;
import GUI.Models.ProjectManagerModel;
import GUI.Models.TechnicianModel;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

public class TechnicianViewController implements Initializable{


    @FXML
    private  ComboBox comboBoxSelectProject;
    private ProjectManager_DB projectManagerDb;
    @FXML
    private TextField docNameTXT, customerNameTXT, projectNameTXT;

    @FXML
    private DatePicker startDateTXT, endDateTXT;
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
    private TechnicianModel technicianModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            projectManagerDb = new ProjectManager_DB();
            technicianModel = new TechnicianModel();
            selectedProject();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void selectedProject() throws IOException, SQLServerException {
        ArrayList<Project> projects = new ProjectManagerModel().loadProjects();
        comboBoxSelectProject.getItems().clear();
        comboBoxSelectProject.getItems().addAll(projects);
    }

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
        String docName = docNameTXT.getText();
        LocalDate startDate = startDateTXT.getValue();
        LocalDate endDate = endDateTXT.getValue();
        String customerName = customerNameTXT.getText();

        // Generate the PDF
        try {
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
                contentStream.showText("Project Description: " + docName);
                contentStream.newLineAtOffset(0, -14);
                contentStream.showText("Start Date: " + startDate);
                contentStream.newLineAtOffset(0, -14);
                contentStream.showText("End Date: " + endDate);
                contentStream.newLineAtOffset(0, -14);
                contentStream.showText("Customer Name: " + customerName);
                contentStream.newLineAtOffset(0, -14);

                // Save the text to the PDF
                String text = textFieldArea.getText();
                String[] lines = text.split("\n");
                for (String line : lines) {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -14); // Adjust the line spacing as needed
                }
                contentStream.endText();
            }

            // Save the PDF document
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            document.close();

            // Get the PDF data as bytes
            byte[] pdfData = outputStream.toByteArray();

            // Encode the PDF data to Base64
            String encodedPdfData = Base64.getEncoder().encodeToString(pdfData);

            Project selectedProject = (Project) comboBoxSelectProject.getSelectionModel().getSelectedItem();
            int projectId = selectedProject.getProjectId();
            Documentation documentation = new Documentation(0, docName, startDate, endDate, customerName, encodedPdfData, projectId);
            // Save the generated PDF to the database
            projectManagerDb.saveDocToDataBase(documentation);

            // Display a success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "Project details and PDF saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            // Display an error message
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while saving the project details and PDF.");
        }
    }



    // Add this helper method to display alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
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
            contentStream.showText("Project Description: " + docNameTXT.getText());
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



    public void newProjectButton(ActionEvent event) {
        String projectName = projectNameTXT.getText();

        try {
            technicianModel.createNewProject(projectName);

            projectNameTXT.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have successfully created a new project ..!");
            alert.show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
