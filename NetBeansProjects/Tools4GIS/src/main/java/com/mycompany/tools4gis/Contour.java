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
public class Contour 
{
    String id;
    List<GSMpoint> points;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<GSMpoint> getPoints() {
        return points;
    }

    public void setPoints(List<GSMpoint> points) {
        this.points = points;
    }
    
    
    
}
