/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Gabriel Porto
 */
public class Task {
    private String id;
    private String name;
    private String description;
    private String type;
    private String stage;
    private String member;
    
    public Task() {
        this.id = "";
        this.name = "";
        this.description = "";
        this.type = "";
        this.stage = "To Do Stage";
        this.member = "";
    }
    
    public Task(String name, String type, String member) {
        this.id = "0";
        this.name = name;
        this.description = "";
        this.type = type;
        this.stage = "To Do Stage";
        this.member = member;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getStage() {
        return stage;
    }

    public String getMember() {
        return member;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public void setMember(String member) {
        this.member = member;
    }
    
    
    
}
