
package model.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import model.Member;
import model.Project;
import model.Task;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Gabriel Porto
 */
public class TaskDao {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String DATA_FILE = "dataTask.txt";
    private static final String DATA_FILE_TMP = "dataTask-tmp.txt";

    public TaskDao() {
        checkFile();
    }

    public void checkFile() {
        File dataFile = new File(DATA_FILE);
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                System.out.println("Data Task file created with success");
            } catch (IOException e) {
                System.out.println("Exception creating data task file. Exception: " + e);
            }
        }
    }

    public String create(Task task) {
        task.setId(generateId());
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE, true));
            bw.write(this.parseTaskToData(task));
            bw.flush();
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Exception creating object. Exception: " + e);
        }
        return task.getId();
    }

    public Task read(String id) {
        Task task = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            String data;
            while ((data = br.readLine()) != null) {
                if (!id.isEmpty() && data.contains(id)) {
                    task = parseDataToTask(data);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Exception reading object. Exception: " + e);
        }
        return task;
    }

    public List<Task> readAll() {
        List<Task> tasks = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            String data;
            while ((data = br.readLine()) != null) {
                tasks.add(parseDataToTask(data));
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Exception reading objects. Exception: " + e);
        }
        return tasks;
    }

    public List<Task> readAllByFilter(String filter) {
        List<Task> tasks = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            String data;
            while ((data = br.readLine()) != null) {
                if (data.contains(filter)) {
                    tasks.add(parseDataToTask(data));
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Exception reading objects. Exception: " + e);
        }
        return tasks;
    }
    
    public List<Task> changeTaskType() {
        List<Task> tasks = this.readAll();
        List<Task> tasksUpdate = new ArrayList<>();
        ProjectDao projectDao = new ProjectDao();
        Project project = projectDao.readOne();

        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            String data;
            int i = 0;
            while ((data = br.readLine()) != null) {
                List<Integer> typeActive = new ArrayList<>();
                String type = "";
                Task task = tasks.get(i);
                typeActive = tasks.get(i).getTypeActive();
                if(typeActive.get(0) == 1) {
                    type = project.getType1();
                } else if(typeActive.get(1) == 1) {
                    type = project.getType2();
                } else if(typeActive.get(2) == 1) {
                    type = project.getType3();
                } else if(typeActive.get(3) == 1) {
                    type = project.getType4();
                }
                task.setType(type);
                tasksUpdate.add(task);
                i++;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Exception reading objects. Exception: " + e);
        }
        return tasksUpdate;
    }
    
    public void update(Task task) {
        try {
            File db = new File(DATA_FILE);
            File tempDB = new File(DATA_FILE_TMP);

            BufferedReader br = new BufferedReader(new FileReader(db));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempDB));

            String data;
            while ((data = br.readLine()) != null) {
                if (!task.getId().isEmpty() && data.contains(task.getId())) {
                    bw.write(parseTaskToData(task));
                } else {
                    bw.write(data);
                }
                bw.flush();
                bw.newLine();
            }

            br.close();
            bw.close();
            db.delete();

            boolean status = tempDB.renameTo(db);
            System.out.println("Update status: " + status);
        } catch (IOException e) {
            System.out.println("Exception updating object. Exception: " + e);
        }
    }

    public void delete(String id) {
        try {
            File db = new File(DATA_FILE);
            File tempDB = new File(DATA_FILE_TMP);

            BufferedReader br = new BufferedReader(new FileReader(db));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempDB));

            String dataTask;
            while ((dataTask = br.readLine()) != null) {
                if (!id.isEmpty() && dataTask.contains(id)) {
                    continue;
                }
                bw.write(dataTask);
                bw.flush();
                bw.newLine();
            }

            br.close();
            bw.close();
            db.delete();

            boolean status = tempDB.renameTo(db);
            System.out.println("Delete status: " + status);
        } catch (IOException e) {
            System.out.println("Exception removing object. Exception: " + e);
        }
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }

    private String parseTaskToData(Task task) throws IOException {
        return objectMapper.writeValueAsString(task);
    }

    private Task parseDataToTask(String data) throws IOException {
        return objectMapper.readValue(data, Task.class);
    }
    
}
