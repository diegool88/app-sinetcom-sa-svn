/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.Articulo;
import ec.com.sinetcom.orm.EstadoTicket;
import ec.com.sinetcom.orm.Ticket;
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.servicios.TicketServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name="misTicketsPorEstado")
@ViewScoped
public class MisTicketsPorEstadoBean implements Serializable{
    
    @EJB
    private TicketServicio ticketServicio;
    
    @ManagedProperty(value = "#{login}")
    private AdministracionUsuarioBean administracionUsuarioBean;

    public void setAdministracionUsuarioBean(AdministracionUsuarioBean administracionUsuarioBean) {
        this.administracionUsuarioBean = administracionUsuarioBean;
    }
    
    //Se escogen todos los estados de un ticket
    private List<EstadoTicket> estadoTickets;
    //Se escogen todos los ingeniero disponibles con las competencias de la cola
    private List<Usuario> ingenieros;
    //Se define el arreglo de ticket
    private List<Ticket> tickets; 
    //Se define el ticket seleccionado
    private Ticket ticketSeleccionado;
    //Articulos del ticket seleccionado
    private List<Articulo> articulos;
    //Articulo Nuevo
    private Articulo articuloNuevo;
    
    @PostConstruct
    public void doInit(){
        //this.tickets = new ArrayList<Ticket>();
        //this.estadoTickets = this.ticketServicio.obtenerTodosLosEstados();
        this.tickets = this.ticketServicio.obtenerTodosLosTicketsPorUnEstado(administracionUsuarioBean.getUsuarioActual(), 1);
        this.ingenieros = this.ticketServicio.obtenerTodosLosIngenieros();
        this.articuloNuevo = new Articulo();
        this.articuloNuevo.setDe(this.administracionUsuarioBean.getUsuarioActual());
    }
    
    public void cambioDeTab(TabChangeEvent event){
        Tab activo = event.getTab();
        int seleccion = Integer.parseInt(activo.getAttributes().get("id").toString().split("-")[1]);
        this.tickets = this.ticketServicio.obtenerTodosLosTicketsPorUnEstado(administracionUsuarioBean.getUsuarioActual(), seleccion);
        this.articulos = null;
        Mensajes.mostrarMensajeInformativo(activo.getAttributes().get("title").toString());
    }
    
    public void asignarIngeniero(ActionEvent event){
        if(this.ticketSeleccionado != null){
            this.ticketServicio.asignarTicketAPropietario(this.ticketSeleccionado, this.administracionUsuarioBean.getUsuarioActual());
        }
    }
    
    public void crearNuevoArticulo(ActionEvent event){
    
    }
    
    public void registroSeleccionado(SelectEvent event){
        this.articulos = this.ticketServicio.obtenerTodosLosArticulosDeTicket((Ticket)event.getObject());
    }
    
    public void registroDeseleccionado(UnselectEvent event){
        this.articulos = null;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Ticket getTicketSeleccionado() {
        return ticketSeleccionado;
    }

    public void setTicketSeleccionado(Ticket ticketSeleccionado) {
        this.ticketSeleccionado = ticketSeleccionado;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public List<EstadoTicket> getEstadoTickets() {
        return estadoTickets;
    }

    public void setEstadoTickets(List<EstadoTicket> estadoTickets) {
        this.estadoTickets = estadoTickets;
    }

    public List<Usuario> getIngenieros() {
        return ingenieros;
    }

    public void setIngenieros(List<Usuario> ingenieros) {
        this.ingenieros = ingenieros;
    }

    public Articulo getArticuloNuevo() {
        return articuloNuevo;
    }

    public void setArticuloNuevo(Articulo articuloNuevo) {
        this.articuloNuevo = articuloNuevo;
    }
    
    
}
