/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pplaner;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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
public class ProjectController implements Initializable {
        
    private final ProjectDao projectDao = new ProjectDao();
    
    private List<Project> projectsList = new ArrayList();
    private ObservableList<Project> observableProjects;
    
    String oldType1;
    String oldType2;
    String oldType3;
    String oldType4;

    @FXML
    private Button kanbanButton;
    @FXML
    private Button backlogButton;
    @FXML
    private Button ppButton;
    @FXML
    private Button saveChangesButton;
    @FXML
    private Label labelProjectName;
    @FXML
    private Label labelProjectType1;
    @FXML
    private TextField inputProjectName;
    @FXML
    private Button editProjectButton;
    @FXML
    private Label labelProjectDate;
    @FXML
    private DatePicker inputProjectDate;
    @FXML
    private Label labelProjectType2;
    @FXML
    private Label labelProjectType3;
    @FXML
    private Label labelProjectType4;
    @FXML
    private Button memberButton;
    @FXML
    private TextField inputProjectType1;
    @FXML
    private TextField inputProjectType2;
    @FXML
    private TextField inputProjectType3;
    @FXML
    private TextField inputProjectType4;
    @FXML
    private Button createProjectButton;
    @FXML
    private TableView<Project> projectsTable;
    @FXML
    private TableColumn<Project, String> projectNameColumn;
    @FXML
    private TableColumn<Project, String> projectDateColumn;
    @FXML
    private Button deleteProjectButton;
    @FXML
    private Button HomeButton;
    @FXML
    private Button leaveButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        fillProjectsTable();
        
        if(!projectDao.checkEmpty()) {
           createProjectButton.setDisable(true);
        }
        
        createProjectButton.setDisable(false);
        
        inputProjectName.setDisable(true);
        inputProjectDate.setDisable(true);
        inputProjectType1.setDisable(true);
        inputProjectType2.setDisable(true);
        inputProjectType3.setDisable(true);
        inputProjectType4.setDisable(true);
        saveChangesButton.setVisible(false);
        editProjectButton.setVisible(false);
        deleteProjectButton.setVisible(false);
        
        projectsTable.getSelectionModel().selectedItemProperty().addListener(
                
                (observable, oldValue, newValue) -> 
                {
            try {
                selectProjectBacklog(newValue);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
    public void selectProjectBacklog(Project project) throws InterruptedException {
       
        editProjectButton.setVisible(true);
        deleteProjectButton.setVisible(true);
        deleteProjectButton.setDisable(true);
        
        if(!saveChangesButton.isVisible()) {
            inputProjectName.setText(project.getName());
            LocalDate date = LocalDate.parse(project.getDeliveryDate());
            inputProjectDate.setValue(date);
            inputProjectType1.setText(project.getType1());
            inputProjectType2.setText(project.getType2());
            inputProjectType3.setText(project.getType3());
            inputProjectType4.setText(project.getType4());

        } else if(saveChangesButton.isVisible()) {
            inputProjectName.setDisable(true);
            inputProjectDate.setDisable(true);
            editProjectButton.setVisible(false);
            saveChangesButton.setVisible(false);
            deleteProjectButton.setVisible(false);
            inputProjectType1.setDisable(true);
            inputProjectType2.setDisable(true);
            inputProjectType3.setDisable(true);
            inputProjectType4.setDisable(true);
        }
    }
    
    private void fillProjectsTable(){
        projectNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        projectDateColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        projectsList = projectDao.readAll();
        observableProjects = FXCollections.observableArrayList(projectsList);

        projectsTable.setItems(observableProjects);
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
    private void switchToCreateProject(ActionEvent event) throws IOException {
        App.setRoot("CreateProject");
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
    private void saveChangesProject(ActionEvent event) {
        ProjectDao projectDao = new ProjectDao();
        projectDao.checkFile();
        
        Project project = projectsTable.getSelectionModel().getSelectedItem();
        
        Boolean allCorrect = true;
        
        oldType1 = project.getType1();
        oldType2 = project.getType2();
        oldType3 = project.getType3();
        oldType4 = project.getType4();

        
        if(inputProjectName.getText().equals("")) {
            labelProjectName.setStyle("-fx-text-fill: #c71616;");
            inputProjectName.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            labelProjectName.setStyle("");
            inputProjectName.setStyle("");
        }
        
        if(inputProjectType1.getText().equals("")) {
            labelProjectType1.setStyle("-fx-text-fill: #c71616;");
            inputProjectType1.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            labelProjectType1.setStyle("");
            inputProjectType1.setStyle("");
        }
        
        if(inputProjectType2.getText().equals("")) {
            labelProjectType2.setStyle("-fx-text-fill: #c71616;");
            inputProjectType2.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            labelProjectType2.setStyle("");
            inputProjectType2.setStyle("");
        }
        
        if(inputProjectType3.getText().equals("")) {
            labelProjectType3.setStyle("-fx-text-fill: #c71616;");
            inputProjectType3.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            labelProjectType3.setStyle("");
            inputProjectType3.setStyle("");
        }
        
        if(inputProjectType4.getText().equals("")) {
            labelProjectType4.setStyle("-fx-text-fill: #c71616;");
            inputProjectType4.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            labelProjectType4.setStyle("");
            inputProjectType4.setStyle("");
        }
        
        

        
        if(allCorrect == true) {

            project.setName(inputProjectName.getText());
            LocalDate date = inputProjectDate.getValue();
            project.setDeliveryDate(date.toString());
            project.setType1(inputProjectType1.getText());
            project.setType2(inputProjectType2.getText());
            project.setType3(inputProjectType3.getText());
            project.setType4(inputProjectType4.getText());

            List<String> types = new ArrayList();
            types.add(project.getType1());
            types.add(project.getType2());
            types.add(project.getType3());
            types.add(project.getType4());
            project.setAllTypes(types);
            this.projectDao.update(project);
            fillProjectsTable();
            
            // Caso: quando os tipos de projeto forem alterados, os membros também devem trocar o tipo de projeto.
            if(!oldType1.contentEquals(project.getType1()) || !oldType2.contentEquals(project.getType2()) || !oldType3.contentEquals(project.getType3()) 
                    || !oldType4.contentEquals(project.getType4())) {

                MemberDao memberDao = new MemberDao();

                List<Member> membersUpdate = memberDao.changeMemberTypes();
                for(Member member : membersUpdate) {
                    memberDao.update(member);        
                }

                TaskDao taskDao = new TaskDao();

                List<Task> tasksUpdate = taskDao.changeTaskType();
                for(Task task : tasksUpdate) {
                    taskDao.update(task);        
                }  
            }         

            inputProjectName.setDisable(true);
            inputProjectDate.setDisable(true);
            inputProjectType1.setDisable(true);
            inputProjectType2.setDisable(true);
            inputProjectType3.setDisable(true);
            inputProjectType4.setDisable(true);
            saveChangesButton.setVisible(false);
            
            inputProjectName.setText("");
            inputProjectType1.setText("");
            inputProjectType2.setText("");
            inputProjectType3.setText("");
            inputProjectType4.setText("");
            
        }

    }
    
    @FXML
    private void editProject(ActionEvent event) {
        inputProjectName.setDisable(false);
        inputProjectDate.setDisable(false);
        inputProjectType1.setDisable(false);
        inputProjectType2.setDisable(false);
        inputProjectType3.setDisable(false);
        inputProjectType4.setDisable(false);
        saveChangesButton.setVisible(true);
    }
    
    // Será implementado futuramente, ainda está em testes sob apenas um projeto.
    @FXML
    private void deleteProject(ActionEvent event) {
    }

    @FXML
    private void ProjectNameDetailPressed(KeyEvent event) {
        labelProjectName.setStyle("");
        inputProjectName.setStyle("");
    }

    @FXML
    private void ProjectType1DetailPressed(KeyEvent event) {
        labelProjectType1.setStyle("");
        inputProjectType1.setStyle("");
    }

    @FXML
    private void ProjectType2DetailPressed(KeyEvent event) {
        labelProjectType2.setStyle("");
        inputProjectType2.setStyle("");
    }

    @FXML
    private void ProjectType3DetailPressed(KeyEvent event) {
        labelProjectType3.setStyle("");
        inputProjectType3.setStyle("");
    }

    @FXML
    private void ProjectType4DetailPressed(KeyEvent event) {
        labelProjectType4.setStyle("");
        inputProjectType4.setStyle("");
    }

    @FXML
    private void leaveProject(ActionEvent event) {
        Stage stage;
        stage = (Stage) leaveButton.getScene().getWindow();
        System.out.println("Leaving application...");
        stage.close();
    }

 
}
