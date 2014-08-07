/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.HistorialDeTicketFacade;
import ec.com.sinetcom.dao.TicketFacade;
import ec.com.sinetcom.orm.Ticket;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author diegoflores
 */
@Stateless
@LocalBean
public class TicketServicio {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private TicketFacade ticketFacade;
    
    @EJB
    private HistorialDeTicketFacade historialDeTicketFacade;
    
    /**
     * Servicio que permite crear un nuevo ticket
     */
    public boolean crearNuevoTicket(Ticket ticket){
        
        try{
            this.ticketFacade.create(ticket);
        }catch(Exception e){
            return false;
        }
        
        return true;
    }
    
    /**
     * Servicio que permite verificar que se hayan cumplido los SLAs (En texto y en boolean)
     */
    public Object verificarSLAdeTicket(Ticket ticket, boolean enTexto){
        
        StringBuilder respuesta = new StringBuilder();
        
        String prioridadTicket = ticket.getPrioridadTicketcodigo().getNombre().toLowerCase().trim();
        
        int tiempoDeRespuestaEsperado = 0;
        
        int tiempoDeResolucionEsperado = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoDeSolucion();
        
        if(prioridadTicket.contains("alta")){
            tiempoDeRespuestaEsperado = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoRespuestaPrioridadAlta();
        }else if(prioridadTicket.contains("media")){
            tiempoDeRespuestaEsperado = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoRespuestaPrioridadMedia();
        }else if(prioridadTicket.contains("baja")){
            tiempoDeRespuestaEsperado = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoRespuestaPrioridadBaja();
        }
        
        //En este segmento de código se verifica el tiempo de respuesta inicial
        respuesta.append("Tiempo de Respuesta Inicial\n");
        respuesta.append("Tiempo esperado: ").append(tiempoDeRespuestaEsperado).append("h\n");
        Date fechaDeCreacion = ticket.getFechaDeCreacion();
        Date fechaDePrimerContacto = this.historialDeTicketFacade.obtenerFechaDePrimerContactoDeTicket(ticket);
        long duracion1 = fechaDeCreacion.getTime() - fechaDePrimerContacto.getTime();
        long diferenciaEnHoras1 = TimeUnit.MILLISECONDS.toHours(duracion1);
        respuesta.append("Tiempo real: ").append(diferenciaEnHoras1).append("h\n\n");
        
        //En este segmento de código se verifica el tiempo de resolución del caso
        respuesta.append("Tiempo de Finalización de Ticket\n");
        respuesta.append("Tiempo esperado: ").append(tiempoDeResolucionEsperado).append("h\n");
        long duracion2 = ticket.getFechaDeCreacion().getTime() - ticket.getFechaDeCierre().getTime();
        long diferenciaEnHoras2 = TimeUnit.MILLISECONDS.toHours(duracion2);
        respuesta.append("Tiempo real: ").append(diferenciaEnHoras2).append("h\n");
        
        if(enTexto){
            return respuesta.toString();
        }else{
            return diferenciaEnHoras1 <= tiempoDeRespuestaEsperado && diferenciaEnHoras2 <= tiempoDeResolucionEsperado;
        }
            
        
    }
    
}
