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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "TipoGarantia", catalog = "dbsinetcom", schema = "")
@NamedQueries({
    @NamedQuery(name = "TipoGarantia.findAll", query = "SELECT t FROM TipoGarantia t")})
public class TipoGarantia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valido")
    private boolean valido;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoGarantiaid")
    private List<GarantiaEconomica> garantiaEconomicaList;

    public TipoGarantia() {
    }

    public TipoGarantia(Integer id) {
        this.id = id;
    }

    public TipoGarantia(Integer id, String tipo, boolean valido) {
        this.id = id;
        this.tipo = tipo;
        this.valido = valido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean getValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public List<GarantiaEconomica> getGarantiaEconomicaList() {
        return garantiaEconomicaList;
    }

    public void setGarantiaEconomicaList(List<GarantiaEconomica> garantiaEconomicaList) {
        this.garantiaEconomicaList = garantiaEconomicaList;
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
        if (!(object instanceof TipoGarantia)) {
            return false;
        }
        TipoGarantia other = (TipoGarantia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.TipoGarantia[ id=" + id + " ]";
    }
    
}
