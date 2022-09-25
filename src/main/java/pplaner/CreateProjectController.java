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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Gabriel Porto
 */
public class CreateProjectController implements Initializable {

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
    private DatePicker inputProjectDate;
    @FXML
    private Label labelTaskDescription;
    @FXML
    private Label labelTaskDescription1;
    @FXML
    private TextField inputProjectType1;
    @FXML
    private Label labelTaskDescription11;
    @FXML
    private TextField inputProjectType11;
    @FXML
    private Label labelTaskName;
    @FXML
    private TextField inputProjectType111;
    @FXML
    private Label labelTaskDescription111;
    @FXML
    private Button createTaskButton;
    @FXML
    private Label labelTaskDescription1111;
    @FXML
    private TextField inputProjectType1111;
    @FXML
    private TextField inputTaskName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private void labelMemberNameDetailPressed(KeyEvent event) {
    }

    @FXML
    private void createTask(ActionEvent event) {
    }

    @FXML
    private void nameTaskKeyPressed(KeyEvent event) {
    }
    
}
