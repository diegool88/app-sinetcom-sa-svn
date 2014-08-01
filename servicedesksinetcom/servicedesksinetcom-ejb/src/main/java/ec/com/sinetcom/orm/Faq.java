/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.orm;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "Faq", catalog = "dbsinetcom", schema = "")
@NamedQueries({
    @NamedQuery(name = "Faq.findAll", query = "SELECT f FROM Faq f")})
public class Faq implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero")
    private int numero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "asunto")
    private String asunto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valido")
    private boolean valido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aprovado")
    private boolean aprovado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "palabrasClave")
    private String palabrasClave;
    @Lob
    @Size(max = 65535)
    @Column(name = "campo1")
    private String campo1;
    @Lob
    @Size(max = 65535)
    @Column(name = "campo2")
    private String campo2;
    @Lob
    @Size(max = 65535)
    @Column(name = "campo3")
    private String campo3;
    @Lob
    @Size(max = 65535)
    @Column(name = "campo4")
    private String campo4;
    @Lob
    @Size(max = 65535)
    @Column(name = "campo5")
    private String campo5;
    @Lob
    @Size(max = 65535)
    @Column(name = "campo6")
    private String campo6;
    @Column(name = "fechaDeModificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeModificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaDeCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeCreacion;
    @JoinColumn(name = "Usuario_id_modificacion", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuarioidmodificacion;
    @JoinColumn(name = "Usuario_id_creacion", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioidcreacion;
    @JoinColumn(name = "CategoriaFaq_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private CategoriaFaq categoriaFaqcodigo;

    public Faq() {
    }

    public Faq(Integer id) {
        this.id = id;
    }

    public Faq(Integer id, int numero, String asunto, String nombre, boolean valido, boolean aprovado, String palabrasClave, Date fechaDeCreacion) {
        this.id = id;
        this.numero = numero;
        this.asunto = asunto;
        this.nombre = nombre;
        this.valido = valido;
        this.aprovado = aprovado;
        this.palabrasClave = palabrasClave;
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public boolean getAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public String getPalabrasClave() {
        return palabrasClave;
    }

    public void setPalabrasClave(String palabrasClave) {
        this.palabrasClave = palabrasClave;
    }

    public String getCampo1() {
        return campo1;
    }

    public void setCampo1(String campo1) {
        this.campo1 = campo1;
    }

    public String getCampo2() {
        return campo2;
    }

    public void setCampo2(String campo2) {
        this.campo2 = campo2;
    }

    public String getCampo3() {
        return campo3;
    }

    public void setCampo3(String campo3) {
        this.campo3 = campo3;
    }

    public String getCampo4() {
        return campo4;
    }

    public void setCampo4(String campo4) {
        this.campo4 = campo4;
    }

    public String getCampo5() {
        return campo5;
    }

    public void setCampo5(String campo5) {
        this.campo5 = campo5;
    }

    public String getCampo6() {
        return campo6;
    }

    public void setCampo6(String campo6) {
        this.campo6 = campo6;
    }

    public Date getFechaDeModificacion() {
        return fechaDeModificacion;
    }

    public void setFechaDeModificacion(Date fechaDeModificacion) {
        this.fechaDeModificacion = fechaDeModificacion;
    }

    public Date getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(Date fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public Usuario getUsuarioidmodificacion() {
        return usuarioidmodificacion;
    }

    public void setUsuarioidmodificacion(Usuario usuarioidmodificacion) {
        this.usuarioidmodificacion = usuarioidmodificacion;
    }

    public Usuario getUsuarioidcreacion() {
        return usuarioidcreacion;
    }

    public void setUsuarioidcreacion(Usuario usuarioidcreacion) {
        this.usuarioidcreacion = usuarioidcreacion;
    }

    public CategoriaFaq getCategoriaFaqcodigo() {
        return categoriaFaqcodigo;
    }

    public void setCategoriaFaqcodigo(CategoriaFaq categoriaFaqcodigo) {
        this.categoriaFaqcodigo = categoriaFaqcodigo;
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
        if (!(object instanceof Faq)) {
            return false;
        }
        Faq other = (Faq) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.Faq[ id=" + id + " ]";
    }
    
}
