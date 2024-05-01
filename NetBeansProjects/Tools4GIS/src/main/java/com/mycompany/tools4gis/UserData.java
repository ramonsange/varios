/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tools4gis;

/**
 *
 * @author Ramon
 */
public class UserData 
{
    private String volumeId;
    private int hiLevel;
    private int loLevel;

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public int getHiLevel() {
        return hiLevel;
    }

    public void setHiLevel(int hiLevel) {
        this.hiLevel = hiLevel;
    }

    public int getLoLevel() {
        return loLevel;
    }

    public void setLoLevel(int loLevel) {
        this.loLevel = loLevel;
    }

    public UserData(String volumeId, int hiLevel, int loLevel) {
        this.volumeId = volumeId;
        this.hiLevel = hiLevel;
        this.loLevel = loLevel;
    }
    
    public String toString()
    {
        return this.volumeId+" "+this.loLevel+" "+this.hiLevel;
    }
    
    
}
