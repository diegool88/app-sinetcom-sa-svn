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
@Table(name = "DetalleDeMovimientoDeProducto", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleDeMovimientoDeProducto.findAll", query = "SELECT h FROM DetalleDeMovimientoDeProducto h")})
public class DetalleDeMovimientoDeProducto implements Serializable {
    @JoinColumn(name = "ItemProducto_numeroSerial_entra", referencedColumnName = "numeroSerial")
    @ManyToOne
    private ItemProducto itemProductonumeroSerialentra;
    @JoinColumn(name = "ItemProducto_numeroSerial_sale", referencedColumnName = "numeroSerial")
    @ManyToOne(optional = false)
    private ItemProducto itemProductonumeroSerialsale;
    @JoinColumn(name = "RegistroDeMovimientoDeInventario_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private RegistroDeMovimientoDeInventario registroDeMovimientoDeInventariocodigo;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaEvento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEvento;
    @JoinColumn(name = "Usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioid;

    public DetalleDeMovimientoDeProducto() {
    }

    public DetalleDeMovimientoDeProducto(Integer id) {
        this.id = id;
    }

    public DetalleDeMovimientoDeProducto(Integer id, Date fechaEvento) {
        this.id = id;
        this.fechaEvento = fechaEvento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public Usuario getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Usuario usuarioid) {
        this.usuarioid = usuarioid;
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
        if (!(object instanceof DetalleDeMovimientoDeProducto)) {
            return false;
        }
        DetalleDeMovimientoDeProducto other = (DetalleDeMovimientoDeProducto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.HistorialDeMovimientoDeProducto[ id=" + id + " ]";
    }

    public RegistroDeMovimientoDeInventario getRegistroDeMovimientoDeInventariocodigo() {
        return registroDeMovimientoDeInventariocodigo;
    }

    public void setRegistroDeMovimientoDeInventariocodigo(RegistroDeMovimientoDeInventario registroDeMovimientoDeInventariocodigo) {
        this.registroDeMovimientoDeInventariocodigo = registroDeMovimientoDeInventariocodigo;
    }

    public ItemProducto getItemProductonumeroSerialentra() {
        return itemProductonumeroSerialentra;
    }

    public void setItemProductonumeroSerialentra(ItemProducto itemProductonumeroSerialentra) {
        this.itemProductonumeroSerialentra = itemProductonumeroSerialentra;
    }

    public ItemProducto getItemProductonumeroSerialsale() {
        return itemProductonumeroSerialsale;
    }

    public void setItemProductonumeroSerialsale(ItemProducto itemProductonumeroSerialsale) {
        this.itemProductonumeroSerialsale = itemProductonumeroSerialsale;
    }
    
}
