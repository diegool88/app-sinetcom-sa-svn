/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.ActividadEnSitio;
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
public class ActividadEnSitioFacade extends AbstractFacade<ActividadEnSitio> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActividadEnSitioFacade() {
        super(ActividadEnSitio.class);
    }
    
    /**
     * Función que obtiene todas las actividades en sitio de un ticket
     * @param ticket
     * @return 
     */
    public List<ActividadEnSitio> obtenerTodasLasActividadesDeUnTicket(Ticket ticket){
        String sql = "SELECT a FROM ActividadEnSitio a WHERE a.ticketticketNumber = ?1";
        Query qry = this.em.createQuery(sql);
        qry.setParameter(1, ticket);
        return qry.getResultList().isEmpty() ? null : qry.getResultList();
    }
    
}
