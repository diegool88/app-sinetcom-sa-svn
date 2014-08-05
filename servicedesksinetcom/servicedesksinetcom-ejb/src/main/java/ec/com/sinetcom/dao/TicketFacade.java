/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.EstadoTicket;
import ec.com.sinetcom.orm.PrioridadTicket;
import ec.com.sinetcom.orm.ServicioTicket;
import ec.com.sinetcom.orm.Ticket;
import ec.com.sinetcom.orm.Usuario;
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
     */
    public List<Ticket> obtenerTodosLosTicketsPorEstado(String estado){
        String sql = "SELECT t FROM Ticket t JOIN t.estadoTicketcodigo e WHERE e.nombre LIKE :estado";
        Query qry = this.em.createNamedQuery(sql);  
        qry.setParameter("estado", estado);
        return qry.getResultList();
    }
    
    
}
