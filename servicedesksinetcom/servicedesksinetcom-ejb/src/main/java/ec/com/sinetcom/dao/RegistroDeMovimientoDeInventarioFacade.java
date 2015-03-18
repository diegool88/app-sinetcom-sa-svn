/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.RegistroDeMovimientoDeInventario;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author diegoflores
 */
@Stateless
public class RegistroDeMovimientoDeInventarioFacade extends AbstractFacade<RegistroDeMovimientoDeInventario> {
    @EJB
    private TipoDeMovimientoFacade tipoDeMovimientoFacade;
    
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegistroDeMovimientoDeInventarioFacade() {
        super(RegistroDeMovimientoDeInventario.class);
    }
    
    /**
     * Devuelve todos los registro de mov. de inventario que se deben actualizar
     * @return 
     */
    public List<RegistroDeMovimientoDeInventario> obtenerTodosLosRegistrosDeMovimientoDeInventariosPorActualizar(){
        String sql = "SELECT r FROM RegistroDeMovimientoDeInventario r WHERE r.fechaDeEntrada IS NULL AND r.tipoDeMovimientoid <> ?1";
        Query qry = this.em.createQuery(sql);
        qry.setParameter(1, this.tipoDeMovimientoFacade.find(5));
        return qry.getResultList();
    }
    
    /**
     * Devuelve todos los registros de mov. de inventario que se están finalizados
     * @return 
     */
    public List<RegistroDeMovimientoDeInventario> obtenerTodosLosRegistrosDeMovimientoDeInventariosFinalizados(){
        String sql = "SELECT r FROM RegistroDeMovimientoDeInventario r WHERE r.fechaDeEntrada IS NOT NULL OR r.tipoDeMovimientoid = ?1";
        Query qry = this.em.createQuery(sql);
        qry.setParameter(1, this.tipoDeMovimientoFacade.find(5));
        return qry.getResultList();
    }
    
}
