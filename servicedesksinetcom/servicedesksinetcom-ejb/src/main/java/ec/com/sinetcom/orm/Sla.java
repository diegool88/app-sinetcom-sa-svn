/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.orm;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "Sla", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sla.findAll", query = "SELECT s FROM Sla s")})
public class Sla implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tiempoRespuestaPrioridadAlta")
    private int tiempoRespuestaPrioridadAlta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tiempoRespuestaPrioridadMedia")
    private int tiempoRespuestaPrioridadMedia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tiempoRespuestaPrioridadBaja")
    private int tiempoRespuestaPrioridadBaja;
    @Column(name = "tiempoDeEscalacion")
    private Integer tiempoDeEscalacion;
    @Column(name = "tiempoDeActualizacionDeEscalacion")
    private Integer tiempoDeActualizacionDeEscalacion;
    @Column(name = "tiempoDeRespuestaDeEscalacion")
    private Integer tiempoDeRespuestaDeEscalacion;
    @Column(name = "tiempoDeSolucion")
    private Integer tiempoDeSolucion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "slaid")
    private List<Contrato> contratoList;
    @JoinColumn(name = "TipoDisponibilidad_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoDisponibilidad tipoDisponibilidadid;

    public Sla() {
    }

    public Sla(Integer id) {
        this.id = id;
    }

    public Sla(Integer id, int tiempoRespuestaPrioridadAlta, int tiempoRespuestaPrioridadMedia, int tiempoRespuestaPrioridadBaja) {
        this.id = id;
        this.tiempoRespuestaPrioridadAlta = tiempoRespuestaPrioridadAlta;
        this.tiempoRespuestaPrioridadMedia = tiempoRespuestaPrioridadMedia;
        this.tiempoRespuestaPrioridadBaja = tiempoRespuestaPrioridadBaja;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTiempoRespuestaPrioridadAlta() {
        return tiempoRespuestaPrioridadAlta;
    }

    public void setTiempoRespuestaPrioridadAlta(int tiempoRespuestaPrioridadAlta) {
        this.tiempoRespuestaPrioridadAlta = tiempoRespuestaPrioridadAlta;
    }

    public int getTiempoRespuestaPrioridadMedia() {
        return tiempoRespuestaPrioridadMedia;
    }

    public void setTiempoRespuestaPrioridadMedia(int tiempoRespuestaPrioridadMedia) {
        this.tiempoRespuestaPrioridadMedia = tiempoRespuestaPrioridadMedia;
    }

    public int getTiempoRespuestaPrioridadBaja() {
        return tiempoRespuestaPrioridadBaja;
    }

    public void setTiempoRespuestaPrioridadBaja(int tiempoRespuestaPrioridadBaja) {
        this.tiempoRespuestaPrioridadBaja = tiempoRespuestaPrioridadBaja;
    }

    public Integer getTiempoDeEscalacion() {
        return tiempoDeEscalacion;
    }

    public void setTiempoDeEscalacion(Integer tiempoDeEscalacion) {
        this.tiempoDeEscalacion = tiempoDeEscalacion;
    }

    public Integer getTiempoDeActualizacionDeEscalacion() {
        return tiempoDeActualizacionDeEscalacion;
    }

    public void setTiempoDeActualizacionDeEscalacion(Integer tiempoDeActualizacionDeEscalacion) {
        this.tiempoDeActualizacionDeEscalacion = tiempoDeActualizacionDeEscalacion;
    }

    public Integer getTiempoDeRespuestaDeEscalacion() {
        return tiempoDeRespuestaDeEscalacion;
    }

    public void setTiempoDeRespuestaDeEscalacion(Integer tiempoDeRespuestaDeEscalacion) {
        this.tiempoDeRespuestaDeEscalacion = tiempoDeRespuestaDeEscalacion;
    }

    public Integer getTiempoDeSolucion() {
        return tiempoDeSolucion;
    }

    public void setTiempoDeSolucion(Integer tiempoDeSolucion) {
        this.tiempoDeSolucion = tiempoDeSolucion;
    }

    @XmlTransient
    public List<Contrato> getContratoList() {
        return contratoList;
    }

    public void setContratoList(List<Contrato> contratoList) {
        this.contratoList = contratoList;
    }

    public TipoDisponibilidad getTipoDisponibilidadid() {
        return tipoDisponibilidadid;
    }

    public void setTipoDisponibilidadid(TipoDisponibilidad tipoDisponibilidadid) {
        this.tipoDisponibilidadid = tipoDisponibilidadid;
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
        if (!(object instanceof Sla)) {
            return false;
        }
        Sla other = (Sla) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.Sla[ id=" + id + " ]";
    }
    
}
