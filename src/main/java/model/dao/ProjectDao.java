
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
import model.Project;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Gabriel Porto
 */
public class ProjectDao {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String DATA_FILE = "dataProject.txt";
    private static final String DATA_FILE_TMP = "dataProject-tmp.txt";

    public ProjectDao() {
        checkFile();
    }

    public void checkFile() {
        File dataFile = new File(DATA_FILE);
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                System.out.println("Data Project file created with success");
            } catch (IOException e) {
                System.out.println("Exception creating data project file. Exception: " + e);
            }
        }
    }
    
    public boolean checkEmpty() {
        boolean result = true;
        List<Project> projects = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            String data;
            while ((data = br.readLine()) != null) {
                projects.add(parseDataToProject(data));
                result = false;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Exception reading objects. Exception: " + e);
        }

        return result;
    }

    public String create(Project project) {
        project.setId(generateId());
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE, true));
            bw.write(this.parseProjectToData(project));
            bw.flush();
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Exception creating object. Exception: " + e);
        }
        return project.getId();
    }
    
    public String createOne(Project project) {
        project.setId(generateId());
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE, true));
            bw.write(this.parseProjectToData(project));
            bw.flush();
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Exception creating object. Exception: " + e);
        }
        return project.getId();
    }

    public Project read(String id) {
        Project project = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            String data;
            while ((data = br.readLine()) != null) {
                if (!id.isEmpty() && data.contains(id)) {
                    project = parseDataToProject(data);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Exception reading object. Exception: " + e);
        }
        return project;
    }

    public List<Project> readAll() {
        List<Project> projects = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            String data;
            while ((data = br.readLine()) != null) {
                projects.add(parseDataToProject(data));
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Exception reading objects. Exception: " + e);
        }
        return projects;
    }
    
    public Project readOne() {
        Project project = new Project();
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            String data;
            while ((data = br.readLine()) != null) {
                project = parseDataToProject(data);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Exception reading objects. Exception: " + e);
        }
        return project;
    }
    

    public List<Project> readAllByFilter(String filter) {
        List<Project> projects = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            String data;
            while ((data = br.readLine()) != null) {
                if (data.contains(filter)) {
                    projects.add(parseDataToProject(data));
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Exception reading objects. Exception: " + e);
        }
        return projects;
    }
    
    public void update(Project project) {
        try {
            File db = new File(DATA_FILE);
            File tempDB = new File(DATA_FILE_TMP);

            BufferedReader br = new BufferedReader(new FileReader(db));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempDB));

            String data;
            while ((data = br.readLine()) != null) {
                if (!project.getId().isEmpty() && data.contains(project.getId())) {
                    bw.write(parseProjectToData(project));
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

            String dataProject;
            while ((dataProject = br.readLine()) != null) {
                if (!id.isEmpty() && dataProject.contains(id)) {
                    continue;
                }
                bw.write(dataProject);
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

    private String parseProjectToData(Project project) throws IOException {
        return objectMapper.writeValueAsString(project);
    }

    private Project parseDataToProject(String data) throws IOException {
        return objectMapper.readValue(data, Project.class);
    }
    
}
