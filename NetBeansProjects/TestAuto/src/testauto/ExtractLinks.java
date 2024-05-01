/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testauto;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author ramon
 */
public class ExtractLinks {

    /**
     * @param args the command line arguments
     */
    
    public ExtractLinks()
    {
        
    }
    
    public  void extract() throws InterruptedException, MalformedURLException, IOException 
    {
        
        System.setProperty("webdriver.chrome.driver", "C:\\lib\\selenium\\drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        /*list.add("https://www.checkatrade.com/trades/twickenhamsalesandservicecentreltd");
        list.add("https://www.checkatrade.com/trades/twickenhamsalesandservicecentreltd");
        list.add("https://www.checkatrade.com/trades/campbellsmobilemechanic");
        list.add("https://www.checkatrade.com/trades/maypolemotors");
        list.add("https://www.checkatrade.com/trades/churchhillmotors");
        list.add("https://www.checkatrade.com/trades/nlcelectricalandhomemaintenanc");*/
        
        driver.manage().window().maximize();
        
        //https://www.checkatrade.com/Search?page=2&categoryId=2&location=SO15%202TF
        
        
        
        for(int i=0;i<50;i++)
        {
            Thread.sleep(5000);
            String pagina="https://www.checkatrade.com/Search?page="+i+"&categoryId=2&location=SO15%202TF";
            try
            {
                //cargamos el builder
                System.out.println("Cargando abriendo pagina..."+ pagina);
                driver.get(pagina);
                if(i==0)//aceptamos la politica de coockies solo la primera vez que visitamos la web o con el primer builder
                {
                    System.out.println("Limpiando coockies...");
                    driver.findElement(By.cssSelector("#onetrust-accept-btn-handler")).click();
                    System.out.println("Coockies limpias");
                }
                System.out.println("Pagina cargada...");
                Robot r=new Robot();
                r.mouseMove(500,500);
                r.keyPress(KeyEvent.BUTTON3_DOWN_MASK);
                r.keyRelease(KeyEvent.BUTTON3_DOWN_MASK);
                
                //driver.findElement(By.name("all-links")).click();
                
                Thread.sleep(5000);
                
            }
            catch(Exception ex)
            {
                System.out.println("**** Error ");
                
                ex.printStackTrace();
            }
            System.out.println("************************************************");
        }
        
        

        
       
    }
    public static List  getTradesFromDirectory(String directoryPath) 
    {
        List list=new ArrayList();
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
                List temp=extractTradersFromFile(directoryPath+"\\"+listOfFiles[i].getName());
                
                for(int k=0;k<temp.size();k++)
                {
                    list.add(temp.get(k));
                }
                
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    return list;    
    }
    public static List extractTradersFromFile(String file)
    {
        List list=new ArrayList();
        
        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.contains("trades"))
                {
                    System.out.println(" *** "+data);
                    list.add(data);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        
        return list;
        
    }
    
    
    
}
