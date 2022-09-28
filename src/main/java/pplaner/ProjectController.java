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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
    
    ProjectDao projectDao = new ProjectDao();

    @FXML
    private Button kanbanButton;
    @FXML
    private Button backlogButton;
    @FXML
    private Button LeaveButton;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        projectDao.checkFile();
        
        if(projectDao.checkEmpty()){
            createProjectButton.setVisible(true);
            
            inputProjectName.setDisable(true);
            inputProjectDate.setDisable(true);
            inputProjectType1.setDisable(true);
            inputProjectType2.setDisable(true);
            inputProjectType3.setDisable(true);
            inputProjectType4.setDisable(true);
            
            saveChangesButton.setVisible(false);
            editProjectButton.setVisible(false);
            
        } else {
            
            createProjectButton.setVisible(false);
            
            Project currentProject = new Project();
            currentProject = projectDao.readOne();

            String date;
            date = currentProject.getDeliveryDate();
            LocalDate dateDelivery = LocalDate.parse(date);
            inputProjectDate.setValue(dateDelivery);

            inputProjectName.setText(currentProject.getName());       
            inputProjectType1.setText(currentProject.getType1());
            inputProjectType2.setText(currentProject.getType2());
            inputProjectType3.setText(currentProject.getType3());
            inputProjectType4.setText(currentProject.getType4());

            inputProjectName.setDisable(true);
            inputProjectDate.setDisable(true);
            inputProjectType1.setDisable(true);
            inputProjectType2.setDisable(true);
            inputProjectType3.setDisable(true);
            inputProjectType4.setDisable(true);

            saveChangesButton.setVisible(false);      
        }

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
    private void labelMemberNameDetailPressed(KeyEvent event) {
    }

    @FXML
    private void saveChangesProject(ActionEvent event) {
        ProjectDao projectDao = new ProjectDao();
        projectDao.checkFile();
        
        Boolean allCorrect = true;
        
        if(allCorrect == true) {
            Project project = new Project();
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
            this.projectDao.createOne(project);

        }
        
        inputProjectName.setDisable(true);
        inputProjectDate.setDisable(true);
        inputProjectType1.setDisable(true);
        inputProjectType2.setDisable(true);
        inputProjectType3.setDisable(true);
        inputProjectType4.setDisable(true);
        saveChangesButton.setVisible(false);

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

  
}
