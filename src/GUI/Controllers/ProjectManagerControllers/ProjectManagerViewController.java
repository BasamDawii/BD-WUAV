package GUI.Controllers.ProjectManagerControllers;


import BE.Employee;
import BE.ProjectDetails;
import GUI.Models.ProjectManagerModel;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ProjectManagerViewController {
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
    private TableColumn <ProjectDetails, String> projectName;
    @FXML
    private TableColumn <ProjectDetails, String> projectDesc;
    @FXML
    private TableColumn <ProjectDetails, Date> startDate;
    @FXML
    private TableColumn <ProjectDetails, Date> endDate;
    @FXML
    private TableColumn <ProjectDetails, String> customerName;

    public void handleLogoutButton(ActionEvent event) throws IOException, SQLServerException {
        navigateToView("/GUI/Views/LoginView.fxml", event);
    }
    public void viewAllProject(ActionEvent event) throws IOException, SQLServerException {
        splitPane.setVisible(false);
        tableView.setVisible(true);

        ArrayList<ProjectDetails> arrayList = new ArrayList<>();
        arrayList = new ProjectManagerModel().loadData();
        projectName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        projectDesc.setCellValueFactory(new PropertyValueFactory<>("projectDesc"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customer_name"));

        ObservableList<ProjectDetails> observableList = tableView.getItems();
        tableView.getItems().clear();
        observableList.removeAll();
        for (ProjectDetails project: arrayList) {
            observableList.add(project);
        }
        tableView.setItems(observableList);
    }
    public void addTechnician(ActionEvent event) throws IOException, SQLServerException {
        tableView.setVisible(false);
        splitPane.setVisible(true);
        ArrayList<Integer> projectId = new ProjectManagerModel().loadProjectId();
        ArrayList<Integer> technicianId = new ProjectManagerModel().loadTechnicianId();

        ObservableList<String> list1 = comboBox1.getItems();
        ObservableList<String> list2 = comboBox2.getItems();
        comboBox1.getItems().clear();
        comboBox2.getItems().clear();
        for (Integer i: projectId) {
            list1.add(i+"");
        }
        for (Integer i: technicianId) {
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
        usernameLabel.setTranslateX(60);
    }
}
