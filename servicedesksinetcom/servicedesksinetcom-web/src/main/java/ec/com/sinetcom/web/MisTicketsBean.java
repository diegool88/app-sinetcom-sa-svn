/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.ActividadEnSitio;
import ec.com.sinetcom.orm.Articulo;
import ec.com.sinetcom.orm.ColaTicket;
import ec.com.sinetcom.orm.EstadoTicket;
import ec.com.sinetcom.orm.PrioridadTicket;
import ec.com.sinetcom.orm.Ticket;
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.servicios.TicketServicio;
import ec.com.sinetcom.webutil.BotonesTickets;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "misTickets")
@ViewScoped
public class MisTicketsBean extends BotonesTickets implements Serializable {

    @EJB
    private TicketServicio ticketServicio;

    @ManagedProperty(value = "#{login}")
    private AdministracionUsuarioBean administracionUsuarioBean;

    public void setAdministracionUsuarioBean(AdministracionUsuarioBean administracionUsuarioBean) {
        this.administracionUsuarioBean = administracionUsuarioBean;
    }

    //Se escogen todos las colas de un ticket
    private List<ColaTicket> colasTickets;
    //Se escogen todos los estados de un ticket
    private List<EstadoTicket> estadosTickets;
    //Se escogen todos las prioridades de un ticket
    private List<PrioridadTicket> prioridadesTickets;
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
    //Cola seleccionada
    private String tabSeleccionado;
    //Actividades realizadas
    private List<ActividadEnSitio> actividadesEnSitio;
    //Actividad en sitio
    private ActividadEnSitio nuevaActividadEnSitio;
    //Fecha Actual
    private Date fechaActual;
    //Archivo adjunto de nuevo articulo
    private UploadedFile archivoAdjunto;
    //Indicador de resolcion de ticket
    private Boolean resueltoConExito;
    //Archivo para descargar
    private StreamedContent archivoPorDescargar;
    //Link de descarga
    private UICommand link;
    //Tipo de Selección
    private String ticketPor;
    private TabView tabView;

    public void init() {

        if (this.ticketPor.isEmpty()) {
            Mensajes.mostrarMensajeDeError("No se logra ver el parámetro");
        } else {
            Mensajes.mostrarMensajeInformativo(this.ticketPor);
        }

        if (this.ticketPor.equals("cola")) {
            this.colasTickets = this.ticketServicio.obtenerTodasLasColas();
            this.tickets = this.ticketServicio.obtenerTodosLosTicketsPorUnaCola(administracionUsuarioBean.getUsuarioActual(), 1);
            //this.seleccionarTabsColas();
        } else if (this.ticketPor.equals("estado")) {
            this.estadosTickets = this.ticketServicio.obtenerTodosLosEstados();
            this.tickets = this.ticketServicio.obtenerTodosLosTicketsPorUnEstado(administracionUsuarioBean.getUsuarioActual(), 1);
            this.tabView = new TabView();
            for (EstadoTicket estado : this.estadosTickets) {
                Tab tab = new Tab();
                tab.setTitle(estado.getNombre());
                tab.setId("tab-" + estado.getCodigo());
                this.tabView.getChildren().add(tab);
            }
//this.seleccionarTabsEstados();
        } else if (this.ticketPor.equals("prioridad")) {
            this.prioridadesTickets = this.ticketServicio.obtenerTodasLasPrioridades();
            this.tickets = this.ticketServicio.obtenerTodosLosTicketsPorUnaPrioridad(administracionUsuarioBean.getUsuarioActual(), 1);
            //this.seleccionarTabsPrioridades();
        }
        this.ingenieros = this.ticketServicio.obtenerTodosLosIngenieros();
        this.articuloNuevo = new Articulo();
        this.articuloNuevo.setDe(this.administracionUsuarioBean.getUsuarioActual());
        this.actividadesEnSitio = new ArrayList<ActividadEnSitio>();
        this.nuevaActividadEnSitio = new ActividadEnSitio();
        this.fechaActual = new Date();
        if (this.ticketSeleccionado == null) {
            this.sinSeleccion();
        }
        this.resueltoConExito = true;
    }

    @PostConstruct
    public void doInit() {
        //this.tickets = new ArrayList<Ticket>();
        //this.estadoTickets = this.ticketServicio.obtenerTodosLosEstados();
        //this.colasTickets = this.ticketServicio.obtenerTodasLasColas();
        //this.tickets = this.ticketServicio.obtenerTodosLosTicketsPorUnaCola(administracionUsuarioBean.getUsuarioActual(), 1);
        if (!FacesContext.getCurrentInstance().isPostback()) {
            this.ticketPor = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipo");
            //this.ticketPor = 
            if (this.ticketPor.equals("cola")) {
                this.colasTickets = this.ticketServicio.obtenerTodasLasColas();
                this.tickets = this.ticketServicio.obtenerTodosLosTicketsPorUnaCola(administracionUsuarioBean.getUsuarioActual(), 1);
                //this.seleccionarTabsColas();
            } else if (this.ticketPor.equals("estado")) {
                this.estadosTickets = this.ticketServicio.obtenerTodosLosEstados();
                this.tickets = this.ticketServicio.obtenerTodosLosTicketsPorUnEstado(administracionUsuarioBean.getUsuarioActual(), 1);
//                this.tabView = new TabView();
//                for (EstadoTicket estado : this.estadosTickets) {
//                    Tab tab = new Tab();
//                    tab.setTitle(estado.getNombre());
//                    tab.setId("tab-" + estado.getCodigo());
//                    this.tabView.getChildren().add(tab);
//                }
                //this.seleccionarTabsEstados();
            } else if (this.ticketPor.equals("prioridad")) {
                this.prioridadesTickets = this.ticketServicio.obtenerTodasLasPrioridades();
                this.tickets = this.ticketServicio.obtenerTodosLosTicketsPorUnaPrioridad(administracionUsuarioBean.getUsuarioActual(), 1);
                //this.seleccionarTabsPrioridades();
            }
            this.ingenieros = this.ticketServicio.obtenerTodosLosIngenieros();
            this.articuloNuevo = new Articulo();
            this.articuloNuevo.setDe(this.administracionUsuarioBean.getUsuarioActual());
            this.actividadesEnSitio = new ArrayList<ActividadEnSitio>();
            this.nuevaActividadEnSitio = new ActividadEnSitio();
            this.fechaActual = new Date();
            if (this.ticketSeleccionado == null) {
                this.sinSeleccion();
            }
            this.resueltoConExito = true;
        }

    }

//    public void cargarTabs(){
//        if(this.ticketPor.equals("cola")){
//            this.colasTickets = this.ticketServicio.obtenerTodasLasColas();
//            this.tickets = this.ticketServicio.obtenerTodosLosTicketsPorUnaCola(administracionUsuarioBean.getUsuarioActual(), 1);
//            this.seleccionarTabsColas();
//        }else if(this.ticketPor.equals("estado")){
//            this.estadosTickets = this.ticketServicio.obtenerTodosLosEstados();
//            this.tickets = this.ticketServicio.obtenerTodosLosTicketsPorUnEstado(administracionUsuarioBean.getUsuarioActual(), 1);
//            this.seleccionarTabsEstados();
//        }else if(this.ticketPor.equals("prioridad")){
//            this.prioridadesTickets = this.ticketServicio.obtenerTodasLasPrioridades();
//            this.tickets = this.ticketServicio.obtenerTodosLosTicketsPorUnaPrioridad(administracionUsuarioBean.getUsuarioActual(), 1);
//            this.seleccionarTabsPrioridades();
//        }
//        Mensajes.mostrarMensajeInformativo("Entre a cargar Tabs!, Cargando tipo: " + this.ticketPor);
//    }
    public void cambioDeTab(TabChangeEvent event) {
        Tab activo = event.getTab();
        int seleccion = Integer.parseInt(activo.getAttributes().get("tab-id").toString());
        if (this.ticketPor.equals("cola")) {
            this.tickets = this.ticketServicio.obtenerTodosLosTicketsPorUnaCola(administracionUsuarioBean.getUsuarioActual(), seleccion);
        } else if (this.ticketPor.equals("estado")) {
            this.tickets = this.ticketServicio.obtenerTodosLosTicketsPorUnEstado(administracionUsuarioBean.getUsuarioActual(), seleccion);
        } else if (this.ticketPor.equals("prioridad")) {
            this.tickets = this.ticketServicio.obtenerTodosLosTicketsPorUnaPrioridad(administracionUsuarioBean.getUsuarioActual(), seleccion);
        }
        //this.tickets = this.ticketServicio.obtenerTodosLosTicketsPorUnaCola(administracionUsuarioBean.getUsuarioActual(), seleccion);
        this.articulos = null;
        this.ticketSeleccionado = null;
        this.tabSeleccionado = activo.getAttributes().get("title").toString();
        this.sinSeleccion();
        Mensajes.mostrarMensajeInformativo(activo.getAttributes().get("title").toString());
    }

    public void asignarIngeniero(ActionEvent event) {
        if (this.ticketSeleccionado != null) {
            this.ticketServicio.asignarTicketAPropietario(this.ticketSeleccionado, this.administracionUsuarioBean.getUsuarioActual());
            Mensajes.mostrarMensajeInformativo("Ticket #" + this.ticketSeleccionado.getTicketNumber() + " asignado a Ing. " + this.ticketSeleccionado.getUsuarioidpropietario().getNombreCompleto());
        }
    }

    public void eliminarActividad(ActionEvent event) {
        this.ticketServicio.eliminarActividadDeTicket((Integer) event.getComponent().getAttributes().get("idActividad"));
        this.actividadesEnSitio = this.ticketServicio.obtenerTodasLasActividadesEnSitioDeUnTicket(this.ticketSeleccionado);
        Mensajes.mostrarMensajeInformativo("Actividad Eliminada!");
    }

    public void descargarArchivoAdjunto(ActionEvent event) {
        //ByteArrayInputStream stream = new ByteArrayInputStream(((Articulo)event.getComponent().getAttributes().get("idActividad")).getContenidoAdjunto());
        Articulo articuloSel = (Articulo) event.getComponent().getAttributes().get("articuloId");
        this.archivoPorDescargar = new DefaultStreamedContent(new ByteArrayInputStream(articuloSel.getContenidoAdjunto()),
                articuloSel.getExtensionArchivo().equals("jpg")
                || articuloSel.getExtensionArchivo().equals("jpeg")
                || articuloSel.getExtensionArchivo().equals("gif")
                || articuloSel.getExtensionArchivo().equals("png") ? "image/" + articuloSel.getExtensionArchivo() : "application/pdf", "archivo." + articuloSel.getExtensionArchivo());
        //descargarArchivoPorByteArray("archivo", articuloSel.getExtensionArchivo(), articuloSel.getContenidoAdjunto());
    }

    public void descargarArchivoPorByteArray(String nombreArchivo, String tipoArchivo, byte[] datos) {
        FacesContext faces = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();
        response.setContentLength(datos.length);
        response.addHeader("Content-Type", tipoArchivo.equals("jpg") || tipoArchivo.equals("png") || tipoArchivo.equals("jpeg") || tipoArchivo.equals("gif") ? "image/" + tipoArchivo : "application/" + tipoArchivo);
        response.addHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");
        ServletOutputStream out = null;
        try {
            long length = datos.length;
            out = response.getOutputStream();
            response.setContentLength((int) length);
            out.write(datos, 0, (int) length);
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception ex) {
                    System.err.println(ex);
                }
            }
        }
        // The most important line - finish response as we have already
        // close output stream response writer
        faces.responseComplete();
    }

    public void adjuntarArchivo(FileUploadEvent event) {
        this.archivoAdjunto = event.getFile();
    }

    public void crearNuevoArticulo(ActionEvent event) {
        if (this.archivoAdjunto != null) {
            this.articuloNuevo.setContenidoAdjunto(this.archivoAdjunto.getContents());
            String[] tipo = this.archivoAdjunto.getContentType().split("/");
            this.articuloNuevo.setExtensionArchivo(tipo[1]);
        }
        this.ticketServicio.ingresarNuevoArticuloAlTicket(this.ticketSeleccionado, this.articuloNuevo, this.administracionUsuarioBean.getUsuarioActual(), false);
        this.articulos = this.ticketServicio.obtenerTodosLosArticulosDeTicket(this.ticketSeleccionado);
    }

    public void agregarActividadEnSitio(ActionEvent event) {
        if (this.ticketSeleccionado != null) {
            this.nuevaActividadEnSitio.setTicketticketNumber(this.ticketSeleccionado);
            this.ticketServicio.agregarActividadEnSitio(this.nuevaActividadEnSitio);
            this.actividadesEnSitio = this.ticketServicio.obtenerTodasLasActividadesEnSitioDeUnTicket(this.ticketSeleccionado);
        }
    }

    public void enserarActividadEnSitio(ActionEvent event) {
        this.nuevaActividadEnSitio = new ActividadEnSitio();
    }

    public void registroSeleccionado(SelectEvent event) {
        this.sinSeleccion();
        this.articulos = this.ticketServicio.obtenerTodosLosArticulosDeTicket((Ticket) event.getObject());
        this.actividadesEnSitio = this.ticketServicio.obtenerTodasLasActividadesEnSitioDeUnTicket((Ticket) event.getObject());
        int codigoEstado = ((Ticket) event.getObject()).getEstadoTicketcodigo().getCodigo();
        if (codigoEstado != 3 && codigoEstado != 4) {
            this.seleccionadoUno();
        }
    }

    public void registroDeseleccionado(UnselectEvent event) {
        this.articulos = null;
        this.actividadesEnSitio = null;
        this.sinSeleccion();
    }

    public void cerrarCaso(ActionEvent event) {
        boolean tieneActividades = this.ticketServicio.obtenerTodasLasActividadesEnSitioDeUnTicket(this.ticketSeleccionado) != null;
        if (!tieneActividades) {
            Mensajes.mostrarMensajeDeError("Usted no puede cerrar este ticket, debe tener registrada al menos una actividad!");
        } else {
            this.ticketSeleccionado.setFechaDeModificacion(new Date());
            this.ticketSeleccionado.setFechaDeCierre(new Date());
            this.ticketServicio.cerrarTicket(this.ticketSeleccionado, this.administracionUsuarioBean.getUsuarioActual(), this.resueltoConExito);
            this.tickets = this.ticketServicio.obtenerTodosLosTicketsPorUnaCola(this.administracionUsuarioBean.getUsuarioActual(), this.ticketSeleccionado.getColaid().getId());
            Mensajes.mostrarMensajeInformativo("El Ticket# " + this.ticketSeleccionado.getTicketNumber() + " ha sido cerrado con éxito!");
        }
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

    public List<ColaTicket> getColasTickets() {
        return colasTickets;
    }

    public void setColasTickets(List<ColaTicket> colasTickets) {
        this.colasTickets = colasTickets;
    }

    public String getTabSeleccionado() {
        return tabSeleccionado;
    }

    public void setTabSeleccionado(String tabSeleccionado) {
        this.tabSeleccionado = tabSeleccionado;
    }

    public List<ActividadEnSitio> getActividadesEnSitio() {
        return actividadesEnSitio;
    }

    public void setActividadesEnSitio(List<ActividadEnSitio> actividadesEnSitio) {
        this.actividadesEnSitio = actividadesEnSitio;
    }

    public ActividadEnSitio getNuevaActividadEnSitio() {
        return nuevaActividadEnSitio;
    }

    public void setNuevaActividadEnSitio(ActividadEnSitio nuevaActividadEnSitio) {
        this.nuevaActividadEnSitio = nuevaActividadEnSitio;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public UploadedFile getArchivoAdjunto() {
        return archivoAdjunto;
    }

    public void setArchivoAdjunto(UploadedFile archivoAdjunto) {
        this.archivoAdjunto = archivoAdjunto;
    }

    public Boolean getResueltoConExito() {
        return resueltoConExito;
    }

    public void setResueltoConExito(Boolean resueltoConExito) {
        this.resueltoConExito = resueltoConExito;
    }

    public StreamedContent getArchivoPorDescargar() {
        return archivoPorDescargar;
    }

    public void setArchivoPorDescargar(StreamedContent archivoPorDescargar) {
        this.archivoPorDescargar = archivoPorDescargar;
    }

    public UICommand getLink() {
        return link;
    }

    public void setLink(UICommand link) {
        this.link = link;
    }

    public String getTicketPor() {
        return ticketPor;
    }

    public void setTicketPor(String ticketPor) {
        this.ticketPor = ticketPor;
    }

    public List<EstadoTicket> getEstadosTickets() {
        return estadosTickets;
    }

    public void setEstadosTickets(List<EstadoTicket> estadosTickets) {
        this.estadosTickets = estadosTickets;
    }

    public List<PrioridadTicket> getPrioridadesTickets() {
        return prioridadesTickets;
    }

    public void setPrioridadesTickets(List<PrioridadTicket> prioridadesTickets) {
        this.prioridadesTickets = prioridadesTickets;
    }

    public TabView getTabView() {
        return tabView;
    }

    public void setTabView(TabView tabView) {
        this.tabView = tabView;
    }

}
