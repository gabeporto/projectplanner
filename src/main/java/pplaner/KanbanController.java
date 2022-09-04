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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Task;
import model.dao.TaskDao;

/**
 * FXML Controller class
 *
 * @author Gabriel Porto
 */
public class KanbanController implements Initializable {
    
    private List<Task> tasks = new ArrayList<>();
    private final TaskDao taskDao = new TaskDao();
    
    private List<Task> tasksList = new ArrayList();
    private ObservableList<Task> observableTasks;

    
    @FXML
    private TableView<Task> tableTasksToDo;
    @FXML
    private TableColumn<Task, String> nameTaskColumnToDo;
    @FXML
    private TableColumn<Task, String> typeTaskColumnToDo;
    @FXML
    private TableColumn<Task, String> memberTaskColumnToDo;
    
    
    @FXML
    private TableView<Task> tableTasksInProgress;
    @FXML
    private TableColumn<Task, String> nameTaskColumnInProgress;
    @FXML
    private TableColumn<Task, String> typeTaskColumnInProgress;
    @FXML
    private TableColumn<Task, String> memberTaskColumnInProgress;
    
    @FXML
    private TableView<Task> tableTasksDone;
    @FXML
    private TableColumn<Task, String> nameTaskColumnDone;
    @FXML
    private TableColumn<Task, String> typeTaskColumnDone;
    @FXML
    private TableColumn<Task, String> memberTaskColumnDone;
   
    
    @FXML
    private Button backlogButton;
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTableTasks();
        
        tableTasksToDo.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectTaskInTabke(newValue));
    }    
    
    private void fillTableTasks(){
        // To do tasks
        nameTaskColumnToDo.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeTaskColumnToDo.setCellValueFactory(new PropertyValueFactory<>("type"));
        memberTaskColumnToDo.setCellValueFactory(new PropertyValueFactory<>("member"));
        tasksList = taskDao.readAllByFilter("To Do Stage");
        observableTasks = FXCollections.observableArrayList(tasksList);

        tableTasksToDo.setItems(observableTasks);
        
        // In progress tasks
        nameTaskColumnInProgress.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeTaskColumnInProgress.setCellValueFactory(new PropertyValueFactory<>("type"));
        memberTaskColumnInProgress.setCellValueFactory(new PropertyValueFactory<>("member"));
        tasksList = taskDao.readAllByFilter("In Progress Stage");
        observableTasks = FXCollections.observableArrayList(tasksList);

        tableTasksInProgress.setItems(observableTasks);
        
        // Done tasks
        nameTaskColumnDone.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeTaskColumnDone.setCellValueFactory(new PropertyValueFactory<>("type"));
        memberTaskColumnDone.setCellValueFactory(new PropertyValueFactory<>("member"));
        tasksList = taskDao.readAllByFilter("Done Stage");
        observableTasks = FXCollections.observableArrayList(tasksList);

        tableTasksDone.setItems(observableTasks);
        
    }   
    

    public void selectTaskInTabke(Task task) {
            System.out.print("Select Task!");

        }

    @FXML
    private void switchToBacklog(ActionEvent event) throws IOException {
        App.setRoot("TasksBacklog"); 
    }
    
}
