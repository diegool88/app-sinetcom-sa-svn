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
        String sql = "SELECT c FROM Contacto c WHERE c.clienteEmpresaruc = ?1";
        Query qry = this.em.createNamedQuery(sql);  
        qry.setParameter(1, clienteEmpresa);
        return qry.getResultList();
    }
    
}
