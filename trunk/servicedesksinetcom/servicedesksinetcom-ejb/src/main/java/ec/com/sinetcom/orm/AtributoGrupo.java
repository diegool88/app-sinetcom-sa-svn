/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.orm;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "AtributoGrupo", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AtributoGrupo.findAll", query = "SELECT a FROM AtributoGrupo a")})
public class AtributoGrupo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipoDeDato")
    private String tipoDeDato;
    @JoinColumn(name = "Grupo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Grupo grupoid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "atributoGrupo")
    private List<CamposUsuario> camposUsuarioList;

    public AtributoGrupo() {
    }

    public AtributoGrupo(Integer id) {
        this.id = id;
    }

    public AtributoGrupo(Integer id, String nombre, String tipoDeDato) {
        this.id = id;
        this.nombre = nombre;
        this.tipoDeDato = tipoDeDato;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDeDato() {
        return tipoDeDato;
    }

    public void setTipoDeDato(String tipoDeDato) {
        this.tipoDeDato = tipoDeDato;
    }

    public Grupo getGrupoid() {
        return grupoid;
    }

    public void setGrupoid(Grupo grupoid) {
        this.grupoid = grupoid;
    }

    @XmlTransient
    public List<CamposUsuario> getCamposUsuarioList() {
        return camposUsuarioList;
    }

    public void setCamposUsuarioList(List<CamposUsuario> camposUsuarioList) {
        this.camposUsuarioList = camposUsuarioList;
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
        if (!(object instanceof AtributoGrupo)) {
            return false;
        }
        AtributoGrupo other = (AtributoGrupo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.AtributoGrupo[ id=" + id + " ]";
    }
    
}
