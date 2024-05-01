/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sendemail;

/**
 *
 * @author Ramon
 */
public class Manufacture
{
    String name;
    String email;
    
    public Manufacture(String name, String email)
    {
        this.email=email;
        this.name=name;
        
    }

    Manufacture() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}
