/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

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
     * @param correoElectronico
     * @return 
     */
    public Usuario obtenerUsuarioPorCorreoElectronico(String correoElectronico){
        String sql = "SELECT u FROM Usuario u WHERE u.correoElectronico = ?1";
        Query qry = this.em.createQuery(sql);  
        qry.setParameter(1, correoElectronico);
        return qry.getResultList().isEmpty() ? null : (Usuario)qry.getSingleResult();
    }
    
    /**
     * Permite obtener un usuario por cedula de ciudadania
     * @param cedula
     * @return 
     */
    public Usuario obtenerUsuarioPorCedulaCiudadania(String cedula){
        String sql = "SELECT u FROM Usuario u WHERE u.cedulaDeCuidadania = ?1";
        Query qry = this.em.createQuery(sql);  
        qry.setParameter(1, cedula);
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
    
    /**
     * Obtiene todos los clientes de la empresa
     * @return 
     */
    public List<Usuario> obtenerTodosLosClientes(){
        String sql = "SELECT u FROM Usuario u JOIN u.grupoid g WHERE g.id = :grupo";
        Query qry = this.em.createQuery(sql);
        qry.setParameter("grupo", 4);
        return qry.getResultList();
    }
    
    /**
     * Obtiene todos los account manager
     * @return 
     */
    public List<Usuario> obtenerTodosLosAccountManager(){
        String sql = "SELECT u FROM Usuario u JOIN u.grupoid g WHERE g.id = :grupo";
        Query qry = this.em.createQuery(sql);
        qry.setParameter("grupo", 1);
        return qry.getResultList();
    }
    
    /**
     * Verifica si el usuario tiene asociación
     * @param usuarioId
     * @return 
     */
    public boolean tieneAsociacionConAlgunaEntidad(Integer usuarioId){
        String sql = "SELECT u FROM Usuario u WHERE u.id = ?1";
        Query qry = this.em.createQuery(sql);
        qry.setParameter(1, usuarioId);
        Usuario usuario = ((Usuario)qry.getSingleResult());
        return !usuario.getClienteEmpresaList().isEmpty() && !usuario.getTicketList().isEmpty() && !usuario.getTicketList1().isEmpty() && !usuario.getDetalleDeMovimientoDeProductoList().isEmpty();
    }
}
