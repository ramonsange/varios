package com.mycompany.tools4gis.eddus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static final String DATABASE_URL = "jdbc:ucanaccess://C://Users//Ramon//Downloads//AdaptationAIRAC323_B//AdaptationAIRAC323_B.mdb";
    private static final String DATABASE_USER = "myuser";
    private static final String DATABASE_PASSWORD = "mypassword";
    Connection connection = null;
    
    public Connector(String url)
    {
        try
        {
            String temp ="jdbc:ucanaccess://"+url;
            System.out.println("URL: "+temp);
            System.out.println("Opening connection");
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            this.connection =  DriverManager.getConnection(temp, DATABASE_USER, DATABASE_PASSWORD);
            
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public Connection getConnection()
    {
        return this. connection;
    }
    public void closeConnection()
    {
        try
        {
            System.out.println("Closing connection");
            this.connection.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

}