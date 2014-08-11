/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.Competencias;
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
public class UsuarioFacade extends AbstractFacade<Usuario> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public List<Usuario> obtenerUsuariosPorCompetencias(Competencias competencias){
        String sql = "SELECT u FROM Usuario u WHERE :competencias IN u.competenciasList";
        Query qry = this.em.createNamedQuery(sql);  
        qry.setParameter("competencias", competencias);
        return qry.getResultList();
    }
    
}
