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
import javafx.stage.Stage;
import model.Member;
import model.Project;
import model.Task;
import model.dao.MemberDao;
import model.dao.ProjectDao;
import model.dao.TaskDao;

/**
 * FXML Controller class
 *
 * @author Gabriel Porto
 */
public class TasksBacklogController implements Initializable {
    
    private List<Task> tasks = new ArrayList<>();
    private final TaskDao taskDao = new TaskDao();
    private final ProjectDao projectDao = new ProjectDao();
    Project project = projectDao.readOne();
    
    String[] tasksStage = {"A fazer", "Em progresso", "Concluído"};
    private List<Task> tasksList = new ArrayList();
    private ObservableList<Task> observableTasks;
    
    private List<Member> members = new ArrayList<>();
    private final MemberDao memberDao = new MemberDao();
    List<String> membersList = this.memberDao.readAllByName();
    
    String previousTaskStage;
    String previousTaskType;

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
    private ChoiceBox<String> labelTaskTypeDetail;
    @FXML
    private ChoiceBox<String> labelTaskMemberDetail;
    @FXML
    private Button editTaskButton;
    @FXML
    private Button saveChangesButton;
    @FXML
    private Button kanbanButton;
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
    private Button leaveButton;
    @FXML
    private Button analyticsButton;
    @FXML
    private Button ProjectButton;
    @FXML
    private Button HomeButton;
    @FXML
    private Button PPButton;
    @FXML
    private Button filterKanbanButton;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private ChoiceBox<String> memberChoiceBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTasksBacklog();
        labelTaskStageDetail.getItems().addAll(tasksStage);
        labelTaskStageDetail.setDisable(true);
        labelTaskTypeDetail.getItems().addAll(project.getAllTypes());
        labelTaskMemberDetail.getItems().addAll(membersList);
        labelTaskTypeDetail.setDisable(true);   
        labelTaskNameDetail.setDisable(true);
        labelTaskDescriptionDetail.setDisable(true);
        labelTaskTypeDetail.setDisable(true);
        labelTaskMemberDetail.setDisable(true);
        saveChangesButton.setVisible(false);
        editTaskButton.setVisible(false);
        deleteTaskButton.setVisible(false);
        
        typeChoiceBox.getItems().add("Todos");
        typeChoiceBox.getItems().addAll(project.getAllTypes());
        typeChoiceBox.setValue("Todos");
        memberChoiceBox.getItems().add("Todos");
        memberChoiceBox.getItems().addAll(membersList);
        memberChoiceBox.setValue("Todos");
        
        
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
            labelTaskTypeDetail.setValue(task.getType());
            labelTaskMemberDetail.setValue(task.getMember());
            
        } else if(saveChangesButton.isVisible()) {
            labelTaskNameDetail.setDisable(true);
            labelTaskDescriptionDetail.setDisable(true);
            labelTaskStageDetail.setDisable(true);
            labelTaskTypeDetail.setDisable(true);
            labelTaskMemberDetail.setDisable(true);  
            saveChangesButton.setVisible(false);
            
            labelTaskNameDetail.setText("");
            labelTaskDescriptionDetail.setText("");
            labelTaskTypeDetail.setValue("");
            labelTaskMemberDetail.setValue("");
            editTaskButton.setVisible(false);
            deleteTaskButton.setVisible(false);
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
    private void switchToAboutUs(ActionEvent event) throws IOException {
        App.setRoot("AboutUs");
    }
    
    @FXML
    private void switchToAnalytics(ActionEvent event) throws IOException {
        App.setRoot("Analytics");
    }
    
    @FXML
    private void deleteTask(ActionEvent event) {
        Project project = projectDao.readOne();
        Task task = tasksBacklog.getSelectionModel().getSelectedItem();
        String taskStage = task.getStage();
        
        projectDao.update(project);
        
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
        previousTaskStage = taskSelected.getStage();
        previousTaskType = taskSelected.getType();
        
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
       
        Project project = projectDao.readOne();
        TaskDao taskDao = new TaskDao();
        this.taskDao.checkFile();
        
        Task task = tasksBacklog.getSelectionModel().getSelectedItem();

        Boolean allCorrect = true;
        
        if(labelTaskNameDetail.getText().equals("")) {
            labelName.setStyle("-fx-text-fill: #c71616;");
            labelTaskNameDetail.setStyle("-fx-border-color: red; -fx-border-radius: 10px;");
            allCorrect = false;
        } else {
            labelTaskNameDetail.setStyle("");
        }
        
        if(labelTaskDescriptionDetail.getText().equals("")) {
            labelDescription.setStyle("-fx-text-fill: #c71616;");
            labelTaskDescriptionDetail.setStyle("-fx-border-color: red; -fx-border-radius: 10px;");
            allCorrect = false;
        } else {
            labelTaskDescriptionDetail.setStyle("");
        }
        
        if(labelTaskTypeDetail.getValue().equals("")) {
            labelType.setStyle("-fx-text-fill: #c71616;");
            labelTaskTypeDetail.setStyle("-fx-border-color: red; -fx-border-radius: 10px;");
            allCorrect = false;
        } else {
            labelTaskTypeDetail.setStyle("");
        }
        
        if(labelTaskStageDetail.getValue().equals("")) {
            labelStage.setStyle("-fx-text-fill: #c71616;");
            labelTaskStageDetail.setStyle("-fx-border-color: red; -fx-border-radius: 10px;");
            allCorrect = false;
        } else {
            labelTaskStageDetail.setStyle("");
        }
        
        if(labelTaskMemberDetail.getValue().equals("")) {
            labelMember.setStyle("-fx-text-fill: #c71616;");
            labelTaskMemberDetail.setStyle("-fx-border-color: red; -fx-border-radius: 10px;");
            allCorrect = false;
        } else {
            labelTaskMemberDetail.setStyle("");
        }
                  
        if(allCorrect == true) {
            task.setName(labelTaskNameDetail.getText());
            task.setDescription(labelTaskDescriptionDetail.getText());
            task.setType(labelTaskTypeDetail.getValue());
                       
            List<Integer> typeActive = new ArrayList<>();
            int typeIndex = labelTaskTypeDetail.getSelectionModel().getSelectedIndex();
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
            
            
            if(labelTaskStageDetail.getValue() == "A fazer") {
                task.setStage("To Do Stage");
            } else if(labelTaskStageDetail.getValue() == "Em progresso") {
                task.setStage("In Progress Stage");
            } else if(labelTaskStageDetail.getValue() == "Concluído") {
                task.setStage("Done Stage");
            }
            
            
            task.setMember(labelTaskMemberDetail.getValue());
            this.projectDao.update(project);
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
    private void labelTaskStageDetailPressed(MouseEvent event) {
        labelTaskStageDetail.setStyle("");
        labelStage.setStyle("");
    }

    private void labelTaskMemberDetailPressed(KeyEvent event) {
        labelTaskMemberDetail.setStyle("");
        labelMember.setStyle("");     
    }

    @FXML
    private void leaveProject(ActionEvent event) {
        Stage stage;
        stage = (Stage) leaveButton.getScene().getWindow();
        System.out.println("Leaving application...");
        stage.close();
    }

    @FXML
    private void filterKanban(ActionEvent event) {
        if(!this.projectDao.checkEmpty()) {
            
            taskDao.checkFile();
                      
            String chosenType = typeChoiceBox.getValue();
            String chosenMember = memberChoiceBox.getValue();
            
       
            // Caso seja filtrado apenas por Membro.
            if(chosenType == "Todos" && chosenMember != "Todos") {
                List<Task> filteredTasksMember = taskDao.readAllByMember(chosenMember); 
                                                       
                observableTasks = FXCollections.observableArrayList(filteredTasksMember);
                tasksBacklog.setItems(observableTasks);
               
            // Caso seja filtrado apenas por Tipo.
            } else if(chosenType != "Todos" && chosenMember == "Todos") {
                List<Task> filteredTasksType = taskDao.readAllByType(chosenType);

                observableTasks = FXCollections.observableArrayList(filteredTasksType);
                tasksBacklog.setItems(observableTasks);

            // Caso seja filtrado por Tipo e por Membro.  
            } else if(chosenType != "Todos" && chosenMember != "Todos") {
                List<Task> filteredTasksTypeMember = taskDao.readAllByTypeAndMember(chosenType, chosenMember);
                
                observableTasks = FXCollections.observableArrayList(filteredTasksTypeMember);
                tasksBacklog.setItems(observableTasks);
              
            // Caso o filtro seja Todas as tasks
            } else if(chosenType == "Todos" && chosenMember == "Todos") {
                fillTasksBacklog();
            }
       
        }
        
    }

}
