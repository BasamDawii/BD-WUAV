package GUI.Controllers.SalespersonControllers;

import BE.Documentation;
import BE.Employee;
import BE.Project;
import BE.ProjectDetails;
import DAL.ProjectManager_DB;
import GUI.Models.ProjectManagerModel;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import java.awt.Desktop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.ResourceBundle;

public class SalespersonViewController implements Initializable {
    private Employee loggedInEmployee;
    @FXML
    private Label usernameLabel;
    @FXML
    private TableView<ProjectDetails> tableView;
    @FXML
    private SplitPane splitPane;
    @FXML
    private ComboBox<String> comboBox1;
    @FXML
    private ComboBox<String> comboBox2;
    @FXML
    private TableColumn<ProjectDetails, String> projectName;
    @FXML
    private TableColumn <ProjectDetails, LocalDate> startDate;
    @FXML
    private TableColumn <ProjectDetails, LocalDate> endDate;
    @FXML
    private TableColumn <ProjectDetails, String> customerName;

    private ProjectManagerModel projectManagerModel;

    @FXML
    private Button printButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            viewAllProject();
        } catch (IOException | SQLServerException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handlePrintButton() {
        // Get the selected project from the table view
        ProjectDetails selectedProject = tableView.getSelectionModel().getSelectedItem();

        if (selectedProject != null) {
            // Retrieve the document from the database using the selected project's ID
            ProjectManager_DB projectManagerDB = new ProjectManager_DB();
            int projectId = selectedProject.getProjectId();
            try {
                Documentation documentation = projectManagerDB.getDocumentation(projectId);
                if (documentation != null) {
                    byte[] pdfData = documentation.getPdfData();

                    // Save the document as a temporary file
                    File tempFile = File.createTempFile("document", ".pdf");
                    try (OutputStream outputStream = new FileOutputStream(tempFile)) {
                        // Write the document data to the file
                        outputStream.write(pdfData);
                    } catch (IOException e) {
                        // Handle the exception appropriately
                        e.printStackTrace();
                        // You may show an error message here if needed
                        return;
                    }

                    // Open the file for printing
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.PRINT)) {
                        desktop.print(tempFile);
                    } else {
                        // Printing is not supported on the current platform
                        // You may show an error message here if needed
                    }

                    // Delete the temporary file
                    tempFile.delete();
                } else {
                    // No documentation found for the specified project
                    // You may show an error message here if needed
                }
            } catch (SQLException | IOException e) {
                // Handle the exception appropriately
                e.printStackTrace();
                // You may show an error message here if needed
            }
        } else {
            // No project is selected, you may show an error message here if needed
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
    public void viewAllProject() throws IOException, SQLServerException {


        ArrayList<ProjectDetails> arrayList = new ArrayList<>();
        arrayList = new ProjectManagerModel().loadData();
        projectName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customer_name"));

        ObservableList<ProjectDetails> observableList = tableView.getItems();
        tableView.getItems().clear();
        observableList.removeAll();
        for (ProjectDetails project: arrayList) {
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
}

