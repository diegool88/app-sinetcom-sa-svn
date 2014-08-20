/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.configuracion;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author diegoflores
 */
public class UtilidadDeCorreoElectronico {

    private String SMTPServer = "192.168.150.50";
    private String SMTPServerPort = "25";
    private String SMTPEmail = "soporte@sinetcom.com.ec";
    private String SMTPPassword = "Sinetcom123";
    
    private Email email = new Email() {
        @Override
        public Email setMsg(String string) throws EmailException {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    private MultiPartEmail emailM = new MultiPartEmail();

    public UtilidadDeCorreoElectronico() {
    }

    /**
     * Función que permite enviar un correo simple
     *
     * @param de
     * @param para
     * @param asunto
     * @param cuerpo
     * @param cc
     * @throws EmailException
     */
    public void enviarCorreoSimple(String de, String para, String asunto, String cuerpo, List<String> cc) throws EmailException {
        this.email = new SimpleEmail();
        this.email.setHostName(SMTPServer);
        this.email.setSmtpPort(Integer.parseInt(SMTPServerPort));
        this.email.setAuthenticator(new DefaultAuthenticator(SMTPEmail, SMTPPassword));
        this.email.setSSL(true);
        this.email.setFrom(de);
        for (String copia : cc) {
            this.email.addCc(copia);
        }
        this.email.setSubject(asunto);
        this.email.setMsg(cuerpo);
        this.email.addTo(para);
        this.email.send();
    }

    public void enviarCorreoConAdjunto(String de, String para, String asunto, String cuerpo, List<String> cc, byte[] adjunto, String nombreArchivo) throws EmailException {
        this.emailM = new MultiPartEmail();
        this.emailM.setHostName("mail.sinetcom.com.ec");
        //this.email.setSSL(false);
        //this.emailM.setSmtpPort(Integer.parseInt(SMTPServerPort));
        //this.emailM.setAuthenticator(new DefaultAuthenticator(SMTPEmail, SMTPPassword));
        //this.emailM.setSSL(false);
        this.emailM.setFrom(de);
        for (String copia : cc) {
            this.emailM.addCc(copia);
        }
        this.emailM.setSubject(asunto);
        this.emailM.setMsg(cuerpo);
        this.emailM.addTo(para);
        if (adjunto != null && nombreArchivo != null) {
            //Se crea el archivo adjunto
            guardarArchivoAdjunto(nombreArchivo, adjunto);
            //Agregar archivo adjunto al correo
            EmailAttachment archivoAdjunto = new EmailAttachment();
            archivoAdjunto.setPath("/temp/" + nombreArchivo);
            archivoAdjunto.setDisposition(EmailAttachment.ATTACHMENT);
            archivoAdjunto.setDescription("Archivo Adjunto");
            archivoAdjunto.setName(nombreArchivo.toUpperCase());
            //Se agrega el adjunto al correo
            this.emailM.attach(archivoAdjunto);
        }
        //Se envia el correo
        this.email.send();
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

    public Email getEmail() {
        return email;
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
}
