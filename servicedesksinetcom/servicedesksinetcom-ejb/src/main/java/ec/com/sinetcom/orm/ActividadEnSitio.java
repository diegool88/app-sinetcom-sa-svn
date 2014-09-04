/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.orm;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "ActividadEnSitio", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadEnSitio.findAll", query = "SELECT a FROM ActividadEnSitio a"),
    @NamedQuery(name = "ActividadEnSitio.findById", query = "SELECT a FROM ActividadEnSitio a WHERE a.id = :id"),
    @NamedQuery(name = "ActividadEnSitio.findByFechaInicio", query = "SELECT a FROM ActividadEnSitio a WHERE a.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "ActividadEnSitio.findByFechaFin", query = "SELECT a FROM ActividadEnSitio a WHERE a.fechaFin = :fechaFin"),
    @NamedQuery(name = "ActividadEnSitio.findByHorasDeViaje", query = "SELECT a FROM ActividadEnSitio a WHERE a.horasDeViaje = :horasDeViaje"),
    @NamedQuery(name = "ActividadEnSitio.findByMinutosDeViaje", query = "SELECT a FROM ActividadEnSitio a WHERE a.minutosDeViaje = :minutosDeViaje")})
public class ActividadEnSitio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaFin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "horasDeViaje")
    private Integer horasDeViaje;
    @Column(name = "minutosDeViaje")
    private Integer minutosDeViaje;
    @JoinColumn(name = "Ticket_ticketNumber", referencedColumnName = "ticketNumber")
    @ManyToOne(optional = false)
    private Ticket ticketticketNumber;

    public ActividadEnSitio() {
    }

    public ActividadEnSitio(Integer id) {
        this.id = id;
    }

    public ActividadEnSitio(Integer id, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getHorasDeViaje() {
        return horasDeViaje;
    }

    public void setHorasDeViaje(Integer horasDeViaje) {
        this.horasDeViaje = horasDeViaje;
    }

    public Integer getMinutosDeViaje() {
        return minutosDeViaje;
    }

    public void setMinutosDeViaje(Integer minutosDeViaje) {
        this.minutosDeViaje = minutosDeViaje;
    }

    public Ticket getTicketticketNumber() {
        return ticketticketNumber;
    }

    public void setTicketticketNumber(Ticket ticketticketNumber) {
        this.ticketticketNumber = ticketticketNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadEnSitio)) {
            return false;
        }
        ActividadEnSitio other = (ActividadEnSitio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.ActividadEnSitio[ id=" + id + " ]";
    }
    
}
