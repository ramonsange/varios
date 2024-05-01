/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tools4gis;

/**
 *
 * @author Ramon
 */
public class Volumen 
{
    private String id;
    private Contour contour;
    private int upLimit;
    private int loLimit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Contour getContour() {
        return contour;
    }

    public void setContour(Contour contour) {
        this.contour = contour;
    }

    public int getUpLimit() {
        return upLimit;
    }

    public void setUpLimit(int upLimit) {
        this.upLimit = upLimit;
    }

    public int getLoLimit() {
        return loLimit;
    }

    public void setLoLimit(int loLimit) {
        this.loLimit = loLimit;
    }
    
    
}
