/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.orm;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author diegoflores
 */
@Embeddable
public class AtributoItemProductoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ParametrosDeProducto_id")
    private int parametrosDeProductoid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ItemProducto_numeroSerial")
    private String itemProductonumeroSerial;

    public AtributoItemProductoPK() {
    }

    public AtributoItemProductoPK(int parametrosDeProductoid, String itemProductonumeroSerial) {
        this.parametrosDeProductoid = parametrosDeProductoid;
        this.itemProductonumeroSerial = itemProductonumeroSerial;
    }

    public int getParametrosDeProductoid() {
        return parametrosDeProductoid;
    }

    public void setParametrosDeProductoid(int parametrosDeProductoid) {
        this.parametrosDeProductoid = parametrosDeProductoid;
    }

    public String getItemProductonumeroSerial() {
        return itemProductonumeroSerial;
    }

    public void setItemProductonumeroSerial(String itemProductonumeroSerial) {
        this.itemProductonumeroSerial = itemProductonumeroSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) parametrosDeProductoid;
        hash += (itemProductonumeroSerial != null ? itemProductonumeroSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtributoItemProductoPK)) {
            return false;
        }
        AtributoItemProductoPK other = (AtributoItemProductoPK) object;
        if (this.parametrosDeProductoid != other.parametrosDeProductoid) {
            return false;
        }
        if ((this.itemProductonumeroSerial == null && other.itemProductonumeroSerial != null) || (this.itemProductonumeroSerial != null && !this.itemProductonumeroSerial.equals(other.itemProductonumeroSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.AtributoItemProductoPK[ parametrosDeProductoid=" + parametrosDeProductoid + ", itemProductonumeroSerial=" + itemProductonumeroSerial + " ]";
    }
    
}
