/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.Ciudad;
import ec.com.sinetcom.orm.ClienteDireccion;
import ec.com.sinetcom.orm.ClienteEmpresa;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author diegoflores
 */
@Stateless
public class ClienteDireccionFacade extends AbstractFacade<ClienteDireccion> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteDireccionFacade() {
        super(ClienteDireccion.class);
    }
    
    /**
     * Permite verificar la existencia de una dirección
     * @param ciudad
     * @param clienteEmpresa
     * @return 
     */
    public ClienteDireccion existeDireccionCliente(Ciudad ciudad, ClienteEmpresa clienteEmpresa){
        String sql = "SELECT d FROM ClienteDireccion d WHERE d.clienteEmpresaid = ?1 AND d.ciudad = ?2";
        Query qry = this.em.createQuery(sql);
        qry.setParameter(1, clienteEmpresa);
        qry.setParameter(2, ciudad);
        return qry.getResultList().isEmpty() ? null : (ClienteDireccion)qry.getSingleResult();
    }
    
}
