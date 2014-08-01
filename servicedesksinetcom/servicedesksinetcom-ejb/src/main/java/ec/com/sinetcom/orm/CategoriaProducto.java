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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "CategoriaProducto", catalog = "dbsinetcom", schema = "")
@NamedQueries({
    @NamedQuery(name = "CategoriaProducto.findAll", query = "SELECT c FROM CategoriaProducto c")})
public class CategoriaProducto implements Serializable {
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
    @Size(max = 254)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinTable(name = "CategoriaFabricante", joinColumns = {
        @JoinColumn(name = "Categoria_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "Fabricante_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Fabricante> fabricanteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriaid")
    private List<LineaDeProducto> lineaDeProductoList;

    public CategoriaProducto() {
    }

    public CategoriaProducto(Integer id) {
        this.id = id;
    }

    public CategoriaProducto(Integer id, String nombre) {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Fabricante> getFabricanteList() {
        return fabricanteList;
    }

    public void setFabricanteList(List<Fabricante> fabricanteList) {
        this.fabricanteList = fabricanteList;
    }

    public List<LineaDeProducto> getLineaDeProductoList() {
        return lineaDeProductoList;
    }

    public void setLineaDeProductoList(List<LineaDeProducto> lineaDeProductoList) {
        this.lineaDeProductoList = lineaDeProductoList;
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
        if (!(object instanceof CategoriaProducto)) {
            return false;
        }
        CategoriaProducto other = (CategoriaProducto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.CategoriaProducto[ id=" + id + " ]";
    }
    
}
