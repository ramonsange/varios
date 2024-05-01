/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.smsproject;
import java.io.IOException;
import org.jsmpp.bean.*;
import org.jsmpp.session.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsmpp.PDUException;
/**
 *
 * @author Ramon
 */
public class SMSProject {

    public static void main(String[] args) throws Exception {
        try {
            String server = "localhost";
            int port = 2775;
            String systemId = "mySystemId";
            String password = "myPassword";
            String sender = "senderId";
            String recipient = "00447415830912";
            String message = "Hello, world!";
            
            // Bind to SMSC
            SMPPSession session = new SMPPSession();
            session.connectAndBind(server, port, new BindParameter(BindType.BIND_TX, systemId, password, "cp", TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null));
            
            // Send SMS
            
            
            String messageId = session.submitShortMessage("CMT", TypeOfNumber.ALPHANUMERIC, NumberingPlanIndicator.UNKNOWN, sender, TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.ISDN, recipient, new ESMClass(), (byte)0, (byte)1, "ddd", null, new RegisteredDelivery(SMSCDeliveryReceipt.DEFAULT), (byte)0, new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false), (byte)0, message.getBytes());
            
            

            System.out.println("Message submitted, ID: " + messageId);
            
            // Unbind and disconnect
            session.unbindAndClose();
        } catch (IOException ex) {
            Logger.getLogger(SMSProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
