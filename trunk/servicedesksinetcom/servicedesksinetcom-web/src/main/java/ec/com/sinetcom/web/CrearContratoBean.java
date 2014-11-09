/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.TipoContrato;
import ec.com.sinetcom.servicios.ContratoServicio;
import java.sql.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ABozzano
 */
@ManagedBean (name="crearContratoBean")
@ViewScoped
public class CrearContratoBean {
    
    @EJB
    private ContratoServicio contratoServicio;
    private String numeroContrato;
    private Integer tipoContrato;
    private String rucCliente;
    private Integer sla;
    private Integer accountManagerAsignado;
    private Date fechaSuscripcion;
    private String objeto;
    
    public List<TipoContrato> tiposDeContrato() {
        return contratoServicio.cargarTiposContrato();
}

    public ContratoServicio getContratoServicio() {
        return contratoServicio;
    }

    public void setContratoServicio(ContratoServicio contratoServicio) {
        this.contratoServicio = contratoServicio;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public Integer getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(Integer tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public String getRucCliente() {
        return rucCliente;
    }

    public void setRucCliente(String rucCliente) {
        this.rucCliente = rucCliente;
    }

    public Integer getSla() {
        return sla;
    }

    public void setSla(Integer sla) {
        this.sla = sla;
    }

    public Integer getAccountManagerAsignado() {
        return accountManagerAsignado;
    }

    public void setAccountManagerAsignado(Integer accountManagerAsignado) {
        this.accountManagerAsignado = accountManagerAsignado;
    }

    public Date getFechaSuscripcion() {
        return fechaSuscripcion;
    }

    public void setFechaSuscripcion(Date fechaSuscripcion) {
        this.fechaSuscripcion = fechaSuscripcion;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }
}
