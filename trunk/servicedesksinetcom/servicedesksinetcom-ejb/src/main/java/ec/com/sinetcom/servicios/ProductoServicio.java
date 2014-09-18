/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.CategoriaProductoFacade;
import ec.com.sinetcom.dao.FabricanteFacade;
import ec.com.sinetcom.dao.ItemProductoFacade;
import ec.com.sinetcom.orm.CategoriaProducto;
import ec.com.sinetcom.orm.Fabricante;
import ec.com.sinetcom.orm.ItemProducto;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author diegoflores
 */
@Stateless
@LocalBean
public class ProductoServicio {

    @EJB
    private FabricanteFacade fabricanteFacade;
    @EJB
    private CategoriaProductoFacade categoriaProductoFacade;
    @EJB
    private ItemProductoFacade itemProductoFacade;
    
    /**
     * Servicio que obtiene todos los fabricantes
     * @return 
     */
    public List<Fabricante> obtenerTodosLosFabricantes(){
        return this.fabricanteFacade.findAll();
    }
    
    /**
     * Servicio que obtiene todos las categorias
     * @return 
     */
    public List<CategoriaProducto> obtenerTodasLasCategoriasProducto(){
        return this.categoriaProductoFacade.findAll();
    }
    
    /**
     * Servicio que obtiene todos los productos padre por categoria y fabricante
     * @param fabricante
     * @param categoriaProducto
     * @return 
     */
    public List<ItemProducto> obtenerTodosLosProductoPadrePorCategoriaDeFabricante(Fabricante fabricante, CategoriaProducto categoriaProducto){
        return this.itemProductoFacade.obtenerTodosLosProductosPadrePorCategoriaYFabricante(fabricante, categoriaProducto);
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
