/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.Pago;
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
public class PagoFacade extends AbstractFacade<Pago> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PagoFacade() {
        super(Pago.class);
    }
    
    public List<Pago> obtenerTodosLosPagosPorVencerEnProx2Semanas(){
        String sql = "SELECT * FROM `Pago` Pago WHERE TIMESTAMPDIFF(DAY, NOW(),`Pago`.`plazo`) < 15 AND TIMESTAMPDIFF(DAY, NOW(),`Pago`.`Plazo`) >= 0";
        Query qry = this.em.createNativeQuery(sql, Pago.class);
        return qry.getResultList();
    }
    
}
