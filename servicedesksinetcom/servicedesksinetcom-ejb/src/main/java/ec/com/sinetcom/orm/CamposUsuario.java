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

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "CamposUsuario", catalog = "dbsinetcom", schema = "")
@NamedQueries({
    @NamedQuery(name = "CamposUsuario.findAll", query = "SELECT c FROM CamposUsuario c")})
public class CamposUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CamposUsuarioPK camposUsuarioPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private int valor;
    @JoinColumn(name = "Usuario_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "AtributoGrupo_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AtributoGrupo atributoGrupo;

    public CamposUsuario() {
    }

    public CamposUsuario(CamposUsuarioPK camposUsuarioPK) {
        this.camposUsuarioPK = camposUsuarioPK;
    }

    public CamposUsuario(CamposUsuarioPK camposUsuarioPK, int valor) {
        this.camposUsuarioPK = camposUsuarioPK;
        this.valor = valor;
    }

    public CamposUsuario(int atributoGrupoid, int usuarioid) {
        this.camposUsuarioPK = new CamposUsuarioPK(atributoGrupoid, usuarioid);
    }

    public CamposUsuarioPK getCamposUsuarioPK() {
        return camposUsuarioPK;
    }

    public void setCamposUsuarioPK(CamposUsuarioPK camposUsuarioPK) {
        this.camposUsuarioPK = camposUsuarioPK;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public AtributoGrupo getAtributoGrupo() {
        return atributoGrupo;
    }

    public void setAtributoGrupo(AtributoGrupo atributoGrupo) {
        this.atributoGrupo = atributoGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (camposUsuarioPK != null ? camposUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CamposUsuario)) {
            return false;
        }
        CamposUsuario other = (CamposUsuario) object;
        if ((this.camposUsuarioPK == null && other.camposUsuarioPK != null) || (this.camposUsuarioPK != null && !this.camposUsuarioPK.equals(other.camposUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.CamposUsuario[ camposUsuarioPK=" + camposUsuarioPK + " ]";
    }
    
}
