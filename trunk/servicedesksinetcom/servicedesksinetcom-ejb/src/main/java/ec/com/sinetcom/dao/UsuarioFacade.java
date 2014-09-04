/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.Competencias;
import ec.com.sinetcom.orm.Grupo;
import ec.com.sinetcom.orm.Usuario;
import java.util.ArrayList;
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
public class UsuarioFacade extends AbstractFacade<Usuario> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @EJB
    private GrupoFacade grupoFacade;
    
    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    /**
     * Permite obtener las competencias de un usuario (No funciona)
     * @param competencias
     * @return 
     */
    public List<Usuario> obtenerUsuariosPorCompetencias(Competencias competencias){
        String sql = "SELECT u FROM Usuario u INNER JOIN u.competenciasList c WHERE c.id = ?1";
        Query qry = this.em.createQuery(sql);
        qry.setParameter(1, competencias.getId());
        return qry.getResultList().isEmpty() ? null : qry.getResultList();
    }
    
    /**
     * Permite verificar la existencia de un usuario registrado
     * @param cuenta
     * @param password
     * @return 
     */
    public Usuario verificarExistenciaDeUsuario(String cuenta, String password){
        String sql = "SELECT u FROM Usuario u WHERE u.correoElectronico = ?1 AND u.password = ?2";
        Query qry = this.em.createQuery(sql);  
        qry.setParameter(1, cuenta);
        qry.setParameter(2, password);
        return qry.getResultList().isEmpty() ? null : (Usuario)qry.getSingleResult();
    }
    
    /**
     * Permite obtener un usuario por correo electrónico
     * @param correoElectrocnico
     * @return 
     */
    public Usuario obtenerUsuarioPorCorreoElectronico(String correoElectrocnico){
        String sql = "SELECT u FROM Usuario u WHERE u.correoElectronico = ?1";
        Query qry = this.em.createQuery(sql);  
        qry.setParameter(1, correoElectrocnico);
        return qry.getResultList().isEmpty() ? null : (Usuario)qry.getSingleResult();
    }
    
    /**
     * Obtener todos los ingenieros de soporte
     * @return 
     */
    public List<Usuario> obtenerTodosLosIngenieros(){
        String sql = "SELECT u FROM Usuario u JOIN u.grupoid g WHERE g.id = :grupo1 OR g.id = :grupo2";
        Query qry = this.em.createQuery(sql);
        qry.setParameter("grupo1", 2);
        qry.setParameter("grupo2", 3);
        return qry.getResultList();
    }
    
}
