/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testauto;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import org.openqa.selenium.Keys;

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
public class SendToBuilders {

    /**
     * @param args the command line arguments
     */
    
    public SendToBuilders()
    {
        
    }
    
    public  void send() throws InterruptedException, MalformedURLException, IOException 
    {
        List listOfTrades=getTradesFromDirectory("C:\\Users\\Ramon\\Downloads\\builders");
        
        
        System.setProperty("webdriver.chrome.driver", "C:\\lib\\selenium\\drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
 
        
        driver.manage().window().maximize();
        
        
        
        for(int i=0;i<listOfTrades.size();i++)
        {
            Thread.sleep(5000);
            if(
                ((String)listOfTrades.get(i)).toLowerCase().contains("jhcommercial")||
                ((String)listOfTrades.get(i)).toLowerCase().contains("nugen")
              )
                
                
            {
                continue;
            }
                
                
        
            try
            {
                //cargamos el builder
                System.out.println("Cargando builder..."+ listOfTrades.get(i));
                driver.get((String)listOfTrades.get(i));
                if(i==0)//aceptamos la politica de coockies solo la primera vez que visitamos la web o con el primer builder
                {
                    System.out.println("Limpiando coockies...");
                    driver.findElement(By.cssSelector("#onetrust-accept-btn-handler")).click();
                    System.out.println("Coockies limpias");
                }
                System.out.println("Builder cargado...");
                //una vez cargado el builder, hacemos click en el boton para que nos lleve a rellenar el formulario de envio
                System.out.println("Yendo al al formulario de contacto...");
                
                //sc-6bac5830-0 crZSbT sc-6bac5830-3 esipQI
                click(driver,".sc-6bac5830-3", 1000);
                click(driver,".sc-7f50166e-6", 1000);
                click(driver,".sc-7f50166e-6", 1000);
                click(driver,".sc-7f50166e-6", 1000);
                
                try
                {
                    driver.findElement(By.name("location")).sendKeys("SO15 2PB");
                    //driver.findElement(By.name("location")).sendKeys("LS1 1EB");
                    
                    Thread.sleep(1000);
                }
                catch(Exception ex)
                {
                    System.out.println(ex.getCause());
                }
                try
                {
                    driver.findElements(By.cssSelector(".sc-6bac5830-1")).get(3).click();
                    Thread.sleep(1000);
                }
                catch(Exception ex)
                {
                    System.out.println(ex.getCause());
                }
                
                
                
                
                //rellenamos el formulario de envio
                System.out.println("Rellenado el formulario...");
                
                String description="Hi, \n" +
                          "I need to fix or replace existing floating floor and insulation. Is this something you can do please?\n"
                        + "If yes, please let me know at your earliest convinience. "+
                          "Regards\n" +
                          "Ramon";
                //String description="RWE REWRtertERTE EWRTG eRTYEWt EWTer WETRYWETRYUWER YTWerTGEWRYEW terTeRYEWTYWERYeRYERYEW WERETYEWRYEWRYEWRYERTY";
                driver.findElement(By.name("job_description")).sendKeys(description);
                                
                driver.findElement(By.name("name")).sendKeys("Ramon");
                driver.findElement(By.name("email")).sendKeys("ramonsangee@hotmail.com");
                driver.findElement(By.name("phone_number")).sendKeys("07415830912");
                driver.findElement(By.name("post_code")).sendKeys("SO15 2PB");
                
                /*driver.findElement(By.name("name")).sendKeys("Peter Rodd");
                driver.findElement(By.name("email")).sendKeys("peterrod@gmail.com");
                driver.findElement(By.name("phone_number")).sendKeys("07584556366");
                driver.findElement(By.name("post_code")).sendKeys("SW15 6TR");*/
                
                
                System.out.println("Formulario relleno...");
                System.out.println("Enviando formulario...");
                //Enviar !
                
                click(driver,".sc-6bac5830-2", 1000);
                
                
                //
                
                System.out.println("Formulario enviado....");
                Thread.sleep(5000);
                
            }
            catch(Exception ex)
            {
                System.out.println("**** Error "+(String)listOfTrades.get(i));
                listOfTrades.add((String)listOfTrades.get(i));
                ex.printStackTrace();
            }
            System.out.println("************************************************");
        }
    }
    
    public void click(WebDriver driver, String css, long time)
    {
        try
        {
            driver.findElement(By.cssSelector(css)).click();
            Thread.sleep(time);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
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
