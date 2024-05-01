package com.mycompany.sendemail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class SendEmail 
{
    List manufactures = null;
    public void send() throws MessagingException, IOException, FileNotFoundException, SQLException {

        final String username = "ramonsangee@hotmail.com";
        final String password = "Cantajuegos2020@@@***";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", "smtp-mail.outlook.com");
        
        this.manufactures=this.getManufaturesData("C:\\Users\\Ramon\\Downloads\\temp\\manufactures.txt");

        Session session = Session.getInstance(props, new Authenticator() 
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try 
        {
            for(int i=0;i<manufactures.size();i++)
            {
                //Create a temporary folder to store the attachements
                //Files.createDirectories(Paths.get("c:\\Users\\ramon\\Downloads\\temp\\"));


                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                String emailAdd=((Manufacture)manufactures.get(i)).getEmail();
                String manufacture=((Manufacture)manufactures.get(i)).getName();
                System.out.println("Enviando a "+manufacture);
                
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAdd));
                

                message.setSubject("Idea to Enhance Your Active Security System" );
                String msg = this.loadMessage("c:\\Users\\ramon\\Downloads\\temp\\message.txt", manufacture);
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);

                // Attachments           
                /*File folder = new File("c:\\Users\\ramon\\Downloads\\temp\\");

                for (final File fileEntry : folder.listFiles()) {

                    System.out.println(fileEntry.getAbsolutePath());

                    MimeBodyPart attachmentPart = new MimeBodyPart();

                    attachmentPart.attachFile(new File(fileEntry.getAbsolutePath()));

                    multipart.addBodyPart(attachmentPart);

                }*/

                message.setContent(multipart);

                Transport.send(message);

                //Delete the temporary attachements folder
                //FileUtils.deleteDirectory(new File("c:\\Users\\ramon\\Downloads\\temp\\"));

                System.out.println("\tEnviado");
            }

        } catch (MessagingException e) {

            throw new RuntimeException(e);

        }

    }
    
    public  String loadMessage(String filePath, String manufacture) 
    {
        String message = "<html>";

        try ( BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                
                message += line + "<br>"; // Agregar línea al mensaje
            }
            message=message.replaceAll("<MANUFACTURE>", manufacture);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message+="</html>";
    }
    
    public  List<Manufacture> getManufaturesData(String filePath) 
    {
        
        List<Manufacture> list=new ArrayList<>();
        try ( BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] temp=line.split(";");
                Manufacture m=new Manufacture();
                m.setName(temp[0]);
                m.setEmail(temp[1]);
                list.add(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
