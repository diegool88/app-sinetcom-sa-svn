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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "Usuario", catalog = "dbsinetcom", schema = "")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")})
public class Usuario implements Serializable {
    @ManyToMany(mappedBy = "usuarioList")
    private List<Competencias> competenciasList;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioidresponsable")
    private List<Ticket> ticketList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioidpropietario")
    private List<Ticket> ticketList1;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "cedulaDeCuidadania")
    private String cedulaDeCuidadania;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "correoElectronico")
    private String correoElectronico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "apellido")
    private String apellido;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "telefonoMovil")
    private String telefonoMovil;
    @Size(max = 15)
    @Column(name = "telefonoFijo")
    private String telefonoFijo;
    @Size(max = 15)
    @Column(name = "telefonoEmpresarial")
    private String telefonoEmpresarial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountManagerAsignado")
    private List<Contrato> contratoList;
    @OneToMany(mappedBy = "intructorSinetcom")
    private List<Curso> cursoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<CamposUsuario> camposUsuarioList;
    @OneToMany(mappedBy = "usuarioidmodificacion")
    private List<Faq> faqList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioidcreacion")
    private List<Faq> faqList1;
    @JoinColumn(name = "Grupo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Grupo grupoid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioid")
    private List<HistorialDeTicket> historialDeTicketList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioid")
    private List<ClienteEmpresa> clienteEmpresaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioid")
    private List<HistorialDeMovimientoDeProducto> historialDeMovimientoDeProductoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "para")
    private List<Articulo> articuloList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "de")
    private List<Articulo> articuloList1;
    
    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(Integer id, String correoElectronico, String password, String nombre, String apellido, String telefonoMovil) {
        this.id = id;
        this.correoElectronico = correoElectronico;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefonoMovil = telefonoMovil;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCedulaDeCuidadania() {
        return cedulaDeCuidadania;
    }

    public void setCedulaDeCuidadania(String cedulaDeCuidadania) {
        this.cedulaDeCuidadania = cedulaDeCuidadania;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoEmpresarial() {
        return telefonoEmpresarial;
    }

    public void setTelefonoEmpresarial(String telefonoEmpresarial) {
        this.telefonoEmpresarial = telefonoEmpresarial;
    }

    @XmlTransient
    public List<Contrato> getContratoList() {
        return contratoList;
    }

    public void setContratoList(List<Contrato> contratoList) {
        this.contratoList = contratoList;
    }

    @XmlTransient
    public List<Curso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<Curso> cursoList) {
        this.cursoList = cursoList;
    }

    
    
    @XmlTransient
    public List<CamposUsuario> getCamposUsuarioList() {
        return camposUsuarioList;
    }

    public void setCamposUsuarioList(List<CamposUsuario> camposUsuarioList) {
        this.camposUsuarioList = camposUsuarioList;
    }

    @XmlTransient
    public List<Faq> getFaqList() {
        return faqList;
    }

    public void setFaqList(List<Faq> faqList) {
        this.faqList = faqList;
    }

    @XmlTransient
    public List<Faq> getFaqList1() {
        return faqList1;
    }

    public void setFaqList1(List<Faq> faqList1) {
        this.faqList1 = faqList1;
    }

    public Grupo getGrupoid() {
        return grupoid;
    }

    public void setGrupoid(Grupo grupoid) {
        this.grupoid = grupoid;
    }

    @XmlTransient
    public List<HistorialDeTicket> getHistorialDeTicketList() {
        return historialDeTicketList;
    }

    public void setHistorialDeTicketList(List<HistorialDeTicket> historialDeTicketList) {
        this.historialDeTicketList = historialDeTicketList;
    }

    @XmlTransient
    public List<ClienteEmpresa> getClienteEmpresaList() {
        return clienteEmpresaList;
    }

    public void setClienteEmpresaList(List<ClienteEmpresa> clienteEmpresaList) {
        this.clienteEmpresaList = clienteEmpresaList;
    }

    @XmlTransient
    public List<HistorialDeMovimientoDeProducto> getHistorialDeMovimientoDeProductoList() {
        return historialDeMovimientoDeProductoList;
    }

    public void setHistorialDeMovimientoDeProductoList(List<HistorialDeMovimientoDeProducto> historialDeMovimientoDeProductoList) {
        this.historialDeMovimientoDeProductoList = historialDeMovimientoDeProductoList;
    }

    @XmlTransient
    public List<Articulo> getArticuloList() {
        return articuloList;
    }

    public void setArticuloList(List<Articulo> articuloList) {
        this.articuloList = articuloList;
    }

    @XmlTransient
    public List<Articulo> getArticuloList1() {
        return articuloList1;
    }

    public void setArticuloList1(List<Articulo> articuloList1) {
        this.articuloList1 = articuloList1;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.Usuario[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @XmlTransient
    public List<Ticket> getTicketList1() {
        return ticketList1;
    }

    public void setTicketList1(List<Ticket> ticketList1) {
        this.ticketList1 = ticketList1;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<Competencias> getCompetenciasList() {
        return competenciasList;
    }

    public void setCompetenciasList(List<Competencias> competenciasList) {
        this.competenciasList = competenciasList;
    }

    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }
    
    
}
