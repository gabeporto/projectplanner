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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Member;
import model.Task;
import model.dao.MemberDao;
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
    
    private List<Member> members = new ArrayList<>();
    private final MemberDao memberDao = new MemberDao();
    
    String[] tasksStage = {"A fazer", "Em progresso", "Concluído"};

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
    private Button ppButton;
    @FXML
    private Button kanbanButton;
    @FXML
    private Button LeaveButton;
    @FXML
    private Label labelName;
    @FXML
    private Label labelDescription;
    @FXML
    private Label labelType;
    @FXML
    private Label labelMember;
    @FXML
    private ChoiceBox<String> labelTaskStageDetail;
    @FXML
    private Label labelStage;
    @FXML
    private Button MemberButton;
    @FXML
    private Button createTaskButton;
    @FXML
    private Button projectButton;
    @FXML
    private Button homeButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTasksBacklog();
        labelTaskStageDetail.getItems().addAll(tasksStage);
        labelTaskStageDetail.setDisable(true);
        labelTaskNameDetail.setDisable(true);
        labelTaskDescriptionDetail.setDisable(true);
        labelTaskTypeDetail.setDisable(true);
        labelTaskMemberDetail.setDisable(true);
        saveChangesButton.setVisible(false);
        editTaskButton.setVisible(false);
        deleteTaskButton.setVisible(false);
        
        if(memberDao.checkEmpty()) {
            createTaskButton.setDisable(true);
        } else {
            createTaskButton.setDisable(false);
        }
        
        tasksBacklog.getSelectionModel().selectedItemProperty().addListener(
                
                (observable, oldValue, newValue) -> 
                {
            try {
                selectTaskBacklog(newValue);
            } catch (InterruptedException ex) {
                Logger.getLogger(TasksBacklogController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }    
      
    private void fillTasksBacklog(){
        nameTaskColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeTaskColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        memberTaskColumn.setCellValueFactory(new PropertyValueFactory<>("member"));
        tasksList = taskDao.readAll();
        observableTasks = FXCollections.observableArrayList(tasksList);

        tasksBacklog.setItems(observableTasks);
    }   
    
    public void selectTaskBacklog(Task task) throws InterruptedException {
        
        editTaskButton.setVisible(true);
        deleteTaskButton.setVisible(true);    
        if(!saveChangesButton.isVisible()) {
            labelTaskNameDetail.setText(task.getName());
            labelTaskDescriptionDetail.setText(task.getDescription());
            String taskStage = task.getStage();
            if(taskStage.contentEquals("To Do Stage")) {
                labelTaskStageDetail.setValue("A fazer");
            } else if(taskStage.contentEquals("In Progress Stage")) {
                labelTaskStageDetail.setValue("Em progresso");
            } else if(taskStage.contentEquals("Done Stage")) {
                labelTaskStageDetail.setValue("Concluído");
            }
            labelTaskTypeDetail.setText(task.getType());
            labelTaskMemberDetail.setText(task.getMember());
            
        } else if(saveChangesButton.isVisible()) {
            labelTaskNameDetail.setDisable(true);
            labelTaskDescriptionDetail.setDisable(true);
            labelTaskStageDetail.setDisable(true);
            labelTaskTypeDetail.setDisable(true);
            labelTaskMemberDetail.setDisable(true);  
            saveChangesButton.setVisible(false);
            
            labelTaskNameDetail.setText("");
            labelTaskDescriptionDetail.setText("");
            labelTaskTypeDetail.setText("");
            labelTaskMemberDetail.setText("");
        }
    }

    @FXML
    private void switchToCreateTask(ActionEvent event) throws IOException {
        App.setRoot("CreateTask"); 
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
    private void deleteTask(ActionEvent event) {
        Task task = tasksBacklog.getSelectionModel().getSelectedItem();
        taskDao.delete(task.getId());
        tasksBacklog.getItems().remove(task);
        
        saveChangesButton.setVisible(false);
        editTaskButton.setVisible(false);
        deleteTaskButton.setVisible(false);
        
    }
    
    private void showTaskDetails() throws IOException{     
        Task task = tasksBacklog.getSelectionModel().getSelectedItem();
        App.setRoot("TaskDetails"); 
    }

    @FXML
    private void editTask(ActionEvent event) {

        Task taskSelected = tasksBacklog.getSelectionModel().getSelectedItem();
        
        // Quando nenhuma task foi selecionada e não habilitada para edição.
        String value = labelTaskNameDetail.getText();
            if(!value.equals("")) {
                saveChangesButton.setVisible(true);
                labelTaskNameDetail.setDisable(false);
                labelTaskDescriptionDetail.setDisable(false);
                labelTaskStageDetail.setDisable(false);
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
            labelName.setStyle("-fx-text-fill: #c71616;");
            labelTaskNameDetail.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            labelTaskNameDetail.setStyle("");
        }
        
        if(labelTaskDescriptionDetail.getText().equals("")) {
            labelDescription.setStyle("-fx-text-fill: #c71616;");
            labelTaskDescriptionDetail.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            labelTaskDescriptionDetail.setStyle("");
        }
        
        if(labelTaskTypeDetail.getText().equals("")) {
            labelType.setStyle("-fx-text-fill: #c71616;");
            labelTaskTypeDetail.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            labelTaskTypeDetail.setStyle("");
        }
        
        if(labelTaskStageDetail.getValue().equals("")) {
            labelStage.setStyle("-fx-text-fill: #c71616;");
            labelTaskStageDetail.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            labelTaskStageDetail.setStyle("");
        }
        
        if(labelTaskMemberDetail.getText().equals("")) {
            labelMember.setStyle("-fx-text-fill: #c71616;");
            labelTaskMemberDetail.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            labelTaskMemberDetail.setStyle("");
        }
                  
        if(allCorrect == true) {
            task.setName(labelTaskNameDetail.getText());
            task.setDescription(labelTaskDescriptionDetail.getText());
            task.setType(labelTaskTypeDetail.getText());
            
            if(labelTaskStageDetail.getValue() == "A fazer") {
                task.setStage("To Do Stage");
            } else if(labelTaskStageDetail.getValue() == "Em Progresso") {
                task.setStage("In Progress Stage");
            } else if(labelTaskStageDetail.getValue() == "Concluído") {
                task.setStage("Done Stage");
            }
            
            task.setMember(labelTaskMemberDetail.getText());
            this.taskDao.update(task);
            fillTasksBacklog();
            labelTaskNameDetail.setDisable(true);
            labelTaskDescriptionDetail.setDisable(true);
            labelTaskStageDetail.setDisable(true);
            labelTaskTypeDetail.setDisable(true);
            labelTaskMemberDetail.setDisable(true);
            saveChangesButton.setVisible(false);
        }     
    }

    @FXML
    private void labelTaskNameDetailPressed(KeyEvent event) {
        labelTaskNameDetail.setStyle("");
        labelName.setStyle("");
    }

    @FXML
    private void labelTaskDescriptionDetailPressed(KeyEvent event) {
        labelTaskDescriptionDetail.setStyle("");
        labelDescription.setStyle("");
    }
    
    @FXML
    private void labelTaskTypeDetailPressed(KeyEvent event) {
        labelTaskTypeDetail.setStyle("");
        labelType.setStyle("");
    }
    
    
    @FXML
    private void labelTaskStageDetailPressed(MouseEvent event) {
        labelTaskStageDetail.setStyle("");
        labelStage.setStyle("");
    }

    @FXML
    private void labelTaskMemberDetailPressed(KeyEvent event) {
        labelTaskMemberDetail.setStyle("");
        labelMember.setStyle("");     
    }

}
