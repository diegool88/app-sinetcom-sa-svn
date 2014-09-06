/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.webutil;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author diegoflores
 */
public class Mensajes {
    
    public static void mostrarMensajeInformativo(String mensaje){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public static void mostrarMensajeDeError(String mensaje){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", mensaje); 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public static void mostrarMensajeDeAdvertencia(String mensaje){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia: ",  mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
