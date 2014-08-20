/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.webutil.Mensajes;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name="misTicketsPorEstado")
@ViewScoped
public class MisTicketsPorEstado implements Serializable{
    
    
    
    @PostConstruct
    public void doInit(){
        
    }
    
    public void cambioDeTab(TabChangeEvent event){
        Tab activo = event.getTab();
        Mensajes.mostrarMensajeInformativo(activo.getAttributes().get("title").toString());
    }
}
