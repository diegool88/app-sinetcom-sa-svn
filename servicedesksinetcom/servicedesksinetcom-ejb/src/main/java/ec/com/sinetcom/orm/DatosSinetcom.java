/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.orm;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "DatosSinetcom", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatosSinetcom.findAll", query = "SELECT d FROM DatosSinetcom d"),
    @NamedQuery(name = "DatosSinetcom.findByRuc", query = "SELECT d FROM DatosSinetcom d WHERE d.ruc = :ruc"),
    @NamedQuery(name = "DatosSinetcom.findByRazonSocial", query = "SELECT d FROM DatosSinetcom d WHERE d.razonSocial = :razonSocial"),
    @NamedQuery(name = "DatosSinetcom.findByNombreComercial", query = "SELECT d FROM DatosSinetcom d WHERE d.nombreComercial = :nombreComercial"),
    @NamedQuery(name = "DatosSinetcom.findByEmailPresidente", query = "SELECT d FROM DatosSinetcom d WHERE d.emailPresidente = :emailPresidente"),
    @NamedQuery(name = "DatosSinetcom.findByEmailGerenteGeneral", query = "SELECT d FROM DatosSinetcom d WHERE d.emailGerenteGeneral = :emailGerenteGeneral"),
    @NamedQuery(name = "DatosSinetcom.findByEmailNoResponder", query = "SELECT d FROM DatosSinetcom d WHERE d.emailNoResponder = :emailNoResponder"),
    @NamedQuery(name = "DatosSinetcom.findByEmailSupervisorContratos", query = "SELECT d FROM DatosSinetcom d WHERE d.emailSupervisorContratos = :emailSupervisorContratos"),
    @NamedQuery(name = "DatosSinetcom.findByEmailGerenteComercial", query = "SELECT d FROM DatosSinetcom d WHERE d.emailGerenteComercial = :emailGerenteComercial"),
    @NamedQuery(name = "DatosSinetcom.findByEmailGerenteTecnico", query = "SELECT d FROM DatosSinetcom d WHERE d.emailGerenteTecnico = :emailGerenteTecnico")})
public class DatosSinetcom implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "ruc")
    private String ruc;
    @Size(max = 45)
    @Column(name = "razonSocial")
    private String razonSocial;
    @Size(max = 45)
    @Column(name = "nombreComercial")
    private String nombreComercial;
    @Size(max = 45)
    @Column(name = "emailPresidente")
    private String emailPresidente;
    @Size(max = 45)
    @Column(name = "emailGerenteGeneral")
    private String emailGerenteGeneral;
    @Size(max = 45)
    @Column(name = "emailNoResponder")
    private String emailNoResponder;
    @Size(max = 45)
    @Column(name = "emailSupervisorContratos")
    private String emailSupervisorContratos;
    @Size(max = 45)
    @Column(name = "emailGerenteComercial")
    private String emailGerenteComercial;
    @Size(max = 45)
    @Column(name = "emailGerenteTecnico")
    private String emailGerenteTecnico;

    public DatosSinetcom() {
    }

    public DatosSinetcom(String ruc) {
        this.ruc = ruc;
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

    public String getEmailPresidente() {
        return emailPresidente;
    }

    public void setEmailPresidente(String emailPresidente) {
        this.emailPresidente = emailPresidente;
    }

    public String getEmailGerenteGeneral() {
        return emailGerenteGeneral;
    }

    public void setEmailGerenteGeneral(String emailGerenteGeneral) {
        this.emailGerenteGeneral = emailGerenteGeneral;
    }

    public String getEmailNoResponder() {
        return emailNoResponder;
    }

    public void setEmailNoResponder(String emailNoResponder) {
        this.emailNoResponder = emailNoResponder;
    }

    public String getEmailSupervisorContratos() {
        return emailSupervisorContratos;
    }

    public void setEmailSupervisorContratos(String emailSupervisorContratos) {
        this.emailSupervisorContratos = emailSupervisorContratos;
    }

    public String getEmailGerenteComercial() {
        return emailGerenteComercial;
    }

    public void setEmailGerenteComercial(String emailGerenteComercial) {
        this.emailGerenteComercial = emailGerenteComercial;
    }

    public String getEmailGerenteTecnico() {
        return emailGerenteTecnico;
    }

    public void setEmailGerenteTecnico(String emailGerenteTecnico) {
        this.emailGerenteTecnico = emailGerenteTecnico;
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
        if (!(object instanceof DatosSinetcom)) {
            return false;
        }
        DatosSinetcom other = (DatosSinetcom) object;
        if ((this.ruc == null && other.ruc != null) || (this.ruc != null && !this.ruc.equals(other.ruc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.DatosSinetcom[ ruc=" + ruc + " ]";
    }
    
}
