/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.GarantiaEconomica;
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
public class GarantiaEconomicaFacade extends AbstractFacade<GarantiaEconomica> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GarantiaEconomicaFacade() {
        super(GarantiaEconomica.class);
    }
    
    public List<GarantiaEconomica> obtenerTodasLasGarantiasEconomicasPorVencerEnProx2Semanas(){
        String sql = "SELECT * FROM `GarantiaEconomica` GarantiaEconomica WHERE TIMESTAMPDIFF(DAY, NOW(),`GarantiaEconomica`.`fechaFin`) < 15 AND TIMESTAMPDIFF(DAY, NOW(),`GarantiaEconomica`.`fechaFin`) >= 0";
        Query qry = this.em.createNativeQuery(sql, GarantiaEconomica.class);
        return qry.getResultList();
    }
}
