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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "LineaDeProducto", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LineaDeProducto.findAll", query = "SELECT l FROM LineaDeProducto l")})
public class LineaDeProducto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "numeroDeReferencia")
    private String numeroDeReferencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 254)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lineaDeProductoid")
    private List<ModeloProducto> modeloProductoList;
    @JoinColumn(name = "Fabricante_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Fabricante fabricanteid;
    @JoinColumn(name = "Categoria_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CategoriaProducto categoriaid;

    public LineaDeProducto() {
    }

    public LineaDeProducto(Integer id) {
        this.id = id;
    }

    public LineaDeProducto(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroDeReferencia() {
        return numeroDeReferencia;
    }

    public void setNumeroDeReferencia(String numeroDeReferencia) {
        this.numeroDeReferencia = numeroDeReferencia;
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

    @XmlTransient
    public List<ModeloProducto> getModeloProductoList() {
        return modeloProductoList;
    }

    public void setModeloProductoList(List<ModeloProducto> modeloProductoList) {
        this.modeloProductoList = modeloProductoList;
    }

    public Fabricante getFabricanteid() {
        return fabricanteid;
    }

    public void setFabricanteid(Fabricante fabricanteid) {
        this.fabricanteid = fabricanteid;
    }

    public CategoriaProducto getCategoriaid() {
        return categoriaid;
    }

    public void setCategoriaid(CategoriaProducto categoriaid) {
        this.categoriaid = categoriaid;
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
        if (!(object instanceof LineaDeProducto)) {
            return false;
        }
        LineaDeProducto other = (LineaDeProducto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.LineaDeProducto[ id=" + id + " ]";
    }
    
}
