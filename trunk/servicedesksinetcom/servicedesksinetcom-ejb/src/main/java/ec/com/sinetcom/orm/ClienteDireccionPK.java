/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.orm;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author diegoflores
 */
@Embeddable
public class ClienteDireccionPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "Ciudad_id")
    private int ciudadid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "ClienteEmpresa_ruc")
    private String clienteEmpresaruc;

    public ClienteDireccionPK() {
    }

    public ClienteDireccionPK(int ciudadid, String clienteEmpresaruc) {
        this.ciudadid = ciudadid;
        this.clienteEmpresaruc = clienteEmpresaruc;
    }

    public int getCiudadid() {
        return ciudadid;
    }

    public void setCiudadid(int ciudadid) {
        this.ciudadid = ciudadid;
    }

    public String getClienteEmpresaruc() {
        return clienteEmpresaruc;
    }

    public void setClienteEmpresaruc(String clienteEmpresaruc) {
        this.clienteEmpresaruc = clienteEmpresaruc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) ciudadid;
        hash += (clienteEmpresaruc != null ? clienteEmpresaruc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteDireccionPK)) {
            return false;
        }
        ClienteDireccionPK other = (ClienteDireccionPK) object;
        if (this.ciudadid != other.ciudadid) {
            return false;
        }
        if ((this.clienteEmpresaruc == null && other.clienteEmpresaruc != null) || (this.clienteEmpresaruc != null && !this.clienteEmpresaruc.equals(other.clienteEmpresaruc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.ClienteDireccionPK[ ciudadid=" + ciudadid + ", clienteEmpresaruc=" + clienteEmpresaruc + " ]";
    }
    
}
