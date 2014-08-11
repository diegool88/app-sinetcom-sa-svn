/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.EstadoTicket;
import ec.com.sinetcom.orm.PrioridadTicket;
import ec.com.sinetcom.orm.ServicioTicket;
import ec.com.sinetcom.orm.Ticket;
import ec.com.sinetcom.orm.Usuario;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author diegoflores
 */
@Stateless
public class TicketFacade extends AbstractFacade<Ticket> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TicketFacade() {
        super(Ticket.class);
    }
    
    /**
     * Función que permite recuperar los tickets por estado de un determinado usuario propietario
     * @param usuario
     * @param estadoTicket
     * @return 
     */
    public List<Ticket> obtenerTicketsPorEstadoDePropietario(Usuario usuario, EstadoTicket estadoTicket){
        String sql = "SELECT t FROM Ticket t WHERE t.usuarioidpropietario = ?1 AND t.estadoTicketcodigo = ?2";
        Query qry = this.em.createNamedQuery(sql);  
        qry.setParameter(1, usuario);
        qry.setParameter(2, estadoTicket);
        return qry.getResultList();
    }
    
    /**
     * Función que permite recuperar los tickets por estado de un determinado usuario propietario (Ingresa String de estado)
     * @param usuario
     * @param estadoTicket
     * @return 
     */
    public List<Ticket> obtenerTicketsPorEstadoDePropietario(Usuario usuario, String estadoTicket){
        String sql = "SELECT t FROM Ticket t WHERE t.usuarioidpropietario = ?1 AND t.estadoTicketcodigo.nombre LIKE :estado";
        Query qry = this.em.createNamedQuery(sql);  
        qry.setParameter(1, usuario);
        qry.setParameter("estado", estadoTicket);
        return qry.getResultList();
    }
    
    /**
     * Función que permite recuperar los tickets por servicios de un determinado usuario propietario
     * @param usuario
     * @param servicioTicket
     * @return 
     */
    public List<Ticket> obtenerTicketsPorServicioDePropietario(Usuario usuario, ServicioTicket servicioTicket){
        String sql = "SELECT t FROM Ticket t WHERE t.usuarioidpropietario = ?1 AND t.servicioTicketcodigo = ?2";
        Query qry = this.em.createNamedQuery(sql);  
        qry.setParameter(1, usuario);
        qry.setParameter(2, servicioTicket);
        return qry.getResultList();
    }
    
    /**
     * Función que permite recuperar los tickets por servicios de un determinado usuario propietario (Ingresa string de servicio)
     * @param usuario
     * @param servicioTicket
     * @return 
     */
    public List<Ticket> obtenerTicketsPorServicioDePropietario(Usuario usuario, String servicioTicket){
        String sql = "SELECT t FROM Ticket t WHERE t.usuarioidpropietario = ?1 AND t.servicioTicketcodigo.nombre LIKE :servicio";
        Query qry = this.em.createNamedQuery(sql);  
        qry.setParameter(1, usuario);
        qry.setParameter("servicio", servicioTicket);
        return qry.getResultList();
    }
    
    /**
     * Función que permite recuperar los tickets por prioridad de un determinado usuario propietario
     * @param usuario
     * @param prioridadTicket
     * @return 
     */
    public List<Ticket> obtenerTicketsPorPrioridadDePropietario(Usuario usuario, PrioridadTicket prioridadTicket){
        String sql = "SELECT t FROM Ticket t WHERE t.usuarioidpropietario = ?1 AND t.prioridadTicketcodigo = ?2";
        Query qry = this.em.createNamedQuery(sql);  
        qry.setParameter(1, usuario);
        qry.setParameter(2, prioridadTicket);
        return qry.getResultList();
    }
    
    /**
     * Función que permite recuperar los tickets por prioridad de un determinado usuario propietario
     * @param usuario
     * @param prioridadTicket
     * @return 
     */
    public List<Ticket> obtenerTicketsPorPrioridadDePropietario(Usuario usuario, String prioridadTicket){
        String sql = "SELECT t FROM Ticket t WHERE t.usuarioidpropietario = ?1 AND t.prioridadTicketcodigo.nombre LIKE :prioridad";
        Query qry = this.em.createNamedQuery(sql);  
        qry.setParameter(1, usuario);
        qry.setParameter("prioridad", prioridadTicket);
        return qry.getResultList();
    }
    
    /**
     * Función que permite recuperar todos los tickets por estado
     * @param estado
     * @return 
     */
    public List<Ticket> obtenerTodosLosTicketsPorEstado(String estado){
        String sql = "SELECT t FROM Ticket t WHERE t.estadoTicketcodigo.nombre LIKE :estado";
        Query qry = this.em.createNamedQuery(sql);  
        qry.setParameter("estado", estado);
        return qry.getResultList();
    }
    
    /**
     * Función que permite recuperar todos los tickets por prioridad
     * @param prioridad
     * @return 
     */
    public List<Ticket> obtenerTodosLosTicketsPorPrioridad(String prioridad){
        String sql = "SELECT t FROM Ticket t WHERE t.estadoTicketcodigo.nombre LIKE :prioridad";
        Query qry = this.em.createNamedQuery(sql);  
        qry.setParameter("prioridad", prioridad);
        return qry.getResultList();
    }
    
    /**
     * Función que permite recuperar todos los tickets por servicio
     * @param servicio
     * @return 
     */
    public List<Ticket> obtenerTodosLosTicketsPorServicio(String servicio){
        String sql = "SELECT t FROM Ticket t WHERE t.estadoTicketcodigo.nombre LIKE :servicio";
        Query qry = this.em.createNamedQuery(sql);  
        qry.setParameter("servicio", servicio);
        return qry.getResultList();
    }
    
    /**
     * Función que permite recuperar todos los tickets que han sido atendidos en el último mes
     * @return 
     */
    public List<Ticket> obtenerTodosLosTicketsAtendidosElUltimoMes(){
        
        int mes = Calendar.getInstance().get(Calendar.MONTH)+1;
        int anio = Calendar.getInstance().get(Calendar.YEAR);
        
        String sql = "SELECT t FROM Ticket t WHERE FUNC('MONTH',t.fechaDeCreacion) = ?1 AND FUNC('YEAR',t.fechaDeCreacion) = ?2";
        Query qry = this.em.createNamedQuery(sql);  
        qry.setParameter(1, mes);
        qry.setParameter(2, anio);
        return qry.getResultList();
    }
    
    /**
     * Función que permite recuperar todos los tickets que han sido atendidos en el último año
     * @return 
     */
    public List<Ticket> obtenerTodosLosTicketsAtendidosElUltimoAnio(){
        
        int anio = Calendar.getInstance().get(Calendar.YEAR);
        
        String sql = "SELECT t FROM Ticket t WHERE AND FUNC('YEAR',t.fechaDeCreacion) = ?1";
        Query qry = this.em.createNamedQuery(sql);  
        qry.setParameter(1, anio);
        return qry.getResultList();
    }
    
    /**
     * Función que permite recuperar todos los tickets que han sido atendidos en el último mes por cliente
     * @param clienteEmpresa
     * @return 
     */
    public List<Ticket> obtenerTodosLosTicketsAtendidosElUltimoMesPorCliente(ClienteEmpresa clienteEmpresa){
        
        int mes = Calendar.getInstance().get(Calendar.MONTH)+1;
        int anio = Calendar.getInstance().get(Calendar.YEAR);
        
        String sql = "SELECT t FROM Ticket t WHERE FUNC('MONTH',t.clienteEmpresaruc) = ?1 AND FUNC('YEAR',t.fechaDeCreacion) = ?2 AND t.clienteEmpresaruc = ?3";
        Query qry = this.em.createNamedQuery(sql);  
        qry.setParameter(1, mes);
        qry.setParameter(2, anio);
        qry.setParameter(3, clienteEmpresa);
        return qry.getResultList();
    }
    
    /**
     * Función que permite recuperar todos los tickets que han sido atendidos en el último año por cliente
     * @param clienteEmpresa
     * @return 
     */
    public List<Ticket> obtenerTodosLosTicketsAtendidosElUltimoAnioPorCliente(ClienteEmpresa clienteEmpresa){

        int anio = Calendar.getInstance().get(Calendar.YEAR);
        
        String sql = "SELECT t FROM Ticket t WHERE FUNC('YEAR',t.fechaDeCreacion) = ?1 AND t.clienteEmpresaruc = ?2";
        Query qry = this.em.createNamedQuery(sql);  
        qry.setParameter(1, anio);
        qry.setParameter(2, clienteEmpresa);
        return qry.getResultList();
    }
    
}
