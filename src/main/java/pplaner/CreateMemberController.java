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
import model.Member;
import model.dao.MemberDao;

/**
 * FXML Controller class
 *
 * @author Gabriel Porto
 */
public class CreateMemberController implements Initializable {
    
    private final MemberDao memberDao = new MemberDao();
    String[] tasksType = {"Prototipagem", "Desenvolvimento", "Documentação", "Testes"};

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkBoxType1.setText(tasksType[0]);
        checkBoxType2.setText(tasksType[1]);
        checkBoxType3.setText(tasksType[2]);
        checkBoxType4.setText(tasksType[3]);

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
            List<String> type = new ArrayList<>();
            Member member = new Member();
            
            member.setName(inputMemberName.getText());
            
            if(checkBoxType1.isSelected()){
                
                type.add(tasksType[0]);
            }
            if(checkBoxType2.isSelected()){
                type.add(tasksType[1]);
            }
            if(checkBoxType3.isSelected()){
                type.add(tasksType[2]);
            }
            if(checkBoxType4.isSelected()){
                type.add(tasksType[3]);
            }
            
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


}
