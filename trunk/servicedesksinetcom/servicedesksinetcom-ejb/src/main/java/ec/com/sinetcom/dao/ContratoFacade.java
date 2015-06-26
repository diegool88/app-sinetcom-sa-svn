/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.Contrato;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author diegoflores
 */
@Stateless
public class ContratoFacade extends AbstractFacade<Contrato> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContratoFacade() {
        super(Contrato.class);
    }
    
    public boolean cargarDatosContrato(){
        Query qry = this.em.createNativeQuery("LOAD DATA LOCAL INFILE '/temp/Contrato.csv' INTO TABLE Contrato CHARACTER SET utf8 FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\\n' IGNORE 1 LINES;");
        return qry.executeUpdate() > 0;
    }
    
    /**
     * Obtiene el número del secuencial del adendum de un contrato
     * @param contrato
     * @return 
     */
    public int obtenerIndiceDeAdendum(Contrato contrato){
        String sql = "SELECT c FROM Contrato c WHERE c.numero LIKE :contrato";
        Query qry = this.em.createQuery(sql);
        qry.setParameter("contrato", "%" + contrato.getNumero() + "%");
        return qry.getResultList().size();
    }
}
