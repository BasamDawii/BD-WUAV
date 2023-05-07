package GUI.Controllers.TechnicianControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import BE.Employee;
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
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;

import DAL.Documentation_DB;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class TechnicianViewController implements Initializable {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> searchResults;


    private Image drawnImage;

    @FXML
    private Label usernameLabel;

    public void setLoggedInUsername(String username) {
        usernameLabel.setText("Logged in as: " + username);
    }

    @FXML
    private final Documentation_DB documentationDb;
    @FXML
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
    private String loggedInUsername;
    private String loggedInPassword;

    public void setLoggedInEmployee(Employee employee, String username, String password) {
        this.loggedInEmployee = employee;
        this.loggedInUsername = username;
        this.loggedInPassword = password;
        usernameLabel.setText("Logged in as: " + username);
    }

    private void addTextToPdf(PDPageContentStream contentStream, String text, float fontSize, float x, float y) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA, fontSize);
        contentStream.setLeading(fontSize * 1.2f);
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);

        String[] lines = text.split("\n");
        for (String line : lines) {
            contentStream.showText(line.replace('\n', ' '));
            contentStream.newLine();
        }

        contentStream.endText();
    }




    public void setLoggedInEmployee(Employee employee) {
        this.loggedInEmployee = employee;
    }

    @FXML
    public void searchProject(ActionEvent event) {
        String searchQuery = searchField.getText().trim();
        if (!searchQuery.isEmpty()) {
            try {
                List<String> projectNames = documentationDb.searchProjectsByName(searchQuery);
                ObservableList<String> observableList = FXCollections.observableArrayList(projectNames);
                searchResults.setItems(observableList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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

    public void setImagePaneContent(Image editedImage, List<Node> drawnContent) {
        // Set the uploaded image to the ImageView
        uploadedImageView.setImage(editedImage);

        // Clear any existing drawn content from the Pane
        savedImageArea.getChildren().clear();

        // Add the new drawn content to the Pane
        savedImageArea.getChildren().addAll(drawnContent);

        // Save the drawn content as a new Image
        drawnImage = paneToImage(savedImageArea);
    }


    private Image combineImages(Image backgroundImage, Image foregroundImage) {
        int width = (int) Math.max(backgroundImage.getWidth(), foregroundImage.getWidth());
        int height = (int) Math.max(backgroundImage.getHeight(), foregroundImage.getHeight());
        BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = combinedImage.createGraphics();

        // Draw the background image
        graphics.drawImage(SwingFXUtils.fromFXImage(backgroundImage, null), 0, 0, null);

        // Draw the foreground image (drawn content)
        graphics.drawImage(SwingFXUtils.fromFXImage(foregroundImage, null), 0, 0, null);

        graphics.dispose();

        return SwingFXUtils.toFXImage(combinedImage, null);
    }

    private Image paneToImage(Pane pane) {
        WritableImage image = new WritableImage((int) pane.getWidth(), (int) pane.getHeight());
        pane.snapshot(null, image);
        return image;
    }

    public void uploadToCloud(ActionEvent event) {
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
            savePdfToDatabase(projectId, pdfData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void addImageToPdf(PDDocument document, PDPageContentStream contentStream, Image image, float x, float y) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);
        contentStream.drawImage(pdImage, x, y, pdImage.getWidth() / 2, pdImage.getHeight() / 2);
    }


    private void addDrawnContentToPdf(PDPageContentStream contentStream) throws IOException {
        // Example: Draw a simple rectangle
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.addRect(50, 300, 100, 50);
        contentStream.fill();
        // Implement your own logic for adding the drawn content
    }

    public void saveToLocal(ActionEvent event) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            // Add image to the PDF
            addImageToPdf(document, contentStream, uploadedImageView.getImage(), 50, 680);

            // Add drawn content to the PDF
            if (drawnImage != null) {
                float drawnImageYPosition = 680.0f - (float) drawnImage.getHeight(); // Adjust the Y position
                addImageToPdf(document, contentStream, drawnImage, 50.0f, drawnImageYPosition);
            }

            // Add text content to the PDF
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(50, 400);
            contentStream.showText("Project Name: " + projectNameField.getText());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Project Description: ");
            contentStream.newLineAtOffset(0, -20);

            // Split the text from textFieldArea into lines
            String text = textFieldArea.getText();
            String[] lines = text.split("\n");
            float leading = 14.0f; // Adjust this value based on your desired line spacing

            contentStream.setFont(PDType1Font.HELVETICA, 12);

            for (String line : lines) {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -leading);
            }

            contentStream.endText();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Save the PDF document in memory
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            byte[] pdfData = outputStream.toByteArray();
            document.close();

            // Save the PDF document to a file
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
        String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=BM_xm;user=" + loggedInUsername + ";password=" + loggedInPassword + ";";
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
    private void savePdfToDatabase(int projectId, byte[] pdfData) {
        String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=BM_xm;user=" + loggedInUsername + ";password=" + loggedInPassword + ";";
        String insertPdfSQL = "INSERT INTO Documentation (projectId, pdfData) VALUES (?, ?);";

        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(insertPdfSQL)) {

            preparedStatement.setInt(1, projectId);
            preparedStatement.setBytes(2, pdfData);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
