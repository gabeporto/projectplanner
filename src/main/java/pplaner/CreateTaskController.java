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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Member;
import model.Project;
import model.Task;
import model.dao.ProjectDao;
import model.dao.MemberDao;
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
    private final MemberDao memberDao = new MemberDao();
    private final ProjectDao projectDao = new ProjectDao();
    
    List<String> membersList = this.memberDao.readAllByName();
    String[] membersName = membersList.toArray(new String[membersList.size()]);
    
    
    Project project = projectDao.readOne();
    
    List<String> tasksType = project.getAllTypes();

    
    @FXML
    private TextField inputTaskName;
    @FXML
    private ChoiceBox<String> inputTaskType;
    @FXML
    private ChoiceBox<String> inputTaskMember;
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
    @FXML
    private Button kanbanButton;
    @FXML
    private Button memberButton;
    @FXML
    private Button ProjectButton;
    @FXML
    private Button HomeButton;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inputTaskType.getItems().addAll(tasksType);
        inputTaskType.setValue(tasksType.get(0));
        inputTaskMember.getItems().addAll(membersList);
        inputTaskMember.setValue(membersName[0]);

    }    
    
    @FXML
    private void createTask(ActionEvent event) throws IOException {
        TaskDao taskDao = new TaskDao();
        MemberDao memberDao = new MemberDao();
        List<Member> membersList;
        membersList = this.memberDao.readAll();
        this.taskDao.checkFile();
        this.memberDao.checkFile();
        
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
        
        if(inputTaskMember.equals("")) {
            labelTaskMember.setStyle("-fx-text-fill: #c71616;");
            inputTaskMember.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            inputTaskMember.setStyle("");
        }  
        
        
        if(allCorrect == true) {
            Task task = new Task();
            Project project = projectDao.readOne();
            task.setName(inputTaskName.getText());
            task.setDescription(inputTaskDescription.getText());      
            task.setType(inputTaskType.getValue());
            
            List<Integer> typeActive = new ArrayList<>();
            int typeIndex = inputTaskType.getSelectionModel().getSelectedIndex();
            switch (typeIndex) {
                case 0:
                    typeActive.add(1);
                    typeActive.add(0);
                    typeActive.add(0);
                    typeActive.add(0);
                    break;
                case 1:
                    typeActive.add(0);
                    typeActive.add(1);
                    typeActive.add(0);
                    typeActive.add(0);
                    break;
                case 2:
                    typeActive.add(0);
                    typeActive.add(0);
                    typeActive.add(1);
                    typeActive.add(0);
                    break;
                case 3:
                    typeActive.add(0);
                    typeActive.add(0);
                    typeActive.add(0);
                    typeActive.add(1);
                    break;
                default:
                    break;
            }
            
            task.setTypeActive(typeActive);
            
            task.setMember(inputTaskMember.getValue());
            this.taskDao.create(task);
            
            this.projectDao.update(project);
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

    @FXML
    private void switchToKanban(ActionEvent event) throws IOException {
        App.setRoot("Kanban");
    }

    @FXML
    private void switchToMember(ActionEvent event) throws IOException {
        App.setRoot("Member");
    }

    @FXML
    private void switchToProject(ActionEvent event) throws IOException {
        App.setRoot("Project");
    }

    @FXML
    private void switchToHome(ActionEvent event) throws IOException {
        App.setRoot("Home");
    }
    
    @FXML
    private void switchToAboutUs(ActionEvent event) throws IOException {
        App.setRoot("AboutUs");
    }

    @FXML
    private void leaveProject(ActionEvent event) {
        Stage stage;
        stage = (Stage) leaveButton.getScene().getWindow();
        System.out.println("Leaving application...");
        stage.close();
    }
}
