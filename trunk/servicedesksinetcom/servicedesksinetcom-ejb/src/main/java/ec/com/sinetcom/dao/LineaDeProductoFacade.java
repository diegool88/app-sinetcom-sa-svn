/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.CategoriaProducto;
import ec.com.sinetcom.orm.Fabricante;
import ec.com.sinetcom.orm.LineaDeProducto;
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
public class LineaDeProductoFacade extends AbstractFacade<LineaDeProducto> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LineaDeProductoFacade() {
        super(LineaDeProducto.class);
    }
    
    /**
     * Obtiene todas la lineas de producto por categoria y fabricante
     * @param fabricante
     * @param categoriaProducto
     * @return 
     */
    public List<LineaDeProducto> obtenerTodasLasLineasDeProductoPorCategoriaYFabricante(Fabricante fabricante, CategoriaProducto categoriaProducto){
        String sql = "SELECT l FROM LineaDeProducto l WHERE l.fabricanteid = ?1 AND l.categoriaid = ?2";
        Query qry = this.em.createQuery(sql);
        qry.setParameter(1, fabricante);
        qry.setParameter(2, categoriaProducto);
        return qry.getResultList();
    }
    
}
