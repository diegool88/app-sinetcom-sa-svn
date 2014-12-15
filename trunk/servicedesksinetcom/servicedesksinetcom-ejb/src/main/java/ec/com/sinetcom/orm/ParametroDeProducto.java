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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "ParametroDeProducto", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametroDeProducto.findAll", query = "SELECT p FROM ParametroDeProducto p")})
public class ParametroDeProducto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
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
    @ManyToMany(mappedBy = "parametroDeProductoList")
    private List<ComponenteElectronicoAtomico> componenteElectronicoAtomicoList;
    @JoinColumn(name = "UnidadMedida_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UnidadMedida unidadMedidaid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parametroDeProducto")
    private List<AtributoItemProducto> atributoItemProductoList;

    public ParametroDeProducto() {
    }

    public ParametroDeProducto(Integer id) {
        this.id = id;
    }

    public ParametroDeProducto(Integer id, String nombre, String tipoDeDato) {
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

    @XmlTransient
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

    @XmlTransient
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
        if (!(object instanceof ParametroDeProducto)) {
            return false;
        }
        ParametroDeProducto other = (ParametroDeProducto) object;
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
