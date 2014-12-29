/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.DatosSinetcom;
import ec.com.sinetcom.servicios.InformacionSinetcomServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "informacionSinetcomBean")
@ViewScoped
public class InformacionSinetcomBean implements Serializable{
    @EJB
    private InformacionSinetcomServicio informacionSinetcomServicio;
    private DatosSinetcom datosSinetcom;
    
    @PostConstruct
    public void doInit(){
        this.datosSinetcom = this.informacionSinetcomServicio.cargarInformacionDeSinetcom();
    }
    
    public void actualizarInformacionDeSinetcom(ActionEvent event){
        if(this.informacionSinetcomServicio.actualizarInformacionDeSinetcom(this.datosSinetcom)){
            Mensajes.mostrarMensajeInformativo("Información actualizada con éxito!");
        }else{
            Mensajes.mostrarMensajeDeError("No se pudo actualizar la información debido a un error interno!");
        }
    }

    public DatosSinetcom getDatosSinetcom() {
        return datosSinetcom;
    }

    public void setDatosSinetcom(DatosSinetcom datosSinetcom) {
        this.datosSinetcom = datosSinetcom;
    }
    
}
