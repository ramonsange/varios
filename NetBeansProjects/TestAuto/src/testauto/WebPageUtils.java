/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testauto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class WebPageUtils 
{

    public  String readWeb(String urlString) throws IOException, InterruptedException 
    {

        String outPut="";
        try {
            URL url = new URL(urlString);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            
 
            while (in.readLine()!= null) {
                // Process each line.
                outPut+=in.readLine();
                
            }
            in.close(); 
 
        } catch (MalformedURLException me) {
            System.out.println(me); 
 
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        return outPut;
    }
    
    public void getURLsFromWeb(String data)
    {
        
        System.out.println(data);
    }
}
