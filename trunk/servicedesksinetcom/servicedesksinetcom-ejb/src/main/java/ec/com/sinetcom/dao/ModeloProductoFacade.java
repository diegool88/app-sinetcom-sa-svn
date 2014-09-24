/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.CategoriaProducto;
import ec.com.sinetcom.orm.Fabricante;
import ec.com.sinetcom.orm.ModeloProducto;
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
public class ModeloProductoFacade extends AbstractFacade<ModeloProducto> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ModeloProductoFacade() {
        super(ModeloProducto.class);
    }
    
    /**
     * Obtiene todos los modelos por categoria de fabricante
     * @param fabricante
     * @param categoriaProducto
     * @return 
     */
    public List<ModeloProducto> obtenerTodosLosModelosPorCategoriaDeFabricante(Fabricante fabricante, CategoriaProducto categoriaProducto){
        String sql = "SELECT m FROM ModeloProducto m JOIN m.lineaDeProductoid l WHERE l.fabricanteid = ?1 AND l.categoriaid = ?2";
        Query qry = this.em.createQuery(sql);
        qry.setParameter(1, fabricante);
        qry.setParameter(2, categoriaProducto);
        return qry.getResultList();
    }
}
