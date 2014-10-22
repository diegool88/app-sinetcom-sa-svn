/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "VisitasTecnicas", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VisitasTecnicas.findAll", query = "SELECT v FROM VisitasTecnicas v"),
    @NamedQuery(name = "VisitasTecnicas.findById", query = "SELECT v FROM VisitasTecnicas v WHERE v.id = :id"),
    @NamedQuery(name = "VisitasTecnicas.findByFecha", query = "SELECT v FROM VisitasTecnicas v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "VisitasTecnicas.findByHora", query = "SELECT v FROM VisitasTecnicas v WHERE v.hora = :hora"),
    @NamedQuery(name = "VisitasTecnicas.findByDescripcion", query = "SELECT v FROM VisitasTecnicas v WHERE v.descripcion = :descripcion"),
    @NamedQuery(name = "VisitasTecnicas.findByIdNotificador", query = "SELECT v FROM VisitasTecnicas v WHERE v.idNotificador = :idNotificador")})
public class VisitasTecnicas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Lob
    @Column(name = "hojaDeServicio")
    private byte[] hojaDeServicio;
    @Size(max = 254)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 45)
    @Column(name = "idNotificador")
    private String idNotificador;
    @JoinColumn(name = "TipoDeVisita_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoDeVisita tipoDeVisitaid;
    @JoinColumn(name = "Ticket_ticketNumber", referencedColumnName = "ticketNumber")
    @ManyToOne(optional = false)
    private Ticket ticketticketNumber;
    @JoinColumn(name = "Contrato_numero", referencedColumnName = "numero")
    @ManyToOne(optional = false)
    private Contrato contratonumero;

    public VisitasTecnicas() {
    }

    public VisitasTecnicas(Integer id) {
        this.id = id;
    }

    public VisitasTecnicas(Integer id, Date fecha, Date hora) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public byte[] getHojaDeServicio() {
        return hojaDeServicio;
    }

    public void setHojaDeServicio(byte[] hojaDeServicio) {
        this.hojaDeServicio = hojaDeServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdNotificador() {
        return idNotificador;
    }

    public void setIdNotificador(String idNotificador) {
        this.idNotificador = idNotificador;
    }

    public TipoDeVisita getTipoDeVisitaid() {
        return tipoDeVisitaid;
    }

    public void setTipoDeVisitaid(TipoDeVisita tipoDeVisitaid) {
        this.tipoDeVisitaid = tipoDeVisitaid;
    }

    public Ticket getTicketticketNumber() {
        return ticketticketNumber;
    }

    public void setTicketticketNumber(Ticket ticketticketNumber) {
        this.ticketticketNumber = ticketticketNumber;
    }

    public Contrato getContratonumero() {
        return contratonumero;
    }

    public void setContratonumero(Contrato contratonumero) {
        this.contratonumero = contratonumero;
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
        if (!(object instanceof VisitasTecnicas)) {
            return false;
        }
        VisitasTecnicas other = (VisitasTecnicas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.VisitasTecnicas[ id=" + id + " ]";
    }
    
}
