/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.HistorialDeMovimientoDeProducto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author diegoflores
 */
@Stateless
public class HistorialDeMovimientoDeProductoFacade extends AbstractFacade<HistorialDeMovimientoDeProducto> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistorialDeMovimientoDeProductoFacade() {
        super(HistorialDeMovimientoDeProducto.class);
    }
    
}
