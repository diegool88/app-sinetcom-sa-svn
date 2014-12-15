/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.orm;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "AtributoItemProducto", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AtributoItemProducto.findAll", query = "SELECT a FROM AtributoItemProducto a")})
public class AtributoItemProducto implements Serializable,  Cloneable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AtributoItemProductoPK atributoItemProductoPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "valor")
    private String valor;
    @JoinColumn(name = "ParametroDeProducto_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ParametroDeProducto parametroDeProducto;
    @JoinColumn(name = "ItemProducto_numeroSerial", referencedColumnName = "numeroSerial", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ItemProducto itemProducto;

    public AtributoItemProducto() {
    }

    public AtributoItemProducto(AtributoItemProductoPK atributoItemProductoPK) {
        this.atributoItemProductoPK = atributoItemProductoPK;
    }

    public AtributoItemProducto(AtributoItemProductoPK atributoItemProductoPK, String valor) {
        this.atributoItemProductoPK = atributoItemProductoPK;
        this.valor = valor;
    }

    public AtributoItemProducto(int parametrosDeProductoid, String itemProductonumeroSerial) {
        this.atributoItemProductoPK = new AtributoItemProductoPK(parametrosDeProductoid, itemProductonumeroSerial);
    }

    public AtributoItemProductoPK getAtributoItemProductoPK() {
        return atributoItemProductoPK;
    }

    public void setAtributoItemProductoPK(AtributoItemProductoPK atributoItemProductoPK) {
        this.atributoItemProductoPK = atributoItemProductoPK;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public ParametroDeProducto getParametroDeProducto() {
        return parametroDeProducto;
    }

    public void setParametroDeProducto(ParametroDeProducto parametrosDeProducto) {
        this.parametroDeProducto = parametrosDeProducto;
    }

    public ItemProducto getItemProducto() {
        return itemProducto;
    }

    public void setItemProducto(ItemProducto itemProducto) {
        this.itemProducto = itemProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (atributoItemProductoPK != null ? atributoItemProductoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtributoItemProducto)) {
            return false;
        }
        AtributoItemProducto other = (AtributoItemProducto) object;
        if ((this.atributoItemProductoPK == null && other.atributoItemProductoPK != null) || (this.atributoItemProductoPK != null && !this.atributoItemProductoPK.equals(other.atributoItemProductoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.AtributoItemProducto[ atributoItemProductoPK=" + atributoItemProductoPK + " ]";
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
    
}
