/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "HistorialDeContratosYEquipos", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistorialDeContratosYEquipos.findAll", query = "SELECT h FROM HistorialDeContratosYEquipos h"),
    @NamedQuery(name = "HistorialDeContratosYEquipos.findById", query = "SELECT h FROM HistorialDeContratosYEquipos h WHERE h.id = :id"),
    @NamedQuery(name = "HistorialDeContratosYEquipos.findByFechaDeEntregaRecepcion", query = "SELECT h FROM HistorialDeContratosYEquipos h WHERE h.fechaDeEntregaRecepcion = :fechaDeEntregaRecepcion")})
public class HistorialDeContratosYEquipos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fechaDeEntregaRecepcion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeEntregaRecepcion;
    @JoinColumn(name = "Contrato_numero", referencedColumnName = "numero")
    @ManyToOne(optional = false)
    private Contrato contratonumero;
    @JoinColumn(name = "ItemProducto_numeroSerial", referencedColumnName = "numeroSerial")
    @ManyToOne(optional = false)
    private ItemProducto itemProductonumeroSerial;

    public HistorialDeContratosYEquipos() {
    }

    public HistorialDeContratosYEquipos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaDeEntregaRecepcion() {
        return fechaDeEntregaRecepcion;
    }

    public void setFechaDeEntregaRecepcion(Date fechaDeEntregaRecepcion) {
        this.fechaDeEntregaRecepcion = fechaDeEntregaRecepcion;
    }

    public Contrato getContratonumero() {
        return contratonumero;
    }

    public void setContratonumero(Contrato contratonumero) {
        this.contratonumero = contratonumero;
    }

    public ItemProducto getItemProductonumeroSerial() {
        return itemProductonumeroSerial;
    }

    public void setItemProductonumeroSerial(ItemProducto itemProductonumeroSerial) {
        this.itemProductonumeroSerial = itemProductonumeroSerial;
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
        if (!(object instanceof HistorialDeContratosYEquipos)) {
            return false;
        }
        HistorialDeContratosYEquipos other = (HistorialDeContratosYEquipos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.HistorialDeContratosYEquipos[ id=" + id + " ]";
    }
    
}
