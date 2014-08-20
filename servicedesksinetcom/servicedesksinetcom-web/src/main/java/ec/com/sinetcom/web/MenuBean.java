/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.dao.ColaFacade;
import ec.com.sinetcom.dao.EstadoTicketFacade;
import ec.com.sinetcom.dao.PrioridadTicketFacade;
import ec.com.sinetcom.orm.Cola;
import ec.com.sinetcom.orm.EstadoTicket;
import ec.com.sinetcom.orm.PrioridadTicket;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.BeforeCompletion;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.ws.rs.core.Response;
import org.primefaces.component.column.Column;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.MenuModel;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author diegoflores
 */
@ManagedBean
@RequestScoped
public class MenuBean implements Serializable{
    
//    @ManagedProperty("#{login}")
//    private AdministracionUsuarioBean login;

    @EJB
    private ColaFacade colaFacade;
    
    @EJB
    private EstadoTicketFacade estadoTicketFacade;
    
    @EJB
    private PrioridadTicketFacade prioridadTicketFacade;
    
    private List<Cola> colas;
    
    private List<EstadoTicket> estadoTickets;
    
    private List<PrioridadTicket> prioridadTickets;
    
//    public AdministracionUsuarioBean getLogin() {
//        return login;
//    }

    public List<Cola> getColas() {
        return colas;
    }

    public List<EstadoTicket> getEstadoTickets() {
        return estadoTickets;
    }

    public List<PrioridadTicket> getPrioridadTickets() {
        return prioridadTickets;
    }
    
    
    @PostConstruct
    public void doInit(){
        this.colas = this.colaFacade.findAll();
        this.estadoTickets = this.estadoTicketFacade.findAll();
        this.prioridadTickets = this.prioridadTicketFacade.findAll();
    }
    
    public String irAPagina(String pagina){
        return pagina + "?faces-redirect=true";
    }
    
    public void redirecionarAPagina(ActionEvent event){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        String seleccion = context.getRequestParameterMap().get("seleccion");
        try {
            context.redirect(context.getRequestContextPath() + seleccion + "?faces-redirect=true");
            //return ((String)event.getComponent().getAttributes().get("seleccion"))+"?faces-redirect=true";
        } catch (IOException ex) {
            Logger.getLogger(MenuBean.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }
}
