
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
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Gabriel Porto
 */
public class MemberDao {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String DATA_FILE = "dataMember.txt";
    private static final String DATA_FILE_TMP = "dataMember-tmp.txt";

    public MemberDao() {
        checkFile();
    }

    public void checkFile() {
        File dataFile = new File(DATA_FILE);
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                System.out.println("Data Member file created with success");
            } catch (IOException e) {
                System.out.println("Exception creating data member file. Exception: " + e);
            }
        }
    }
    
    public boolean checkEmpty() {
        boolean result = true;
        List<Member> members = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            String data;
            while ((data = br.readLine()) != null) {
                members.add(parseDataToMember(data));
                result = false;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Exception reading objects. Exception: " + e);
        }

        return result;
    }

    public String create(Member member) {
        member.setId(generateId());
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE, true));
            bw.write(this.parseMemberToData(member));
            bw.flush();
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Exception creating object. Exception: " + e);
        }
        return member.getId();
    }

    public Member read(String id) {
        Member member = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            String data;
            while ((data = br.readLine()) != null) {
                if (!id.isEmpty() && data.contains(id)) {
                    member = parseDataToMember(data);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Exception reading object. Exception: " + e);
        }
        return member;
    }

    public List<Member> readAll() {
        List<Member> members = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            String data;
            while ((data = br.readLine()) != null) {
                members.add(parseDataToMember(data));
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Exception reading objects. Exception: " + e);
        }
        return members;
    }

    public List<Member> readAllByFilter(String filter) {
        List<Member> members = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            String data;
            while ((data = br.readLine()) != null) {
                if (data.contains(filter)) {
                    members.add(parseDataToMember(data));
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Exception reading objects. Exception: " + e);
        }
        return members;
    }
    
    public List<String> readAllByName() {
        List<Member> members = this.readAll();
        List<String> nameMembers = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            String data;
            int i = 0;
            while ((data = br.readLine()) != null) {
                nameMembers.add(members.get(i).getName());
                i++;

            }
            br.close();
        } catch (IOException e) {
            System.out.println("Exception reading objects. Exception: " + e);
        }
        return nameMembers;
    }
    
    public void update(Member member) {
        try {
            File db = new File(DATA_FILE);
            File tempDB = new File(DATA_FILE_TMP);

            BufferedReader br = new BufferedReader(new FileReader(db));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempDB));

            String data;
            while ((data = br.readLine()) != null) {
                if (!member.getId().isEmpty() && data.contains(member.getId())) {
                    bw.write(parseMemberToData(member));
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

            String dataMember;
            while ((dataMember = br.readLine()) != null) {
                if (!id.isEmpty() && dataMember.contains(id)) {
                    continue;
                }
                bw.write(dataMember);
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

    private String parseMemberToData(Member member) throws IOException {
        return objectMapper.writeValueAsString(member);
    }

    private Member parseDataToMember(String data) throws IOException {
        return objectMapper.readValue(data, Member.class);
    }
    
}
