/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pplaner;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    @FXML
    private Button leaveButton;
    @FXML
    private Button ppButton;
    @FXML
    private Button MemberButton;
    
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
            System.out.println("Task selected!");

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
    private void transferTaskToDo(MouseEvent event) {
        String [] arrayData = {"A fazer", "Em progresso", "Concluído"};
        List<String> dialogData;

        if (event.getClickCount() == 2) // Checking double click
        {
            Dialog dialog = new Dialog();
            dialogData = Arrays.asList(arrayData);
            dialog = new ChoiceDialog(dialogData.get(0), dialogData);
            dialog.setGraphic(null);
            dialog.setContentText("Estado: ");
            dialog.setTitle("Transferir task");
            dialog.setHeaderText("\nNome da task: " + 
                    tableTasksToDo.getSelectionModel().getSelectedItem().getName() + "\n\nDescrição: " + 
                    tableTasksToDo.getSelectionModel().getSelectedItem().getDescription() + "\n\nMembro: " +
                    tableTasksToDo.getSelectionModel().getSelectedItem().getMember() + "\n\n");
            Task task = tableTasksToDo.getSelectionModel().getSelectedItem();

            Optional<String> result = dialog.showAndWait();
            String selected = "";
   
            if (result.isPresent()) {
                selected = result.get();
                this.taskDao.checkFile();
                if(selected == "A fazer") {
                    task.setStage("To Do Stage");
                    fillTableTasks();
                }  else if(selected == "Em progresso") {
                    task.setStage("In Progress Stage");
                } else if(selected == "Concluído") {
                    task.setStage("Done Stage");
                }
                this.taskDao.update(task);
                fillTableTasks();
            } else {
                System.out.println("Transfer cancelled.");
            }
        }
    }

    @FXML
    private void transferTaskInProgress(MouseEvent event) {
        String [] arrayData = {"Em progresso", "A fazer", "Concluído"};
        List<String> dialogData;

        if (event.getClickCount() == 2) // Checking double click
        {
            Dialog dialog = new Dialog();
            dialogData = Arrays.asList(arrayData);
            dialog = new ChoiceDialog(dialogData.get(0), dialogData);
            dialog.setGraphic(null);
            dialog.setContentText("Estado: ");
            dialog.setTitle("Transferir task");
            dialog.setHeaderText("\nNome da task: " + 
                    tableTasksInProgress.getSelectionModel().getSelectedItem().getName() + "\n\nDescrição: " + 
                    tableTasksInProgress.getSelectionModel().getSelectedItem().getDescription() + "\n\nMembro: " +
                    tableTasksInProgress.getSelectionModel().getSelectedItem().getMember() + "\n\n");
            Task task = tableTasksInProgress.getSelectionModel().getSelectedItem();

            Optional<String> result = dialog.showAndWait();
            String selected = "";
   
            if (result.isPresent()) {
                selected = result.get();
                this.taskDao.checkFile();
                if(selected == "A fazer") {
                    task.setStage("To Do Stage");
                    fillTableTasks();
                }  else if(selected == "Em progresso") {
                    task.setStage("In Progress Stage");
                } else if(selected == "Concluído") {
                    task.setStage("Done Stage");
                }
                this.taskDao.update(task);
                fillTableTasks();
            } else {
                System.out.println("Transfer cancelled.");
            }
        }
    }

    @FXML
    private void transferTaskDone(MouseEvent event) {
        String [] arrayData = {"Concluído", "A fazer", "Em progresso"};
        List<String> dialogData;

        if (event.getClickCount() == 2) // Checking double click
        {
            Dialog dialog = new Dialog();
            dialogData = Arrays.asList(arrayData);
            dialog = new ChoiceDialog(dialogData.get(0), dialogData);
            dialog.setGraphic(null);
            dialog.setContentText("Estado: ");
            dialog.setTitle("Transferir task");
            dialog.setHeaderText("\nNome da task: " + 
                    tableTasksDone.getSelectionModel().getSelectedItem().getName() + "\n\nDescrição: " + 
                    tableTasksDone.getSelectionModel().getSelectedItem().getDescription() + "\n\nMembro: " +
                    tableTasksDone.getSelectionModel().getSelectedItem().getMember() + "\n\n");
            Task task = tableTasksDone.getSelectionModel().getSelectedItem();

            Optional<String> result = dialog.showAndWait();
            String selected = "";
            
            if (result.isPresent()) {
                selected = result.get();
                this.taskDao.checkFile();
                if(selected == "A fazer") {
                    task.setStage("To Do Stage");
                }  else if(selected == "Em progresso") {
                    task.setStage("In Progress Stage");
                } else if(selected == "Concluído") {
                    task.setStage("Done Stage");
                }
                this.taskDao.update(task);
                fillTableTasks();
            } else {
                System.out.println("Transfer cancelled.");
            }
        }
    }
    
}
