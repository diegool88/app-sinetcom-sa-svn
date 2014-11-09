/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.servicios.ContratoServicio;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "slaYTipoDeDisponibilidadBean")
@ViewScoped
public class SLAsYTipoDeDisponibilidadBean implements Serializable{
    
    @EJB
    private ContratoServicio contratoServicio;
    
    @PostConstruct
    public void doInit(){
        
    }
}
