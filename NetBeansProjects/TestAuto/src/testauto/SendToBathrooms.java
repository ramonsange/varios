/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testauto;

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
public class SendToBathrooms {

    /**
     * @param args the command line arguments
     */
    WebDriver driver;
    List listaIndeseados =new ArrayList();
    public SendToBathrooms()
    {
        System.setProperty("webdriver.chrome.driver", "C:\\lib\\selenium\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        
        listaIndeseados.add("https://www.checkatrade.com/trades/JhCommercialAndDomesticProper");
        listaIndeseados.add("https://www.checkatrade.com/trades/cblannbuildingservices");
    }
    public void stop()
    {
        driver.quit();
    }
    public  void send() throws InterruptedException, MalformedURLException, IOException 
    {
        List listOfTrades=getTradesFromDirectory("C:\\Users\\ramon\\Downloads\\temporal");
        
        
        /*list.add("https://www.checkatrade.com/trades/twickenhamsalesandservicecentreltd");
        list.add("https://www.checkatrade.com/trades/twickenhamsalesandservicecentreltd");
        list.add("https://www.checkatrade.com/trades/campbellsmobilemechanic");
        list.add("https://www.checkatrade.com/trades/maypolemotors");
        list.add("https://www.checkatrade.com/trades/churchhillmotors");
        list.add("https://www.checkatrade.com/trades/nlcelectricalandhomemaintenanc");*/
        
        driver.manage().window().maximize();
        
        
        
        for(int i=0;i<listOfTrades.size();i++)
        {
            Thread.sleep(5000);
        
            try
            {
                if(((String)listOfTrades.get(i)).contains("/trades/")&& (!encontradoEnIndeseados(listaIndeseados, (String)listOfTrades.get(i))))
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
                    //driver.findElement(By.cssSelector("#__next > div > div.o4wym5-1.LDaOu > div > div.sc-1cowc4v-7.jXqnxT > div.sc-1cowc4v-8.eFUvhk > div.sc-1j35h8p-0.dqOdDK > div.sc-1j35h8p-1.eczBBT > div.sc-1m5wtrs-0.gKxJWE > div.sc-1m5wtrs-2.sc-1m5wtrs-4.sc-1m5wtrs-5.fePgCB > button > span")).click();
                    driver.findElement(By.cssSelector("button[class='sc-1il0x0f-1 y5xpqy-1 eIJvXX y5xpqy-3 y5xpqy-5 sc-1m5wtrs-6 bTCPxd'] span[class='sc-1il0x0f-0 y5xpqy-0 hQxLPW']")).click();
                    //rellenamos el formulario de envio
                    System.out.println("Rellenado el formulario...");

                    String description="Remove garage door\n" +
                                        "Fit in 2 new UPVC double glazzed windows\n" +
                                        "Insulate and batten existing concrete floor\n" +
                                        "Water shall be isolated to existing washing machine area\n" +
                                        "Fuse board and electric meter to be boxed\n" +
                                        "All walls plasterboarded, and plastered to a smooth finish including ceiling\n" +
                                        "New 8 spotlights and 2 switches\n" +
                                        "New 4 double sockets and 2 tv points\n" +
                                        "Adjust or refit existing door to floor level\n" +
                                        "Fit in 2 new radiators\n" +
                                        "New skirting boards and architraves matching existing\n" +
                                        "To be completed ASAP";

                    System.out.println(description);

                    driver.findElement(By.name("job_description")).sendKeys(description);
                    driver.findElement(By.name("name")).sendKeys("Ramon Sange");
                    driver.findElement(By.name("email")).sendKeys("ramonsangee@hotmail.com");
                    driver.findElement(By.name("phone_number")).sendKeys("07415830912");
                    driver.findElement(By.name("post_code")).sendKeys("SO15 2PB");
                    System.out.println("Formulario relleno...");
                    System.out.println("Enviando formulario...");
                    //FALTA DARLE A ENVIAR
                   // driver.findElement(By.cssSelector("button[type='submit'] span[class='sc-1il0x0f-0 y5xpqy-0 hQxLPW']")).click();
                    System.out.println("Formulario enviado....");

                    Thread.sleep(5000);
                }
                else
                {
                    System.out.println("Indeseado "+(String)listOfTrades.get(i));
                }
                
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
    
    public boolean encontradoEnIndeseados( List list,String indeseado)
    {
        return list.contains(indeseado);
        
    }
            
           
    public static List  getTradesFromDirectory(String directoryPath) 
    {
        List list=new ArrayList();
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                //System.out.println("File " + listOfFiles[i].getName());
                List temp=extractTradersFromFile(directoryPath+"\\"+listOfFiles[i].getName());
                
                for(int k=0;k<temp.size();k++)
                {
                    list.add(temp.get(k));
                }
                
            } else if (listOfFiles[i].isDirectory()) {
                //System.out.println("Directory " + listOfFiles[i].getName());
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
