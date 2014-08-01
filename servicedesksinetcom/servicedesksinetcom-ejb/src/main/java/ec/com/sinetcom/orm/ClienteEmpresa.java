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
@Table(name = "ClienteEmpresa", catalog = "dbsinetcom", schema = "")
@NamedQueries({
    @NamedQuery(name = "ClienteEmpresa.findAll", query = "SELECT c FROM ClienteEmpresa c")})
public class ClienteEmpresa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "ruc")
    private String ruc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "razonSocial")
    private String razonSocial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombreComercial")
    private String nombreComercial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "telefonoPBX")
    private String telefonoPBX;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombreRepresentanteLegal")
    private String nombreRepresentanteLegal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteEmpresaruc")
    private List<Contacto> contactoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteEmpresaruc")
    private List<Contrato> contratoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteEmpresa")
    private List<ClienteDireccion> clienteDireccionList;
    @OneToMany(mappedBy = "clienteEmpresaruc")
    private List<RegistroDeMovimientoDeInventario> registroDeMovimientoDeInventarioList;
    @JoinColumn(name = "Usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioid;
    @JoinColumn(name = "TipoEmpresa_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoEmpresa tipoEmpresaid;

    public ClienteEmpresa() {
    }

    public ClienteEmpresa(String ruc) {
        this.ruc = ruc;
    }

    public ClienteEmpresa(String ruc, String razonSocial, String nombreComercial, String telefonoPBX, String nombreRepresentanteLegal) {
        this.ruc = ruc;
        this.razonSocial = razonSocial;
        this.nombreComercial = nombreComercial;
        this.telefonoPBX = telefonoPBX;
        this.nombreRepresentanteLegal = nombreRepresentanteLegal;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getTelefonoPBX() {
        return telefonoPBX;
    }

    public void setTelefonoPBX(String telefonoPBX) {
        this.telefonoPBX = telefonoPBX;
    }

    public String getNombreRepresentanteLegal() {
        return nombreRepresentanteLegal;
    }

    public void setNombreRepresentanteLegal(String nombreRepresentanteLegal) {
        this.nombreRepresentanteLegal = nombreRepresentanteLegal;
    }

    public List<Contacto> getContactoList() {
        return contactoList;
    }

    public void setContactoList(List<Contacto> contactoList) {
        this.contactoList = contactoList;
    }

    public List<Contrato> getContratoList() {
        return contratoList;
    }

    public void setContratoList(List<Contrato> contratoList) {
        this.contratoList = contratoList;
    }

    public List<ClienteDireccion> getClienteDireccionList() {
        return clienteDireccionList;
    }

    public void setClienteDireccionList(List<ClienteDireccion> clienteDireccionList) {
        this.clienteDireccionList = clienteDireccionList;
    }

    public List<RegistroDeMovimientoDeInventario> getRegistroDeMovimientoDeInventarioList() {
        return registroDeMovimientoDeInventarioList;
    }

    public void setRegistroDeMovimientoDeInventarioList(List<RegistroDeMovimientoDeInventario> registroDeMovimientoDeInventarioList) {
        this.registroDeMovimientoDeInventarioList = registroDeMovimientoDeInventarioList;
    }

    public Usuario getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Usuario usuarioid) {
        this.usuarioid = usuarioid;
    }

    public TipoEmpresa getTipoEmpresaid() {
        return tipoEmpresaid;
    }

    public void setTipoEmpresaid(TipoEmpresa tipoEmpresaid) {
        this.tipoEmpresaid = tipoEmpresaid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ruc != null ? ruc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteEmpresa)) {
            return false;
        }
        ClienteEmpresa other = (ClienteEmpresa) object;
        if ((this.ruc == null && other.ruc != null) || (this.ruc != null && !this.ruc.equals(other.ruc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.ClienteEmpresa[ ruc=" + ruc + " ]";
    }
    
}
