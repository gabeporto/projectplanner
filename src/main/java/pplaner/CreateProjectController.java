/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pplaner;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Project;
import model.dao.ProjectDao;

/**
 * FXML Controller class
 *
 * @author Gabriel Porto
 */
public class CreateProjectController implements Initializable {
    
    
    ProjectDao projectDao = new ProjectDao();

    @FXML
    private Button kanbanButton;
    @FXML
    private Button backlogButton;
    @FXML
    private Button memberButton;
    @FXML
    private DatePicker inputProjectDate;
    @FXML
    private TextField inputProjectType1;
    @FXML
    private Button createTaskButton;
    @FXML
    private TextField inputProjectType2;
    @FXML
    private TextField inputProjectType3;
    @FXML
    private TextField inputProjectType4;
    @FXML
    private Label labelProjectName;
    @FXML
    private TextField inputProjectName;
    @FXML
    private Button HomeButton;
    @FXML
    private Label labelProjectDate;
    @FXML
    private Label labelProjectType1;
    @FXML
    private Label labelProjectType2;
    @FXML
    private Label labelProjectType3;
    @FXML
    private Label labelProjectType4;
    @FXML
    private Button leaveButton;
    @FXML
    private Button analyticsButton;
    @FXML
    private Button ProjectButton;
    @FXML
    private Button PPButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inputProjectDate.setValue(LocalDate.now());
    }    

    @FXML
    private void switchToKanban(ActionEvent event) throws IOException {
        App.setRoot("Kanban");
    }

    @FXML
    private void switchToBacklog(ActionEvent event) throws IOException {
        App.setRoot("TasksBacklog");
    }
    
    @FXML
    private void switchToMember(ActionEvent event) throws IOException {
        App.setRoot("Member"); 
    }
    
    @FXML
    private void switchToHome(ActionEvent event) throws IOException {
        App.setRoot("Home"); 
    }
    
    @FXML
    private void switchToProject(ActionEvent event) throws IOException {
        App.setRoot("Project");
    }
    
    @FXML
    private void switchToAboutUs(ActionEvent event) throws IOException {
        App.setRoot("AboutUs");
    }
    
    @FXML
    private void switchToAnalytics(ActionEvent event) throws IOException {
        App.setRoot("Analytics");
    }

    @FXML
    private void createProject(ActionEvent event) throws IOException {
        Project project = new Project();
        
        Boolean allCorrect = true;
        
        if(inputProjectName.getText().equals("")) {
            labelProjectName.setStyle("-fx-text-fill: #c71616;");
            inputProjectName.setStyle("-fx-border-color: red; -fx-border-radius: 10px;");
            allCorrect = false;
        } else {
            labelProjectName.setStyle("");
            inputProjectName.setStyle("");
        }

        if(inputProjectType1.getText().equals("")) {
            labelProjectType1.setStyle("-fx-text-fill: #c71616;");
            inputProjectType1.setStyle("-fx-border-color: red; -fx-border-radius: 10px;");
            allCorrect = false;
        } else {
            labelProjectType1.setStyle("");
            inputProjectType1.setStyle("");
        }
        
        if(inputProjectType2.getText().equals("")) {
            labelProjectType2.setStyle("-fx-text-fill: #c71616;");
            inputProjectType2.setStyle("-fx-border-color: red; -fx-border-radius: 10px;");
            allCorrect = false;
        } else {
            labelProjectType2.setStyle("");
            inputProjectType2.setStyle("");
        }
        
        if(inputProjectType3.getText().equals("")) {
            labelProjectType3.setStyle("-fx-text-fill: #c71616;");
            inputProjectType3.setStyle("-fx-border-color: red; -fx-border-radius: 10px;");
            allCorrect = false;
        } else {
            labelProjectType3.setStyle("");
            inputProjectType3.setStyle("");
        }
        
        if(inputProjectType4.getText().equals("")) {
            labelProjectType4.setStyle("-fx-text-fill: #c71616;");
            inputProjectType4.setStyle("-fx-border-color: red; -fx-border-radius: 10px;");
            allCorrect = false;
        } else {
            labelProjectType4.setStyle("");
            inputProjectType4.setStyle("");
        }
        
        if(allCorrect) {
            project.setName(inputProjectName.getText());
            LocalDate date = inputProjectDate.getValue();
            project.setDeliveryDate(date.toString());
            project.setType1(inputProjectType1.getText());
            project.setType2(inputProjectType2.getText());
            project.setType3(inputProjectType3.getText());
            project.setType4(inputProjectType4.getText());

            List<String> types = new ArrayList();
            types.add(project.getType1());
            types.add(project.getType2());
            types.add(project.getType3());
            types.add(project.getType4());
            project.setAllTypes(types);
            this.projectDao.create(project);

            App.setRoot("Project");

        }
    }

    @FXML
    private void projectNameKeyPressed(KeyEvent event) {
        labelProjectName.setStyle("");
        inputProjectName.setStyle("");
    }
    
    @FXML
    private void projectType1KeyPressed(KeyEvent event) {
        labelProjectType1.setStyle("");
        inputProjectType1.setStyle("");
    }

    @FXML
    private void projectType2KeyPressed(KeyEvent event) {
        labelProjectType2.setStyle("");
        inputProjectType2.setStyle("");
    }

    @FXML
    private void projectType3KeyPressed(KeyEvent event) {
        labelProjectType3.setStyle("");
        inputProjectType3.setStyle("");
    }

    @FXML
    private void projectType4KeyPressed(KeyEvent event) {
        labelProjectType4.setStyle("");
        inputProjectType4.setStyle("");
    }

    @FXML
    private void leaveProject(ActionEvent event) {
        Stage stage;
        stage = (Stage) leaveButton.getScene().getWindow();
        System.out.println("Leaving application...");
        stage.close();
    }


}
