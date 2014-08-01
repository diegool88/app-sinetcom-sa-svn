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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "TipoDisponibilidad", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDisponibilidad.findAll", query = "SELECT t FROM TipoDisponibilidad t")})
public class TipoDisponibilidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "disponibilidad")
    private String disponibilidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valido")
    private boolean valido;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDisponibilidadid")
    private List<Sla> slaList;

    public TipoDisponibilidad() {
    }

    public TipoDisponibilidad(Integer id) {
        this.id = id;
    }

    public TipoDisponibilidad(Integer id, String disponibilidad, boolean valido) {
        this.id = id;
        this.disponibilidad = disponibilidad;
        this.valido = valido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public boolean getValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    @XmlTransient
    public List<Sla> getSlaList() {
        return slaList;
    }

    public void setSlaList(List<Sla> slaList) {
        this.slaList = slaList;
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
        if (!(object instanceof TipoDisponibilidad)) {
            return false;
        }
        TipoDisponibilidad other = (TipoDisponibilidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.TipoDisponibilidad[ id=" + id + " ]";
    }
    
}
