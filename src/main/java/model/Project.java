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
public class Project {
    private String id;
    private String name;
    private String deliveryDate;
    private String type1;
    private String type2;
    private String type3;
    private String type4;
    private List<String> allTypes = new ArrayList<>();
    
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
    
    
    
}
