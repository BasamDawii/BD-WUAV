package GUI.Controllers.SalespersonControllers;

import BE.Documentation;
import BE.Employee;
import GUI.Models.FacadeModel;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.ResourceBundle;

public class SalespersonViewController implements Initializable {
    @FXML
    private Button saveLocalButtonn;
    private Employee loggedInEmployee;
    @FXML
    private Label usernameLabel;
    @FXML
    private TableView<Documentation> tableView;
    @FXML
    private TableColumn<Documentation, String> id, docName, startDate, endDate, customerName, projectId;
    FacadeModel facadeModel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            facadeModel = new FacadeModel();
            viewAllProject();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
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
    public void viewAllProject() throws IOException, SQLException {

        ArrayList<Documentation> arrayList = new ArrayList<>();
        arrayList = facadeModel.loadDocumentationData();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        docName.setCellValueFactory(new PropertyValueFactory<>("docName"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        projectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));

        ObservableList<Documentation> observableList = tableView.getItems();
        tableView.getItems().clear();
        observableList.removeAll();
        for (Documentation project: arrayList) {
            observableList.add(project);
            System.out.println(project.toString());
        }
        tableView.setItems(observableList);
    }

    public void setLoggedInEmployee(Employee employee) {
        this.loggedInEmployee = employee;
        usernameLabel.setText(employee.getUsername());
        usernameLabel.setTranslateX(100);
    }


    public void saveLocalButton(ActionEvent event) {
        // Get the selected item from the table view
        Documentation selectedDocument = tableView.getSelectionModel().getSelectedItem();
        if (selectedDocument == null) {
            // No item selected, show an error message
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a document from the table.");
            return;
        }

        // Retrieve the document ID from the selected item
        int documentId = selectedDocument.getId();

        // Prompt the user to choose a file path to save the PDF
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(saveLocalButtonn.getScene().getWindow());

        if (file != null) {
            try {
                // Get the PDF data from the selected document
                String pdfData = selectedDocument.getPdfData();

                // Decode the Base64 PDF data
                byte[] decodedData = Base64.getDecoder().decode(pdfData);

                // Save the decoded PDF data to the chosen file path
                try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                    fileOutputStream.write(decodedData);
                    fileOutputStream.flush();

                    // Display a success message
                    showAlert(Alert.AlertType.INFORMATION, "Success", "PDF saved successfully!");
                } catch (IOException e) {
                    e.printStackTrace();
                    // Display an error message
                    showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while saving the PDF.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Display an error message
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while decoding the PDF.");
            }
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

