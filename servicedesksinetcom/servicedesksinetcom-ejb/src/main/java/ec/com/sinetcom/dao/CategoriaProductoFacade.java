/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.CategoriaProducto;
import ec.com.sinetcom.orm.Fabricante;
import java.util.ArrayList;
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
public class CategoriaProductoFacade extends AbstractFacade<CategoriaProducto> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriaProductoFacade() {
        super(CategoriaProducto.class);
    }
    
    /**
     * Devuelve todos las categorias que no tiene un fabricante
     * @param fabricante
     * @return 
     */
    public List<CategoriaProducto> obtenerTodasLasCategoriasProductoOpuestasDeFabricante(Fabricante fabricante){
        if(fabricante.getCategoriaProductoList().isEmpty()){
            return this.findAll();
        }
        String sql = "SELECT c FROM CategoriaProducto c WHERE c.id NOT IN :lista";
        Query qry = this.em.createQuery(sql);
        List<String> idCategorias = new ArrayList<String>();
        for(CategoriaProducto categoria: fabricante.getCategoriaProductoList()){
            idCategorias.add(categoria.getId().toString());
        }
        qry.setParameter("lista", idCategorias);
        return qry.getResultList();
    }
    
}
