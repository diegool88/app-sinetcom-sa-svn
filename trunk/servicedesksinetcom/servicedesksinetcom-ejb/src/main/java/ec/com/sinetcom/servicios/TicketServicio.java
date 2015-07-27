/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import com.mchange.v1.util.ArrayUtils;
import ec.com.sinetcom.configuracion.TareaTicketInfo;
import ec.com.sinetcom.configuracion.UtilidadDeCorreoElectronico;
import ec.com.sinetcom.configuracion.UtilidadDeEmail;
import ec.com.sinetcom.dao.ActividadEnSitioFacade;
import ec.com.sinetcom.dao.ArticuloFacade;
import ec.com.sinetcom.dao.ClienteEmpresaFacade;
import ec.com.sinetcom.dao.ColaFacade;
import ec.com.sinetcom.dao.ContactoFacade;
import ec.com.sinetcom.dao.ContratoFacade;
import ec.com.sinetcom.dao.DatosSinetcomFacade;
import ec.com.sinetcom.dao.EstadoTicketFacade;
import ec.com.sinetcom.dao.EventoTicketFacade;
import ec.com.sinetcom.dao.GrupoFacade;
import ec.com.sinetcom.dao.HistorialDeTicketFacade;
import ec.com.sinetcom.dao.ItemProductoFacade;
import ec.com.sinetcom.dao.PrioridadTicketFacade;
import ec.com.sinetcom.dao.ServicioTicketFacade;
import ec.com.sinetcom.dao.TicketFacade;
import ec.com.sinetcom.dao.UsuarioFacade;
import ec.com.sinetcom.orm.ActividadEnSitio;
import ec.com.sinetcom.orm.Articulo;
import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.ColaTicket;
import ec.com.sinetcom.orm.Contacto;
import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.DatosSinetcom;
import ec.com.sinetcom.orm.EstadoTicket;
import ec.com.sinetcom.orm.Grupo;
import ec.com.sinetcom.orm.HistorialDeTicket;
import ec.com.sinetcom.orm.ItemProducto;
import ec.com.sinetcom.orm.PrioridadTicket;
import ec.com.sinetcom.orm.ServicioTicket;
import ec.com.sinetcom.orm.Sla;
import ec.com.sinetcom.orm.Ticket;
import ec.com.sinetcom.orm.Usuario;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import static org.quartz.JobBuilder.*;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.SimpleTrigger.*;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.*;

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
    @EJB
    private GrupoFacade grupoFacade;
    @EJB
    private ActividadEnSitioFacade actividadEnSitioFacade;
    @EJB
    private ContratoFacade contratoFacade;
    @EJB
    private TicketNotificadorServicio notificadorServicio;
    @EJB
    private DatosSinetcomFacade datosSinetcomFacade;

    /**
     * Obtiene todas las colas
     *
     * @return
     */
    public List<ColaTicket> obtenerTodasLasColas() {
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
     * Obtiene todos los estados posibles de un ticket
     *
     * @return
     */
    public List<EstadoTicket> obtenerTodosLosEstados() {
        return this.estadoTicketFacade.findAll();
    }

    /**
     * Obtener el cliente empresa del ruc (Converter)
     *
     * @param ruc
     * @return
     */
    public ClienteEmpresa obtenerClienteEmpresa(Integer id) {
        return this.clienteEmpresaFacade.find(id);
    }

    /**
     * Obtener la cola por el id (Converter)
     *
     * @param id
     * @return
     */
    public ColaTicket obtenerColaTicket(int id) {
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
     * Obtener el usuario por el id (Converter)
     *
     * @param id
     * @return
     */
    public Usuario obtenerUsuario(int id) {
        return this.usuarioFacade.find(id);
    }

    /**
     * Obtiene todos los servicios
     *
     * @return
     */
    public List<ServicioTicket> obtenerTodosLosServiciosDeTicket() {
        return this.servicioTicketFacade.findAll();
    }

    /**
     * Obtiene todos los productos de un cliente
     *
     * @param clienteIDSeleccionado
     * @return
     */
    public List<ItemProducto> obtenerTodosLosProductosDeUnCliente(ClienteEmpresa clienteIDSeleccionado) {
        return clienteIDSeleccionado != null ? this.itemProductoFacade.obtenerTodosLosProductosDeCliente(clienteIDSeleccionado.getContratoList()) : null;
    }

    /**
     * Obtiene todos los tickets de un usuario por estado
     *
     * @param usuario
     * @param estado
     * @return
     */
    public List<Ticket> obtenerTodosLosTicketsPorUnEstado(Usuario usuario, int estado) {
        EstadoTicket estadoTicket = this.estadoTicketFacade.find(estado);
        if (estadoTicket != null) {
            return usuario != null ? this.ticketFacade.obtenerTicketsPorEstadoDePropietario(usuario, estadoTicket) : this.ticketFacade.obtenerTodosLosTicketsPorEstado(estadoTicket);
        } else {
            return null;
        }
    }

    /**
     * Obtiene todos los tickets de un usuario por cola
     *
     * @param usuario
     * @param cola
     * @return
     */
    public List<Ticket> obtenerTodosLosTicketsPorUnaCola(Usuario usuario, int cola) {
        ColaTicket colaTicket = this.colaFacade.find(cola);
        if (colaTicket != null) {
            return usuario != null ? this.ticketFacade.obtenerTicketsPorColaDePropietario(usuario, colaTicket) : this.ticketFacade.obtenerTodosLosTicketsPorCola(colaTicket);
        } else {
            return null;
        }
    }

    /**
     * Obtiene todos los tickets de un usuario por prioridad
     *
     * @param usuario
     * @param prioridad
     * @return
     */
    public List<Ticket> obtenerTodosLosTicketsPorUnaPrioridad(Usuario usuario, int prioridad) {
        PrioridadTicket prioridadTicket = this.prioridadTicketFacade.find(prioridad);
        if (prioridadTicket != null) {
            return usuario != null ? this.ticketFacade.obtenerTicketsPorPrioridadDePropietario(usuario, prioridadTicket) : this.ticketFacade.obtenerTodosLosTicketsPorPrioridad(prioridadTicket);
        } else {
            return null;
        }
    }

    /**
     * Obtiene todos los ingenieros de soporte
     *
     * @return
     */
    public List<Usuario> obtenerTodosLosIngenieros() {
        return this.usuarioFacade.obtenerTodosLosIngenieros();
    }

    /**
     * Obtiene todos los articulos de un ticket
     *
     * @param ticket
     * @return
     */
    public List<Articulo> obtenerTodosLosArticulosDeTicket(Ticket ticket) {
        return this.articuloFacade.obtenerTodosLosArticulosDeUnTicket(ticket);
    }

    /**
     * Obtiene un articulo por id
     *
     * @param id
     * @return
     */
    public Articulo obtenerUnArticuloPorId(int id) {
        return this.articuloFacade.find(id);
    }

    /**
     * Obtiene todos las actividades en sitio de un ticket
     *
     * @param ticket
     * @return
     */
    public List<ActividadEnSitio> obtenerTodasLasActividadesEnSitioDeUnTicket(Ticket ticket) {
        return this.actividadEnSitioFacade.obtenerTodasLasActividadesDeUnTicket(ticket);
    }

    /**
     * Eliminar un actividad en sitio
     *
     * @param id
     */
    public void eliminarActividadDeTicket(int id) {
        ActividadEnSitio actividadEnSitio = this.actividadEnSitioFacade.find(id);
        if (actividadEnSitio != null) {
            this.actividadEnSitioFacade.remove(actividadEnSitio);
        }
    }

    /**
     * Sirve para actualizar los datos ingresados en el ticket pasado
     *
     * @param ticket
     */
    public void actualizarInformacionTicket(Ticket ticket) {
        this.ticketFacade.edit(ticket);
    }
    
    /**
     * Devuelve un formato corto de fecha y hora
     * @param fecha
     * @return 
     */
    public String formatoCortoDeFecha(Date fecha){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return format.format(fecha);
    }

    /**
     * Servicio que permite crear un nuevo ticket
     *
     * @param ticket
     * @param usuario
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
            //se ingresa la fecha de última modificación
            ticket.setFechaDeModificacion(ticket.getFechaDeCreacion());
            //Se determina el tiempo de actualizacion segun el SLA
            //int tiempoDeActualizacion = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoDeActualizacionDeEscalacion();
            //Información del ticket y el sla
            Sla ticketSla = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid();
            PrioridadTicket prioridadTicket = ticket.getPrioridadTicketcodigo();
            int tiempoDeActualizacion = prioridadTicket.getValor() == 1 ? ticketSla.getTiempoRespuestaPrioridadAlta() : prioridadTicket.getValor() == 2 ? ticketSla.getTiempoRespuestaPrioridadMedia() : ticketSla.getTiempoRespuestaPrioridadBaja();
            //Se añade el tiempo de actualización para pos-validación según SLA
            c = Calendar.getInstance();
            c.add(Calendar.HOUR, tiempoDeActualizacion);
            //Se hacen verificaciones por tipo de disponibilidad es decir 24x7 o 8x5
            int horaActual = c.get(Calendar.HOUR_OF_DAY);
            int diaDeLaSemana = c.get(Calendar.DAY_OF_WEEK);
            if (ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTipoDisponibilidadid().getDisponibilidad().equals("8x5") && (diaDeLaSemana == Calendar.SATURDAY || diaDeLaSemana == Calendar.SUNDAY || (diaDeLaSemana == Calendar.FRIDAY && horaActual > 17))) {
                if (diaDeLaSemana == Calendar.FRIDAY) {
                    c.add(Calendar.DAY_OF_MONTH, 3);
                } else if (diaDeLaSemana == Calendar.SATURDAY) {
                    c.add(Calendar.DAY_OF_MONTH, 2);
                } else {
                    c.add(Calendar.DAY_OF_MONTH, 1);
                }
                c.set(Calendar.HOUR_OF_DAY, 8);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
            } else if (ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTipoDisponibilidadid().getDisponibilidad().equals("8x5") && (horaActual < 8 || horaActual > 17) && (diaDeLaSemana != Calendar.SATURDAY && diaDeLaSemana != Calendar.SUNDAY)) {
                if (horaActual > 17) {
                    c.add(Calendar.DAY_OF_MONTH, 1);
                }
                c.set(Calendar.HOUR_OF_DAY, 8);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
            }
            ticket.setFechaDeProximaActualizacion(c.getTime());
            if (ticket.getFechaDeProximaActualizacion().compareTo(ticket.getFechaDeCierre()) > 0) {
                Calendar cierre = Calendar.getInstance();
                cierre.setTime(c.getTime());
                cierre.add(Calendar.HOUR, tiempoDeVida);
                ticket.setFechaDeCierre(cierre.getTime());
            }
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
            //Creamos el Notificador
            TareaTicketInfo tareaTicketInfo = new TareaTicketInfo("t_sla" + ticket.getTicketNumber(), "Notificador SLA ticket# " + ticket.getTicketNumber(), "LoteTareaNotificarSLA", ticket);
            //Información del ticket y el sla
            //Sla ticketSla = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid();
            //PrioridadTicket prioridadTicket = ticket.getPrioridadTicketcodigo();
            String hora = prioridadTicket.getValor() == 1 ? String.valueOf(ticketSla.getTiempoRespuestaPrioridadAlta()) : prioridadTicket.getValor() == 2 ? String.valueOf(ticketSla.getTiempoRespuestaPrioridadMedia()) : String.valueOf(ticketSla.getTiempoRespuestaPrioridadBaja());
            //Definir la hora de inicio
            //c = Calendar.getInstance();
            //c.add(Calendar.HOUR, Integer.parseInt(hora));
            c.add(Calendar.MINUTE, -30);
            tareaTicketInfo.setStartDate(c.getTime());
            tareaTicketInfo.setEndDate(ticket.getFechaDeCierre());
            tareaTicketInfo.setSecond(String.valueOf(c.get(Calendar.SECOND)));
            tareaTicketInfo.setMinute(String.valueOf(c.get(Calendar.MINUTE)) + "/10");
            tareaTicketInfo.setHour(String.valueOf(c.get(Calendar.HOUR_OF_DAY)));
            tareaTicketInfo.setMonth("*");
            tareaTicketInfo.setDayOfMonth("*");
            tareaTicketInfo.setDayOfWeek(ticketSla.getTipoDisponibilidadid().getDisponibilidad().equals("8x5") ? "Mon, Tue, Wed, Thu, Fri" : "*");
            tareaTicketInfo.setYear("*");
            tareaTicketInfo.setDescription("Tarea SLA para ticket# " + ticket.getTicketNumber());
            this.notificadorServicio.createJob(tareaTicketInfo);

            //Se añade un historico del evento
            HistorialDeTicket historialDeTicket = new HistorialDeTicket();
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(1));
            historialDeTicket.setFechaDelEvento(new Date());
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket));
            historialDeTicket.setUsuarioid(ticket.getUsuarioidcreador());
            historialDeTicket.setTicketticketNumber(ticket);
            this.historialDeTicketFacade.create(historialDeTicket);

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
        //Contrato contrato = this.contratoFacade.
        c.setTime(itemProducto.getContratonumero().getFechaDeSuscripcion());
        c.add(Calendar.YEAR, itemProducto.getContratonumero().getTiempoDeValidez());

        Date fechaDeVencimientoDeContrato = c.getTime();

        //Verificamos que el usuario pueda crear el ticket de soporte validando la fecha de vencimiento del contrato y que su cuenta se encuentre activa
        if (usuario.getActivo()) {
            return true;
        } else {
            return fechaDeVencimientoDeContrato.compareTo(Calendar.getInstance().getTime()) <= 0;
        }
    }

    /**
     * Servicio que permite asignar ticket a un ingeniero (Propietario del
     * ticket)
     *
     * @param ticket
     * @param usuarioAsignante
     * @return
     */
    public boolean asignarTicketAPropietario(Ticket ticket, Usuario usuarioAsignante) {
        try {
            ticket.setFechaDeModificacion(new Date());
            if (ticket.getEstadoTicketcodigo().getCodigo() == 1) {
                EstadoTicket estadoTicket = this.estadoTicketFacade.find(2);
                ticket.setEstadoTicketcodigo(estadoTicket);
            }
            this.ticketFacade.edit(ticket);
            HistorialDeTicket historialDeTicket = new HistorialDeTicket();
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(5));
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket));
            historialDeTicket.setTicketticketNumber(ticket);
            historialDeTicket.setFechaDelEvento(new Date());
            historialDeTicket.setUsuarioid(usuarioAsignante);
            this.historialDeTicketFacade.create(historialDeTicket);

            //Se envia un correo de notificación al ingeniero asignado al soporte del ticket
            UtilidadDeEmail utilidadDeEmail = new UtilidadDeEmail();
            utilidadDeEmail.enviarMensajeConAdjunto("soporte@sinetcom.com.ec", ticket.getUsuarioidpropietario().getCorreoElectronico(), "Asignación de Ticket# " + ticket.getTicketNumber(), crearCuerpoDeCorreoAsignacionTicketAIngeniero(ticket), null, null, null);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Servicio que permite agregar una nueva actividad en sitio a un ticket
     *
     * @param actividadEnSitio
     * @return
     */
    public boolean agregarActividadEnSitio(ActividadEnSitio actividadEnSitio) {
        try {
            this.actividadEnSitioFacade.create(actividadEnSitio);
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
            ticket.setFechaDeModificacion(new Date());
            this.ticketFacade.edit(ticket);
            HistorialDeTicket historialDeTicket = new HistorialDeTicket();
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(3));
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket));
            historialDeTicket.setTicketticketNumber(ticket);
            historialDeTicket.setFechaDelEvento(new Date());
            historialDeTicket.setUsuarioid(usuario);
            this.historialDeTicketFacade.create(historialDeTicket);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Servicio que permite poner el caso en pendiente
     *
     * @param ticket
     * @param usuario
     * @return
     */
    public boolean ponerEnPendiente(Ticket ticket, Usuario usuario) {
        EstadoTicket pendiente = this.estadoTicketFacade.find(5);
        try {
            if (ticket.getFechaDePrimerContacto() != null) {
                ticket.setFechaDePrimerContacto(null);
            }
            this.cambiarEstadoDeTicket(ticket, pendiente, usuario);
            Timer tarea = this.notificadorServicio.getTareaInfoById("t_sla" + ticket.getTicketNumber());
            if (tarea != null) {
                tarea.cancel();
            }
            Timer tarea2 = this.notificadorServicio.getTareaInfoById("t_update" + ticket.getTicketNumber());
            if (tarea2 != null) {
                tarea2.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Servicio que permite re-abrir un caso de soporte
     *
     * @param ticket
     * @param usuario
     * @return
     */
    public boolean reabrirCaso(Ticket ticket, Usuario usuario) {
        EstadoTicket abierto = this.estadoTicketFacade.find(2);
        try {
            //Definimos el tiempo de vida del ticket nuevamente de 0
            int tiempoDeVida = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoDeSolucion();
            //Cambiamos el estado
            this.cambiarEstadoDeTicket(ticket, abierto, usuario);
            //Reseteamos la fecha de creación y modificación
            ticket.setFechaDeCreacion(Calendar.getInstance().getTime());
            ticket.setFechaDeModificacion(Calendar.getInstance().getTime());
            //Creamos el notificador del tiempo de respuesta inicial
            TareaTicketInfo tareaTicketInfo = new TareaTicketInfo("t_sla" + ticket.getTicketNumber(), "Notificador SLA ticket# " + ticket.getTicketNumber(), "LoteTareaNotificarSLA", ticket);
            Sla ticketSla = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid();
            PrioridadTicket prioridadTicket = ticket.getPrioridadTicketcodigo();
            int tiempoDeActualizacion = prioridadTicket.getValor() == 1 ? ticketSla.getTiempoRespuestaPrioridadAlta() : prioridadTicket.getValor() == 2 ? ticketSla.getTiempoRespuestaPrioridadMedia() : ticketSla.getTiempoRespuestaPrioridadBaja();
            Calendar c = Calendar.getInstance();
            c.add(Calendar.HOUR, tiempoDeActualizacion);
            //Se hacen verificaciones por tipo de disponibilidad es decir 24x7 o 8x5
            int horaActual = c.get(Calendar.HOUR_OF_DAY);
            int diaDeLaSemana = c.get(Calendar.DAY_OF_WEEK);
            if (ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTipoDisponibilidadid().getDisponibilidad().equals("8x5") && (diaDeLaSemana == Calendar.SATURDAY || diaDeLaSemana == Calendar.SUNDAY || (diaDeLaSemana == Calendar.FRIDAY && horaActual > 17))) {
                if (diaDeLaSemana == Calendar.FRIDAY) {
                    c.add(Calendar.DAY_OF_MONTH, 3);
                } else if (diaDeLaSemana == Calendar.SATURDAY) {
                    c.add(Calendar.DAY_OF_MONTH, 2);
                } else {
                    c.add(Calendar.DAY_OF_MONTH, 1);
                }
                c.set(Calendar.HOUR_OF_DAY, 8);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
            } else if (ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTipoDisponibilidadid().getDisponibilidad().equals("8x5") && (horaActual < 8 || horaActual > 17) && (diaDeLaSemana != Calendar.SATURDAY && diaDeLaSemana != Calendar.SUNDAY)) {
                if (horaActual > 17) {
                    c.add(Calendar.DAY_OF_MONTH, 1);
                }
                c.set(Calendar.HOUR_OF_DAY, 8);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
            }
            ticket.setFechaDeProximaActualizacion(c.getTime());
            if (ticket.getFechaDeProximaActualizacion().compareTo(ticket.getFechaDeCierre()) > 0) {
                Calendar cierre = Calendar.getInstance();
                cierre.setTime(c.getTime());
                cierre.add(Calendar.HOUR, tiempoDeVida);
                ticket.setFechaDeCierre(cierre.getTime());
            }
            //Se actualiza el ticket
            this.ticketFacade.edit(ticket);
            //Se crea un notificador para 30 minutos antes del vencimiento del primer contacto
            c.add(Calendar.MINUTE, -30);
            tareaTicketInfo.setStartDate(c.getTime());
            tareaTicketInfo.setSecond(String.valueOf(c.get(Calendar.SECOND)));
            tareaTicketInfo.setMinute(String.valueOf(c.get(Calendar.MINUTE)) + "/10");
            tareaTicketInfo.setHour(String.valueOf(c.get(Calendar.HOUR_OF_DAY)));
            tareaTicketInfo.setEndDate(ticket.getFechaDeCierre());
            tareaTicketInfo.setMonth("*");
            tareaTicketInfo.setDayOfMonth("*");
            tareaTicketInfo.setDayOfWeek(ticketSla.getTipoDisponibilidadid().getDisponibilidad().equals("8x5") ? "Mon, Tue, Wed, Thu, Fri" : "*");
            tareaTicketInfo.setYear("*");
            tareaTicketInfo.setDescription("Tarea SLA para ticket# " + ticket.getTicketNumber());
            this.notificadorServicio.createJob(tareaTicketInfo);
            //Creamos el notificador de tiempo de actualización
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
    public boolean cambiarColaDeTicket(Ticket ticket, ColaTicket cola, Usuario usuario) {
        try {
            ticket.setColaid(cola);
            ticket.setFechaDeModificacion(new Date());
            this.ticketFacade.edit(ticket);
            HistorialDeTicket historialDeTicket = new HistorialDeTicket();
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(4));
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket));
            historialDeTicket.setTicketticketNumber(ticket);
            historialDeTicket.setFechaDelEvento(new Date());
            historialDeTicket.setUsuarioid(usuario);
            this.historialDeTicketFacade.create(historialDeTicket);
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
     * @param esArticuloInicial
     * @return
     */
    public boolean ingresarNuevoArticuloAlTicket(Ticket ticket, Articulo articulo, Usuario usuario, boolean esArticuloInicial) {
        try {
            ticket.setFechaDeModificacion(new Date());
            //Se determina el tiempo de actualizacion segun el SLA
            int tiempoDeActualizacion = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoDeActualizacionDeEscalacion();
            Calendar c = Calendar.getInstance();
            //c = Calendar.getInstance();
            c.add(Calendar.HOUR, tiempoDeActualizacion);
            //Se hacen verificaciones por tipo de disponibilidad es decir 24x7 o 8x5
            int horaActual = c.get(Calendar.HOUR_OF_DAY);
            int diaDeLaSemana = c.get(Calendar.DAY_OF_WEEK);
            if (ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTipoDisponibilidadid().getDisponibilidad().equals("8x5") && (diaDeLaSemana == Calendar.SATURDAY || diaDeLaSemana == Calendar.SUNDAY || (diaDeLaSemana == Calendar.FRIDAY && horaActual > 17))) {
                if (diaDeLaSemana == Calendar.FRIDAY) {
                    c.add(Calendar.DAY_OF_MONTH, 3);
                } else if (diaDeLaSemana == Calendar.SATURDAY) {
                    c.add(Calendar.DAY_OF_MONTH, 2);
                } else {
                    c.add(Calendar.DAY_OF_MONTH, 1);
                }
                c.set(Calendar.HOUR_OF_DAY, 8);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
            } else if (ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTipoDisponibilidadid().getDisponibilidad().equals("8x5") && (horaActual < 8 || horaActual > 17) && (diaDeLaSemana != Calendar.SATURDAY && diaDeLaSemana != Calendar.SUNDAY)) {
                if (horaActual > 17) {
                    c.add(Calendar.DAY_OF_MONTH, 1);
                }
                c.set(Calendar.HOUR_OF_DAY, 8);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
            }
            ticket.setFechaDeProximaActualizacion(c.getTime());
            this.ticketFacade.edit(ticket);
            //Se actualiza el notificador
            Timer tarea = this.notificadorServicio.getTareaInfoById("t_update" + ticket.getTicketNumber());
            if (tarea != null) {
                TareaTicketInfo info = (TareaTicketInfo) tarea.getInfo();
                info.setStartDate(c.getTime());
                info.setHour(String.valueOf(c.get(Calendar.HOUR)));
                info.setMinute(String.valueOf(c.get(Calendar.MINUTE)));
                info.setSecond(String.valueOf(c.get(Calendar.SECOND)));
                this.notificadorServicio.updateJob(info);
            }
            articulo.setDe(usuario);
            articulo.setPara(ticket.getUsuarioidcreador());
            StringBuilder cc = new StringBuilder();
            List<String> emails = contactosDeTicket(ticket, true);
            int i = 0;
            for (String email : emails) {
                if (i++ < emails.size()) {
                    cc.append(email).append(";");
                }
            }
            articulo.setCopia(cc.toString());
            articulo.setTicketticketNumber(ticket);
            articulo.setFechaDeCreacion(new Date());
            articulo.setOrden(this.articuloFacade.obtenerOrdenDeArticuloTicket(ticket));
            articuloFacade.create(articulo);
            HistorialDeTicket historialDeTicket = new HistorialDeTicket();
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(2));
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket));
            historialDeTicket.setTicketticketNumber(ticket);
            historialDeTicket.setFechaDelEvento(new Date());
            historialDeTicket.setUsuarioid(usuario);

            //Se envía un correo por ticket nuevo
            UtilidadDeEmail utilidadDeCorreoElectronico = new UtilidadDeEmail();
            DatosSinetcom datosSinetcom = this.datosSinetcomFacade.find("1791839692001");
            if (esArticuloInicial) {
                //Se envia el correo electrónico notificando a todos los interesados 
                utilidadDeCorreoElectronico.enviarMensajeConAdjunto(datosSinetcom.getEmailNoResponder(), "soporte@sinetcom.com.ec", "Nueva incidencia - Caso# " + ticket.getTicketNumber(), crearCuerpoDeCorreoNuevoTicket(ticket, articulo, false), contactosDeTicket(ticket, true), articulo.getContenidoAdjunto() != null ? articulo.getContenidoAdjunto() : null, articulo.getContenidoAdjunto() != null ? ticket.getTicketNumber() + "_" + articulo.getId() + "." + articulo.getExtensionArchivo() : null);
                //Se envia un correo a todos los tecnicos de Sinetcom
                utilidadDeCorreoElectronico.enviarMensajeConAdjunto(datosSinetcom.getEmailNoResponder(), "soporte@sinetcom.com.ec", "Nueva incidencia - Caso# " + ticket.getTicketNumber(), crearCuerpoDeCorreoNuevoTicket(ticket, articulo, true), contactosDeTicket(ticket, false), articulo.getContenidoAdjunto() != null ? articulo.getContenidoAdjunto() : null, articulo.getContenidoAdjunto() != null ? ticket.getTicketNumber() + "_" + articulo.getId() + "." + articulo.getExtensionArchivo() : null);
            } else {
                //Se envia un correo electrónico del nuevo articulo
                utilidadDeCorreoElectronico.enviarMensajeConAdjunto(datosSinetcom.getEmailNoResponder(), "soporte@sinetcom.com.ec", "Actualización - Caso# " + ticket.getTicketNumber(), crearCuerpoDeCorreoNuevoArticuloEnTicket(articulo), contactosDeTicket(ticket, true), articulo.getContenidoAdjunto() != null ? articulo.getContenidoAdjunto() : null, articulo.getContenidoAdjunto() != null ? ticket.getTicketNumber() + "_" + articulo.getId() + "." + articulo.getExtensionArchivo() : null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean cerrarTicket(Ticket ticket, Usuario usuario, boolean conExito) {
        try {
            EstadoTicket estadoDeCierre = conExito == true ? this.estadoTicketFacade.find(3) : this.estadoTicketFacade.find(4);
            ticket.setEstadoTicketcodigo(estadoDeCierre);
            ticket.setFechaDeModificacion(new Date());
            ticket.setFechaDeCierre(ticket.getFechaDeModificacion());
            this.ticketFacade.edit(ticket);
            //Se añade un historico del evento
            HistorialDeTicket historialDeTicket = new HistorialDeTicket();
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(6));
            historialDeTicket.setFechaDelEvento(new Date());
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket));
            historialDeTicket.setUsuarioid(usuario);
            historialDeTicket.setTicketticketNumber(ticket);
            this.historialDeTicketFacade.create(historialDeTicket);
            //Se elimina el Notificador de SLA
            Timer tarea = this.notificadorServicio.getTareaInfoById("t_sla" + ticket.getTicketNumber());
            if (tarea != null) {
                tarea.cancel();
            }
            Timer tarea2 = this.notificadorServicio.getTareaInfoById("t_update" + ticket.getTicketNumber());
            if (tarea2 != null) {
                tarea2.cancel();
            }
            //Se envia el correo electrónico
            UtilidadDeEmail utilidadDeCorreoElectronico = new UtilidadDeEmail();
            DatosSinetcom datosSinetcom = this.datosSinetcomFacade.find("1791839692001");
            utilidadDeCorreoElectronico.enviarMensajeConAdjunto(datosSinetcom.getEmailNoResponder(), "soporte@sinetcom.com.ec", "Ticket#" + ticket.getTicketNumber() + " fue cerrado", crearCuerpoDeCorreoTicketCerrado(ticket, usuario), contactosDeTicket(ticket, true), null, null);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Servicio que permite ingresar la fecha de primer contacto
     *
     * @param ticket
     * @param usuario
     * @param fechaDeContacto
     * @return
     */
    public boolean ingresarPrimerContactoDeTicket(Ticket ticket, Usuario usuario) {
        try {
            //Se agrega la información de actualización y modificación
            ticket.setFechaDeModificacion(new Date());
            Calendar c = Calendar.getInstance();
            c.add(Calendar.HOUR, ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoDeActualizacionDeEscalacion());
            ticket.setFechaDeProximaActualizacion(c.getTime());
            //Se modifica el ticket y se agrega en el historial
            this.ticketFacade.edit(ticket);
            HistorialDeTicket historialDeTicket = new HistorialDeTicket();
            historialDeTicket.setEventoTicketcodigo(this.eventoTicketFacade.find(7));
            historialDeTicket.setFechaDelEvento(ticket.getFechaDePrimerContacto());
            historialDeTicket.setOrden(this.historialDeTicketFacade.obtenerOrdenDeHistorialDeTicket(ticket));
            historialDeTicket.setUsuarioid(usuario);
            historialDeTicket.setTicketticketNumber(ticket);
            this.historialDeTicketFacade.create(historialDeTicket);
            //Eliminamos el notificador de primer contacto
            Timer tarea = this.notificadorServicio.getTareaInfoById("t_sla" + ticket.getTicketNumber());
            if (tarea != null) {
                tarea.cancel();
            }
            //Creamos el notificador de actualización de ticket
            TareaTicketInfo tareaTicketInfo = new TareaTicketInfo("t_update" + ticket.getTicketNumber(), "Notificador Actualizacion ticket# " + ticket.getTicketNumber(), "LoteTareaNotificarTiempoDeActualizacion", ticket);
            Sla ticketSla = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid();
            c = Calendar.getInstance();
            c.add(Calendar.HOUR, ticketSla.getTiempoDeActualizacionDeEscalacion());
            tareaTicketInfo.setStartDate(c.getTime());
            tareaTicketInfo.setSecond(String.valueOf(c.get(Calendar.SECOND)));
            tareaTicketInfo.setMinute(String.valueOf(c.get(Calendar.MINUTE)));
            tareaTicketInfo.setHour(String.valueOf(c.get(Calendar.HOUR_OF_DAY)) + "/" + ticketSla.getTiempoDeActualizacionDeEscalacion());
            c = Calendar.getInstance();
            c.add(Calendar.HOUR, ticketSla.getTiempoDeSolucion());
            tareaTicketInfo.setEndDate(c.getTime());
            tareaTicketInfo.setMonth("*");
            tareaTicketInfo.setDayOfMonth("*");
            tareaTicketInfo.setDayOfWeek(ticketSla.getTipoDisponibilidadid().getDisponibilidad().equals("8x5") ? "Mon, Tue, Wed, Thu, Fri" : "*");
            tareaTicketInfo.setYear("*");
            tareaTicketInfo.setDescription("Tarea Actualizacion para ticket# " + ticket.getTicketNumber());
            this.notificadorServicio.createJob(tareaTicketInfo);
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
        Date fechaDePrimerContacto = ticket.getFechaDePrimerContacto();
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
    private List<String> contactosDeTicket(Ticket ticket, boolean incluirClientes) {

        //Se inicializa el arreglo total
        List<String> ccTodos = new ArrayList<String>();

        //Se añade a las persona que esten involucradas en el caso
        if (incluirClientes) {
            List<Contacto> contactos = this.contactoFacade.obtenerContactosDeCliente(ticket.getClienteEmpresaid());
            if (contactos != null) {
                for (Contacto contactoEmpresa : contactos) {
                    ccTodos.add(contactoEmpresa.getCorreoElectronico());
                }
            }
        }

        DatosSinetcom datosSinetcom = this.datosSinetcomFacade.find("1791839692001");
        //Se agrega a la presidencia en copia
        //ccTodos.add("jorge.yanez@sinetcom.com.ec");
        //Agregando al Helpdesk en copia
        ccTodos.add("soporte@sinetcom.com.ec");
        //Agregando al Presidente de la compañia
        /*
         Por activar en etapa de producción
         */
        ccTodos.add(datosSinetcom.getEmailPresidente());
        //Agrenado al Gerente de soporte
        ccTodos.add(datosSinetcom.getEmailGerenteTecnico());
        //Agregando a la persona que creo el ticket
        ccTodos.add(ticket.getUsuarioidcreador().getCorreoElectronico());
        //Agregando al propietario del ticket
        ccTodos.add(ticket.getUsuarioidpropietario().getCorreoElectronico());

        return ccTodos;
    }

    /**
     * Esta función permite crear el cuerpo del correo electrónico de un nuevo
     * ticket
     *
     * @param ticket
     * @param articulo
     * @return
     */
    private String crearCuerpoDeCorreoNuevoTicket(Ticket ticket, Articulo articulo, boolean empresaDeSoporte) {

        StringBuilder cuerpo = new StringBuilder();
        //Se indica los datos principales del ticket
        cuerpo.append("Le informamos que un nuevo ticket de soporte se ha creado:").append("\n");
        cuerpo.append("Ticket #: ").append(ticket.getTicketNumber()).append("\n");
        cuerpo.append("Creado por: ").append(ticket.getUsuarioidcreador().getNombre()).append(" ").append(ticket.getUsuarioidcreador().getApellido()).append("\n");
        cuerpo.append("Empresa: ").append(ticket.getClienteEmpresaid().getNombreComercial()).append("\n");
        cuerpo.append("Fecha y Hora de Creación: ").append(formatoCortoDeFecha(ticket.getFechaDeCreacion())).append("\n");
        cuerpo.append("Prioridad: ").append(ticket.getPrioridadTicketcodigo().getNombre()).append("\n");
        cuerpo.append("Servicio Solicitado: ").append(ticket.getServicioTicketcodigo().getNombre()).append("\n");
        cuerpo.append("Estado: ").append(ticket.getEstadoTicketcodigo().getNombre()).append("\n");
        cuerpo.append("Equipo: ").append(ticket.getItemProductonumeroSerial().getModeloProductoid().getLineaDeProductoid().getNombre()).append(ticket.getItemProductonumeroSerial().getModeloProductoid().getModelo()).append(" - S/N ").append(ticket.getItemProductonumeroSerial().getNumeroSerial()).append("\n");
        //Se verifica si la fecha de fin de contrato está próxima
        Calendar fechaMaximaDeValidez = Calendar.getInstance();
        Contrato contrato = ticket.getItemProductonumeroSerial().getContratonumero();
        fechaMaximaDeValidez.setTime(contrato.getFechaDeEntregaRecepcion());
        fechaMaximaDeValidez.add(Calendar.YEAR, contrato.getTiempoDeValidez());
        long diferenciaTiempo = Math.abs(((new Date()).getTime() - fechaMaximaDeValidez.getTime().getTime()) / (1000 * 60 * 60 * 24));
        if (diferenciaTiempo <= 30) {
            cuerpo.append("NOTA: Recuerde que solo quedan ").append(diferenciaTiempo).append(" días para la finalización del contrato de soporte sobre este equipo.").append("\n");
        }
        //Se agrega la informacion a la empresa de soporte
        if (empresaDeSoporte) {
            cuerpo.append("SLA: ").append(ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTipoDisponibilidadid().getDisponibilidad()).append("\n");
            Calendar c = Calendar.getInstance();
            switch (ticket.getPrioridadTicketcodigo().getValor()) {
                case 1:
                    c.add(Calendar.HOUR, ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoRespuestaPrioridadAlta());
                    cuerpo.append("Hora máxima de Primer Contacto: ").append(formatoCortoDeFecha(c.getTime())).append("\n");
                    break;
                case 2:
                    c.add(Calendar.HOUR, ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoRespuestaPrioridadMedia());
                    cuerpo.append("Hora máxima de Primer Contacto: ").append(formatoCortoDeFecha(c.getTime())).append("\n");
                    break;
                case 3:
                    c.add(Calendar.HOUR, ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoRespuestaPrioridadBaja());
                    cuerpo.append("Hora máxima de Primer Contacto: ").append(formatoCortoDeFecha(c.getTime())).append("\n");
                    break;
            }
            Calendar g = Calendar.getInstance();
            g.add(Calendar.YEAR, ticket.getItemProductonumeroSerial().getContratonumero().getGarantiaTecnica());
            cuerpo.append("Tiempo de resolución: ").append(ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoDeSolucion()).append(" h\n");
            cuerpo.append("Repuestos: ").append(ticket.getItemProductonumeroSerial().getContratonumero().getIncluyeRepuestos() ? "Incluye" : "No Incluye").append("\n");
            cuerpo.append("Garantía Técnica: ").append(g.getTime().compareTo(new Date()) < 0 ? "Vencido" : "En vigencia").append("; Fecha: ").append(formatoCortoDeFecha(g.getTime())).append("\n");
            Contacto contacto = ticket.getItemProductonumeroSerial().getContratonumero().getAdministradorDeContrato();
            cuerpo.append("Persona de Contacto: ").append(contacto.getNombre()).append("\n");
            cuerpo.append("Teléfono fijo: ").append(contacto.getTelefonoFijo()).append("\n");
            cuerpo.append("Teléfono Móvil: ").append(contacto.getTelefonoMovil()).append("\n");
            cuerpo.append("Correo Electrónico: ").append(contacto.getCorreoElectronico()).append("\n");
        }
        //Se agrega la información del Articulo
        cuerpo.append("---------------------------------------------------").append("\n");
        cuerpo.append("Asunto: ").append(ticket.getTitulo()).append("\n");
        cuerpo.append("Descripción del problema: ").append(articulo.getCuerpo()).append("\n\n");
        if (!empresaDeSoporte) {
            int horasMaximasDeContacto = 0;
            switch (ticket.getPrioridadTicketcodigo().getValor()) {
                case 1:
                    horasMaximasDeContacto = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoRespuestaPrioridadAlta();
                    break;
                case 2:
                    horasMaximasDeContacto = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoRespuestaPrioridadMedia();
                    break;
                case 3:
                    horasMaximasDeContacto = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoRespuestaPrioridadBaja();
                    break;
            }
            cuerpo.append("Recuerde que un técnico se pondrá en contacto con usted en un plazo máximo de ").append(horasMaximasDeContacto).append(" horas.\n\n");
            cuerpo.append("Saludos Cordiales\n");
        }
        return cuerpo.toString();
    }

    /**
     * Función que permite crear el cuerpo del correo electronico de un nuevo
     * articulo
     *
     * @param articulo
     * @return
     */
    private String crearCuerpoDeCorreoNuevoArticuloEnTicket(Articulo articulo) {
        StringBuilder cuerpo = new StringBuilder();
        //Agregamos el nuevo articulo al ticket
        cuerpo.append("Se ha agregado un nuevo artículo al ticket# ").append(articulo.getTicketticketNumber().getTicketNumber()).append("\n\n");
        //Agregamos el asunto
        cuerpo.append("Asunto: ").append(articulo.getAsunto()).append("\n\n");
        //Agregamos el cuerpo del articulo
        cuerpo.append("Detalle: ").append(articulo.getCuerpo()).append("\n\n");
        cuerpo.append("Saludos Cordiales.");
        return cuerpo.toString();
    }

    /**
     * Función que permite crear el cuerpo del mensaje cuando un ticket es
     * cerrado
     *
     * @param ticket
     * @param usuario
     * @return
     */
    private String crearCuerpoDeCorreoTicketCerrado(Ticket ticket, Usuario usuario) {
        StringBuilder cuerpo = new StringBuilder();
        //Agregamos el cuerpo del ticket cerrado
        cuerpo.append("Se ha cerrado el ticket# ").append(ticket.getTicketNumber()).append(" vea abajo los detalles: ").append("\n");
        //Agregamos el detalle del cierre del ticket
        cuerpo.append("Hora de Cierre: ").append(formatoCortoDeFecha(ticket.getFechaDeCierre())).append("\n");
        cuerpo.append("Usuario: ").append(usuario.getNombreCompleto()).append("\n");
        cuerpo.append("El ticket fue cerrado con éxito!").append("\n\n");
        cuerpo.append("Saludos Cordiales.");
        return cuerpo.toString();
    }

    /**
     * Función que permite crear el cuerpo de un correo de notificación de SLA
     * de Ticket de soporte
     *
     * @param ticket
     * @return
     */
    private String crearCuerpoDeCorreoTicketSLA(Ticket ticket) {
        StringBuilder cuerpo = new StringBuilder();
        cuerpo.append("Este es un correo autómatico de recordatorio.").append("\n");
        cuerpo.append("Le recordamos que debe actualizar el ticket# ").append(ticket.getTicketNumber()).append(".\n\n");
        cuerpo.append("Saludos!").append("\n");
        return cuerpo.toString();
    }

    /**
     *
     * @param ticket
     * @return
     */
    private String crearCuerpoDeCorreoPrimerContactoTicket(Ticket ticket) {
        StringBuilder cuerpo = new StringBuilder();
        cuerpo.append("Este es un correo autómatico de recordatorio.").append("\n\n");
        cuerpo.append("Le recordamos que debe establecer contacto con el cliente ").append(ticket.getClienteEmpresaid().getNombreComercial()).append(" a favor del ticket# ").append(ticket.getTicketNumber()).append(".\n");
        cuerpo.append("NOTA: Evite incumplir con el SLA de este ticket!").append("\n\n");
        cuerpo.append("Saludos!").append("\n");
        return cuerpo.toString();
    }

    /**
     * Función que permite crear el cuerpo de correo de la asignación de ticket
     * a un ingeniero
     *
     * @param ticket
     * @return
     */
    private String crearCuerpoDeCorreoAsignacionTicketAIngeniero(Ticket ticket) {
        StringBuilder cuerpo = new StringBuilder();
        cuerpo.append("Se le informa que el ticket# ").append(ticket.getTicketNumber()).append(" le ha sido asignado.").append("\n\n");
        cuerpo.append("A continuación los detalles: ").append("\n");
        cuerpo.append("Ticket #: ").append(ticket.getTicketNumber()).append("\n");
        cuerpo.append("Creado por: ").append(ticket.getUsuarioidcreador().getNombre()).append(" ").append(ticket.getUsuarioidcreador().getApellido()).append("\n");
        cuerpo.append("Empresa: ").append(ticket.getClienteEmpresaid().getNombreComercial()).append("\n");
        cuerpo.append("Fecha y Hora de Creación: ").append(ticket.getFechaDeCreacion().toString()).append("\n");
        cuerpo.append("Prioridad: ").append(ticket.getPrioridadTicketcodigo().getNombre()).append("\n");
        cuerpo.append("Servicio Solicitado: ").append(ticket.getServicioTicketcodigo().getNombre()).append("\n");
        cuerpo.append("Estado: ").append(ticket.getEstadoTicketcodigo().getNombre()).append("\n");
        cuerpo.append("Equipo: ").append(ticket.getItemProductonumeroSerial().getModeloProductoid().getLineaDeProductoid().getNombre()).append(ticket.getItemProductonumeroSerial().getModeloProductoid().getModelo()).append(" - S/N ").append(ticket.getItemProductonumeroSerial().getNumeroSerial()).append("\n");
        cuerpo.append("SLA: ").append(ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTipoDisponibilidadid().getDisponibilidad()).append("\n");
        Calendar c = Calendar.getInstance();
        switch (ticket.getPrioridadTicketcodigo().getValor()) {
            case 1:
                c.add(Calendar.HOUR, ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoRespuestaPrioridadAlta());
                cuerpo.append("Hora máxima de Primer Contacto: ").append(c.getTime()).append("\n");
                break;
            case 2:
                c.add(Calendar.HOUR, ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoRespuestaPrioridadMedia());
                cuerpo.append("Hora máxima de Primer Contacto: ").append(c.getTime()).append("\n");
                break;
            case 3:
                c.add(Calendar.HOUR, ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoRespuestaPrioridadBaja());
                cuerpo.append("Hora máxima de Primer Contacto: ").append(c.getTime()).append("\n");
                break;
        }
        Calendar g = Calendar.getInstance();
        g.add(Calendar.YEAR, ticket.getItemProductonumeroSerial().getContratonumero().getGarantiaTecnica());
        cuerpo.append("Tiempo de resolución: ").append(ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoDeSolucion()).append(" h\n");
        cuerpo.append("Repuestos: ").append(ticket.getItemProductonumeroSerial().getContratonumero().getIncluyeRepuestos() ? "Incluye" : "No Incluye").append("\n");
        cuerpo.append("Garantía Técnica: ").append(g.getTime().compareTo(new Date()) < 0 ? "Vencido" : "En vigencia").append(" Fecha: ").append(g.getTime()).append("\n\n");
        cuerpo.append("Le recomendamos que le de seguimiento de forma inmediata para no inclumplir los SLAs.").append("\n\n");
        cuerpo.append("Saludos Cordiales").append("\n");

        return cuerpo.toString();
    }

    /**
     * Función que permite enviar un correo de notificación de SLA
     *
     * @param ticket
     */
    public void enviarNotificacionDeActualizacionDeTicket(Ticket ticket) {
        //Ticket ticketActual = this.ticketFacade.find(ticket.getTicketNumber());
        Sla sla = ticket.getItemProductonumeroSerial().getContratonumero().getSlaid();
        int horaDelDia = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (sla.getTipoDisponibilidadid().getDisponibilidad().equals("8x5") && (horaDelDia < 8 || horaDelDia > 17)) {
            System.out.println("Se ha omitido notificador por horario no disponible del ticket# " + ticket.getTicketNumber());
            return;
        }
        long diferencia = new Date().getTime() - ticket.getFechaDeModificacion().getTime();
        long horas = diferencia / (60 * 60 * 1000) % 24;
        //Se verifica si el último tiempo de actualización supera al estipulado en el contrato
        if (horas >= ticket.getItemProductonumeroSerial().getContratonumero().getSlaid().getTiempoDeActualizacionDeEscalacion()) {
            String cuerpo = this.crearCuerpoDeCorreoTicketSLA(ticket);
            UtilidadDeEmail utilidadDeEmail = new UtilidadDeEmail();
            utilidadDeEmail.enviarMensajeConAdjunto("soporte@sinetcom.com.ec", ticket.getUsuarioidpropietario().getCorreoElectronico(), "Recordatorio - Ticket de Soporte# " + ticket.getTicketNumber(), cuerpo, null, null, null);
            System.out.println("Se acaba de enviar un correo de notificación del ticket# " + ticket.getTicketNumber() + " a " + ticket.getUsuarioidpropietario().getCorreoElectronico());
        } else {
            System.out.println("Se ha omitido notificador por haber actualizado el ticket# " + ticket.getTicketNumber());
        }
    }

    /**
     * Función que permite enviar un correo de notificación para primer contacto
     *
     * @param ticket
     */
    public void enviarNotificacionDePrimerContacto(Ticket ticket) {
        String cuerpo = this.crearCuerpoDeCorreoPrimerContactoTicket(ticket);
        UtilidadDeEmail utilidadDeEmail = new UtilidadDeEmail();
        utilidadDeEmail.enviarMensajeConAdjunto("soporte@sinetcom.com.ec", ticket.getUsuarioidpropietario().getCorreoElectronico(), "Primer Contacto - Ticket de Soporte# " + ticket.getTicketNumber(), cuerpo, null, null, null);
        System.out.println("Se acaba de enviar un correo de notificación del ticket# " + ticket.getTicketNumber() + " a " + ticket.getUsuarioidpropietario().getCorreoElectronico());
    }
}
