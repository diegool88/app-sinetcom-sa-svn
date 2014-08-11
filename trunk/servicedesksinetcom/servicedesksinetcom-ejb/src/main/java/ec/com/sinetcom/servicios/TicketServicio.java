/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.configuracion.UtilidadDeCorreoElectronico;
import ec.com.sinetcom.dao.ArticuloFacade;
import ec.com.sinetcom.dao.ContactoFacade;
import ec.com.sinetcom.dao.EventoTicketFacade;
import ec.com.sinetcom.dao.HistorialDeTicketFacade;
import ec.com.sinetcom.dao.TicketFacade;
import ec.com.sinetcom.dao.UsuarioFacade;
import ec.com.sinetcom.orm.Articulo;
import ec.com.sinetcom.orm.Cola;
import ec.com.sinetcom.orm.Competencias;
import ec.com.sinetcom.orm.Contacto;
import ec.com.sinetcom.orm.EstadoTicket;
import ec.com.sinetcom.orm.HistorialDeTicket;
import ec.com.sinetcom.orm.ItemProducto;
import ec.com.sinetcom.orm.Ticket;
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.quartz.EnviarNotificacionJob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import org.quartz.JobDetail;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleTrigger.*;
import org.quartz.JobDataMap;
import org.quartz.Trigger;

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
    
    @EJB
    private ContactoFacade contactoFacade;
    
    
    /**
     * Servicio que permite crear un nuevo ticket
     * @param ticket
     * @return 
     */
    public boolean crearNuevoTicket(Ticket ticket){
        
        try{
            
            //Se determina el tiempo de vida del ticket según el SLA
            int tiempoDeVida = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoDeSolucion();
            Calendar c = Calendar.getInstance();
            c.add(Calendar.HOUR, tiempoDeVida);
            ticket.setTiempoDeVida(c.getTime());
            //Se indica la fecha actual como fecha de creacion
            ticket.setFechaDeCreacion(new Date());
            //Se determina el tiempo de actualizacion segun el SLA
            int tiempoDeActualizacion = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoDeActualizacionDeEscalacion();
            c = Calendar.getInstance();
            c.add(Calendar.HOUR, tiempoDeActualizacion);
            ticket.setFechaDeProximaActualizacion(c.getTime());
            //Creamos el ticket
            this.ticketFacade.create(ticket);
            
            //Se crea una tarea de notificacion para cuando se termine el tiempo de actualizacion
            JobDataMap dataMap = new JobDataMap();
            dataMap.put("ticket", ticket);
            JobDetail tarea = newJob(EnviarNotificacionJob.class)
                    .withIdentity("actualizar" + ticket.getTicketNumber(), "tickets")
                    .usingJobData(dataMap)
                    .build();
            Calendar c_notif = c.getInstance();
            c_notif.add(Calendar.MINUTE, -15);
            Trigger tareaTrigger = newTrigger()
                    .withIdentity("t_actualizar" + ticket.getTicketNumber(), "tickets")
                    .startAt(c_notif.getTime())
                    .endAt(c.getTime())
                    .build();
            
            
            HistorialDeTicket historialDeTicket = new HistorialDeTicket();
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(0));
            historialDeTicket.setFechaDelEvento(new Date());
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket)+1);
            historialDeTicket.setUsuarioid(ticket.getUsuarioidcreador());
            historialDeTicket.setTicketticketNumber(ticket);
            this.historialDeTicketFacade.create(historialDeTicket);
            UtilidadDeCorreoElectronico utilidadDeCorreoElectronico = new UtilidadDeCorreoElectronico();
            
            //Se envia el correo electrónico notificando a todos los interesados 
            utilidadDeCorreoElectronico.enviarCorreoSimple(utilidadDeCorreoElectronico.getSMTPEmail(), "soporte@sinetcom.com.ec" , "Nueva incidencia - Caso# " + ticket.getTicketNumber(), "", contactosDeTicket(ticket));
            
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
    
    /**
     * Función que permite subir la hoja de servicio como un archivo adjunto
     * @param archivo
     * @return 
     */
    public boolean subirHojaDeServicioDeTicket(byte[] archivo, Ticket ticket){
        try{
            ticket.setHojaDeServicio(archivo);
            this.ticketFacade.edit(ticket);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Función que permite obtener todos los contactos involucrados en el ticket
     * @param ticket
     * @return 
     */
    private String[] contactosDeTicket(Ticket ticket){
        
        //Se envía un correo a todos los que tienen la competencia respectiva al caso
        Competencias competencias = ticket.getColaid().getCompetenciasid();
        List<Usuario> usuarios = this.usuarioFacade.obtenerUsuariosPorCompetencias(competencias);
        String[] correosCC = null;
        for(int i = 0 ; i < usuarios.size() ; i++){
            correosCC[i] = usuarios.get(i).getCorreoElectronico();
        }

        //Se añade a las persona que esten involucradas en el caso
        List<Contacto> contactos = this.contactoFacade.obtenerContactosDeCliente(ticket.getClienteEmpresaruc());
        int j = 0;
        for(int i = correosCC.length - 1; i < correosCC.length + contactos.size() ; i++){
            correosCC[i] = contactos.get(j++).getCorreoElectronico();
        }
        

        //Se agrega a la presidencia en copia
        correosCC[correosCC.length] = "jorge.yanez@sinetcom.com.ec";
        //Agregando al Helpdesk en copia
        correosCC[correosCC.length] = "soporte@sinetcom.com.ec";
        
        return correosCC;
    }
    
}
