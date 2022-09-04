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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import model.Task;
import model.dao.TaskDao;

/**
 * FXML Controller class
 *
 * @author Gabriel Porto
 */
public class TasksBacklogController implements Initializable {
    
    private List<Task> tasks = new ArrayList<>();
    private final TaskDao taskDao = new TaskDao();
    
    private List<Task> tasksList = new ArrayList();
    private ObservableList<Task> observableTasks;

    @FXML
    private Button secondaryButton;
    @FXML
    private TableView<Task> tasksBacklog;
    @FXML
    private TableColumn<Task, String> nameTaskColumn;
    @FXML
    private TableColumn<Task, String> typeTaskColumn;
    @FXML
    private TableColumn<Task, String> memberTaskColumn;
    @FXML
    private Button deleteTaskButton;
    @FXML
    private TextField labelTaskNameDetail;
    @FXML
    private TextField labelTaskDescriptionDetail;
    @FXML
    private TextField labelTaskTypeDetail;
    @FXML
    private TextField labelTaskMemberDetail;
    @FXML
    private Button editTaskButton;
    @FXML
    private Button saveChangesButton;
    @FXML
    private Button kanbanButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTasksBacklog();
        labelTaskNameDetail.setDisable(true);
        labelTaskDescriptionDetail.setDisable(true);
        labelTaskTypeDetail.setDisable(true);
        labelTaskMemberDetail.setDisable(true);
        saveChangesButton.setVisible(false);

        tasksBacklog.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectTaskBacklog(newValue));
        
        
    }    
    
    
    private void fillTasksBacklog(){
        nameTaskColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeTaskColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        memberTaskColumn.setCellValueFactory(new PropertyValueFactory<>("member"));
        tasksList = taskDao.readAll();
        observableTasks = FXCollections.observableArrayList(tasksList);

        tasksBacklog.setItems(observableTasks);
    }   
    
    public void selectTaskBacklog(Task task) {
        
        if(!saveChangesButton.isVisible()) {
            labelTaskNameDetail.setText(task.getName());
            labelTaskDescriptionDetail.setText(task.getDescription());
            labelTaskTypeDetail.setText(task.getType());
            labelTaskMemberDetail.setText(task.getMember());
        }
    }

    @FXML
    private void switchToPrimary(ActionEvent event) throws IOException {
        App.setRoot("CreateTask"); 
    }
    
    @FXML
    private void switchToKanban(ActionEvent event) throws IOException {
        App.setRoot("Kanban"); 
    }
    

    @FXML
    private void deleteTask(ActionEvent event) {
        Task task = tasksBacklog.getSelectionModel().getSelectedItem();
        taskDao.delete(task.getId());
        tasksBacklog.getItems().remove(task);
    }
    
    private void showTaskDetails() throws IOException{     
        Task task = tasksBacklog.getSelectionModel().getSelectedItem();
        App.setRoot("TaskDetails"); 
    }

    @FXML
    private void editTask(ActionEvent event) {
        
        
        String value = labelTaskNameDetail.getText();
            if(!value.equals("")) {
            saveChangesButton.setVisible(true);
            labelTaskNameDetail.setDisable(false);
            labelTaskDescriptionDetail.setDisable(false);
            labelTaskTypeDetail.setDisable(false);
            labelTaskMemberDetail.setDisable(false);
            
            }
    }

    @FXML
    private void saveChangesTask(ActionEvent event) {
       
        TaskDao taskDao = new TaskDao();
        this.taskDao.checkFile();
        
        Task task = tasksBacklog.getSelectionModel().getSelectedItem();

        Boolean allCorrect = true;
        
        if(labelTaskNameDetail.getText().equals("")) {
            labelTaskNameDetail.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            labelTaskNameDetail.setStyle("");
        }
        
        if(labelTaskDescriptionDetail.getText().equals("")) {
            labelTaskDescriptionDetail.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            labelTaskDescriptionDetail.setStyle("");
        }
        
        if(labelTaskTypeDetail.getText().equals("")) {
            labelTaskTypeDetail.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            labelTaskTypeDetail.setStyle("");
        }
        
        if(labelTaskMemberDetail.getText().equals("")) {
            labelTaskMemberDetail.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            labelTaskMemberDetail.setStyle("");
        }
               
        
        if(allCorrect == true) {
            task.setName(labelTaskNameDetail.getText());
            task.setDescription(labelTaskDescriptionDetail.getText());
            task.setType(labelTaskTypeDetail.getText());
            task.setMember(labelTaskMemberDetail.getText());
            this.taskDao.update(task);
            fillTasksBacklog();
            labelTaskNameDetail.setDisable(true);
            labelTaskDescriptionDetail.setDisable(true);
            labelTaskTypeDetail.setDisable(true);
            labelTaskMemberDetail.setDisable(true);
            saveChangesButton.setVisible(false);

        }
              

    }

    @FXML
    private void labelTaskNameDetailPressed(KeyEvent event) {
        labelTaskNameDetail.setStyle("");
    }

    @FXML
    private void labelTaskDescriptionDetailPressed(KeyEvent event) {
        labelTaskDescriptionDetail.setStyle("");
    }

    @FXML
    private void labelTaskTypeDetailPressed(KeyEvent event) {
        labelTaskTypeDetail.setStyle("");
    }

    @FXML
    private void labelTaskMemberDetailPressed(KeyEvent event) {
        labelTaskMemberDetail.setStyle("");
    }

}
