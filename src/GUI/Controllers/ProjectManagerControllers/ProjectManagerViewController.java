package GUI.Controllers.ProjectManagerControllers;


import BE.Documentation;
import BE.Employee;
import BE.Project;
import BE.Technician;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProjectManagerViewController implements Initializable {
    private Employee loggedInEmployee;
    @FXML
    private Label usernameLabel;

    @FXML
    private SplitPane splitPane;
    @FXML
    private ComboBox<Project> comboBox1;
    @FXML
    private ComboBox<Technician> comboBox2;

    @FXML
    private TableView<Documentation> tableView;
    @FXML
    private TableColumn<Documentation, String> id, docName, startDate, endDate, customerName, projectId;
    FacadeModel facadeModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            facadeModel = new FacadeModel();
            checkProjectStatus();
            viewAllProject();
            addTechnician();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void handleLogoutButton(ActionEvent event) throws IOException, SQLServerException {
        navigateToView("/GUI/Views/LoginView.fxml", event);
    }
    public void viewAllProject() throws IOException, SQLException {

        ArrayList<Documentation> arrayList = new ArrayList<>();
        arrayList = facadeModel.loadData();
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
        }
        tableView.setItems(observableList);
    }
    public void addTechnician() throws IOException, SQLException {
        ObservableList<Project> projectId = facadeModel.getAllProjects();
        ObservableList<Technician> technicianId = facadeModel.getAllTechnicians();

        ObservableList<Project> list1 = comboBox1.getItems();
        ObservableList<Technician> list2 = comboBox2.getItems();
        comboBox1.getItems().clear();
        comboBox2.getItems().clear();
        for (Project i: projectId) {
            list1.add(i);
        }
        for (Technician i: technicianId) {
            list2.add(i);
        }

    }

    public Integer getSelectedProjectId(){
        Project selectedProject = (Project) comboBox1.getSelectionModel().getSelectedItem();
        return selectedProject != null ? selectedProject.getId() : null;
    }

    public Integer getSelectedTechnicianId(){
        Technician selectedTechnician = (Technician) comboBox2.getSelectionModel().getSelectedItem();
        return selectedTechnician != null ? selectedTechnician.getId() : null;
    }

    public void confirmTechnician(ActionEvent event) throws SQLException {
        Integer projectId = getSelectedProjectId();
        Integer technicianId = getSelectedTechnicianId();

        if (projectId == null || technicianId == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a project and a technician.");
            return;
        }

        try {
            boolean added = facadeModel.addEmpProject(projectId, technicianId);
            if (added) {
                showAlert(Alert.AlertType.INFORMATION, "Technician Status", "Technician Added");
            } else {
                showAlert(Alert.AlertType.ERROR, "Technician Status", "[ERROR]! Technician Not Added");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void navigateToView(String viewPath, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
        Parent root = loader.load(getClass().getResource(viewPath));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setLoggedInEmployee(Employee employee) {
        this.loggedInEmployee = employee;
        usernameLabel.setText(employee.getUsername());
        usernameLabel.setTranslateX(90);
    }

    public void tableViewSelected(MouseEvent event) {
    }

    public void handleDeleteButton(ActionEvent event) throws SQLException {
        // Get the selected documentation from the table view
        Documentation selectedDocumentation = tableView.getSelectionModel().getSelectedItem();
        if (selectedDocumentation == null) {
            // No item selected, show an error message
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a document from the table.");
            return;
        }

        // Display a confirmation dialog before deleting
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Documentation");
        alert.setContentText("Are you sure you want to delete the selected documentation?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed the deletion, proceed with deleting the documentation

            // Call the delete method in your ProjectManagerModel or ProjectManager_DB to delete the documentation from the database
            boolean deleted = facadeModel.deleteDocumentation(selectedDocumentation);
            if (deleted) {
                // Remove the selected documentation from the table view
                tableView.getItems().remove(selectedDocumentation);

                // Show a success message
                showAlert(Alert.AlertType.INFORMATION, "Success", "Documentation deleted successfully!");
            } else {
                // Show an error message if deletion fails
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete the documentation.");
            }
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void checkProjectStatus() {
        ArrayList<Documentation> projects = null;
        try {
            projects = facadeModel.loadData();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        LocalDate currentDate = LocalDate.now();

        for (Documentation project : projects) {
            LocalDate startDate = project.getStartDate();
            long monthsSinceStart = ChronoUnit.MONTHS.between(startDate, currentDate);

            if (monthsSinceStart >= 48) {
                String projectName = project.getDocName(); // Update this line
                String message = "Project " + projectName + " has reached 48 months.";
                showAlert(Alert.AlertType.WARNING, "Project Exceeded 48 Months", message);
                // Handle deletion or extension of the project based on user's choice
            }
        }
    }
}
