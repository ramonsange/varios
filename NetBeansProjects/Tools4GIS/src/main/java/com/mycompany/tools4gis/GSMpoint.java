/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tools4gis;

/**
 *
 * @author Ramon
 */
public class GSMpoint implements Comparable<GSMpoint>
{
    String name;

    
    int latG;
    int latMin;
    int latSec;
    String latOr;
    
    int lonG;
    int lonMin;
    int lonSec;
    String lonOr;
    
    int seq;

    
    @Override
    public String toString()
    {
        return latG+""+latMin+""+latSec+""+latOr+""+lonG+""+lonMin+""+lonSec+""+lonOr+"    "+seq;
    }
    
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getLatOr() {
        return latOr;
    }

    public void setLatOr(String latOr) {
        this.latOr = latOr;
    }

    public String getLonOr() {
        return lonOr;
    }

    public void setLonOr(String lonOr) {
        this.lonOr = lonOr;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getLatG() {
        return latG;
    }

    public void setLatG(int latG) {
        this.latG = latG;
    }

    public int getLatMin() {
        return latMin;
    }

    public void setLatMin(int latMin) {
        this.latMin = latMin;
    }

    public int getLatSec() {
        return latSec;
    }

    public void setLatSec(int latSec) {
        this.latSec = latSec;
    }

    public int getLonG() {
        return lonG;
    }

    public void setLonG(int lonG) {
        this.lonG = lonG;
    }

    public int getLonMin() {
        return lonMin;
    }

    public void setLonMin(int lonMin) {
        this.lonMin = lonMin;
    }

    public int getLonSec() {
        return lonSec;
    }

    public void setLonSec(int lonSec) {
        this.lonSec = lonSec;
    }
    
    
    public GSMpoint getAcopy(GSMpoint original)
    {
        GSMpoint copy = new GSMpoint();
        
        copy.setLatG(original.getLatG());
        copy.setLatMin(original.getLatMin());
        copy.setLatSec(original.getLatSec());

        copy.setLonG(original.getLonG());
        copy.setLonMin(original.getLonMin());
        copy.setLonSec(original.getLonSec());
        
        copy.setLatOr(original.getLatOr());
        copy.setLonOr(original.getLonOr());
        
        copy.setSeq(original.getSeq());
        copy.setName(original.getName());
        
        return copy;
        
    }
    public GSMpoint()
    {
        
    }
    
    @Override
    public int compareTo(GSMpoint other) {
        return this.seq - other.seq;
                
    }
    
}
