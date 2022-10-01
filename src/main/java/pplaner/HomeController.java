/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pplaner;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import model.Project;
import model.dao.ProjectDao;

/**
 * FXML Controller class
 *
 * @author Gabriel Porto
 */
public class HomeController implements Initializable {

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
    private Label labelProjectType1;
    @FXML
    private ProgressBar progressProjectType1;
    @FXML
    private ProgressBar progressProjectType2;
    @FXML
    private Label labelProjectType2;
    @FXML
    private Label labelProjectType3;
    @FXML
    private ProgressBar progressProjectType3;
    @FXML
    private ProgressBar progressProjectType4;
    @FXML
    private Label labelProjectType4;
    @FXML
    private Label labelProjectName;
    @FXML
    private Label labelCompletedTasks;
    @FXML
    private Button ProjectButton;
    @FXML
    private ProgressIndicator projectProgress;
    @FXML
    private Label labelPercentageType1;
    @FXML
    private Label labelPercentageType2;
    @FXML
    private Label labelPercentageType3;
    @FXML
    private Label labelPercentageType4;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ProjectDao projectDao = new ProjectDao();
        Boolean isEmpty = projectDao.checkEmpty();
        
        if(!isEmpty){
            Project project = projectDao.readOne();
            labelProjectName.setText(project.getName());
            
            project.filterType1Done();
            float resultType1 = project.calculatePercentageType1();
            labelProjectType1.setText(project.getType1());
            labelPercentageType1.setText(Float.toString(resultType1*100) + "%");
            progressProjectType1.setProgress(resultType1);
            
            project.filterType2Done();
            float resultType2 = project.calculatePercentageType2();
            labelProjectType2.setText(project.getType2());
            labelPercentageType2.setText(Float.toString(resultType2*100) + "%");
            progressProjectType2.setProgress(resultType2);
            
            project.filterType3Done();
            float resultType3 = project.calculatePercentageType3();
            labelProjectType3.setText(project.getType3());
            labelPercentageType3.setText(Float.toString(resultType3*100) + "%");
            progressProjectType3.setProgress(resultType3);
            
            project.filterType4Done();
            float resultType4 = project.calculatePercentageType4();
            labelProjectType4.setText(project.getType4());
            labelPercentageType4.setText(Float.toString(resultType4*100) + "%");
            progressProjectType4.setProgress(resultType4);

            projectProgress.setProgress(project.calculatePercentageProgress());
            if(project.calculatePercentageProgress() == 1) {
                labelCompletedTasks.setVisible(false);
            }
            
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
    private void switchToProject(ActionEvent event) throws IOException {
        App.setRoot("Project");
    }
    
}
