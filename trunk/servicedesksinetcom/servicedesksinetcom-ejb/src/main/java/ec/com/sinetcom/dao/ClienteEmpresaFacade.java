/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author diegoflores
 */
@Stateless
public class ClienteEmpresaFacade extends AbstractFacade<ClienteEmpresa> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteEmpresaFacade() {
        super(ClienteEmpresa.class);
    }
    
    public ClienteEmpresa obtenerClienteEmpresaPorRuc(String ruc){
        String sql = "SELECT c FROM ClienteEmpresa c WHERE c.ruc = ?1";
        Query qry = this.em.createQuery(sql);  
        qry.setParameter(1, ruc);
        return qry.getResultList().isEmpty() ? null : (ClienteEmpresa)qry.getResultList().get(0);
    }
    
    public ClienteEmpresa obtenerClienteEmpresaPorUsuario(Usuario usuario){
        String sql = "SELECT c FROM ClienteEmpresa c WHERE c.usuarioid = ?1";
        Query qry = this.em.createQuery(sql);  
        qry.setParameter(1, usuario);
        return qry.getResultList().isEmpty() ? null : (ClienteEmpresa)qry.getResultList().get(0);
    }
    
    public boolean tieneClienteEmpresaContratos(String ruc){
        String sql = "SELECT c FROM ClienteEmpresa c WHERE c.ruc = ?1";
        Query qry = this.em.createQuery(sql);  
        qry.setParameter(1, ruc);
        return !((ClienteEmpresa)qry.getSingleResult()).getContratoList().isEmpty();
    }
    
    public boolean cargarDatosClienteEmpresa(){
        Query qry = this.em.createNativeQuery("LOAD DATA LOCAL INFILE '/temp/ClienteEmpresa.csv' INTO TABLE ClienteEmpresa CHARACTER SET utf8 FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\\n' IGNORE 1 LINES;");
        return qry.executeUpdate() > 0;
    }
}
