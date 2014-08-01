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

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "Pago", catalog = "dbsinetcom", schema = "")
@NamedQueries({
    @NamedQuery(name = "Pago.findAll", query = "SELECT p FROM Pago p")})
public class Pago implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orden")
    private int orden;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "monto")
    private String monto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "plazo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date plazo;
    @Column(name = "anticipo")
    private Boolean anticipo;
    @Size(max = 45)
    @Column(name = "idNotificador")
    private String idNotificador;
    @JoinColumn(name = "Contrato_numero", referencedColumnName = "numero")
    @ManyToOne(optional = false)
    private Contrato contratonumero;

    public Pago() {
    }

    public Pago(Integer id) {
        this.id = id;
    }

    public Pago(Integer id, int orden, String monto, Date plazo) {
        this.id = id;
        this.orden = orden;
        this.monto = monto;
        this.plazo = plazo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public Date getPlazo() {
        return plazo;
    }

    public void setPlazo(Date plazo) {
        this.plazo = plazo;
    }

    public Boolean getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(Boolean anticipo) {
        this.anticipo = anticipo;
    }

    public String getIdNotificador() {
        return idNotificador;
    }

    public void setIdNotificador(String idNotificador) {
        this.idNotificador = idNotificador;
    }

    public Contrato getContratonumero() {
        return contratonumero;
    }

    public void setContratonumero(Contrato contratonumero) {
        this.contratonumero = contratonumero;
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
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.Pago[ id=" + id + " ]";
    }
    
}
