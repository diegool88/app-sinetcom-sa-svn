/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.TipoContrato;
import ec.com.sinetcom.servicios.TipoContratoServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Andres
 */
@ManagedBean(name = "ingresoTipoContratoBean")
@ViewScoped
public class ingresoTipoContratoBean implements Serializable{
    
    @EJB
    private TipoContratoServicio tipoContratoServicio;
    
    private String tipo;
    private Boolean valides;
    private TipoContrato tipoContratoSeleccionado;
    
    private List<TipoContrato> tiposContrato;
    
    @PostConstruct
    public void init() {
        this.tiposContrato = tipoContratoServicio.cargarTiposContactos();
    }
    
    public void crearTipoContrato() {
        TipoContrato tipocontrato = new TipoContrato();
        
        tipocontrato.setTipo(tipo);
        tipocontrato.setValido(valides);
        
        if(tipoContratoServicio.crearTipoContrato(tipocontrato)){
            Mensajes.mostrarMensajeInformativo("Tipo Contrato creado exitosamente!");
        }else{
            Mensajes.mostrarMensajeDeError("Error interno, Tipo Contrato no se pudo crear!");
        }
        this.tiposContrato = tipoContratoServicio.cargarTiposContactos();
                
    }
    
    public void eliminarTipo() {        
        if(tipoContratoServicio.eliminarTipoContrato(tipoContratoSeleccionado.getId())){
            Mensajes.mostrarMensajeInformativo("Tipo Contrato eliminado exitosamente!");
        }else{
            Mensajes.mostrarMensajeDeError("Error interno, Tipo Contrato no se pudo eliminar!");
        }  
        this.tiposContrato = tipoContratoServicio.cargarTiposContactos();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getValides() {
        return valides;
    }

    public void setValides(Boolean valides) {
        this.valides = valides;
    }        

    public TipoContrato getTipoContratoSeleccionado() {
        return tipoContratoSeleccionado;
    }

    public void setTipoContratoSeleccionado(TipoContrato tipoContratoSeleccionado) {
        this.tipoContratoSeleccionado = tipoContratoSeleccionado;
    }

    public List<TipoContrato> getTiposContrato() {
        return tiposContrato;
    }

    public void setTiposContrato(List<TipoContrato> tiposContrato) {
        this.tiposContrato = tiposContrato;
    }
}
