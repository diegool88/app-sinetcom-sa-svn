/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

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
     * Función que permite recuperar los tickets abiertos de un determinado usuario propietario
     */
    public List<Ticket> obtenerTicketsAbiertosDePropietario(Usuario usuario){
        String sql = "SELECT t FROM Ticket t JOIN t.estadoTicketcodigo e WHERE t.usuarioidpropietario = ?1 AND l.nombre LIKE :estado";
        Query qry = this.em.createNamedQuery(sql);  
        qry.setParameter(1, usuario);
        qry.setParameter("estado", "abierto");
        return qry.getResultList();
    }
}
