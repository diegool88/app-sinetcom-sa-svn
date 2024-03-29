/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.HistorialDeTicket;
import ec.com.sinetcom.orm.Ticket;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author diegoflores
 */
@Stateless
public class HistorialDeTicketFacade extends AbstractFacade<HistorialDeTicket> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistorialDeTicketFacade() {
        super(HistorialDeTicket.class);
    }
    
    /**
     * Función que permite obtener la fecha en que se realizó el contacto con el cliente
     * @param ticket
     * @return 
     */
    public Date obtenerFechaDePrimerContactoDeTicket(Ticket ticket){
        String sql = "SELECT h FROM HistorialDeTicket h JOIN h.eventoTicketcodigo e WHERE h.ticketticketNumber = ?1 AND e.codigo = :evento";
        Query qry = this.em.createQuery(sql);  
        qry.setParameter(1, ticket);
        qry.setParameter("evento", 7);
        return qry.getResultList().isEmpty() ? null : ((HistorialDeTicket)qry.getResultList().get(0)).getFechaDelEvento();
        
    }
    
    /**
     * Funcion que permite obtener el numero de la ultima entrada historica de un ticket
     * @param ticket
     * @return 
     */
    public int obtenerOrdenDeHistorialDeTicket(Ticket ticket){
        String sql = "SELECT h FROM HistorialDeTicket h WHERE h.ticketticketNumber = ?1 ORDER BY h.fechaDelEvento DESC";
        Query qry = this.em.createQuery(sql);  
        qry.setParameter(1, ticket);
        return qry.getResultList().size() + 1;
    }
    
}
