/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.webutil;

import javax.annotation.PostConstruct;

/**
 *
 * @author diegoflores
 */
public class BotonesTickets {
    
    private Boolean desactivarCerrarTicket;
    private Boolean desactivarAsignarTicket;
    private Boolean desactivarPonerEnPendiente;
    private Boolean desactivarAgregarArticulo;
    private Boolean desactivarReAbrirCaso;
    private Boolean desactivarPrimerContacto;
    private Boolean desactivarHojaDeServicio;
    private Boolean desactivarDescargarHojaDeServicio;
    
    @PostConstruct
    public void doInit(){
        this.sinSeleccion();
    }
    
    public void sinSeleccion(){
        this.resetear();
        this.desactivarAsignarTicket = Boolean.TRUE;
        this.desactivarCerrarTicket = Boolean.TRUE;
        this.desactivarPonerEnPendiente = Boolean.TRUE;
        this.desactivarAgregarArticulo = Boolean.TRUE;
        this.desactivarReAbrirCaso = Boolean.TRUE;
        this.desactivarPrimerContacto = Boolean.TRUE;
        this.desactivarHojaDeServicio = Boolean.TRUE;
        this.desactivarDescargarHojaDeServicio = Boolean.TRUE;
    }
    
    public void seleccionadoUno(){
        this.resetear();
    }

    public void resetear(){
        this.desactivarAsignarTicket = Boolean.FALSE;
        this.desactivarCerrarTicket = Boolean.FALSE;
        this.desactivarPonerEnPendiente = Boolean.FALSE;
        this.desactivarAgregarArticulo = Boolean.FALSE;
        this.desactivarReAbrirCaso = Boolean.TRUE;
        this.desactivarPrimerContacto = Boolean.TRUE;
        this.desactivarHojaDeServicio = Boolean.FALSE;
        this.desactivarDescargarHojaDeServicio = Boolean.TRUE;
        
    }
    
    public void mostrarReAbrirCaso(){
        this.desactivarReAbrirCaso = Boolean.FALSE;
        this.desactivarPonerEnPendiente = Boolean.TRUE;
    }
    
    public void mostrarPrimerContacto(){
        this.desactivarPrimerContacto = Boolean.FALSE;
    }
    
    public void desactivarHojaDeServicio(){
        this.desactivarHojaDeServicio = Boolean.TRUE;
    }
    
    public void mostrarDescargarHojaDeServicio(){
        this.desactivarDescargarHojaDeServicio = Boolean.FALSE;
    }
    
    public Boolean getDesactivarCerrarTicket() {
        return desactivarCerrarTicket;
    }

    public void setDesactivarCerrarTicket(Boolean desactivarCerrarTicket) {
        this.desactivarCerrarTicket = desactivarCerrarTicket;
    }

    public Boolean getDesactivarAsignarTicket() {
        return desactivarAsignarTicket;
    }

    public void setDesactivarAsignarTicket(Boolean desactivarAsignarTicket) {
        this.desactivarAsignarTicket = desactivarAsignarTicket;
    }

    public Boolean getDesactivarPonerEnPendiente() {
        return desactivarPonerEnPendiente;
    }

    public void setDesactivarPonerEnPendiente(Boolean desactivarPonerEnPendiente) {
        this.desactivarPonerEnPendiente = desactivarPonerEnPendiente;
    }

    public Boolean getDesactivarAgregarArticulo() {
        return desactivarAgregarArticulo;
    }

    public void setDesactivarAgregarArticulo(Boolean desactivarAgregarArticulo) {
        this.desactivarAgregarArticulo = desactivarAgregarArticulo;
    }

    public Boolean getDesactivarReAbrirCaso() {
        return desactivarReAbrirCaso;
    }

    public void setDesactivarReAbrirCaso(Boolean desactivarReAbrirCaso) {
        this.desactivarReAbrirCaso = desactivarReAbrirCaso;
    }

    public Boolean getDesactivarPrimerContacto() {
        return desactivarPrimerContacto;
    }

    public void setDesactivarPrimerContacto(Boolean desactivarPrimerContacto) {
        this.desactivarPrimerContacto = desactivarPrimerContacto;
    }

    public Boolean getDesactivarHojaDeServicio() {
        return desactivarHojaDeServicio;
    }

    public void setDesactivarHojaDeServicio(Boolean desactivarHojaDeServicio) {
        this.desactivarHojaDeServicio = desactivarHojaDeServicio;
    }

    public Boolean getDesactivarDescargarHojaDeServicio() {
        return desactivarDescargarHojaDeServicio;
    }

    public void setDesactivarDescargarHojaDeServicio(Boolean desactivarDescargarHojaDeServicio) {
        this.desactivarDescargarHojaDeServicio = desactivarDescargarHojaDeServicio;
    }
    
    
}
