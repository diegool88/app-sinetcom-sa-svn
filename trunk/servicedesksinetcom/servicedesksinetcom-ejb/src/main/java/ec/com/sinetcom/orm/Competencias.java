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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "Competencias", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Competencias.findAll", query = "SELECT c FROM Competencias c")})
public class Competencias implements Serializable {
    @JoinTable(name = "UsuarioCompetencias", joinColumns = {
        @JoinColumn(name = "Competencias_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "Usuario_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Usuario> usuarioList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 254)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "competenciasid")
    private List<UsuarioCompetencias> usuarioCompetenciasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "competenciasid")
    private List<Cola> colaList;

    public Competencias() {
    }

    public Competencias(Integer id) {
        this.id = id;
    }

    public Competencias(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<UsuarioCompetencias> getUsuarioCompetenciasList() {
        return usuarioCompetenciasList;
    }

    public void setUsuarioCompetenciasList(List<UsuarioCompetencias> usuarioCompetenciasList) {
        this.usuarioCompetenciasList = usuarioCompetenciasList;
    }

    @XmlTransient
    public List<Cola> getColaList() {
        return colaList;
    }

    public void setColaList(List<Cola> colaList) {
        this.colaList = colaList;
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
        if (!(object instanceof Competencias)) {
            return false;
        }
        Competencias other = (Competencias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.Competencias[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }
    
}
