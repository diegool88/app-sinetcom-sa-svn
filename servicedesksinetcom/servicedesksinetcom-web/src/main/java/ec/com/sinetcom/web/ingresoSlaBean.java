/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.Sla;
import ec.com.sinetcom.orm.TipoDisponibilidad;
import ec.com.sinetcom.servicios.SlaServicio;
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
@ManagedBean(name = "ingresoSlaBean")
@ViewScoped
public class ingresoSlaBean implements Serializable{
    
    @EJB
    private SlaServicio slaServicio;
    
    private Integer numSla;
    private Integer tiempoRespuestaPAlta;
    private Integer tiempoRespuestaPMedia;
    private Integer timepoRespuestaPBaja;
    private Integer tiempoEscalacion;
    private Integer tiempoActualizacionEscalacion;
    private Integer tiempoRespuestaEscalacion;
    private Integer tiempoSolucion;
    private Sla slaSeleccionado;
    
    private List<Sla> slas;
    private List<TipoDisponibilidad> tiposDisponibilidad;
    
    @PostConstruct
    public void init() {
        this.slas = slaServicio.cragarSlas();
        this.tiposDisponibilidad = slaServicio.cragarTiposDisponibilidad();
    }
    
    public void crearSla() {
        Sla sla = new Sla();
        
        sla.setTipoDisponibilidadid(slaServicio.recuperarTipoDisponibilidad(numSla));
        sla.setTiempoRespuestaPrioridadAlta(tiempoRespuestaPAlta);
        sla.setTiempoRespuestaPrioridadMedia(tiempoRespuestaPMedia);
        sla.setTiempoRespuestaPrioridadBaja(timepoRespuestaPBaja);
        sla.setTiempoDeEscalacion(tiempoEscalacion);
        sla.setTiempoDeActualizacionDeEscalacion(tiempoActualizacionEscalacion);
        sla.setTiempoDeRespuestaDeEscalacion(tiempoRespuestaEscalacion);
        sla.setTiempoDeSolucion(tiempoSolucion);
        
        slaServicio.crearSLA(sla);
        this.slas = slaServicio.cragarSlas();
    }
    
    public void eliminarSla() {
        slaServicio.eliminarSLA(slaSeleccionado.getId());
        this.slas = slaServicio.cragarSlas();
    }

    public Integer getNumSla() {
        return numSla;
    }

    public void setNumSla(Integer numSla) {
        this.numSla = numSla;
    }

    public Integer getTiempoRespuestaPAlta() {
        return tiempoRespuestaPAlta;
    }

    public void setTiempoRespuestaPAlta(Integer tiempoRespuestaPAlta) {
        this.tiempoRespuestaPAlta = tiempoRespuestaPAlta;
    }  

    public Integer getTiempoRespuestaPMedia() {
        return tiempoRespuestaPMedia;
    }

    public void setTiempoRespuestaPMedia(Integer tiempoRespuestaPMedia) {
        this.tiempoRespuestaPMedia = tiempoRespuestaPMedia;
    }

    public Integer getTimepoRespuestaPBaja() {
        return timepoRespuestaPBaja;
    }

    public void setTimepoRespuestaPBaja(Integer timepoRespuestaPBaja) {
        this.timepoRespuestaPBaja = timepoRespuestaPBaja;
    }   

    public Integer getTiempoEscalacion() {
        return tiempoEscalacion;
    }

    public void setTiempoEscalacion(Integer tiempoEscalacion) {
        this.tiempoEscalacion = tiempoEscalacion;
    }

    public Integer getTiempoActualizacionEscalacion() {
        return tiempoActualizacionEscalacion;
    }

    public void setTiempoActualizacionEscalacion(Integer tiempoActualizacionEscalacion) {
        this.tiempoActualizacionEscalacion = tiempoActualizacionEscalacion;
    }

    public Integer getTiempoRespuestaEscalacion() {
        return tiempoRespuestaEscalacion;
    }

    public void setTiempoRespuestaEscalacion(Integer tiempoRespuestaEscalacion) {
        this.tiempoRespuestaEscalacion = tiempoRespuestaEscalacion;
    }    

    public Integer getTiempoSolucion() {
        return tiempoSolucion;
    }

    public void setTiempoSolucion(Integer tiempoSolucion) {
        this.tiempoSolucion = tiempoSolucion;
    }

    public Sla getSlaSeleccionado() {
        return slaSeleccionado;
    }

    public void setSlaSeleccionado(Sla slaSeleccionado) {
        this.slaSeleccionado = slaSeleccionado;
    }

    public List<Sla> getSlas() {
        return slas;
    }

    public void setSlas(List<Sla> slas) {
        this.slas = slas;
    }

    public List<TipoDisponibilidad> getTiposDisponibilidad() {
        return tiposDisponibilidad;
    }

    public void setTiposDisponibilidad(List<TipoDisponibilidad> tiposDisponibilidad) {
        this.tiposDisponibilidad = tiposDisponibilidad;
    }            
}
