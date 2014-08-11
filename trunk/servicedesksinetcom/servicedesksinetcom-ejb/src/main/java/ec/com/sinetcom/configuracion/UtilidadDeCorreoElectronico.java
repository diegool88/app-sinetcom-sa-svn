/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.configuracion;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author diegoflores
 */
public class UtilidadDeCorreoElectronico {
    
    final private String SMTPServer = "mail.sinetcom.com.ec";
    final private String SMTPServerPort = "25";
    final private String SMTPEmail = "soporte@sinetcom.com.ec";
    final private String SMTPPassword = "Sinetcom123";
    
    private Email email = new Email() {

            @Override
            public Email setMsg(String string) throws EmailException {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    
    public UtilidadDeCorreoElectronico(){
    
    }
    
    /**
     * Función que permite enviar un correo simple
     * @param de
     * @param para
     * @param asunto
     * @param cuerpo
     * @param cc
     * @throws EmailException 
     */
    public void enviarCorreoSimple(String de, String para, String asunto, String cuerpo, String[] cc) throws EmailException{
        this.email = new SimpleEmail();
        this.email.setHostName(SMTPServer);
        this.email.setSmtpPort(465);
        this.email.setAuthenticator(new DefaultAuthenticator(SMTPEmail, SMTPPassword));
        this.email.setSSL(true);
        this.email.setFrom(de);
        for(String copia : cc){
            this.email.addCc(copia);
        }
        this.email.setSubject(asunto);
        this.email.setMsg(cuerpo);
        this.email.addTo(para);
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
    
    
}
