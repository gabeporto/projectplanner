/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pplaner;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

/**
 * FXML Controller class
 *
 * @author Gabriel Porto
 */
public class HomeController implements Initializable {

    @FXML
    private Button kanbanButton;
    @FXML
    private Button backlogButton;
    @FXML
    private Button memberButton;
    @FXML
    private Button LeaveButton;
    @FXML
    private Button ppButton;
    @FXML
    private Label labelProjectType1;
    @FXML
    private ProgressBar progressProjectType1;
    @FXML
    private ProgressBar progressProjectType2;
    @FXML
    private Label labelProjectType2;
    @FXML
    private Label labelProjectType3;
    @FXML
    private ProgressBar progressProjectType3;
    @FXML
    private ProgressBar progressProjectType4;
    @FXML
    private Label labelProjectType4;
    @FXML
    private Label labelProjectName;
    @FXML
    private Label labelCompletedTasks;
    @FXML
    private Button ProjectButton;
    @FXML
    private ProgressIndicator projectProgress;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progressProjectType1.setProgress(0.5);
        progressProjectType2.setProgress(0.3);
        progressProjectType3.setProgress(0.7);
        progressProjectType4.setProgress(0.9);
        projectProgress.setProgress(0.8);
        
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
    private void switchToProject(ActionEvent event) throws IOException {
        App.setRoot("Project");
    }
    
}
