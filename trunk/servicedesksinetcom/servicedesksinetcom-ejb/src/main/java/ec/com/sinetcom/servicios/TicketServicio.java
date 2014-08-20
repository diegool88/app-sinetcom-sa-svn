/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import com.mchange.v1.util.ArrayUtils;
import ec.com.sinetcom.configuracion.UtilidadDeCorreoElectronico;
import ec.com.sinetcom.configuracion.UtilidadDeEmail;
import ec.com.sinetcom.dao.ArticuloFacade;
import ec.com.sinetcom.dao.ClienteEmpresaFacade;
import ec.com.sinetcom.dao.ColaFacade;
import ec.com.sinetcom.dao.ContactoFacade;
import ec.com.sinetcom.dao.EstadoTicketFacade;
import ec.com.sinetcom.dao.EventoTicketFacade;
import ec.com.sinetcom.dao.HistorialDeTicketFacade;
import ec.com.sinetcom.dao.ItemProductoFacade;
import ec.com.sinetcom.dao.PrioridadTicketFacade;
import ec.com.sinetcom.dao.ServicioTicketFacade;
import ec.com.sinetcom.dao.TicketFacade;
import ec.com.sinetcom.dao.UsuarioFacade;
import ec.com.sinetcom.orm.Articulo;
import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.Cola;
import ec.com.sinetcom.orm.Competencias;
import ec.com.sinetcom.orm.Contacto;
import ec.com.sinetcom.orm.EstadoTicket;
import ec.com.sinetcom.orm.HistorialDeTicket;
import ec.com.sinetcom.orm.ItemProducto;
import ec.com.sinetcom.orm.PrioridadTicket;
import ec.com.sinetcom.orm.ServicioTicket;
import ec.com.sinetcom.orm.Ticket;
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.quartz.EnviarNotificacionJob;
import java.util.ArrayList;
import java.util.Arrays;
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
    @EJB
    private ServicioTicketFacade servicioTicketFacade;
    @EJB
    private ColaFacade colaFacade;
    @EJB
    private PrioridadTicketFacade prioridadTicketFacade;
    @EJB
    private ClienteEmpresaFacade clienteEmpresaFacade;
    @EJB
    private ItemProductoFacade itemProductoFacade;
    @EJB
    private EstadoTicketFacade estadoTicketFacade;

    /**
     * Obtiene todas las colas
     *
     * @return
     */
    public List<Cola> obtenerTodasLasColas() {
        return this.colaFacade.findAll();
    }

    /**
     * Obtiene todas las prioridades de ticket
     *
     * @return
     */
    public List<PrioridadTicket> obtenerTodasLasPrioridades() {
        return this.prioridadTicketFacade.findAll();
    }

    /**
     * Obtiene todas los cliente
     *
     * @return
     */
    public List<ClienteEmpresa> obtenerTodosLosClientes() {
        return this.clienteEmpresaFacade.findAll();
    }

    /**
     * Obtener el cliente empresa del ruc (Converter)
     *
     * @param ruc
     * @return
     */
    public ClienteEmpresa obtenerClienteEmpresa(String ruc) {
        return this.clienteEmpresaFacade.find(ruc.trim());
    }

    /**
     * Obtener la cola por el id (Converter)
     *
     * @param id
     * @return
     */
    public Cola obtenerColaTicket(int id) {
        return this.colaFacade.find(id);
    }

    /**
     * Obtener el servicio por el codigo (Converter)
     *
     * @param codigo
     * @return
     */
    public ServicioTicket obtenerServicioTicket(int codigo) {
        return this.servicioTicketFacade.find(codigo);
    }

    /**
     * Obtener la prioridad por el codigo (Converter)
     *
     * @param codigo
     * @return
     */
    public PrioridadTicket obtenerPrioridadTicket(int codigo) {
        return this.prioridadTicketFacade.find(codigo);
    }

    /**
     * Obtener el producto por el serial (Converter)
     *
     * @param serial
     * @return
     */
    public ItemProducto obtenerItemProducto(String serial) {
        return this.itemProductoFacade.find(serial);
    }

    /**
     * Obtiene todos los servicios
     *
     * @return
     */
    public List<ServicioTicket> obtenerTodosLosServiciosDeTicket() {
        return this.servicioTicketFacade.findAll();
    }

    public List<ItemProducto> obtenerTodosLosProductosDeUnCliente(ClienteEmpresa clienteIDSeleccionado) {
        return clienteIDSeleccionado != null ? this.itemProductoFacade.obtenerTodosLosProductosDeCliente(clienteIDSeleccionado.getContratoList()) : null;
    }

    /**
     * Servicio que permite crear un nuevo ticket
     *
     * @param ticket
     * @return
     */
    public boolean crearNuevoTicket(Ticket ticket, Usuario usuario) {

        try {

            //Se determina el tiempo de vida del ticket según el SLA
            int tiempoDeVida = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoDeSolucion();
            Calendar c = Calendar.getInstance();
            c.add(Calendar.HOUR, tiempoDeVida);
            ticket.setTiempoDeVida(c.getTime());
            //Se indica la fecha máxima de cierre
            ticket.setFechaDeCierre(c.getTime());
            //Se indica la fecha actual como fecha de creacion
            ticket.setFechaDeCreacion(new Date());
            //Se determina el tiempo de actualizacion segun el SLA
            int tiempoDeActualizacion = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoDeActualizacionDeEscalacion();
            c = Calendar.getInstance();
            c.add(Calendar.HOUR, tiempoDeActualizacion);
            ticket.setFechaDeProximaActualizacion(c.getTime());
            //Se indica la persona que crea el ticket
            ticket.setUsuarioidcreador(usuario);
            //Se indica el usuario propietario por defecto y el responsable (Soporte HELPDESK)
            Usuario usuarioHelpdek = this.usuarioFacade.obtenerUsuarioPorCorreoElectronico("soporte@sinetcom.com.ec");
            ticket.setUsuarioidpropietario(usuarioHelpdek);
            ticket.setUsuarioidresponsable(usuarioHelpdek);
            //Se indica el estado actual del ticket
            EstadoTicket estadoNuevo = this.estadoTicketFacade.find(1);
            ticket.setEstadoTicketcodigo(estadoNuevo);
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
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(1));
            historialDeTicket.setFechaDelEvento(new Date());
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket) + 1);
            historialDeTicket.setUsuarioid(ticket.getUsuarioidcreador());
            historialDeTicket.setTicketticketNumber(ticket);
            this.historialDeTicketFacade.create(historialDeTicket);
            
            //Se envia el correo electrónico notificando a todos los interesados 
            //utilidadDeCorreoElectronico.enviarCorreoConAdjunto(utilidadDeCorreoElectronico.getSMTPEmail(), "soporte@sinetcom.com.ec", "Nueva incidencia - Caso# " + ticket.getTicketNumber(), crearCuerpoDeCorreoNuevoTicket(ticket, , true), null, adjunto, null);
            //utilidadDeCorreoElectronico.enviarCorreoSimple(utilidadDeCorreoElectronico.getSMTPEmail(), "soporte@sinetcom.com.ec", "Nueva incidencia - Caso# " + ticket.getTicketNumber(), "", contactosDeTicket(ticket));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Servicio que permite verificar el permiso de creacion de ticket por parte
     * del cliente
     *
     * @param usuario
     * @param itemProducto
     * @return
     */
    public boolean permisoDeCreacionDeTicket(Usuario usuario, ItemProducto itemProducto) {
        Calendar c = Calendar.getInstance();
        c.setTime(itemProducto.getContratonumero().getFechaDeSuscripcion());
        c.add(Calendar.YEAR, itemProducto.getContratonumero().getTiempoDeValidez());

        Date fechaDeVencimientoDeContrato = c.getTime();

        //Verificamos que el usuario pueda crear el ticket de soporte validando la fecha de vencimiento del contrato y que su cuenta se encuentre activa
        if (usuario.getActivo() && fechaDeVencimientoDeContrato.compareTo(Calendar.getInstance().getTime()) <= 0) {
            return true;
        } else if (usuario.getActivo() && fechaDeVencimientoDeContrato.compareTo(Calendar.getInstance().getTime()) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Servicio que permite asignar ticket a un ingeniero (Propietario del
     * ticket)
     *
     * @param ticket
     * @param usuarioPropietario
     * @param usuarioAsignante
     * @return
     */
    public boolean asignarTicketAPropietario(Ticket ticket, Usuario usuarioPropietario, Usuario usuarioAsignante) {
        try {
            ticket.setUsuarioidpropietario(usuarioPropietario);
            this.ticketFacade.edit(ticket);
            HistorialDeTicket historialDeTicket = new HistorialDeTicket();
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(4));
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket));
            historialDeTicket.setTicketticketNumber(ticket);
            historialDeTicket.setFechaDelEvento(new Date());
            historialDeTicket.setUsuarioid(usuarioAsignante);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Servicio que permite actualizar el estado de ticket
     *
     * @param ticket
     * @param estadoTicket
     * @param usuario
     * @return
     */
    public boolean cambiarEstadoDeTicket(Ticket ticket, EstadoTicket estadoTicket, Usuario usuario) {
        try {
            ticket.setEstadoTicketcodigo(estadoTicket);
            this.ticketFacade.edit(ticket);
            HistorialDeTicket historialDeTicket = new HistorialDeTicket();
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(2));
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket));
            historialDeTicket.setTicketticketNumber(ticket);
            historialDeTicket.setFechaDelEvento(new Date());
            historialDeTicket.setUsuarioid(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Servicio que permite actualizar la cola del ticket
     *
     * @param ticket
     * @param cola
     * @param usuario
     * @return
     */
    public boolean cambiarColaDeTicket(Ticket ticket, Cola cola, Usuario usuario) {
        try {
            ticket.setColaid(cola);
            this.ticketFacade.edit(ticket);
            HistorialDeTicket historialDeTicket = new HistorialDeTicket();
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(3));
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket));
            historialDeTicket.setTicketticketNumber(ticket);
            historialDeTicket.setFechaDelEvento(new Date());
            historialDeTicket.setUsuarioid(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Actualizar Informacion del ticket (Ingresar un nuevo articulo)
     *
     * @param ticket
     * @param articulo
     * @param usuario
     * @return
     */
    public boolean ingresarNuevoArticuloAlTicket(Ticket ticket, Articulo articulo, Usuario usuario) {
        try {
            articulo.setDe(usuario);
            articulo.setPara(ticket.getUsuarioidcreador());
            StringBuilder cc = new StringBuilder();
            List<String> emails = contactosDeTicket(ticket);
            int i = 0;
            for (String email : emails) {
                if (i++ < emails.size()) {
                    cc.append(email).append(";");
                }
            }
            articulo.setCopia(cc.toString());
            articulo.setTicketticketNumber(ticket);
            articulo.setFechaDeCreacion(new Date());
            articuloFacade.create(articulo);
            HistorialDeTicket historialDeTicket = new HistorialDeTicket();
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(1));
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket));
            historialDeTicket.setTicketticketNumber(ticket);
            historialDeTicket.setFechaDelEvento(new Date());
            historialDeTicket.setUsuarioid(usuario);
            
            //Se envía un correo por ticket nuevo
            UtilidadDeEmail utilidadDeCorreoElectronico = new UtilidadDeEmail();
            //Se envia el correo electrónico notificando a todos los interesados 
            utilidadDeCorreoElectronico.enviarMensajeConAdjunto(utilidadDeCorreoElectronico.getSMTPEmail(), "soporte@sinetcom.com.ec", "Nueva incidencia - Caso# " + ticket.getTicketNumber(), crearCuerpoDeCorreoNuevoTicket(ticket, articulo, false), contactosDeTicket(ticket) , articulo.getContenidoAdjunto() != null ? articulo.getContenidoAdjunto() : null, articulo.getContenidoAdjunto() != null ? ticket.getTicketNumber() + "_" + articulo.getId() + "." + articulo.getExtensionArchivo() : null);
            //Se envia un correo a todos los tecnicos de Sinetcom
            utilidadDeCorreoElectronico.enviarMensajeConAdjunto(utilidadDeCorreoElectronico.getSMTPEmail(), "soporte@sinetcom.com.ec", "Nueva incidencia - Caso# " + ticket.getTicketNumber(), crearCuerpoDeCorreoNuevoTicket(ticket, articulo, true), contactosDeTicket(ticket) , articulo.getContenidoAdjunto() != null ? articulo.getContenidoAdjunto() : null, articulo.getContenidoAdjunto() != null ? ticket.getTicketNumber() + "_" + articulo.getId() + "." + articulo.getExtensionArchivo() : null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Servicio que permite verificar que se hayan cumplido los SLAs (En texto y
     * en boolean)
     *
     * @param ticket
     * @param enTexto
     * @return
     */
    public Object verificarSLAdeTicket(Ticket ticket, boolean enTexto) {

        StringBuilder respuesta = new StringBuilder();

        String prioridadTicket = ticket.getPrioridadTicketcodigo().getNombre().toLowerCase().trim();

        int tiempoDeRespuestaEsperado = 0;

        int tiempoDeResolucionEsperado = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoDeSolucion();

        if (prioridadTicket.contains("alta")) {
            tiempoDeRespuestaEsperado = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoRespuestaPrioridadAlta();
        } else if (prioridadTicket.contains("media")) {
            tiempoDeRespuestaEsperado = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoRespuestaPrioridadMedia();
        } else if (prioridadTicket.contains("baja")) {
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

        if (enTexto) {
            return respuesta.toString();
        } else {
            return diferenciaEnHoras1 <= tiempoDeRespuestaEsperado && diferenciaEnHoras2 <= tiempoDeResolucionEsperado;
        }


    }

    /**
     * Función que permite subir la hoja de servicio como un archivo adjunto
     *
     * @param archivo
     * @return
     */
    public boolean subirHojaDeServicioDeTicket(byte[] archivo, Ticket ticket) {
        try {
            ticket.setHojaDeServicio(archivo);
            this.ticketFacade.edit(ticket);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Función que permite obtener todos los contactos involucrados en el ticket
     *
     * @param ticket
     * @return
     */
    private List<String> contactosDeTicket(Ticket ticket) {

        //Se inicializa el arreglo total
        List<String> ccTodos = new ArrayList<String>();
        //Se envía un correo a todos los que tienen la competencia respectiva al caso
        Competencias competencias = ticket.getColaid().getCompetenciasid();
        List<Usuario> usuarios = this.usuarioFacade.obtenerUsuariosPorCompetencias(competencias);
        
        //String[] correosCC1 = new String[usuarios == null ? 0 : usuarios.size()];
        if (usuarios != null) {
            for(Usuario usuarioContacto: usuarios){
                ccTodos.add(usuarioContacto.getCorreoElectronico());
            }
        }

        //Se añade a las persona que esten involucradas en el caso
        List<Contacto> contactos = this.contactoFacade.obtenerContactosDeCliente(ticket.getClienteEmpresaruc());
        if (contactos != null) {
            for(Contacto contactoEmpresa: contactos){
                ccTodos.add(contactoEmpresa.getCorreoElectronico());
            }
        }

        //Se agrega a la presidencia en copia
        ccTodos.add("jorge.yanez@sinetcom.com.ec");
        //Agregando al Helpdesk en copia
        ccTodos.add("soporte@sinetcom.com.ec");

        return ccTodos;
    }
    
    /**
     * Esta función permite crear el cuerpo del correo electrónico de un nuevo ticket
     * @param ticket
     * @param articulo
     * @return 
     */
    private String crearCuerpoDeCorreoNuevoTicket(Ticket ticket, Articulo articulo, boolean empresaDeSoporte){
        
        StringBuilder cuerpo = new StringBuilder();
        //Se indica los datos principales del ticket
        cuerpo.append("Le informamos que un nuevo ticket de soporte se ha creado:").append("\n");
        cuerpo.append("Ticket #: ").append(ticket.getTicketNumber()).append("\n");
        cuerpo.append("Creado por: ").append(ticket.getUsuarioidcreador().getNombre()).append(" ").append(ticket.getUsuarioidcreador().getApellido()).append("\n");
        cuerpo.append("Empresa: ").append(ticket.getClienteEmpresaruc().getNombreComercial()).append("\n");
        cuerpo.append("Fecha y Hora de Creación: ").append(ticket.getFechaDeCreacion().toString()).append("\n");
        cuerpo.append("Prioridad: ").append(ticket.getPrioridadTicketcodigo().getNombre()).append("\n");
        cuerpo.append("Servicio Solicitado: ").append(ticket.getServicioTicketcodigo().getNombre()).append("\n");
        cuerpo.append("Estado: ").append(ticket.getEstadoTicketcodigo().getNombre()).append("\n");
        cuerpo.append("Equipo: ").append(ticket.getItemProductonumeroSerial().getModeloProductoid().getLineaDeProductoid().getNombre()).append(ticket.getItemProductonumeroSerial().getModeloProductoid().getModelo()).append(" - S/N ").append(ticket.getItemProductonumeroSerial().getNumeroSerial()).append("\n");
        //Se agrega la informacion a la empresa de soporte
        if(empresaDeSoporte){
            cuerpo.append("SLA: ").append(ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTipoDisponibilidadid().getDisponibilidad()).append("\n");
            Calendar c = Calendar.getInstance();
            switch(ticket.getPrioridadTicketcodigo().getValor())
            {
                case 1:
                    c.add(Calendar.HOUR, ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoRespuestaPrioridadAlta());
                    cuerpo.append("Tiempo de Primer Contacto: ").append(c.getTime()).append("\n");
                    break;
                case 2:
                    c.add(Calendar.HOUR, ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoRespuestaPrioridadMedia());
                    cuerpo.append("Tiempo de Primer Contacto: ").append(c.getTime()).append("\n");
                    break;
                case 3:
                    c.add(Calendar.HOUR, ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoRespuestaPrioridadBaja());
                    cuerpo.append("Tiempo de Primer Contacto: ").append(c.getTime()).append("\n");
                    break;
            }
            Calendar g = Calendar.getInstance();
            g.add(Calendar.YEAR, ticket.getItemProductonumeroSerial().getContratonumero().getGarantiaTecnica());
            cuerpo.append("Tiempo de resolución: ").append(ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoDeSolucion()).append(" h\n");
            cuerpo.append("Repuestos: ").append(ticket.getItemProductonumeroSerial().getContratonumero().getIncluyeRepuestos() ? "Incluye" : "No Incluye").append("\n");
            cuerpo.append("Garantía Técnica: ").append(g.getTime().compareTo(new Date()) < 0 ? "Vencido" : "En vigencia").append(" Fecha: ").append(g.getTime()).append("\n");
        }
        //Se agrega la información del Articulo
        cuerpo.append("---------------------------------------------------").append("\n");
        cuerpo.append("Asunto: ").append(ticket.getTitulo()).append("\n");
        cuerpo.append("Descripción del problema: ").append(articulo.getCuerpo()).append("\n");
        
        return cuerpo.toString();
    }
    
    /**
     * Función que permite crear el cuerpo del correo electronico de un nuevo articulo
     * @param articulo
     * @return 
     */
    public String crearCuerpoDeCorreoNuevoArticuloEnTicket(Articulo articulo){
        StringBuilder cuerpo = new StringBuilder();
        //Agregamos el nuevo articulo al ticket
        cuerpo.append("Se ha agregado un nuevo artículo al ticket# ").append(articulo.getTicketticketNumber().getTicketNumber()).append("\n");
        //Agregamos el cuerpo del articulo
        cuerpo.append(articulo.getCuerpo()).append("\n");
        return cuerpo.toString();
    }
    
}
