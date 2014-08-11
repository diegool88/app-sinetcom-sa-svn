/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.configuracion.UtilidadDeCorreoElectronico;
import ec.com.sinetcom.dao.ArticuloFacade;
import ec.com.sinetcom.dao.EventoTicketFacade;
import ec.com.sinetcom.dao.HistorialDeTicketFacade;
import ec.com.sinetcom.dao.TicketFacade;
import ec.com.sinetcom.dao.UsuarioFacade;
import ec.com.sinetcom.orm.Articulo;
import ec.com.sinetcom.orm.Cola;
import ec.com.sinetcom.orm.Competencias;
import ec.com.sinetcom.orm.EstadoTicket;
import ec.com.sinetcom.orm.HistorialDeTicket;
import ec.com.sinetcom.orm.ItemProducto;
import ec.com.sinetcom.orm.Ticket;
import ec.com.sinetcom.orm.Usuario;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
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
    
    @EJB
    private ArticuloFacade articuloFacade;
    
    @EJB
    private EventoTicketFacade eventoTicketFacade;
    
    @EJB
    private UsuarioFacade usuarioFacade;
    
    /**
     * Servicio que permite crear un nuevo ticket
     * @param ticket
     * @return 
     */
    public boolean crearNuevoTicket(Ticket ticket){
        
        try{
            this.ticketFacade.create(ticket);
            HistorialDeTicket historialDeTicket = new HistorialDeTicket();
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(0));
            historialDeTicket.setFechaDelEvento(new Date());
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket)+1);
            historialDeTicket.setUsuarioid(ticket.getUsuarioidcreador());
            historialDeTicket.setTicketticketNumber(ticket);
            this.historialDeTicketFacade.create(historialDeTicket);
            UtilidadDeCorreoElectronico utilidadDeCorreoElectronico = new UtilidadDeCorreoElectronico();
            Competencias competencias = ticket.getColaid().getCompetenciasid();
            List<Usuario> usuarios = this.usuarioFacade.obtenerUsuariosPorCompetencias(competencias);
            String[] correosCC = null;
            for(int i = 0 ; i < usuarios.size() ; i++){
                correosCC[i] = usuarios.get(i).getCorreoElectronico();
            }
            utilidadDeCorreoElectronico.enviarCorreoSimple(utilidadDeCorreoElectronico.getSMTPEmail(), "soporte@sinetcom.com.ec" , "Nueva incidencia - Caso# " + ticket.getTicketNumber(), "", correosCC);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    /**
     * Servicio que permite verificar el permiso de creacion de ticket por parte del cliente
     * @param usuario
     * @param itemProducto
     * @return 
     */
    public boolean permisoDeCreacionDeTicket(Usuario usuario, ItemProducto itemProducto){
        Calendar c = Calendar.getInstance();
        c.setTime(itemProducto.getContratonumero().getFechaDeSuscripcion());
        c.add(Calendar.YEAR, itemProducto.getContratonumero().getTiempoDeValidez());
        
        Date fechaDeVencimientoDeContrato = c.getTime();
        
        //Verificamos que el usuario pueda crear el ticket de soporte validando la fecha de vencimiento del contrato y que su cuenta se encuentre activa
        if(usuario.getActivo() && fechaDeVencimientoDeContrato.compareTo(Calendar.getInstance().getTime()) <= 0){
            return true;
        }else if(usuario.getActivo() && fechaDeVencimientoDeContrato.compareTo(Calendar.getInstance().getTime()) > 0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Servicio que permite asignar ticket a un ingeniero (Propietario del ticket)
     * @param ticket
     * @param usuarioPropietario
     * @param usuarioAsignante
     * @return 
     */
    public boolean asignarTicketAPropietario(Ticket ticket, Usuario usuarioPropietario, Usuario usuarioAsignante){
        try{
            ticket.setUsuarioidpropietario(usuarioPropietario);
            this.ticketFacade.edit(ticket);
            HistorialDeTicket historialDeTicket = new HistorialDeTicket();
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(4));
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket));
            historialDeTicket.setTicketticketNumber(ticket);
            historialDeTicket.setFechaDelEvento(new Date());
            historialDeTicket.setUsuarioid(usuarioAsignante);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    /**
     * Servicio que permite actualizar el estado de ticket
     * @param ticket
     * @param estadoTicket
     * @param usuario
     * @return 
     */
    public boolean cambiarEstadoDeTicket(Ticket ticket, EstadoTicket estadoTicket, Usuario usuario){
        try{
            ticket.setEstadoTicketcodigo(estadoTicket);
            this.ticketFacade.edit(ticket);
            HistorialDeTicket historialDeTicket = new HistorialDeTicket();
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(2));
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket));
            historialDeTicket.setTicketticketNumber(ticket);
            historialDeTicket.setFechaDelEvento(new Date());
            historialDeTicket.setUsuarioid(usuario);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    /**
     * Servicio que permite actualizar la cola del ticket
     * @param ticket
     * @param cola
     * @param usuario
     * @return 
     */
    public boolean cambiarColaDeTicket(Ticket ticket, Cola cola, Usuario usuario){
        try{
            ticket.setColaid(cola);
            this.ticketFacade.edit(ticket);
            HistorialDeTicket historialDeTicket = new HistorialDeTicket();
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(3));
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket));
            historialDeTicket.setTicketticketNumber(ticket);
            historialDeTicket.setFechaDelEvento(new Date());
            historialDeTicket.setUsuarioid(usuario);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    /**
     * Actualizar Informacion del ticket (Ingresar un nuevo articulo)
     * @param ticket
     * @param articulo
     * @param usuario
     * @return 
     */
    public boolean ingresarNuevoArticuloAlTicket(Ticket ticket, Articulo articulo, Usuario usuario){
        try{
            articulo.setTicketticketNumber(ticket);
            articulo.setFechaDeCreacion(new Date());
            articuloFacade.create(articulo);
            HistorialDeTicket historialDeTicket = new HistorialDeTicket();
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(1));
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket));
            historialDeTicket.setTicketticketNumber(ticket);
            historialDeTicket.setFechaDelEvento(new Date());
            historialDeTicket.setUsuarioid(usuario);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    /**
     * Servicio que permite verificar que se hayan cumplido los SLAs (En texto y en boolean)
     * @param ticket
     * @param enTexto
     * @return 
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
