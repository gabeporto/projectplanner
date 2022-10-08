/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pplaner;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import model.Project;
import model.dao.ProjectDao;

/**
 * FXML Controller class
 *
 * @author Gabriel Porto
 */
public class AnalyticsController implements Initializable {

    @FXML
    private Button leaveButton;
    @FXML
    private Button HomeButton;
    @FXML
    private Button PPButton;
    @FXML
    private Button kanbanButton;
    @FXML
    private Button backlogButton;
    @FXML
    private Button memberButton;
    @FXML
    private Button ProjectButton;
    @FXML
    private PieChart PieChartType1;
    @FXML
    private PieChart PieChartType2;
    @FXML
    private PieChart PieChartType3;
    @FXML
    private PieChart PieChartType4;
    @FXML
    private Label labelDaysToEnd;
    @FXML
    private Label labelDays;
    @FXML
    private Label PieChartType1Title1;
    @FXML
    private Label PieChartType1Title2;
    @FXML
    private Label PieChartType1Title3;
    @FXML
    private Label PieChartType1Title4;
    @FXML
    private ProgressBar barType1TaskAmount;
    @FXML
    private ProgressBar barType2TaskAmount;
    @FXML
    private ProgressBar barType3TaskAmount;
    @FXML
    private ProgressBar barType4TaskAmount;
    @FXML
    private Label labelType1TaskAmount;
    @FXML
    private Label labelType2TaskAmount;
    @FXML
    private Label labelType3TaskAmount;
    @FXML
    private Label labelType4TaskAmount;
    @FXML
    private Label labelType1Qtd;
    @FXML
    private Label labelType2Qtd;
    @FXML
    private Label labelType3Qtd;
    @FXML
    private Label labelType4Qtd;
    @FXML
    private Label textFinalDateIn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ProjectDao projectDao = new ProjectDao();
        Boolean isEmpty = projectDao.checkEmpty();
        
        if(!isEmpty){
            Project project = projectDao.readOne();
            
            // Carrega todas os dados das propriedades
            project.filterType1ToDo();
            project.filterType1InProgress();
            project.filterType1Done();
            
            project.filterType2ToDo();
            project.filterType2InProgress();
            project.filterType2Done();
            
            project.filterType3ToDo();
            project.filterType3InProgress();
            project.filterType3Done();
            
            project.filterType4ToDo();
            project.filterType4InProgress();
            project.filterType4Done();
            
            // Calcula o Tipo que possui a maior quantidade de tasks para gerar o range do gráfico.
            int maxTasks = 0;
            if(maxTasks < project.getNumberOfType1Tasks()) {
                maxTasks = project.getNumberOfType1Tasks();
            }
            if(maxTasks < project.getNumberOfType2Tasks()) {
                    maxTasks = project.getNumberOfType2Tasks();
            }
            if(maxTasks < project.getNumberOfType3Tasks()) {
                    maxTasks = project.getNumberOfType3Tasks();
            }
            if(maxTasks < project.getNumberOfType4Tasks()) {
                    maxTasks = project.getNumberOfType4Tasks();
            }
            
            project.calculatePercentageTasksType1(maxTasks);
            project.calculatePercentageTasksType2(maxTasks);
            project.calculatePercentageTasksType3(maxTasks);
            project.calculatePercentageTasksType4(maxTasks);
            
            // Propriedades do progressBar da quantidade da Tasks de cada Tipo
            labelType1TaskAmount.setText(project.getType1());
            labelType2TaskAmount.setText(project.getType2());
            labelType3TaskAmount.setText(project.getType3());
            labelType4TaskAmount.setText(project.getType4());
            
            labelType1Qtd.setText(Integer.toString(project.getNumberOfType1Tasks()));
            labelType2Qtd.setText(Integer.toString(project.getNumberOfType2Tasks()));
            labelType3Qtd.setText(Integer.toString(project.getNumberOfType3Tasks()));
            labelType4Qtd.setText(Integer.toString(project.getNumberOfType4Tasks()));
            
            barType1TaskAmount.setProgress(project.calculatePercentageTasksType1(maxTasks));
            barType2TaskAmount.setProgress(project.calculatePercentageTasksType2(maxTasks));
            barType3TaskAmount.setProgress(project.calculatePercentageTasksType3(maxTasks));
            barType4TaskAmount.setProgress(project.calculatePercentageTasksType4(maxTasks));
            
            // Propriedades do gráfico de progresso do Tipo 1;
            PieChartType1.setData(
                    FXCollections.observableArrayList(
                    new PieChart.Data("A fazer", project.getNumberOfType1ToDo()),
                    new PieChart.Data("Em progresso", project.getNumberOfType1InProgress()),
                    new PieChart.Data("Concluído", project.getNumberOfType1Done())));

            PieChartType1Title1.setText(project.getType1());
            PieChartType1.setLabelsVisible(false);
            PieChartType1.setLegendVisible(false);

            // Propriedades do gráfico de progresso do Tipo 2
            PieChartType2.setData(
                    FXCollections.observableArrayList(
                    new PieChart.Data("A fazer", project.getNumberOfType2ToDo()),
                    new PieChart.Data("Em progresso", project.getNumberOfType2InProgress()),
                    new PieChart.Data("Concluído", project.getNumberOfType2Done())));

            PieChartType1Title2.setText(project.getType2());
            PieChartType2.setLabelsVisible(false);
            PieChartType2.setLegendVisible(false);

            // Propriedades do gráfico de progresso do Tipo 3
            PieChartType3.setData(
                    FXCollections.observableArrayList(
                    new PieChart.Data("A fazer", project.getNumberOfType3ToDo()),
                    new PieChart.Data("Em progresso", project.getNumberOfType3InProgress()),
                    new PieChart.Data("Concluído", project.getNumberOfType3Done())));

            PieChartType1Title3.setText(project.getType3());
            PieChartType3.setLabelsVisible(false);
            PieChartType3.setLegendVisible(false);

            // Propriedades do gráfico de progresso do Tipo 4
            PieChartType4.setData(
                    FXCollections.observableArrayList(
                    new PieChart.Data("A fazer", project.getNumberOfType4ToDo()),
                    new PieChart.Data("Em progresso", project.getNumberOfType4InProgress()),
                    new PieChart.Data("Concluído", project.getNumberOfType4Done())));

            PieChartType1Title4.setText(project.getType4());
            PieChartType4.setLabelsVisible(false);
            PieChartType4.setLegendVisible(false);
            
            
            // Propriedades da Data final do Projeto
            LocalDate dateAfter = LocalDate.parse(project.getDeliveryDate());
            LocalDate dateNow = LocalDate.now();
            int daysBetween = (int) ChronoUnit.DAYS.between(dateNow, dateAfter);
            labelDaysToEnd.setText(Integer.toString(daysBetween));
            
            if(daysBetween == 1) {
                labelDays.setText("dia");
            } else if(daysBetween <= 0) {
                textFinalDateIn.setText("Data final");
                labelDaysToEnd.setText("CONCLUÍDA");
                labelDaysToEnd.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
                labelDays.setVisible(false);
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
    
    @FXML
    private void switchToAboutUs(ActionEvent event) throws IOException {
        App.setRoot("AboutUs");
    }

    @FXML
    private void switchToHome(ActionEvent event) throws IOException {
        App.setRoot("Home");
    }

    @FXML
    private void leaveProject(ActionEvent event) {
        Stage stage;
        stage = (Stage) leaveButton.getScene().getWindow();
        System.out.println("Leaving application...");
        stage.close();
    } 


}
