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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Member;
import model.Project;
import model.dao.MemberDao;
import model.dao.ProjectDao;
import pplaner.App;

/**
 * FXML Controller class
 *
 * @author Gabriel Porto
 */
public class MemberController implements Initializable {
    
    private final MemberDao memberDao = new MemberDao();
    private final ProjectDao projectDao = new ProjectDao();
    
    private Project project = projectDao.readOne();
    
    private List<Member> membersList = new ArrayList();
    private ObservableList<Member> observableMembers;
    

    @FXML
    private Button ppButton;
    @FXML
    private Button kanbanButton;
    @FXML
    private TableView<Member> membersTable;
    @FXML
    private Button deleteMemberButton;
    @FXML
    private Button editMemberButton;
    @FXML
    private Button saveChangesButton;
    @FXML
    private Button backlogButton;
    @FXML
    private Button createMemberButton;
    @FXML
    private TableColumn<Member, String> nameMemberColumn;
    @FXML
    private TableColumn<Member, String> typeMemberColumn;
    @FXML
    private TextField labelMemberNameDetail;
    @FXML
    private Label labelMemberName;
    @FXML
    private Label labelMemberType;
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
    private Button homeButton;
    @FXML
    private Button leaveButton;
    @FXML
    private Button analyticsButton;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillMembersTable();
        checkBoxType1.setText(project.getType1());
        checkBoxType2.setText(project.getType2());
        checkBoxType3.setText(project.getType3());
        checkBoxType4.setText(project.getType4());
        labelMemberNameDetail.setDisable(true);
        checkBoxType1.setDisable(true);
        checkBoxType2.setDisable(true);
        checkBoxType3.setDisable(true);
        checkBoxType4.setDisable(true);
        saveChangesButton.setVisible(false);
        editMemberButton.setVisible(false);
        deleteMemberButton.setVisible(false);
        
        membersTable.getSelectionModel().selectedItemProperty().addListener(
                
                (observable, oldValue, newValue) -> 
                {
            try {
                selectMemberBacklog(newValue);
            } catch (InterruptedException ex) {
                Logger.getLogger(TasksBacklogController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
    
    public void selectMemberBacklog(Member member) throws InterruptedException {
        
        checkBoxType1.setSelected(false);
        checkBoxType2.setSelected(false);
        checkBoxType3.setSelected(false);
        checkBoxType4.setSelected(false);

        editMemberButton.setVisible(true);
        deleteMemberButton.setVisible(true);   
        
        if(!saveChangesButton.isVisible()) {
            labelMemberNameDetail.setText(member.getName());
            List<Integer> mTypes = member.getTypeActive();
            if(mTypes.get(0) == 1) {
                checkBoxType1.setSelected(true);
            }
            if(mTypes.get(1) == 1) {
                checkBoxType2.setSelected(true);
            }
            if(mTypes.get(2) == 1) {
                checkBoxType3.setSelected(true);
            }
            if(mTypes.get(3) == 1) {
                checkBoxType4.setSelected(true);
            }
  

        } else if(saveChangesButton.isVisible()) {
            labelMemberNameDetail.setDisable(true);
            editMemberButton.setVisible(false);
            saveChangesButton.setVisible(false);
            deleteMemberButton.setVisible(false);
            checkBoxType1.setDisable(true);
            checkBoxType2.setDisable(true);
            checkBoxType3.setDisable(true);
            checkBoxType4.setDisable(true);
            labelMemberNameDetail.setText("");

        }
    }
    
    @FXML
    private void switchToBacklog(ActionEvent event) throws IOException {
        App.setRoot("TasksBacklog");
    }

    @FXML
    private void switchToKanban(ActionEvent event) throws IOException {
        App.setRoot("Kanban");
    }
    
    @FXML
    private void switchToCreateMember(ActionEvent event) throws IOException {
        App.setRoot("CreateMember");
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

    private void fillMembersTable(){
        nameMemberColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeMemberColumn.setCellValueFactory(new PropertyValueFactory<>("type"));  
        membersList = memberDao.readAll();
        observableMembers = FXCollections.observableArrayList(membersList);

        membersTable.setItems(observableMembers);
    }   
    
    @FXML
    private void deleteMember(ActionEvent event) {
        Member member = membersTable.getSelectionModel().getSelectedItem();
        memberDao.delete(member.getId());
        membersTable.getItems().remove(member);
        
        saveChangesButton.setVisible(false);
        editMemberButton.setVisible(false);
        deleteMemberButton.setVisible(false);
        
    }


    @FXML
    private void editMember(ActionEvent event) {
        Member memberSelected = membersTable.getSelectionModel().getSelectedItem();
        
        // Quando nenhum membro foi selecionada e não habilitada para edição.
        String value = labelMemberNameDetail.getText();
            if(!value.equals("")) {
                saveChangesButton.setVisible(true);
                labelMemberNameDetail.setDisable(false);
                checkBoxType1.setDisable(false);
                checkBoxType2.setDisable(false);
                checkBoxType3.setDisable(false);   
                checkBoxType4.setDisable(false);  
            }     
    }

    @FXML
    private void saveChangesTask(ActionEvent event) {
        MemberDao memberDao = new MemberDao();
        this.memberDao.checkFile();
        
        Member member = membersTable.getSelectionModel().getSelectedItem();

        Boolean allCorrect = true;
        
        if(labelMemberNameDetail.getText().equals("")) {
            labelMemberName.setStyle("-fx-text-fill: #c71616;");
            labelMemberNameDetail.setStyle("-fx-border-color: red;");
            allCorrect = false;
        } else {
            labelMemberNameDetail.setStyle("");
        }
        
        if(!checkBoxType1.isSelected() && !checkBoxType2.isSelected() && !checkBoxType3.isSelected() && !checkBoxType4.isSelected()) {
            labelMemberType.setStyle("-fx-text-fill: #c71616;");
            allCorrect = false;
        } else {
            labelMemberType.setStyle("");
        }             
        
                  
        if(allCorrect == true) {
            List<Integer> typeActive = new ArrayList<>(); 

            
            member.setName(labelMemberNameDetail.getText());
            
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
            
            
            this.memberDao.update(member);
            fillMembersTable();
            labelMemberNameDetail.setDisable(true);
            checkBoxType1.setDisable(true);
            checkBoxType2.setDisable(true);
            checkBoxType3.setDisable(true);
            checkBoxType4.setDisable(true);
            saveChangesButton.setVisible(false);
        }  
    }


    @FXML
    private void labelMemberNameDetailPressed(KeyEvent event) {
        labelMemberNameDetail.setStyle("");
        labelMemberName.setStyle("");
    }

    @FXML
    private void leaveProject(ActionEvent event) {
        Stage stage;
        stage = (Stage) leaveButton.getScene().getWindow();
        System.out.println("Leaving application...");
        stage.close();
    }

}
