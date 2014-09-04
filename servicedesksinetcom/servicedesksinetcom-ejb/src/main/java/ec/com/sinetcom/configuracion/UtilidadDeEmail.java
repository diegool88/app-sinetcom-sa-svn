/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.configuracion;

import com.sun.mail.smtp.SMTPTransport;
import java.io.FileOutputStream;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 *
 * @author diegoflores
 */
public class UtilidadDeEmail {

    private String SMTPServer = "mail.sinetcom.com.ec";
    private String SMTPServerPort = "25";
    private String SMTPEmail = "soporte@sinetcom.com.ec";
    private String SMTPPassword = "Sinetcom123";

    public UtilidadDeEmail() {
    }

    public boolean enviarMensajeConAdjunto(String de, String para, String asunto, String cuerpo, List<String> cc, byte[] adjunto, String nombreArchivo) {
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.setProperty("mail.smtp.host", SMTPServer);
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(de));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
            // Set CC
            for(String correo : cc){
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(correo));
            }
            // Set Subject: header field
            message.setSubject(asunto);
            // Create the message part 
            BodyPart messageBodyPart = new MimeBodyPart();
            // Fill the message
            messageBodyPart.setText(cuerpo);
            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            //Guardar Archivo Adjunto
            if(adjunto != null){
                guardarArchivoAdjunto(nombreArchivo, adjunto);
            }
            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "/temp/" + nombreArchivo;
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);
            
            // Send message
            Transport.send(message);
            //Transport.send(message);
            System.out.println("Sent message successfully....");
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return false;
        }
    }
    
    public boolean guardarArchivoAdjunto(String nombreArchivo, byte[] blob) {

        boolean success = true;

        try {
            FileOutputStream fileOuputStream = new FileOutputStream("/temp/" + nombreArchivo);
            fileOuputStream.write(blob);
            fileOuputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        } finally {
            return success;
        }

    }
    
    public String getSMTPServer() {
        return SMTPServer;
    }
    
    public String getSMTPServerPort() {
        return SMTPServerPort;
    }

    public String getSMTPEmail() {
        return SMTPEmail;
    }

    public String getSMTPPassword() {
        return SMTPPassword;
    }

}
