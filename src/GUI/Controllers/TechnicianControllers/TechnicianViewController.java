package GUI.Controllers.TechnicianControllers;

import BE.Employee;
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
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;

import DAL.Documentation_DB;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class TechnicianViewController {
    @FXML
    private Label usernameLabel;

    public void setLoggedInUsername(String username) {
        usernameLabel.setText("Logged in as: " + username);
    }

    @FXML
    private Documentation_DB documentationDb;
    private TextField projectNameField;

    @FXML
    private Pane savedImageArea;
    @FXML
    private TextArea textFieldArea;
    @FXML
    private ImageView uploadedImageView;
    private List<Node> imagePaneNodes;
    private Employee loggedInEmployee;


    public TechnicianViewController() {

        documentationDb = new Documentation_DB();

    }

    public void setLoggedInEmployee(Employee employee) {
        this.loggedInEmployee = employee;
    }


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

    public void setImagePaneContent(Image image, List<Node> nodes) {
        this.uploadedImageView.setImage(image);
        this.imagePaneNodes = nodes;
    }

    public void SaveButton(ActionEvent event) {
        projectNameField = new TextField();

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            // ... (existing code for creating PDF)
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Save the PDF document in memory
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            byte[] pdfData = outputStream.toByteArray();
            document.close();

            // Save the project description to the database
            String projectName = projectNameField.getText(); // Get the project name from the TextField
            int projectId = saveProjectDescriptionToDatabase(projectName);

            // Save the PDF data to the database
            documentationDb.saveDocumentation(projectId, pdfData);

            // Save the PDF document to a file (Optional)
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(savedImageArea.getScene().getWindow());
            if (file != null) {
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(pdfData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private java.awt.Color javaFXColorToAWTColor(javafx.scene.paint.Color fxColor) {
        return new java.awt.Color((float) fxColor.getRed(), (float) fxColor.getGreen(), (float) fxColor.getBlue(), (float) fxColor.getOpacity());
    }

    private int saveProjectDescriptionToDatabase(String projectName) {
        String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=BM_xm;user=YOUR_USERNAME;password=YOUR_PASSWORD;";
        String insertProjectSQL = "INSERT INTO Project (projectName, projectDescription, startDate, customerId, projectManagerId) VALUES (?, ?, ?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(insertProjectSQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, projectName);
            preparedStatement.setDate(3, new java.sql.Date(System.currentTimeMillis())); // Assuming today's date as start date
            preparedStatement.setInt(4, 1); // Replace with actual customerId
            preparedStatement.setInt(5, 1); // Replace with actual projectManagerId

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating project failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }


}
