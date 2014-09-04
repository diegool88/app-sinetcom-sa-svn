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
    }
    
    public void seleccionadoUno(){
        this.resetear();
    }

    public void resetear(){
        this.desactivarAsignarTicket = Boolean.FALSE;
        this.desactivarCerrarTicket = Boolean.FALSE;
        this.desactivarPonerEnPendiente = Boolean.FALSE;
        this.desactivarAgregarArticulo = Boolean.FALSE;
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
    
    
}
