/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
@Table(name = "TipoDeVisita", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDeVisita.findAll", query = "SELECT t FROM TipoDeVisita t"),
    @NamedQuery(name = "TipoDeVisita.findById", query = "SELECT t FROM TipoDeVisita t WHERE t.id = :id"),
    @NamedQuery(name = "TipoDeVisita.findByNombre", query = "SELECT t FROM TipoDeVisita t WHERE t.nombre = :nombre")})
public class TipoDeVisita implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDeVisitaid")
    private List<VisitaTecnica> visitaTecnicaList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "nombre")
    private String nombre;
    

    public TipoDeVisita() {
    }

    public TipoDeVisita(Integer id) {
        this.id = id;
    }

    public TipoDeVisita(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        if (!(object instanceof TipoDeVisita)) {
            return false;
        }
        TipoDeVisita other = (TipoDeVisita) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.TipoDeVisita[ id=" + id + " ]";
    }

    @XmlTransient
    public List<VisitaTecnica> getVisitaTecnicaList() {
        return visitaTecnicaList;
    }

    public void setVisitaTecnicaList(List<VisitaTecnica> visitaTecnicaList) {
        this.visitaTecnicaList = visitaTecnicaList;
    }
    
}
