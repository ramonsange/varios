/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sendemail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import javax.mail.MessagingException;

/**
 *
 * @author Ramon
 */
public class Run 
{
    public static void main(String[] args) throws MessagingException, IOException, FileNotFoundException, SQLException
    {
        SendEmail sendEmail = new SendEmail();
        sendEmail.send();
        
    }
    
}
