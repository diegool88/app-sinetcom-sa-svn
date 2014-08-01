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

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "HistorialDeTicket", catalog = "dbsinetcom", schema = "")
@NamedQueries({
    @NamedQuery(name = "HistorialDeTicket.findAll", query = "SELECT h FROM HistorialDeTicket h")})
public class HistorialDeTicket implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaDelEvento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDelEvento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orden")
    private int orden;
    @JoinColumn(name = "Usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioid;
    @JoinColumn(name = "Ticket_ticketNumber", referencedColumnName = "ticketNumber")
    @ManyToOne(optional = false)
    private Ticket ticketticketNumber;
    @JoinColumn(name = "EventoTicket_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private EventoTicket eventoTicketcodigo;

    public HistorialDeTicket() {
    }

    public HistorialDeTicket(Integer id) {
        this.id = id;
    }

    public HistorialDeTicket(Integer id, Date fechaDelEvento, int orden) {
        this.id = id;
        this.fechaDelEvento = fechaDelEvento;
        this.orden = orden;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaDelEvento() {
        return fechaDelEvento;
    }

    public void setFechaDelEvento(Date fechaDelEvento) {
        this.fechaDelEvento = fechaDelEvento;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public Usuario getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Usuario usuarioid) {
        this.usuarioid = usuarioid;
    }

    public Ticket getTicketticketNumber() {
        return ticketticketNumber;
    }

    public void setTicketticketNumber(Ticket ticketticketNumber) {
        this.ticketticketNumber = ticketticketNumber;
    }

    public EventoTicket getEventoTicketcodigo() {
        return eventoTicketcodigo;
    }

    public void setEventoTicketcodigo(EventoTicket eventoTicketcodigo) {
        this.eventoTicketcodigo = eventoTicketcodigo;
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
        if (!(object instanceof HistorialDeTicket)) {
            return false;
        }
        HistorialDeTicket other = (HistorialDeTicket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.HistorialDeTicket[ id=" + id + " ]";
    }
    
}
