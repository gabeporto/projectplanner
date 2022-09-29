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
import model.Member;
import model.dao.MemberDao;
import pplaner.App;

/**
 * FXML Controller class
 *
 * @author Gabriel Porto
 */
public class MemberController implements Initializable {
    

    String[] tasksType = {"Prototipagem", "Desenvolvimento", "Documentação", "Testes"};
    
    private final MemberDao memberDao = new MemberDao();
    
    private List<Member> membersList = new ArrayList();
    private ObservableList<Member> observableMembers;
    

    @FXML
    private Button ppButton;
    @FXML
    private Button kanbanButton;
    @FXML
    private Button LeaveButton;
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
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillMembersTable();
        checkBoxType1.setText(tasksType[0]);
        checkBoxType2.setText(tasksType[1]);
        checkBoxType3.setText(tasksType[2]);
        checkBoxType4.setText(tasksType[3]);
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
            List<String> mTypes = member.getType();
            if(mTypes.contains(tasksType[0])) {
                checkBoxType1.setSelected(true);
            }
            if(mTypes.contains(tasksType[1])) {
                checkBoxType2.setSelected(true);
            }
            if(mTypes.contains(tasksType[2])) {
                checkBoxType3.setSelected(true);
            }
            if(mTypes.contains(tasksType[3])) {
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
            List<String> type = new ArrayList<>(); 
            
            member.setName(labelMemberNameDetail.getText());
            
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

}
