/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel Porto
 */
public class Member {
    private String id;
    private String name;
    private List<String> type = new ArrayList<>();
    
    public Member() {
        this.id = "";
        this.name = "";
    }
    
    public Member(String id, String name, List<String> type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(List<String> type) {
        this.type = type;
    }
    
    
}
