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
@Table(name = "Articulo", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulo.findAll", query = "SELECT a FROM Articulo a")})
public class Articulo implements Serializable {
    @Lob
    @Column(name = "contenidoAdjunto")
    private byte[] contenidoAdjunto;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "responderA")
    private String responderA;
    @Size(max = 500)
    @Column(name = "copia")
    private String copia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "asunto")
    private String asunto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5000)
    @Column(name = "cuerpo")
    private String cuerpo;
    @Size(max = 45)
    @Column(name = "referencia")
    private String referencia;
    //@Size(max = 120)
    //@Column(name = "rutaDeContenidoAdjunto")
    //private String rutaDeContenidoAdjunto;
    @Column(name = "fechaDeCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeCreacion;
    @Column(name = "fechaDeModificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeModificacion;
    @Column(name = "orden")
    private Integer orden;
    @JoinColumn(name = "para", referencedColumnName = "correoElectronico")
    @ManyToOne(optional = false)
    private Usuario para;
    @JoinColumn(name = "de", referencedColumnName = "correoElectronico")
    @ManyToOne(optional = false)
    private Usuario de;
    @JoinColumn(name = "Ticket_ticketNumber", referencedColumnName = "ticketNumber")
    @ManyToOne(optional = false)
    private Ticket ticketticketNumber;

    public Articulo() {
    }

    public Articulo(Integer id) {
        this.id = id;
    }

    public Articulo(Integer id, String asunto, String cuerpo) {
        this.id = id;
        this.asunto = asunto;
        this.cuerpo = cuerpo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResponderA() {
        return responderA;
    }

    public void setResponderA(String responderA) {
        this.responderA = responderA;
    }

    public String getCopia() {
        return copia;
    }

    public void setCopia(String copia) {
        this.copia = copia;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

//    public String getRutaDeContenidoAdjunto() {
//        return rutaDeContenidoAdjunto;
//    }
//
//    public void setRutaDeContenidoAdjunto(String rutaDeContenidoAdjunto) {
//        this.rutaDeContenidoAdjunto = rutaDeContenidoAdjunto;
//    }

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

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Usuario getPara() {
        return para;
    }

    public void setPara(Usuario para) {
        this.para = para;
    }

    public Usuario getDe() {
        return de;
    }

    public void setDe(Usuario de) {
        this.de = de;
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
        if (!(object instanceof Articulo)) {
            return false;
        }
        Articulo other = (Articulo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.Articulo[ id=" + id + " ]";
    }

    public byte[] getContenidoAdjunto() {
        return contenidoAdjunto;
    }

    public void setContenidoAdjunto(byte[] contenidoAdjunto) {
        this.contenidoAdjunto = contenidoAdjunto;
    }
    
}
