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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Project;
import model.Task;
import model.dao.ProjectDao;
import model.dao.TaskDao;
import model.dao.MemberDao;

/**
 * FXML Controller class
 *
 * @author Gabriel Porto
 */
public class KanbanController implements Initializable {
    
    private List<Task> tasks = new ArrayList<>();
    private final TaskDao taskDao = new TaskDao();
    private final MemberDao memberDao = new MemberDao();
    private final ProjectDao projectDao = new ProjectDao();
    
    Project project = projectDao.readOne();
    private List<Task> tasksList = new ArrayList();
    private ObservableList<Task> observableTasks;
    
    String [] arrayData = {"A fazer", "Em progresso", "Concluído"};
    List<String> membersList = this.memberDao.readAllByName();

    
    @FXML
    private TableView<Task> tableTasksToDo;
    @FXML
    private TableColumn<Task, String> nameTaskColumnToDo;
    @FXML
    private TableColumn<Task, String> memberTaskColumnToDo;
      
    @FXML
    private TableView<Task> tableTasksInProgress;
    @FXML
    private TableColumn<Task, String> nameTaskColumnInProgress;
    @FXML
    private TableColumn<Task, String> memberTaskColumnInProgress;
    
    @FXML
    private TableView<Task> tableTasksDone;
    @FXML
    private TableColumn<Task, String> nameTaskColumnDone;
    @FXML
    private TableColumn<Task, String> memberTaskColumnDone;
   
    
    @FXML
    private Button backlogButton;
    @FXML
    private Button leaveButton;
    @FXML
    private Button analyticsButton;
    @FXML
    private Button memberButton;
    @FXML
    private Button ProjectButton;
    @FXML
    private Button HomeButton;
    @FXML
    private Button PPButton;
    @FXML
    private ChoiceBox<String> memberChoiceBox;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private Button filterKanbanButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeChoiceBox.getItems().add("Todos");
        typeChoiceBox.getItems().addAll(project.getAllTypes());
        typeChoiceBox.setValue("Todos");
        memberChoiceBox.getItems().add("Todos");
        memberChoiceBox.getItems().addAll(membersList);
        memberChoiceBox.setValue("Todos");
        
        fillTableTasks();
        
        tableTasksToDo.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectTaskInTable(newValue));      
    }    
    
    private void fillTableTasks(){
        // To do tasks
        nameTaskColumnToDo.setCellValueFactory(new PropertyValueFactory<>("name"));
        memberTaskColumnToDo.setCellValueFactory(new PropertyValueFactory<>("member"));
        tasksList = taskDao.readAllByFilter("To Do Stage");
        observableTasks = FXCollections.observableArrayList(tasksList);

        tableTasksToDo.setItems(observableTasks);
        
        // In progress tasks
        nameTaskColumnInProgress.setCellValueFactory(new PropertyValueFactory<>("name"));
        memberTaskColumnInProgress.setCellValueFactory(new PropertyValueFactory<>("member"));
        tasksList = taskDao.readAllByFilter("In Progress Stage");
        observableTasks = FXCollections.observableArrayList(tasksList);

        tableTasksInProgress.setItems(observableTasks);
        
        // Done tasks
        nameTaskColumnDone.setCellValueFactory(new PropertyValueFactory<>("name"));
        memberTaskColumnDone.setCellValueFactory(new PropertyValueFactory<>("member"));
        tasksList = taskDao.readAllByFilter("Done Stage");
        observableTasks = FXCollections.observableArrayList(tasksList);

        tableTasksDone.setItems(observableTasks);
        
    }   

    public void selectTaskInTable(Task task) {
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
    private void transferTaskToDo(MouseEvent event) {
        List<String> dialogData;
        
        Project project = projectDao.readOne();
        
        // Checking double click
        if (event.getClickCount() == 2)  {  

            Dialog dialog = new Dialog();         
            dialogData = Arrays.asList(arrayData);
            dialog = new ChoiceDialog(dialogData.get(0), dialogData);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.getDialogPane().setStyle("-fx-background-color: #a6a6a6;");
            dialog.setGraphic(null);
            dialog.setContentText("Estado: ");
            dialog.setTitle("Transferir task");
            dialog.setHeaderText("\nNome da task: " + 
                    tableTasksToDo.getSelectionModel().getSelectedItem().getName() + "\n\nDescrição: " + 
                    tableTasksToDo.getSelectionModel().getSelectedItem().getDescription() + "\n\nTipo: " +
                    tableTasksToDo.getSelectionModel().getSelectedItem().getType() + "\n\nMembro: " +
                    tableTasksToDo.getSelectionModel().getSelectedItem().getMember() + "\n\n");
            Task task = tableTasksToDo.getSelectionModel().getSelectedItem();

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
                this.projectDao.update(project);
                filterKanban();
            } else {
                System.out.println("Transfer cancelled.");
            }
        }
    }

    @FXML
    private void transferTaskInProgress(MouseEvent event) {
        List<String> dialogData;
        
        Project project = projectDao.readOne();

        if (event.getClickCount() == 2) // Checking double click
        {
            Dialog dialog = new Dialog();
            String [] arrayDataInProgress = {"Em progresso", "A fazer", "Concluído"};
            dialogData = Arrays.asList(arrayDataInProgress);
            dialog = new ChoiceDialog(dialogData.get(0), dialogData);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.getDialogPane().setStyle("-fx-background-color: #a6a6a6;");
            dialog.setGraphic(null);
            dialog.setContentText("Estado: ");
            dialog.setTitle("Transferir task");
            dialog.setHeaderText("\nNome da task: " + 
                    tableTasksInProgress.getSelectionModel().getSelectedItem().getName() + "\n\nDescrição: " + 
                    tableTasksInProgress.getSelectionModel().getSelectedItem().getDescription() + "\n\nTipo: " +
                    tableTasksInProgress.getSelectionModel().getSelectedItem().getType() + "\n\nMembro: " +
                    tableTasksInProgress.getSelectionModel().getSelectedItem().getMember() + "\n\n");
            Task task = tableTasksInProgress.getSelectionModel().getSelectedItem();

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
                this.projectDao.update(project);
                filterKanban();
            } else {
                System.out.println("Transfer cancelled.");
            }
        }
    }

    @FXML
    private void transferTaskDone(MouseEvent event) {
        List<String> dialogData;
        
        Project project = projectDao.readOne();

        if (event.getClickCount() == 2) // Checking double click
        {
            Dialog dialog = new Dialog();
            String [] arrayDataDone = {"Concluído", "A fazer", "Em progresso"};
            dialogData = Arrays.asList(arrayDataDone);
            dialog = new ChoiceDialog(dialogData.get(0), dialogData);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.getDialogPane().setStyle("-fx-background-color: #a6a6a6;");
            dialog.setGraphic(null);
            dialog.setContentText("Estado: ");
            dialog.setTitle("Transferir task");
            dialog.setHeaderText("\nNome da task: " + 
                    tableTasksDone.getSelectionModel().getSelectedItem().getName() + "\n\nDescrição: " +
                    tableTasksDone.getSelectionModel().getSelectedItem().getDescription() + "\n\nTipo: " +
                    tableTasksDone.getSelectionModel().getSelectedItem().getType() + "\n\nMembro: " +
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
                this.projectDao.update(project);
                filterKanban();
            } else {
                System.out.println("Transfer cancelled.");
            }
        }
    }

    @FXML
    private void leaveProject(ActionEvent event) {
        Stage stage;
        stage = (Stage) leaveButton.getScene().getWindow();
        System.out.println("Leaving application...");
        stage.close();
    }

    @FXML
    private void filterKanban() {
               
        if(!this.projectDao.checkEmpty()) {
            
            taskDao.checkFile();
                      
            String chosenType = typeChoiceBox.getValue();
            String chosenMember = memberChoiceBox.getValue();
            
            
            // Caso seja filtrado apenas por Membro.
            if(chosenType == "Todos" && chosenMember != "Todos") {
                List<Task> filteredTasksMember = taskDao.readAllByMember(chosenMember); 
                                         
                // To Do tasks
                List<Task> chosenTypeListToDo = new ArrayList<>();
                for (Task typeList : filteredTasksMember) {
                    if(typeList.getStage().contentEquals("To Do Stage")) {
                        chosenTypeListToDo.add(typeList);
                    }
                }
                
                observableTasks = FXCollections.observableArrayList(chosenTypeListToDo);
                tableTasksToDo.setItems(observableTasks);

                // In progress tasks
                List<Task> chosenTypeListInProgress = new ArrayList<>();
                for (Task typeList : filteredTasksMember) {
                    if(typeList.getStage().contentEquals("In Progress Stage")) {
                        chosenTypeListInProgress.add(typeList);
                    }
                }
                
                observableTasks = FXCollections.observableArrayList(chosenTypeListInProgress);
                tableTasksInProgress.setItems(observableTasks);

                // Done tasks
                List<Task> chosenTypeListDone = new ArrayList<>();
                for (Task typeList : filteredTasksMember) {
                    if(typeList.getStage().contentEquals("Done Stage")) {
                        chosenTypeListDone.add(typeList);
                    }
                }
                
                observableTasks = FXCollections.observableArrayList(chosenTypeListDone);
                tableTasksDone.setItems(observableTasks);

                
            // Caso seja filtrado apenas por Tipo.
            } else if(chosenType != "Todos" && chosenMember == "Todos") {
                List<Task> filteredTasksType = taskDao.readAllByType(chosenType);
                
                // To Do tasks
                List<Task> chosenTypeListToDo = new ArrayList<>();
                for (Task typeList : filteredTasksType) {
                    if(typeList.getStage().contentEquals("To Do Stage")) {
                        chosenTypeListToDo.add(typeList);
                    }
                }
                
                observableTasks = FXCollections.observableArrayList(chosenTypeListToDo);
                tableTasksToDo.setItems(observableTasks);

                // In progress tasks
                List<Task> chosenTypeListInProgress = new ArrayList<>();
                for (Task typeList : filteredTasksType) {
                    if(typeList.getStage().contentEquals("In Progress Stage")) {
                        chosenTypeListInProgress.add(typeList);
                    }
                }
                
                observableTasks = FXCollections.observableArrayList(chosenTypeListInProgress);
                tableTasksInProgress.setItems(observableTasks);

                // Done tasks
                List<Task> chosenTypeListDone = new ArrayList<>();
                for (Task typeList : filteredTasksType) {
                    if(typeList.getStage().contentEquals("Done Stage")) {
                        chosenTypeListDone.add(typeList);
                    }
                }
                
                observableTasks = FXCollections.observableArrayList(chosenTypeListDone);
                tableTasksDone.setItems(observableTasks);
                
                
            // Caso seja filtrado por Tipo e por Membro.  
            } else if(chosenType != "Todos" && chosenMember != "Todos") {
                List<Task> filteredTasksTypeMember = taskDao.readAllByTypeAndMember(chosenType, chosenMember);
                
                // To Do tasks
                List<Task> chosenTypeListToDo = new ArrayList<>();
                for (Task typeList : filteredTasksTypeMember) {
                    if(typeList.getStage().contentEquals("To Do Stage")) {
                        chosenTypeListToDo.add(typeList);
                    }
                }
                
                observableTasks = FXCollections.observableArrayList(chosenTypeListToDo);
                tableTasksToDo.setItems(observableTasks);

                // In progress tasks
                List<Task> chosenTypeListInProgress = new ArrayList<>();
                for (Task typeList : filteredTasksTypeMember) {
                    if(typeList.getStage().contentEquals("In Progress Stage")) {
                        chosenTypeListInProgress.add(typeList);
                    }
                }
                
                observableTasks = FXCollections.observableArrayList(chosenTypeListInProgress);
                tableTasksInProgress.setItems(observableTasks);

                // Done tasks
                List<Task> chosenTypeListDone = new ArrayList<>();
                for (Task typeList : filteredTasksTypeMember) {
                    if(typeList.getStage().contentEquals("Done Stage")) {
                        chosenTypeListDone.add(typeList);
                    }
                }
                
                observableTasks = FXCollections.observableArrayList(chosenTypeListDone);
                tableTasksDone.setItems(observableTasks);
            
            // Caso o filtro seja Todas as tasks
            } else if(chosenType == "Todos" && chosenMember == "Todos") {
                fillTableTasks();
            }
       
        }
    }

    @FXML
    private void selectToDoTable(MouseEvent event) {
        tableTasksInProgress.getSelectionModel().clearSelection();
        tableTasksDone.getSelectionModel().clearSelection();
    }

    @FXML
    private void selectInProgressTable(MouseEvent event) {
        tableTasksToDo.getSelectionModel().clearSelection();
        tableTasksDone.getSelectionModel().clearSelection();
    }

    @FXML
    private void selectDoneTable(MouseEvent event) {
        tableTasksToDo.getSelectionModel().clearSelection();
        tableTasksInProgress.getSelectionModel().clearSelection();
    }

}
