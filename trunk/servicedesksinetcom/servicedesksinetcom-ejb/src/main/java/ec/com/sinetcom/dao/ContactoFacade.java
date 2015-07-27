/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.Contacto;
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
public class ContactoFacade extends AbstractFacade<Contacto> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContactoFacade() {
        super(Contacto.class);
    }
    
    public List<Contacto> obtenerContactosDeCliente(ClienteEmpresa clienteEmpresa){
        String sql = "SELECT c FROM Contacto c WHERE c.clienteEmpresaid = ?1";
        Query qry = this.em.createQuery(sql);  
        qry.setParameter(1, clienteEmpresa);
        return qry.getResultList();
    }
    
    public boolean tieneContratosACargo(Integer contactoId){
        String sql = "SELECT c FROM Contacto c WHERE c.id = ?1";
        Query qry = this.em.createQuery(sql);  
        qry.setParameter(1, contactoId);
        return !((Contacto)qry.getSingleResult()).getContratoList().isEmpty();
    }
    
    /**
     * Permite obtener un contacto por correo electrónico
     * @param correoElectronico
     * @return 
     */
    public Contacto obtenerContactoPorCorreoElectronico(String correoElectronico){
        String sql = "SELECT c FROM Contacto c WHERE c.correoElectronico = ?1";
        Query qry = this.em.createQuery(sql);  
        qry.setParameter(1, correoElectronico);
        return qry.getResultList().isEmpty() ? null : (Contacto)qry.getSingleResult();
    }
    
    /**
     * Permite obtener un usuario por cedula de ciudadania
     * @param cedula
     * @return 
     */
    public Contacto obtenerContactoPorCedulaCiudadania(String cedula){
        String sql = "SELECT c FROM Contacto c WHERE c.cedulaDeCuidadania = ?1";
        Query qry = this.em.createQuery(sql);  
        qry.setParameter(1, cedula);
        return qry.getResultList().isEmpty() ? null : (Contacto)qry.getSingleResult();
    }
}
