/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.configuracion.TareaTicketInfo;
import ec.com.sinetcom.servicios.TicketNotificadorServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "listaTareasProgramadasBean")
@ViewScoped
public class ListaTareasProgramadasBean implements Serializable {

    @EJB
    private TicketNotificadorServicio notificadorServicio;
    //Lista de tareas programadas ejecutándose en el servidor
    private List<TareaTicketInfo> listaDeTareas = null;
    //Selección de tarea
    private TareaTicketInfo tareaInfoSeleccionada;

    @PostConstruct
    public void doInit() {
        this.listaDeTareas = this.notificadorServicio.getJobList();
    }

    public List<TareaTicketInfo> getListaDeTareas() {
        return listaDeTareas;
    }

    public TareaTicketInfo getTareaInfoSeleccionada() {
        return tareaInfoSeleccionada;
    }

    public void setTareaInfoSeleccionada(TareaTicketInfo tareaInfoSeleccionada) {
        this.tareaInfoSeleccionada = tareaInfoSeleccionada;
    }
    
    public void tareaSelecionada(SelectEvent event){
        this.tareaInfoSeleccionada = (TareaTicketInfo)event.getObject();
    }
    
    public void eliminarTarea(ActionEvent event) {
        if (this.tareaInfoSeleccionada != null) {
            this.notificadorServicio.deleteJob(this.tareaInfoSeleccionada);
            this.listaDeTareas = this.notificadorServicio.getJobList();
        }
    }
   
    
}
