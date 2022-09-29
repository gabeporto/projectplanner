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
import model.Project;
import model.dao.ProjectDao;

/**
 * FXML Controller class
 *
 * @author Gabriel Porto
 */
public class CreateProjectController implements Initializable {
    
    
    ProjectDao projectDao = new ProjectDao();

    @FXML
    private Button kanbanButton;
    @FXML
    private Button backlogButton;
    @FXML
    private Button memberButton;
    @FXML
    private Button LeaveButton;
    @FXML
    private Button ppButton;
    @FXML
    private DatePicker inputProjectDate;
    @FXML
    private Label labelTaskDescription;
    @FXML
    private Label labelTaskDescription1;
    @FXML
    private TextField inputProjectType1;
    @FXML
    private Button createTaskButton;
    @FXML
    private Label labelTaskDescription2;
    @FXML
    private TextField inputProjectType2;
    @FXML
    private TextField inputProjectType3;
    @FXML
    private Label labelTaskDescription3;
    @FXML
    private Label labelTaskDescription4;
    @FXML
    private TextField inputProjectType4;
    @FXML
    private Label labelProjectName;
    @FXML
    private TextField inputProjectName;
    @FXML
    private Button HomeButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private void switchToHome(ActionEvent event) throws IOException {
        App.setRoot("Home"); 
    }

    @FXML
    private void labelMemberNameDetailPressed(KeyEvent event) {
    }

    @FXML
    private void nameTaskKeyPressed(KeyEvent event) {
    }

    @FXML
    private void createProject(ActionEvent event) throws IOException {
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
        this.projectDao.create(project);
        
        App.setRoot("Project");
    }
 
}
