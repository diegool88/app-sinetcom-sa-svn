/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.TipoDisponibilidad;
import ec.com.sinetcom.servicios.TipoDisponibilidadServicio;
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
@ManagedBean(name = "ingresoTipoDisponibilidadBean")
@ViewScoped
public class ingresoTipoDisponibilidadBean implements Serializable{
    
    @EJB
    private TipoDisponibilidadServicio tipoDisponibilidadServicio;
    
    private String disponibilidad;
    private Boolean estado;
    private TipoDisponibilidad tipoDisponibilidadSeleccionada;
    
    private List<TipoDisponibilidad> tiposDisponibilidad;
    
    @PostConstruct
    public void init() {
        this.tiposDisponibilidad = tipoDisponibilidadServicio.cargarTiposDisponibilidad();
    }
    
    public void crearTipoDisponibilidad() {
        TipoDisponibilidad tipoDisponibilidad = new TipoDisponibilidad();
        
        tipoDisponibilidad.setDisponibilidad(disponibilidad);
        tipoDisponibilidad.setValido(estado);
        
        if(tipoDisponibilidadServicio.crearTipoDisponibilidad(tipoDisponibilidad)){
            Mensajes.mostrarMensajeInformativo("Tipo de Disponibilidad creado exitosamente!");
        }else{
            Mensajes.mostrarMensajeDeError("Error interno, Tipo de disponibilidad no se pudo crear!");
        }
        this.tiposDisponibilidad = tipoDisponibilidadServicio.cargarTiposDisponibilidad();
    }
    
    public void eliminarTipoDisponibilidad() {
        if(tipoDisponibilidadServicio.eliminarTipoDisp(tipoDisponibilidadSeleccionada.getId())){
            Mensajes.mostrarMensajeInformativo("Tipo de Disponibilidad eliminada exitosamente!");
        }else{
            Mensajes.mostrarMensajeDeError("Error interno, Tipo de disponibilidad no se pudo eliminar!");
        }
        this.tiposDisponibilidad = tipoDisponibilidadServicio.cargarTiposDisponibilidad();
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }        

    public TipoDisponibilidad getTipoDisponibilidadSeleccionada() {
        return tipoDisponibilidadSeleccionada;
    }

    public void setTipoDisponibilidadSeleccionada(TipoDisponibilidad tipoDisponibilidadSeleccionada) {
        this.tipoDisponibilidadSeleccionada = tipoDisponibilidadSeleccionada;
    }

    public List<TipoDisponibilidad> getTiposDisponibilidad() {
        return tiposDisponibilidad;
    }

    public void setTiposDisponibilidad(List<TipoDisponibilidad> tiposDisponibilidad) {
        this.tiposDisponibilidad = tiposDisponibilidad;
    }
}
