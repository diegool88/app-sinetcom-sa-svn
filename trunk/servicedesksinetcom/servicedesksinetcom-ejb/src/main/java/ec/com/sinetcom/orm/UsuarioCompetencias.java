/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.orm;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "UsuarioCompetencias", catalog = "dbsinetcom", schema = "")
@NamedQueries({
    @NamedQuery(name = "UsuarioCompetencias.findAll", query = "SELECT u FROM UsuarioCompetencias u")})
public class UsuarioCompetencias implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Usuario_id")
    private Integer usuarioid;
    @JoinColumn(name = "Usuario_id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "Competencias_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Competencias competenciasid;

    public UsuarioCompetencias() {
    }

    public UsuarioCompetencias(Integer usuarioid) {
        this.usuarioid = usuarioid;
    }

    public Integer getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Integer usuarioid) {
        this.usuarioid = usuarioid;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Competencias getCompetenciasid() {
        return competenciasid;
    }

    public void setCompetenciasid(Competencias competenciasid) {
        this.competenciasid = competenciasid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioid != null ? usuarioid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioCompetencias)) {
            return false;
        }
        UsuarioCompetencias other = (UsuarioCompetencias) object;
        if ((this.usuarioid == null && other.usuarioid != null) || (this.usuarioid != null && !this.usuarioid.equals(other.usuarioid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.UsuarioCompetencias[ usuarioid=" + usuarioid + " ]";
    }
    
}
