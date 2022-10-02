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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Member;
import model.Project;
import model.dao.MemberDao;
import model.dao.ProjectDao;

/**
 * FXML Controller class
 *
 * @author Gabriel Porto
 */
public class CreateMemberController implements Initializable {
    
    private final MemberDao memberDao = new MemberDao();
    private final ProjectDao projectDao = new ProjectDao();
    private Project project = projectDao.readOne();
    
    @FXML
    private Button backlogButton;
    @FXML
    private Button leaveButton;
    @FXML
    private Button ppButton;
    @FXML
    private TextField inputMemberName;
    @FXML
    private Label labelMemberName;
    @FXML
    private Label labelMemberType;
    @FXML
    private Button createMemberButton;
    @FXML
    private Button KanbanButton;
    @FXML
    private CheckBox checkBoxType1;
    @FXML
    private CheckBox checkBoxType2;
    @FXML
    private CheckBox checkBoxType3;
    @FXML
    private CheckBox checkBoxType4;
    @FXML
    private Button projectButton;
    @FXML
    private Button HomeButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkBoxType1.setText(project.getType1());
        checkBoxType2.setText(project.getType2());
        checkBoxType3.setText(project.getType3());
        checkBoxType4.setText(project.getType4());

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
    private void switchToKanban(ActionEvent event) throws IOException {
        App.setRoot("Kanban");
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
    private void createMember(ActionEvent event) throws IOException {
        MemberDao memberDao = new MemberDao();
        this.memberDao.checkFile();
        
        Boolean allCorrect = true;
        
        if(inputMemberName.getText().equals("")) {
            labelMemberName.setStyle("-fx-text-fill: #c71616;");
            inputMemberName.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            inputMemberName.setStyle("");
        }
        
        if(!checkBoxType1.isSelected() && !checkBoxType2.isSelected() && !checkBoxType3.isSelected() && !checkBoxType4.isSelected()) {
            labelMemberType.setStyle("-fx-text-fill: #c71616;");
            allCorrect = false;
        } else {
            labelMemberType.setStyle("");
        }      
        
        
        if(allCorrect == true) {

            Member member = new Member();
            List<Integer> typeActive = new ArrayList<>();
            
            member.setName(inputMemberName.getText());
            
            if(checkBoxType1.isSelected()){
                typeActive.add(1);
            } else {
                typeActive.add(0);
            }
            if(checkBoxType2.isSelected()){
                typeActive.add(1);
            } else {
                typeActive.add(0);
            }
            if(checkBoxType3.isSelected()){
                typeActive.add(1);
            } else {
                typeActive.add(0);
            }
            if(checkBoxType4.isSelected()){
                typeActive.add(1);
            } else {
                typeActive.add(0);
            }
            
            // 1 ou 0 caso tenha o tipo de projeto.
            member.setTypeActive(typeActive);

            
            List<String> type = new ArrayList<>(); 
            
            if(typeActive.get(0) == 1) {
                type.add(project.getType1());
            }
            if(typeActive.get(1) == 1) {
                type.add(project.getType2());
            }
            if(typeActive.get(2) == 1) {
                type.add(project.getType3());
            }
            if(typeActive.get(3) == 1) {
                type.add(project.getType4());
            }
            
            // Setando tipos de projeto ao membro.
            member.setType(type);
           
            this.memberDao.create(member);

            App.setRoot("Member");
        }
    }
    
    @FXML
    private void nameTaskKeyPressed(KeyEvent event) {
        inputMemberName.setStyle("");
        labelMemberName.setStyle("");
    }

    @FXML
    private void selectType(ActionEvent event) {
        labelMemberType.setStyle("");
    }

    @FXML
    private void leaveProject(ActionEvent event) {
        Stage stage;
        stage = (Stage) leaveButton.getScene().getWindow();
        System.out.println("Leaving application...");
        stage.close();
    }

}
