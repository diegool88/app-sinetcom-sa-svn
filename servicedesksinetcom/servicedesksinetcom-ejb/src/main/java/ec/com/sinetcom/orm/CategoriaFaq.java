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

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "CategoriaFaq", catalog = "dbsinetcom", schema = "")
@NamedQueries({
    @NamedQuery(name = "CategoriaFaq.findAll", query = "SELECT c FROM CategoriaFaq c")})
public class CategoriaFaq implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 120)
    @Column(name = "comentarios")
    private String comentarios;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valido")
    private boolean valido;
    @OneToMany(mappedBy = "codigoPadre")
    private List<CategoriaFaq> categoriaFaqList;
    @JoinColumn(name = "codigoPadre", referencedColumnName = "codigo")
    @ManyToOne
    private CategoriaFaq codigoPadre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriaFaqcodigo")
    private List<Faq> faqList;

    public CategoriaFaq() {
    }

    public CategoriaFaq(Integer codigo) {
        this.codigo = codigo;
    }

    public CategoriaFaq(Integer codigo, String nombre, boolean valido) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.valido = valido;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public boolean getValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public List<CategoriaFaq> getCategoriaFaqList() {
        return categoriaFaqList;
    }

    public void setCategoriaFaqList(List<CategoriaFaq> categoriaFaqList) {
        this.categoriaFaqList = categoriaFaqList;
    }

    public CategoriaFaq getCodigoPadre() {
        return codigoPadre;
    }

    public void setCodigoPadre(CategoriaFaq codigoPadre) {
        this.codigoPadre = codigoPadre;
    }

    public List<Faq> getFaqList() {
        return faqList;
    }

    public void setFaqList(List<Faq> faqList) {
        this.faqList = faqList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaFaq)) {
            return false;
        }
        CategoriaFaq other = (CategoriaFaq) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.CategoriaFaq[ codigo=" + codigo + " ]";
    }
    
}
