/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.Ciudad;
import ec.com.sinetcom.orm.Provincia;
import ec.com.sinetcom.servicios.ContratoServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "provinciasYCiudadesBean")
@ViewScoped
public class ProvinciasYCiudadesBean implements Serializable{
    
    @EJB
    private ContratoServicio contratoServicio;
    
    private List<Provincia> provincias;
    private List<Ciudad> ciudades;
    private Ciudad nuevaCiudad;
    private Provincia nuevaProvincia;
    
    @PostConstruct
    public void doInit(){
        this.provincias = this.contratoServicio.cargarProvincias();
        this.ciudades = this.contratoServicio.cargarCiudades();
        this.nuevaCiudad = new Ciudad();
        this.nuevaProvincia = new Provincia();
    }
    
    public void crearProvincia(ActionEvent event){
        if(this.contratoServicio.crearProvincia(this.nuevaProvincia)){
            Mensajes.mostrarMensajeInformativo("Provincia creada exitosamente!");
        }else{
            Mensajes.mostrarMensajeDeError("Error interno, provincia no se pudo crear!");
        }
        this.provincias = this.contratoServicio.cargarProvincias();
    }
    
    public void crearCiudad(ActionEvent event){
        if(this.contratoServicio.crearCiudad(this.nuevaCiudad)){
            Mensajes.mostrarMensajeInformativo("Ciudad creada exitosamente!");
        }else{
            Mensajes.mostrarMensajeDeError("Error interno, ciudad no se pudo crear!");
        }
        this.ciudades = this.contratoServicio.cargarCiudades();
    }

    public List<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<Provincia> provincias) {
        this.provincias = provincias;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public Ciudad getNuevaCiudad() {
        return nuevaCiudad;
    }

    public void setNuevaCiudad(Ciudad nuevaCiudad) {
        this.nuevaCiudad = nuevaCiudad;
    }

    public Provincia getNuevaProvincia() {
        return nuevaProvincia;
    }

    public void setNuevaProvincia(Provincia nuevaProvincia) {
        this.nuevaProvincia = nuevaProvincia;
    }
    
    
}
