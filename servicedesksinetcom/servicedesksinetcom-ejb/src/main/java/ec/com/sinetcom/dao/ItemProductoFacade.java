/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.CategoriaProducto;
import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.Fabricante;
import ec.com.sinetcom.orm.ItemProducto;
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
public class ItemProductoFacade extends AbstractFacade<ItemProducto> {
    @PersistenceContext(unitName = "ec.com.sinetcom_servicedesksinetcom-ejb_ejb_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemProductoFacade() {
        super(ItemProducto.class);
    }
    
    /**
     * Función que permite obtener todos los equipos vendidos a determinado cliente de todos sus contratos
     * @param contratos
     * @return 
     */
    public List<ItemProducto> obtenerTodosLosProductosDeCliente(List<Contrato> contratos){
        String sql = "SELECT i FROM ItemProducto i WHERE i.contratonumero.numero IN :contratos";
        Query qry = this.em.createQuery(sql);
        List<String> numeroContratos = new ArrayList<String>();
        for(Contrato contrato: contratos){
            numeroContratos.add(contrato.getNumero());
        }
        qry.setParameter("contratos", numeroContratos);
        return qry.getResultList();
    }
    
    /**
     * Función que permite obtener todos los productos padre.
     * @return 
     */
    public List<ItemProducto> obtenerTodosLosProductosPadre(){
        String sql = "SELECT i FROM ItemProducto i WHERE i.itemProductonumeroSerialpadre IS NULL";
        Query qry = this.em.createQuery(sql);
        return qry.getResultList();
    }
    
    /**
     * Obtiene todos los Productos Padre por categoria y fabricante
     * @param fabricante
     * @param categoriaProducto
     * @return 
     */
    public List<ItemProducto> obtenerTodosLosProductosPadrePorCategoriaYFabricante(Fabricante fabricante, CategoriaProducto categoriaProducto){
        String sql = "SELECT i FROM ItemProducto i JOIN i.modeloProductoid m JOIN m.lineaDeProductoid l WHERE i.itemProductonumeroSerialpadre IS NULL AND ( l.fabricanteid = ?1 AND l.categoriaid = ?2 )";
        Query qry = this.em.createQuery(sql);
        qry.setParameter(1, fabricante);
        qry.setParameter(2, categoriaProducto);
        return qry.getResultList();
    }
}
