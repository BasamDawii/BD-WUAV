package GUI.Controllers.ProjectManagerControllers;


import BE.Documentation;
import BE.Employee;
import GUI.Models.ProjectManagerModel;
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
    private ComboBox<String> comboBox1;
    @FXML
    private ComboBox<String> comboBox2;

    @FXML
    private TableView<Documentation> tableView;
    @FXML
    private TableColumn<Documentation, String> id, docName, startDate, endDate, customerName, projectId;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            viewAllProject();
            addTechnician();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        }
    }


    public void handleLogoutButton(ActionEvent event) throws IOException, SQLServerException {
        navigateToView("/GUI/Views/LoginView.fxml", event);
    }
    public void viewAllProject() throws IOException, SQLServerException {

        ArrayList<Documentation> arrayList = new ArrayList<>();
        arrayList = new ProjectManagerModel().loadData();
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
    public void addTechnician() throws IOException, SQLServerException {
        ArrayList<String> projectId = new ProjectManagerModel().loadProjectNames();
        ArrayList<String> technicianId = new ProjectManagerModel().loadTechnicianNames();

        ObservableList<String> list1 = comboBox1.getItems();
        ObservableList<String> list2 = comboBox2.getItems();
        comboBox1.getItems().clear();
        comboBox2.getItems().clear();
        for (String i: projectId) {
            list1.add(i+"");
        }
        for (String i: technicianId) {
            list2.add(i+"");
        }

    }
    public void confirmTechnician(ActionEvent event) throws IOException, SQLServerException {
        int projectId = Integer.parseInt(comboBox1.getValue());
        int technicianId = Integer.parseInt(comboBox2.getValue());
        boolean added = new ProjectManagerModel().addEmpProject(projectId,technicianId);
        if (added){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Technician Status");
            alert.setHeaderText("Technician Status");
            alert.setContentText("Technician Added");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Technician Status");
            alert.setHeaderText("Technician Status");
            alert.setContentText("[ERROR]! Technician Not Added");
            alert.showAndWait();
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

    public void handleDeleteButton(ActionEvent event) {
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
            boolean deleted = new ProjectManagerModel().deleteDocumentation(selectedDocumentation);
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
}
