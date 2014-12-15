/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "CampoUsuario", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CampoUsuario.findAll", query = "SELECT c FROM CampoUsuario c"),
    @NamedQuery(name = "CampoUsuario.findByAtributoGrupoid", query = "SELECT c FROM CampoUsuario c WHERE c.campoUsuarioPK.atributoGrupoid = :atributoGrupoid"),
    @NamedQuery(name = "CampoUsuario.findByUsuarioid", query = "SELECT c FROM CampoUsuario c WHERE c.campoUsuarioPK.usuarioid = :usuarioid"),
    @NamedQuery(name = "CampoUsuario.findByValor", query = "SELECT c FROM CampoUsuario c WHERE c.valor = :valor")})
public class CampoUsuario implements Serializable {
    @JoinColumn(name = "Usuario_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "AtributoGrupo_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AtributoGrupo atributoGrupo;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CampoUsuarioPK campoUsuarioPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private int valor;
    
    public CampoUsuario() {
    }

    public CampoUsuario(CampoUsuarioPK campoUsuarioPK) {
        this.campoUsuarioPK = campoUsuarioPK;
    }

    public CampoUsuario(CampoUsuarioPK campoUsuarioPK, int valor) {
        this.campoUsuarioPK = campoUsuarioPK;
        this.valor = valor;
    }

    public CampoUsuario(int atributoGrupoid, int usuarioid) {
        this.campoUsuarioPK = new CampoUsuarioPK(atributoGrupoid, usuarioid);
    }

    public CampoUsuarioPK getCampoUsuarioPK() {
        return campoUsuarioPK;
    }

    public void setCampoUsuarioPK(CampoUsuarioPK campoUsuarioPK) {
        this.campoUsuarioPK = campoUsuarioPK;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (campoUsuarioPK != null ? campoUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CampoUsuario)) {
            return false;
        }
        CampoUsuario other = (CampoUsuario) object;
        if ((this.campoUsuarioPK == null && other.campoUsuarioPK != null) || (this.campoUsuarioPK != null && !this.campoUsuarioPK.equals(other.campoUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.CampoUsuario[ campoUsuarioPK=" + campoUsuarioPK + " ]";
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
    
}
