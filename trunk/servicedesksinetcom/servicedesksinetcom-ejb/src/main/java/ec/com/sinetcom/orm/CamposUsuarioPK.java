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

/**
 *
 * @author diegoflores
 */
@Embeddable
public class CamposUsuarioPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "AtributoGrupo_id")
    private int atributoGrupoid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Usuario_id")
    private int usuarioid;

    public CamposUsuarioPK() {
    }

    public CamposUsuarioPK(int atributoGrupoid, int usuarioid) {
        this.atributoGrupoid = atributoGrupoid;
        this.usuarioid = usuarioid;
    }

    public int getAtributoGrupoid() {
        return atributoGrupoid;
    }

    public void setAtributoGrupoid(int atributoGrupoid) {
        this.atributoGrupoid = atributoGrupoid;
    }

    public int getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(int usuarioid) {
        this.usuarioid = usuarioid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) atributoGrupoid;
        hash += (int) usuarioid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CamposUsuarioPK)) {
            return false;
        }
        CamposUsuarioPK other = (CamposUsuarioPK) object;
        if (this.atributoGrupoid != other.atributoGrupoid) {
            return false;
        }
        if (this.usuarioid != other.usuarioid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.CamposUsuarioPK[ atributoGrupoid=" + atributoGrupoid + ", usuarioid=" + usuarioid + " ]";
    }
    
}
