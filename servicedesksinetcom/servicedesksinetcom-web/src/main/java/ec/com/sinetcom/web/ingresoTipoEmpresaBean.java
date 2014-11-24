/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.TipoEmpresa;
import ec.com.sinetcom.servicios.TipoEmpresaServicio;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Andres
 */
@ManagedBean(name = "ingresoTipoEmpresaBean")
@ViewScoped
public class ingresoTipoEmpresaBean {
    
    @EJB
    private TipoEmpresaServicio tipoEmpresaServicio;
    
    private String tipo;
    private Boolean validez;
    private TipoEmpresa tipoEmpresaSeleccionada;
    
    private List<TipoEmpresa> tiposEmpresa;
    
    @PostConstruct
    public void init() {
        this.tiposEmpresa = tipoEmpresaServicio.cragarTiposEmpresa();
    }
    
    public void crearTipoEmpresa() {
        TipoEmpresa tipoEmpresa = new TipoEmpresa();
        
        tipoEmpresa.setTipo(tipo);
        tipoEmpresa.setValido(validez);
        
        tipoEmpresaServicio.crearTipoEmpresa(tipoEmpresa);
    }    

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getValidez() {
        return validez;
    }

    public void setValidez(Boolean validez) {
        this.validez = validez;
    }    

    public TipoEmpresa getTipoEmpresaSeleccionada() {
        return tipoEmpresaSeleccionada;
    }

    public void setTipoEmpresaSeleccionada(TipoEmpresa tipoEmpresaSeleccionada) {
        this.tipoEmpresaSeleccionada = tipoEmpresaSeleccionada;
    }

    public List<TipoEmpresa> getTiposEmpresa() {
        return tiposEmpresa;
    }

    public void setTiposEmpresa(List<TipoEmpresa> tiposEmpresa) {
        this.tiposEmpresa = tiposEmpresa;
    }
}
