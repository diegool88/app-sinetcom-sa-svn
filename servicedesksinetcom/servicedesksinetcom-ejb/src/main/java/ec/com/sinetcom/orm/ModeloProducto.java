/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.orm;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "ModeloProducto", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ModeloProducto.findAll", query = "SELECT m FROM ModeloProducto m")})
public class ModeloProducto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "numeroDeParte")
    private String numeroDeParte;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "modelo")
    private String modelo;
    @Column(name = "fechaLanzamiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLanzamiento;
    @Column(name = "endOfLife")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endOfLife;
    @ManyToMany(mappedBy = "modeloProductoList")
    private List<ItemProducto> itemProductoList;
    @JoinColumn(name = "LineaDeProducto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LineaDeProducto lineaDeProductoid;
    @OneToMany(mappedBy = "modeloProductoid")
    private List<ItemProducto> itemProductoList1;

    public ModeloProducto() {
    }

    public ModeloProducto(Integer id) {
        this.id = id;
    }

    public ModeloProducto(Integer id, String numeroDeParte, String modelo) {
        this.id = id;
        this.numeroDeParte = numeroDeParte;
        this.modelo = modelo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroDeParte() {
        return numeroDeParte;
    }

    public void setNumeroDeParte(String numeroDeParte) {
        this.numeroDeParte = numeroDeParte;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Date getEndOfLife() {
        return endOfLife;
    }

    public void setEndOfLife(Date endOfLife) {
        this.endOfLife = endOfLife;
    }

    @XmlTransient
    public List<ItemProducto> getItemProductoList() {
        return itemProductoList;
    }

    public void setItemProductoList(List<ItemProducto> itemProductoList) {
        this.itemProductoList = itemProductoList;
    }

    public LineaDeProducto getLineaDeProductoid() {
        return lineaDeProductoid;
    }

    public void setLineaDeProductoid(LineaDeProducto lineaDeProductoid) {
        this.lineaDeProductoid = lineaDeProductoid;
    }

    @XmlTransient
    public List<ItemProducto> getItemProductoList1() {
        return itemProductoList1;
    }

    public void setItemProductoList1(List<ItemProducto> itemProductoList1) {
        this.itemProductoList1 = itemProductoList1;
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
        if (!(object instanceof ModeloProducto)) {
            return false;
        }
        ModeloProducto other = (ModeloProducto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.ModeloProducto[ id=" + id + " ]";
    }
    
}
