/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pplaner;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import model.Task;
import model.dao.TaskDao;
import pplaner.App;

/**
 * FXML Controller class
 *
 * @author Gabriel Porto
 */
public class CreateTaskController implements Initializable {
    
    private List<Task> tasks = new ArrayList<>();
    private final TaskDao taskDao = new TaskDao();
   
    String[] tasksType = {"Prototipagem", "Desenvolvimento", "Documentação", "Testes"};
    
    @FXML
    private TextField inputTaskName;
    @FXML
    private ChoiceBox<String> inputTaskType;
    @FXML
    private TextField inputTaskMember;
    @FXML
    private Button backlogButton;
    @FXML
    private Button leaveButton;
    @FXML
    private Button ppButton;
    @FXML
    private TextField inputTaskDescription;
    @FXML
    private Button createTaskButton;
    @FXML
    private Label labelTaskName;
    @FXML
    private Label labelTaskType;
    @FXML
    private Label labelTaskMember;
    @FXML
    private Label labelTaskDescription;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inputTaskType.getItems().addAll(tasksType);
        inputTaskType.setValue("Desenvolvimento");

    }    
    
    @FXML
    private void createTask(ActionEvent event) throws IOException {
        TaskDao taskDao = new TaskDao();
        this.taskDao.checkFile();
        
        Boolean allCorrect = true;
        
        if(inputTaskName.getText().equals("")) {
            labelTaskName.setStyle("-fx-text-fill: #c71616;");
            inputTaskName.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            inputTaskName.setStyle("");
        }
        
        if(inputTaskType.equals("")) {
            labelTaskType.setStyle("-fx-text-fill: #c71616;");
            inputTaskType.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            inputTaskType.setStyle("");
        }      
        
        if(inputTaskDescription.getText().equals("")) {
            labelTaskDescription.setStyle("-fx-text-fill: #c71616;");
            inputTaskDescription.setStyle("-fx-border-color: red;");

            allCorrect = false;
        } else {
            inputTaskDescription.setStyle("");
        }
        
        if(inputTaskMember.getText().equals("")) {
            labelTaskMember.setStyle("-fx-text-fill: #c71616;");
            inputTaskMember.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            inputTaskMember.setStyle("");
        }
                
        
        if(allCorrect == true) {
            Task task = new Task();
            task.setName(inputTaskName.getText());
            task.setDescription(inputTaskDescription.getText());
            task.setType(inputTaskType.getValue());
            task.setMember(inputTaskMember.getText());
            this.taskDao.create(task);

            App.setRoot("TasksBacklog");
        }
    }

    @FXML
    private void nameTaskKeyPressed(KeyEvent event) {
        inputTaskName.setStyle("");
        labelTaskName.setStyle("");
    }
    
     @FXML
    private void descriptionTaskKeyPressed(KeyEvent event) {
        inputTaskDescription.setStyle("");
        labelTaskDescription.setStyle("");
    }

    @FXML
    private void memberTaskKeyPressed(KeyEvent event) {
        inputTaskMember.setStyle("");
        labelTaskMember.setStyle("");
    }

    @FXML
    private void switchToBacklog(ActionEvent event) throws IOException {
        App.setRoot("TasksBacklog");
        
    }
}
