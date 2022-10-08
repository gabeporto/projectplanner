/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;
import model.dao.ProjectDao;
import model.dao.TaskDao;

/**
 *
 * @author Gabriel Porto
 */
public class Project {
    private String id;
    private String name;
    private String deliveryDate;
    private String type1;
    private String type2;
    private String type3;
    private String type4;
    private List<String> allTypes = new ArrayList<>();
    
    private int numberOfType1Tasks = 0;
    private int numberOfType1ToDo = 0;
    private int numberOfType1InProgress = 0;
    private int numberOfType1Done = 0;
    
    private int numberOfType2Tasks = 0;
    private int numberOfType2ToDo = 0;
    private int numberOfType2InProgress = 0;
    private int numberOfType2Done = 0;
    
    private int numberOfType3Tasks = 0;
    private int numberOfType3ToDo = 0;
    private int numberOfType3InProgress = 0;
    private int numberOfType3Done = 0;

    private int numberOfType4Tasks = 0;
    private int numberOfType4ToDo = 0;
    private int numberOfType4InProgress = 0;
    private int numberOfType4Done = 0;
    
    private int numberOfToDoTasks = 0;
    private int numberOfInProgressTasks = 0;
    private int numberOfDoneTasks = 0;
    private int numberOfTasks = 0;
    
    private float percentageProgress = 0;
    private float percentageType1 = 0;
    private float percentageType2 = 0;
    private float percentageType3 = 0;
    private float percentageType4 = 0;
    
    public Project() {
        this.id = "";
        this.name = "";
        this.deliveryDate = "";
        this.type1 = "";
        this.type2 = "";
        this.type3 = "";
        this.type4 = "";
    }
    
    public Project(String name, String deliveryDate, String type1, String type2, String type3, String type4) {
        this.id = "0";
        this.name = name;
        this.deliveryDate = deliveryDate;
        this.type1 = type1;
        this.type2 = type2;
        this.type3 = type3;
        this.type4 = type4;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public String getType3() {
        return type3;
    }

    public String getType4() {
        return type4;
    }

    public List<String> getAllTypes() {
        return allTypes;
    }

    public int getNumberOfType1Tasks() {
        return numberOfType1Tasks;
    }

    public int getNumberOfType2Tasks() {
        return numberOfType2Tasks;
    }

    public int getNumberOfType3Tasks() {
        return numberOfType3Tasks;
    }

    public int getNumberOfType4Tasks() {
        return numberOfType4Tasks;
    }

    public int getNumberOfToDoTasks() {
        return numberOfToDoTasks;
    }

    public int getNumberOfInProgressTasks() {
        return numberOfInProgressTasks;
    }

    public int getNumberOfDoneTasks() {
        return numberOfDoneTasks;
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    public float getPercentageProgress() {
        return percentageProgress;
    }

    public int getNumberOfType1Done() {
        return numberOfType1Done;
    }

    public float getPercentageType1() {
        return percentageType1;
    }

    public float getPercentageType2() {
        return percentageType2;
    }

    public float getPercentageType3() {
        return percentageType3;
    }

    public float getPercentageType4() {
        return percentageType4;
    }

    public int getNumberOfType1ToDo() {
        return numberOfType1ToDo;
    }

    public int getNumberOfType1InProgress() {
        return numberOfType1InProgress;
    }

    public int getNumberOfType2ToDo() {
        return numberOfType2ToDo;
    }


    public int getNumberOfType2InProgress() {
        return numberOfType2InProgress;
    }

    public int getNumberOfType2Done() {
        return numberOfType2Done;
    }

    public int getNumberOfType3ToDo() {
        return numberOfType3ToDo;
    }

    public int getNumberOfType3InProgress() {
        return numberOfType3InProgress;
    }

    public int getNumberOfType3Done() {
        return numberOfType3Done;
    }

    public int getNumberOfType4ToDo() {
        return numberOfType4ToDo;
    }

    public int getNumberOfType4InProgress() {
        return numberOfType4InProgress;
    }

    public int getNumberOfType4Done() {
        return numberOfType4Done;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public void setType3(String type3) {
        this.type3 = type3;
    }

    public void setType4(String type4) {
        this.type4 = type4;
    }

    public void setAllTypes(List<String> allTypes) {
        this.allTypes = allTypes;
    }

    public void addNumberOfTasks() {
        this.numberOfTasks = this.numberOfTasks + 1;
    }
    
    public void subNumberOfTasks() {
        this.numberOfTasks = this.numberOfTasks - 1;
    }

    public float calculatePercentageProgress() {
        TaskDao taskDao = new TaskDao();
        taskDao.checkFile();
        
        this.numberOfDoneTasks = this.numberOfType1Done + this.numberOfType2Done + this.numberOfType3Done + this.numberOfType4Done;
        this.numberOfTasks = taskDao.readAll().size();
        float doneTasks = this.numberOfDoneTasks;
        float numOfTasks = this.numberOfTasks;
        
        this.percentageProgress = doneTasks / numOfTasks;
        return this.percentageProgress;
    }
    
    public float calculatePercentageType1() {
        float doneTasks = this.numberOfType1Done;
        float numOfTasks = this.numberOfType1Tasks;
        
        this.percentageType1 = doneTasks / numOfTasks;
        return this.percentageType1;
    }
    
    public float calculatePercentageType2() {
        float doneTasks = this.numberOfType2Done;
        float numOfTasks = this.numberOfType2Tasks;
        
        this.percentageType2 = doneTasks / numOfTasks;
        return this.percentageType2;
    }
    
    public float calculatePercentageType3() {
        float doneTasks = this.numberOfType3Done;
        float numOfTasks = this.numberOfType3Tasks;
        
        this.percentageType3 = doneTasks / numOfTasks;
        return this.percentageType3;
    }
    
    public float calculatePercentageType4() {
        float doneTasks = this.numberOfType4Done;
        float numOfTasks = this.numberOfType4Tasks;
        
        this.percentageType4 = doneTasks / numOfTasks;
        return this.percentageType4;
    }
    
    public float calculatePercentageTasksType1(int maxTasks) {
        
        float numOfTasks = this.numberOfType1Tasks;
        
        return numOfTasks / maxTasks;
    }
    
    public float calculatePercentageTasksType2(int maxTasks) {
        
        float numOfTasks = this.numberOfType2Tasks;
            
        return numOfTasks / maxTasks;
    }
    
    public float calculatePercentageTasksType3(int maxTasks) {
        
        float numOfTasks = this.numberOfType3Tasks;

        return numOfTasks / maxTasks;
    }
    
    public float calculatePercentageTasksType4(int maxTasks) {
        
        float numOfTasks = this.numberOfType4Tasks;

        return numOfTasks / maxTasks;
    }

    public void filterType1ToDo() {
        int quantity = 0;
        TaskDao taskDao = new TaskDao();
        taskDao.checkFile();
        List<Task> firstFilteredTasks = taskDao.readAllByFilter(this.getType1());
        
        this.numberOfType1Tasks = firstFilteredTasks.size();
        
        List<Task> secondFilteredTasks = new ArrayList<>();
        
        for(Task task : firstFilteredTasks) {
                if(task.getStage().contains("To Do Stage")) {
                    secondFilteredTasks.add(task);
                    quantity = quantity + 1;
                }
            }

        this.numberOfType1ToDo = quantity;

    }
    
    public void filterType1InProgress() {
        int quantity = 0;
        TaskDao taskDao = new TaskDao();
        taskDao.checkFile();
        List<Task> firstFilteredTasks = taskDao.readAllByFilter(this.getType1());
        
        this.numberOfType1Tasks = firstFilteredTasks.size();
        
        List<Task> secondFilteredTasks = new ArrayList<>();
        
        for(Task task : firstFilteredTasks) {
                if(task.getStage().contains("In Progress Stage")) {
                    secondFilteredTasks.add(task);
                    quantity = quantity + 1;
                }
            }
        
        this.numberOfType1InProgress = quantity;

    } 
    
    public void filterType1Done() {
        int quantity = 0;
        TaskDao taskDao = new TaskDao();
        taskDao.checkFile();
        List<Task> firstFilteredTasks = taskDao.readAllByFilter(this.getType1());
        
        this.numberOfType1Tasks = firstFilteredTasks.size();
        
        List<Task> secondFilteredTasks = new ArrayList<>();
        
        for(Task task : firstFilteredTasks) {
                if(task.getStage().contains("Done Stage")) {
                    secondFilteredTasks.add(task);
                    quantity = quantity + 1;
                }
            }
        
        this.numberOfType1Done = quantity;

    }
    
    public void filterType2ToDo() {
        int quantity = 0;
        TaskDao taskDao = new TaskDao();
        taskDao.checkFile();
        List<Task> firstFilteredTasks = taskDao.readAllByFilter(this.getType2());
        
        this.numberOfType2Tasks = firstFilteredTasks.size();
        
        List<Task> secondFilteredTasks = new ArrayList<>();
        
        for(Task task : firstFilteredTasks) {
                if(task.getStage().contains("To Do Stage")) {
                    secondFilteredTasks.add(task);
                    quantity = quantity + 1;
                }
            }
        
        this.numberOfType2ToDo = quantity;

    }
    
    public void filterType2InProgress() {
        int quantity = 0;
        TaskDao taskDao = new TaskDao();
        taskDao.checkFile();
        List<Task> firstFilteredTasks = taskDao.readAllByFilter(this.getType2());
        
        this.numberOfType2Tasks = firstFilteredTasks.size();
        
        List<Task> secondFilteredTasks = new ArrayList<>();
        
        for(Task task : firstFilteredTasks) {
                if(task.getStage().contains("In Progress Stage")) {
                    secondFilteredTasks.add(task);
                    quantity = quantity + 1;
                }
            }
        
        this.numberOfType2InProgress = quantity;

    }
    
    public void filterType2Done() {
        int quantity = 0;
        TaskDao taskDao = new TaskDao();
        taskDao.checkFile();
        List<Task> firstFilteredTasks = taskDao.readAllByFilter(this.getType2());
        
        this.numberOfType2Tasks = firstFilteredTasks.size();
        
        List<Task> secondFilteredTasks = new ArrayList<>();
        
        for(Task task : firstFilteredTasks) {
                if(task.getStage().contains("Done Stage")) {
                    secondFilteredTasks.add(task);
                    quantity = quantity + 1;
                }
            }
        
        this.numberOfType2Done = quantity;

    }
    
    public void filterType3ToDo() {
        int quantity = 0;
        TaskDao taskDao = new TaskDao();
        taskDao.checkFile();
        List<Task> firstFilteredTasks = taskDao.readAllByFilter(this.getType3());
        
        this.numberOfType3Tasks = firstFilteredTasks.size();
        
        List<Task> secondFilteredTasks = new ArrayList<>();
        
        for(Task task : firstFilteredTasks) {
                if(task.getStage().contains("To Do Stage")) {
                    secondFilteredTasks.add(task);
                    quantity = quantity + 1;
                }
            }
        
        this.numberOfType3ToDo = quantity;

    }
    
    public void filterType3InProgress() {
        int quantity = 0;
        TaskDao taskDao = new TaskDao();
        taskDao.checkFile();
        List<Task> firstFilteredTasks = taskDao.readAllByFilter(this.getType3());
        
        this.numberOfType3Tasks = firstFilteredTasks.size();
        
        List<Task> secondFilteredTasks = new ArrayList<>();
        
        for(Task task : firstFilteredTasks) {
                if(task.getStage().contains("In Progress Stage")) {
                    secondFilteredTasks.add(task);
                    quantity = quantity + 1;
                }
            }
        
        this.numberOfType3InProgress = quantity;

    }
    
    public void filterType3Done() {
        int quantity = 0;
        TaskDao taskDao = new TaskDao();
        taskDao.checkFile();
        List<Task> firstFilteredTasks = taskDao.readAllByFilter(this.getType3());
        
        this.numberOfType3Tasks = firstFilteredTasks.size();
        
        List<Task> secondFilteredTasks = new ArrayList<>();
        
        for(Task task : firstFilteredTasks) {
                if(task.getStage().contains("Done Stage")) {
                    secondFilteredTasks.add(task);
                    quantity = quantity + 1;
                }
            }
        
        this.numberOfType3Done = quantity;

    }
    
    public void filterType4ToDo() {
        int quantity = 0;
        TaskDao taskDao = new TaskDao();
        taskDao.checkFile();
        List<Task> firstFilteredTasks = taskDao.readAllByFilter(this.getType4());
        
        this.numberOfType4Tasks = firstFilteredTasks.size();
        
        List<Task> secondFilteredTasks = new ArrayList<>();
        
        for(Task task : firstFilteredTasks) {
                if(task.getStage().contains("To Do Stage")) {
                    secondFilteredTasks.add(task);
                    quantity = quantity + 1;
                }
            }
        
        this.numberOfType4ToDo = quantity;

    }
    
    public void filterType4InProgress() {
        int quantity = 0;
        TaskDao taskDao = new TaskDao();
        taskDao.checkFile();
        List<Task> firstFilteredTasks = taskDao.readAllByFilter(this.getType4());
        
        this.numberOfType4Tasks = firstFilteredTasks.size();
        
        List<Task> secondFilteredTasks = new ArrayList<>();
        
        for(Task task : firstFilteredTasks) {
                if(task.getStage().contains("In Progress Stage")) {
                    secondFilteredTasks.add(task);
                    quantity = quantity + 1;
                }
            }
        
        this.numberOfType4InProgress = quantity;

    }
    
    public void filterType4Done() {
        int quantity = 0;
        TaskDao taskDao = new TaskDao();
        taskDao.checkFile();
        List<Task> firstFilteredTasks = taskDao.readAllByFilter(this.getType4());
        
        this.numberOfType4Tasks = firstFilteredTasks.size();
        
        List<Task> secondFilteredTasks = new ArrayList<>();
        
        for(Task task : firstFilteredTasks) {
                if(task.getStage().contains("Done Stage")) {
                    secondFilteredTasks.add(task);
                    quantity = quantity + 1;
                }
            }
        
        this.numberOfType4Done = quantity;

    }


}
