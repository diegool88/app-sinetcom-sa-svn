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
    @Column(name = "ParametroDeProducto_id")
    private int parametroDeProductoid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ItemProducto_numeroSerial")
    private String itemProductonumeroSerial;

    public AtributoItemProductoPK() {
    }

    public AtributoItemProductoPK(int parametrosDeProductoid, String itemProductonumeroSerial) {
        this.parametroDeProductoid = parametrosDeProductoid;
        this.itemProductonumeroSerial = itemProductonumeroSerial;
    }

    public int getParametroDeProductoid() {
        return parametroDeProductoid;
    }

    public void setParametroDeProductoid(int parametrosDeProductoid) {
        this.parametroDeProductoid = parametrosDeProductoid;
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
        hash += (int) parametroDeProductoid;
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
        if (this.parametroDeProductoid != other.parametroDeProductoid) {
            return false;
        }
        if ((this.itemProductonumeroSerial == null && other.itemProductonumeroSerial != null) || (this.itemProductonumeroSerial != null && !this.itemProductonumeroSerial.equals(other.itemProductonumeroSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.AtributoItemProductoPK[ parametrosDeProductoid=" + parametroDeProductoid + ", itemProductonumeroSerial=" + itemProductonumeroSerial + " ]";
    }
    
}
