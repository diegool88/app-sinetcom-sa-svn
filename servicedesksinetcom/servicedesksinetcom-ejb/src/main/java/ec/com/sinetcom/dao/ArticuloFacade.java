/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.Articulo;
import ec.com.sinetcom.orm.Ticket;
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
public class ArticuloFacade extends AbstractFacade<Articulo> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArticuloFacade() {
        super(Articulo.class);
    }
    
    /**
     * Obtemos todos los articulos de un ticket
     * @param ticket
     * @return 
     */
    public List<Articulo> obtenerTodosLosArticulosDeUnTicket(Ticket ticket){
        String sql = "SELECT a FROM Articulo a WHERE a.ticketticketNumber = ?1";
        Query qry = this.em.createQuery(sql);
        qry.setParameter(1, ticket);
        return qry.getResultList().isEmpty() ? null : qry.getResultList();
    }
    
    /**
     * Obtener orden de Articulo
     * @param ticket
     * @return 
     */
    public int obtenerOrdenDeArticuloTicket(Ticket ticket){
        String sql = "SELECT COUNT(a.id) FROM Articulo a WHERE a.ticketticketNumber = ?1";
        Query qry = this.em.createQuery(sql);
        qry.setParameter(1, ticket);
        return ((Number)qry.getSingleResult()).intValue() + 1;
    }
}
