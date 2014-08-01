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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "ParametrosDeProducto", catalog = "dbsinetcom", schema = "")
@NamedQueries({
    @NamedQuery(name = "ParametrosDeProducto.findAll", query = "SELECT p FROM ParametrosDeProducto p")})
public class ParametrosDeProducto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "tipoDeDato")
    private String tipoDeDato;
    @Size(max = 254)
    @Column(name = "descripcion")
    private String descripcion;
    @ManyToMany(mappedBy = "parametrosDeProductoList")
    private List<ComponenteElectronicoAtomico> componenteElectronicoAtomicoList;
    @JoinColumn(name = "UnidadMedida_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UnidadMedida unidadMedidaid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parametrosDeProducto")
    private List<AtributoItemProducto> atributoItemProductoList;

    public ParametrosDeProducto() {
    }

    public ParametrosDeProducto(Integer id) {
        this.id = id;
    }

    public ParametrosDeProducto(Integer id, String nombre, String tipoDeDato) {
        this.id = id;
        this.nombre = nombre;
        this.tipoDeDato = tipoDeDato;
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

    public String getTipoDeDato() {
        return tipoDeDato;
    }

    public void setTipoDeDato(String tipoDeDato) {
        this.tipoDeDato = tipoDeDato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<ComponenteElectronicoAtomico> getComponenteElectronicoAtomicoList() {
        return componenteElectronicoAtomicoList;
    }

    public void setComponenteElectronicoAtomicoList(List<ComponenteElectronicoAtomico> componenteElectronicoAtomicoList) {
        this.componenteElectronicoAtomicoList = componenteElectronicoAtomicoList;
    }

    public UnidadMedida getUnidadMedidaid() {
        return unidadMedidaid;
    }

    public void setUnidadMedidaid(UnidadMedida unidadMedidaid) {
        this.unidadMedidaid = unidadMedidaid;
    }

    public List<AtributoItemProducto> getAtributoItemProductoList() {
        return atributoItemProductoList;
    }

    public void setAtributoItemProductoList(List<AtributoItemProducto> atributoItemProductoList) {
        this.atributoItemProductoList = atributoItemProductoList;
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
        if (!(object instanceof ParametrosDeProducto)) {
            return false;
        }
        ParametrosDeProducto other = (ParametrosDeProducto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.ParametrosDeProducto[ id=" + id + " ]";
    }
    
}
