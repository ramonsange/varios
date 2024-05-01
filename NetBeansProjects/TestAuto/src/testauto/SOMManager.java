/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testauto;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
public class SOMManager {

    /**
     * @param args the command line arguments
     */
    
    WebDriver driver;
    Actions actions ;
    
    
    public  SOMManager()
    {/*
        System.setProperty("webdriver.chrome.driver", "C:\\lib\\selenium\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.autotrader.co.uk/");
        actions = new Actions(driver);  */
        
         try {
   
             URI uri = new URI("https://www.google.com");

            java.awt.Desktop.getDesktop().browse(uri);
            System.out.println("Web page opened in browser");

        } catch (Exception e) {

            e.printStackTrace();
        }
    
    }        

    public void send(Ventana ventana) throws InterruptedException, AWTException {
        
        
        Robot robot = new Robot();
        robot.mouseMove(ventana.getTx(), ventana.getTy());
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        String temp="Pepito".toUpperCase();
        
        for(int i=0;i<temp.length();i++)
        {
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress((int)temp.charAt(i));
            robot.keyRelease((int)temp.charAt(i));
            robot.keyRelease(KeyEvent.VK_SHIFT);
    
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        
        
        
        /*Robot robot = new Robot();
        Random random = new Random();
        robot.mouseMove(ventana.getTx(), ventana.getTy());
        Thread.sleep(3000);*/
        
        
        
        
        
        

        
        

        
        
        /*
        Actions action = new Actions(driver);
action.MoveByOffset(200,100).Perform();
Thread.Sleep(10000);
action.Click();*/
        
    }
        
    
    
}
