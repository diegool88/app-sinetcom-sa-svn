/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.VisitaTecnica;
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
public class VisitaTecnicaFacade extends AbstractFacade<VisitaTecnica> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VisitaTecnicaFacade() {
        super(VisitaTecnica.class);
    }
    
    /**
     * Obtiene todas las visitas técnicas programadas dentro de las 2 próximas semanas
     * @return 
     */
    public List<VisitaTecnica> obtenerTodasLasVisitasTecnicasEnProx2Semanas(){
        String sql = "SELECT * FROM `VisitaTecnica` VisitaTecnica WHERE TIMESTAMPDIFF(DAY, NOW(),`VisitaTecnica`.`fecha`) < 15 AND TIMESTAMPDIFF(DAY, NOW(),`VisitaTecnica`.`fecha`) >= 0";
        Query qry = this.em.createNativeQuery(sql, VisitaTecnica.class);
        return qry.getResultList();
    }
    
    /**
     * Obtiene todas las visitas técnicas programas para el día de hoy
     * @return 
     */
    public List<VisitaTecnica> obtenerTodasLasVisitasTecnicasProgramadasParaEsteDia(){
        String sql = "SELECT * FROM `VisitaTecnica` VisitaTecnica WHERE TIMESTAMPDIFF(DAY, NOW(),`VisitaTecnica`.`fecha`) = 0";
        Query qry = this.em.createNativeQuery(sql, VisitaTecnica.class);
        return qry.getResultList();
    }
}
