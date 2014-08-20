/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.orm;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "ComponenteElectronicoAtomico", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComponenteElectronicoAtomico.findAll", query = "SELECT c FROM ComponenteElectronicoAtomico c")})
public class ComponenteElectronicoAtomico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @JoinTable(name = "AtributoComponenteElectronicoAtomico", joinColumns = {
        @JoinColumn(name = "ComponenteElectronicoAtomico_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "ParametrosDeProducto_id", referencedColumnName = "id")})
    @ManyToMany
    private List<ParametrosDeProducto> parametrosDeProductoList;
    @OneToMany(mappedBy = "componenteElectronicoAtomicoid")
    private List<ItemProducto> itemProductoList;

    public ComponenteElectronicoAtomico() {
    }

    public ComponenteElectronicoAtomico(Integer id) {
        this.id = id;
    }

    public ComponenteElectronicoAtomico(Integer id, String nombre) {
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

    @XmlTransient
    public List<ParametrosDeProducto> getParametrosDeProductoList() {
        return parametrosDeProductoList;
    }

    public void setParametrosDeProductoList(List<ParametrosDeProducto> parametrosDeProductoList) {
        this.parametrosDeProductoList = parametrosDeProductoList;
    }

    @XmlTransient
    public List<ItemProducto> getItemProductoList() {
        return itemProductoList;
    }

    public void setItemProductoList(List<ItemProducto> itemProductoList) {
        this.itemProductoList = itemProductoList;
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
        if (!(object instanceof ComponenteElectronicoAtomico)) {
            return false;
        }
        ComponenteElectronicoAtomico other = (ComponenteElectronicoAtomico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.ComponenteElectronicoAtomico[ id=" + id + " ]";
    }

    
}
