/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.Curso;
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
public class CursoFacade extends AbstractFacade<Curso> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CursoFacade() {
        super(Curso.class);
    }
    
    /**
     * Busca todos los cursos que se darán en las próximas 2 semanas
     * @return 
     */
    public List<Curso> obtenerTodosLosCursosADictarEnProx2Semanas(){
        String sql = "SELECT * FROM `Curso` Curso WHERE TIMESTAMPDIFF(DAY, NOW(),`Curso`.`fechaDeInicio`) < 15 AND TIMESTAMPDIFF(DAY, NOW(),`Curso`.`fechaDeInicio`) >= 0";
        Query qry = this.em.createNativeQuery(sql, Curso.class);
        return qry.getResultList();
    }
    
}
