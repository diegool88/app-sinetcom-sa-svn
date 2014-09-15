/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.orm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "Ticket", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ticket.findAll", query = "SELECT t FROM Ticket t")})
public class Ticket implements Serializable {
    @Column(name = "fechaDePrimerContacto")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDePrimerContacto;
    @Lob
    @Column(name = "hojaDeServicio")
    private byte[] hojaDeServicio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketticketNumber")
    private List<VisitasMantenimiento> visitasMantenimientoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketticketNumber")
    private List<ActividadEnSitio> actividadEnSitioList;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaDeProximaActualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeProximaActualizacion;
    @JoinColumn(name = "ItemProducto_numeroSerial", referencedColumnName = "numeroSerial")
    @ManyToOne(optional = false)
    private ItemProducto itemProductonumeroSerial;
    @JoinColumn(name = "ClienteEmpresa_ruc", referencedColumnName = "ruc")
    @ManyToOne(optional = false)
    private ClienteEmpresa clienteEmpresaruc;
    @Column(name = "fechaDeCierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeCierre;
    @JoinColumn(name = "Usuario_id_creador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioidcreador;
    @JoinColumn(name = "Usuario_id_responsable", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioidresponsable;
    @JoinColumn(name = "Usuario_id_propietario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioidpropietario;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ticketNumber")
    private Integer ticketNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tiempoDeVida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tiempoDeVida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaDeCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeCreacion;
    @Column(name = "fechaDeModificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeModificacion;
    @Size(max = 45)
    @Column(name = "idNotificadorTiempoDeRespuesta")
    private String idNotificadorTiempoDeRespuesta;
    @Size(max = 45)
    @Column(name = "idNotificadorTiempoDeActualizacion")
    private String idNotificadorTiempoDeActualizacion;
    @Size(max = 45)
    @Column(name = "idNotificadorTiempoDeFinalizacion")
    private String idNotificadorTiempoDeFinalizacion;
    @JoinColumn(name = "ServicioTicket_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private ServicioTicket servicioTicketcodigo;
    @JoinColumn(name = "PrioridadTicket_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private PrioridadTicket prioridadTicketcodigo;
    @JoinColumn(name = "EstadoTicket_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private EstadoTicket estadoTicketcodigo;
    @JoinColumn(name = "Cola_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cola colaid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketticketNumber")
    private List<HistorialDeTicket> historialDeTicketList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketticketNumber")
    private List<Articulo> articuloList;

    public Ticket() {
    }

    public Ticket(Integer ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Ticket(Integer ticketNumber, String titulo, Date tiempoDeVida, Date fechaDeCreacion) {
        this.ticketNumber = ticketNumber;
        this.titulo = titulo;
        this.tiempoDeVida = tiempoDeVida;
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public Integer getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(Integer ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Date getTiempoDeVida() {
        return tiempoDeVida;
    }

    public void setTiempoDeVida(Date tiempoDeVida) {
        this.tiempoDeVida = tiempoDeVida;
    }

    public Date getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(Date fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public Date getFechaDeModificacion() {
        return fechaDeModificacion;
    }

    public void setFechaDeModificacion(Date fechaDeModificacion) {
        this.fechaDeModificacion = fechaDeModificacion;
    }

    public String getIdNotificadorTiempoDeRespuesta() {
        return idNotificadorTiempoDeRespuesta;
    }

    public void setIdNotificadorTiempoDeRespuesta(String idNotificadorTiempoDeRespuesta) {
        this.idNotificadorTiempoDeRespuesta = idNotificadorTiempoDeRespuesta;
    }

    public String getIdNotificadorTiempoDeActualizacion() {
        return idNotificadorTiempoDeActualizacion;
    }

    public void setIdNotificadorTiempoDeActualizacion(String idNotificadorTiempoDeActualizacion) {
        this.idNotificadorTiempoDeActualizacion = idNotificadorTiempoDeActualizacion;
    }

    public String getIdNotificadorTiempoDeFinalizacion() {
        return idNotificadorTiempoDeFinalizacion;
    }

    public void setIdNotificadorTiempoDeFinalizacion(String idNotificadorTiempoDeFinalizacion) {
        this.idNotificadorTiempoDeFinalizacion = idNotificadorTiempoDeFinalizacion;
    }

    public ServicioTicket getServicioTicketcodigo() {
        return servicioTicketcodigo;
    }

    public void setServicioTicketcodigo(ServicioTicket servicioTicketcodigo) {
        this.servicioTicketcodigo = servicioTicketcodigo;
    }

    public PrioridadTicket getPrioridadTicketcodigo() {
        return prioridadTicketcodigo;
    }

    public void setPrioridadTicketcodigo(PrioridadTicket prioridadTicketcodigo) {
        this.prioridadTicketcodigo = prioridadTicketcodigo;
    }

    public EstadoTicket getEstadoTicketcodigo() {
        return estadoTicketcodigo;
    }

    public void setEstadoTicketcodigo(EstadoTicket estadoTicketcodigo) {
        this.estadoTicketcodigo = estadoTicketcodigo;
    }

    public Cola getColaid() {
        return colaid;
    }

    public void setColaid(Cola colaid) {
        this.colaid = colaid;
    }

    @XmlTransient
    public List<HistorialDeTicket> getHistorialDeTicketList() {
        return historialDeTicketList;
    }

    public void setHistorialDeTicketList(List<HistorialDeTicket> historialDeTicketList) {
        this.historialDeTicketList = historialDeTicketList;
    }

    @XmlTransient
    public List<Articulo> getArticuloList() {
        return articuloList;
    }

    public void setArticuloList(List<Articulo> articuloList) {
        this.articuloList = articuloList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ticketNumber != null ? ticketNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.ticketNumber == null && other.ticketNumber != null) || (this.ticketNumber != null && !this.ticketNumber.equals(other.ticketNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.Ticket[ ticketNumber=" + ticketNumber + " ]";
    }

    public Usuario getUsuarioidresponsable() {
        return usuarioidresponsable;
    }

    public void setUsuarioidresponsable(Usuario usuarioidresponsable) {
        this.usuarioidresponsable = usuarioidresponsable;
    }

    public Usuario getUsuarioidpropietario() {
        return usuarioidpropietario;
    }

    public void setUsuarioidpropietario(Usuario usuarioidpropietario) {
        this.usuarioidpropietario = usuarioidpropietario;
    }

    public Date getFechaDeCierre() {
        return fechaDeCierre;
    }

    public void setFechaDeCierre(Date fechaDeCierre) {
        this.fechaDeCierre = fechaDeCierre;
    }

    public Usuario getUsuarioidcreador() {
        return usuarioidcreador;
    }

    public void setUsuarioidcreador(Usuario usuarioidcreador) {
        this.usuarioidcreador = usuarioidcreador;
    }

    public ItemProducto getItemProductonumeroSerial() {
        return itemProductonumeroSerial;
    }

    public void setItemProductonumeroSerial(ItemProducto itemProductonumeroSerial) {
        this.itemProductonumeroSerial = itemProductonumeroSerial;
    }

    public ClienteEmpresa getClienteEmpresaruc() {
        return clienteEmpresaruc;
    }

    public void setClienteEmpresaruc(ClienteEmpresa clienteEmpresaruc) {
        this.clienteEmpresaruc = clienteEmpresaruc;
    }

    public Date getFechaDeProximaActualizacion() {
        return fechaDeProximaActualizacion;
    }

    public void setFechaDeProximaActualizacion(Date fechaDeProximaActualizacion) {
        this.fechaDeProximaActualizacion = fechaDeProximaActualizacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    @XmlTransient
    public List<VisitasMantenimiento> getVisitasMantenimientoList() {
        return visitasMantenimientoList;
    }

    public void setVisitasMantenimientoList(List<VisitasMantenimiento> visitasMantenimientoList) {
        this.visitasMantenimientoList = visitasMantenimientoList;
    }

    @XmlTransient
    public List<ActividadEnSitio> getActividadEnSitioList() {
        return actividadEnSitioList;
    }

    public void setActividadEnSitioList(List<ActividadEnSitio> actividadEnSitioList) {
        this.actividadEnSitioList = actividadEnSitioList;
    }

    public Date getFechaDePrimerContacto() {
        return fechaDePrimerContacto;
    }

    public void setFechaDePrimerContacto(Date fechaDePrimerContacto) {
        this.fechaDePrimerContacto = fechaDePrimerContacto;
    }

    public byte[] getHojaDeServicio() {
        return hojaDeServicio;
    }

    public void setHojaDeServicio(byte[] hojaDeServicio) {
        this.hojaDeServicio = hojaDeServicio;
    }
    
}
