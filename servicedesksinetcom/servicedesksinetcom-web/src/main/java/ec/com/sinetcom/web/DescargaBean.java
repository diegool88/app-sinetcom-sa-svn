/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.Articulo;
import ec.com.sinetcom.servicios.TicketServicio;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name="descargaBean")
@RequestScoped
public class DescargaBean implements Serializable{
    
    @EJB
    private TicketServicio ticketServicio;
    
    @ManagedProperty(value="#{param.articuloId}")
    private String idArticulo;
    
    private StreamedContent archivoPorDescargar;
    
    private UICommand boton;
    
    @PostConstruct
    public void doInit(){
        //FacesContext facesContext = FacesContext.getCurrentInstance();
        //int idArticulo = Integer.parseInt(facesContext.getExternalContext().getRequestParameterMap().get("articuloId"));
        //Articulo articulo = this.ticketServicio.obtenerUnArticuloPorId(idArticulo);
        Articulo articulo = this.ticketServicio.obtenerUnArticuloPorId(Integer.parseInt(idArticulo));
        this.archivoPorDescargar = new DefaultStreamedContent(new ByteArrayInputStream(articulo.getContenidoAdjunto()), 
                articulo.getExtensionArchivo().equals("jpg") || 
                articulo.getExtensionArchivo().equals("jpeg") ||
                articulo.getExtensionArchivo().equals("gif") ||
                articulo.getExtensionArchivo().equals("png") ? "image/" + articulo.getExtensionArchivo() : "application/pdf", "archivo." + articulo.getExtensionArchivo());
    }

    public StreamedContent getArchivoPorDescargar() {
        return archivoPorDescargar;
    }

    public void setArchivoPorDescargar(StreamedContent archivoPorDescargar) {
        this.archivoPorDescargar = archivoPorDescargar;
    }

    public void setIdArticulo(String idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getIdArticulo() {
        return idArticulo;
    }

    public UICommand getBoton() {
        return boton;
    }

    public void setBoton(UICommand boton) {
        this.boton = boton;
    }
    
    
}   
