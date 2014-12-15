/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.DetalleDeMovimientoDeProducto;
import ec.com.sinetcom.orm.RegistroDeMovimientoDeInventario;
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
public class DetalleDeMovimientoDeProductoFacade extends AbstractFacade<DetalleDeMovimientoDeProducto> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DetalleDeMovimientoDeProductoFacade() {
        super(DetalleDeMovimientoDeProducto.class);
    }
    
    /**
     * Cargar el historial del registro de movimiento dado
     * @param registro
     * @return 
     */
    public List<DetalleDeMovimientoDeProducto> forzarCargaDeHistorialDeMovimientoPorRegistro(RegistroDeMovimientoDeInventario registro){
        String sql = "SELECT h FROM HistorialDeMovimientoDeProducto h WHERE h.registroDeMovimientoDeInventariocodigo = ?1";
        Query qry = this.em.createQuery(sql);
        qry.setParameter(1, registro);
        return qry.getResultList();
    }
    
}
