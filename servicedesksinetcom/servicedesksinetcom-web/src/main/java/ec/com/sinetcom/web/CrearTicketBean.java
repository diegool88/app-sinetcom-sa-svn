/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.dao.ClienteEmpresaFacade;
import ec.com.sinetcom.dao.ColaFacade;
import ec.com.sinetcom.dao.ItemProductoFacade;
import ec.com.sinetcom.dao.PrioridadTicketFacade;
import ec.com.sinetcom.dao.ServicioTicketFacade;
import ec.com.sinetcom.orm.Articulo;
import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.Cola;
import ec.com.sinetcom.orm.ItemProducto;
import ec.com.sinetcom.orm.PrioridadTicket;
import ec.com.sinetcom.orm.ServicioTicket;
import ec.com.sinetcom.orm.Ticket;
import ec.com.sinetcom.servicios.TicketServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author diegoflores
 */

@ManagedBean
@ViewScoped
public class CrearTicketBean implements Serializable{
    
    @EJB
    private TicketServicio ticketServicio;
    
    @ManagedProperty(value="#{login}")
    private AdministracionUsuarioBean administracionUsuarioBean;

    public void setAdministracionUsuarioBean(AdministracionUsuarioBean administracionUsuarioBean) {
        this.administracionUsuarioBean = administracionUsuarioBean;
    }
    
    //Las variables tipo lista
    private List<Cola> colas;
    
    private List<PrioridadTicket> prioridadTickets;
    
    private List<ServicioTicket> servicioTickets;
    
    private List<ClienteEmpresa> clienteEmpresas;
    
    private List<ItemProducto> itemProductos;
    
    //El nuevo ticket de soporte
    private Ticket ticket;
    
    private Articulo articulo;
    
    private UploadedFile archivoAdjunto;
    
    private boolean ticketCreado;
    
    @PostConstruct
    public void doInit(){
        this.colas = this.ticketServicio.obtenerTodasLasColas();
        this.prioridadTickets = this.ticketServicio.obtenerTodasLasPrioridades();
        this.servicioTickets = this.ticketServicio.obtenerTodosLosServiciosDeTicket();
        this.clienteEmpresas = this.ticketServicio.obtenerTodosLosClientes();
        this.ticket = new Ticket();
        this.articulo = new Articulo();
        this.ticketCreado = false;
        //this.archivoAdjunto = new DefaultUploadedFile();
    }
    
    /**
     * Actualiza el combo de productos de un cliente
     */
    public void actualizarProductosCliente(){
        this.itemProductos = this.ticketServicio.obtenerTodosLosProductosDeUnCliente(this.ticket.getClienteEmpresaruc());
    }
    
    /**
     * Sube el archivo adjunto
     * @param event 
     */
    public void adjuntarArchivo(FileUploadEvent event){
        this.archivoAdjunto = event.getFile();
    }
    
    /**
     * Evento de creación del ticket de soporte
     * @param event 
     */
    public void crearTicket(ActionEvent event){
        //Agregar el archivo adjunto
        if(archivoAdjunto != null){
            this.articulo.setContenidoAdjunto(this.archivoAdjunto.getContents());
            String[] tipo = this.archivoAdjunto.getContentType().split("/");
            this.articulo.setExtensionArchivo(tipo[1]);
        }
        this.ticketServicio.crearNuevoTicket(this.ticket, this.administracionUsuarioBean.getUsuarioActual());
        this.articulo.setAsunto(this.ticket.getTitulo());
        this.ticketServicio.ingresarNuevoArticuloAlTicket(this.ticket, this.articulo, this.administracionUsuarioBean.getUsuarioActual());
        Mensajes.mostrarMensajeInformativo("Ticket #" + this.ticket.getTicketNumber() + " creado con éxito!");
        this.ticketCreado = true;
    }
    
    public List<Cola> getColas() {
        return colas;
    }

    public void setColas(List<Cola> colas) {
        this.colas = colas;
    }

    public List<PrioridadTicket> getPrioridadTickets() {
        return prioridadTickets;
    }

    public void setPrioridadTickets(List<PrioridadTicket> prioridadTickets) {
        this.prioridadTickets = prioridadTickets;
    }

    public List<ServicioTicket> getServicioTickets() {
        return servicioTickets;
    }

    public void setServicioTickets(List<ServicioTicket> servicioTickets) {
        this.servicioTickets = servicioTickets;
    }

    public List<ClienteEmpresa> getClienteEmpresas() {
        return clienteEmpresas;
    }

    public void setClienteEmpresas(List<ClienteEmpresa> clienteEmpresas) {
        this.clienteEmpresas = clienteEmpresas;
    }

    public List<ItemProducto> getItemProductos() {
        return itemProductos;
    }

    public void setItemProductos(List<ItemProducto> itemProductos) {
        this.itemProductos = itemProductos;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public UploadedFile getArchivoAdjunto() {
        return archivoAdjunto;
    }

    public void setArchivoAdjunto(UploadedFile archivoAdjunto) {
        this.archivoAdjunto = archivoAdjunto;
    }

    public boolean isTicketCreado() {
        return ticketCreado;
    }

    public void setTicketCreado(boolean ticketCreado) {
        this.ticketCreado = ticketCreado;
    }
    
}
