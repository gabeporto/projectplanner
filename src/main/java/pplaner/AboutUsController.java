/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pplaner;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gabriel Porto
 */
public class AboutUsController implements Initializable {

    @FXML
    private Button kanbanButton;
    @FXML
    private Button backlogButton;
    @FXML
    private Button memberButton;
    @FXML
    private Button ProjectButton;
    @FXML
    private Button leaveButton;
    @FXML
    private Button ppButton;
    @FXML
    private Button homeButton;
    @FXML
    private Button linkedInMember1;
    @FXML
    private Button gitHubMember1;
    @FXML
    private Button instagramMember1;
    @FXML
    private Button linkedInMember2;
    @FXML
    private Button gitHubMember2;
    @FXML
    private Button instagramMember2;
    @FXML
    private Button linkedInMember3;
    @FXML
    private Button gitHubMember3;
    @FXML
    private Button instagramMember3;
    @FXML
    private Button AnalyticsButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gitHubMember2.setDisable(true);
        instagramMember3.setDisable(true);
        
    }    
    
    @FXML
    private void switchToHome(ActionEvent event) throws IOException {
        App.setRoot("Home");
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
    
    @FXML
    private void switchToAnalytics(ActionEvent event) throws IOException {
        App.setRoot("Analytics");
    }

    @FXML
    private void leaveProject(ActionEvent event) {
        Stage stage;
        stage = (Stage) leaveButton.getScene().getWindow();
        System.out.println("Leaving application...");
        stage.close();
    }

    @FXML
    private void goToLinkedinMember1(ActionEvent event) {
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("https://www.linkedin.com/in/gabeporto/"));
        } catch (IOException | URISyntaxException e2) {
            e2.printStackTrace();
        }
    }

    @FXML
    private void goToGitHubMember1(ActionEvent event) {
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("https://github.com/gabeporto/"));
        } catch (IOException | URISyntaxException e2) {
            e2.printStackTrace();
        }
    }

    @FXML
    private void goToInstagramMember1(ActionEvent event) {
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("https://www.instagram.com/gabrielporto7/"));
        } catch (IOException | URISyntaxException e2) {
            e2.printStackTrace();
        } 
    }

    @FXML
    private void goToRepository(ActionEvent event) {
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("https://github.com/gabeporto/projectplanner"));
        } catch (IOException | URISyntaxException e2) {
            e2.printStackTrace();
        } 
    }

    @FXML
    private void goToSite(ActionEvent event) {
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("https://useprojectplanner.netlify.app/"));
        } catch (IOException | URISyntaxException e2) {
            e2.printStackTrace();
        } 
    }

    @FXML
    private void goToLinkedinMember2(ActionEvent event) {
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("https://www.linkedin.com/in/lucas-ant%C3%B4nio-8381bb181/"));
        } catch (IOException | URISyntaxException e2) {
            e2.printStackTrace();
        }
    }

    @FXML
    private void goToGitHubMember2(ActionEvent event) {
    }

    @FXML
    private void goToInstagramMember2(ActionEvent event) {
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("https://www.instagram.com/_lucass__ssouza_/"));
        } catch (IOException | URISyntaxException e2) {
            e2.printStackTrace();
        }      
    }

    @FXML
    private void goToLinkedinMember3(ActionEvent event) {
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("https://www.linkedin.com/in/nathan-faria-2061b8201/"));
        } catch (IOException | URISyntaxException e2) {
            e2.printStackTrace();
        } 
    
    }

    @FXML
    private void goToGitHubMember3(ActionEvent event) {
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("https://github.com/ichbinnichts"));
        } catch (IOException | URISyntaxException e2) {
            e2.printStackTrace();
        }
    }

    @FXML
    private void goToInstagramMember3(ActionEvent event) {
    }
}
