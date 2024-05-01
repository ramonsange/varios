/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tools4gis;

import java.util.List;

/**
 *
 * @author Ramon
 */
public class Sector 
{
    String id;
    String type;
    String condition;
    List<Volumen> responsinilityVolumes;
    List<Volumen> interestVolumes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    
    
    public List<Volumen> getResponsinilityVolumes() {
        return responsinilityVolumes;
    }

    public void setResponsinilityVolumes(List<Volumen> responsinilityVolumes) {
        this.responsinilityVolumes = responsinilityVolumes;
    }

    public List<Volumen> getInterestVolumes() {
        return interestVolumes;
    }

    public void setInterestVolumes(List<Volumen> interestVolumes) {
        this.interestVolumes = interestVolumes;
    }
    
    
    
    
}
