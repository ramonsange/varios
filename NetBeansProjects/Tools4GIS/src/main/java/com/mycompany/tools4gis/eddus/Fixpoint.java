package com.mycompany.tools4gis.eddus;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Ramon
 */
public class Fixpoint {
    private long id;
    private String designator;
    private String position;
    String remarks;

    public Fixpoint(long id, String designator, String position,String remarks) {
        this.id = id;
        this.designator = designator;
        this.position = position;
        this.remarks = remarks;
    }

    public String getLatlong() {
        return position;
    }

    public void setLatlong(String latlong) {
        this.position = latlong;
    }

    public String getDescription() {
        return remarks;
    }

    public void setDescription(String description) {
        this.remarks = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesignator() {
        return designator;
    }

    public void setDesignator(String designator) {
        this.designator = designator;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    
    
}
