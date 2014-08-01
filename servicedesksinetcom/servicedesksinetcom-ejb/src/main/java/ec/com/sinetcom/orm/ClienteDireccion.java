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
import javax.validation.constraints.Size;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "ClienteDireccion", catalog = "dbsinetcom", schema = "")
@NamedQueries({
    @NamedQuery(name = "ClienteDireccion.findAll", query = "SELECT c FROM ClienteDireccion c")})
public class ClienteDireccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClienteDireccionPK clienteDireccionPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "direccion1")
    private String direccion1;
    @Size(max = 45)
    @Column(name = "direccion2")
    private String direccion2;
    @JoinColumn(name = "ClienteEmpresa_ruc", referencedColumnName = "ruc", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ClienteEmpresa clienteEmpresa;
    @JoinColumn(name = "Ciudad_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ciudad ciudad;

    public ClienteDireccion() {
    }

    public ClienteDireccion(ClienteDireccionPK clienteDireccionPK) {
        this.clienteDireccionPK = clienteDireccionPK;
    }

    public ClienteDireccion(ClienteDireccionPK clienteDireccionPK, String direccion1) {
        this.clienteDireccionPK = clienteDireccionPK;
        this.direccion1 = direccion1;
    }

    public ClienteDireccion(int ciudadid, String clienteEmpresaruc) {
        this.clienteDireccionPK = new ClienteDireccionPK(ciudadid, clienteEmpresaruc);
    }

    public ClienteDireccionPK getClienteDireccionPK() {
        return clienteDireccionPK;
    }

    public void setClienteDireccionPK(ClienteDireccionPK clienteDireccionPK) {
        this.clienteDireccionPK = clienteDireccionPK;
    }

    public String getDireccion1() {
        return direccion1;
    }

    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public ClienteEmpresa getClienteEmpresa() {
        return clienteEmpresa;
    }

    public void setClienteEmpresa(ClienteEmpresa clienteEmpresa) {
        this.clienteEmpresa = clienteEmpresa;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clienteDireccionPK != null ? clienteDireccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteDireccion)) {
            return false;
        }
        ClienteDireccion other = (ClienteDireccion) object;
        if ((this.clienteDireccionPK == null && other.clienteDireccionPK != null) || (this.clienteDireccionPK != null && !this.clienteDireccionPK.equals(other.clienteDireccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.ClienteDireccion[ clienteDireccionPK=" + clienteDireccionPK + " ]";
    }
    
}
