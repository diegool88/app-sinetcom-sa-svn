/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.CategoriaProducto;
import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.Fabricante;
import ec.com.sinetcom.orm.ItemProducto;
import ec.com.sinetcom.orm.ModeloProducto;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
     * Función que permite obtener todos los equipos vendidos a determinado
     * cliente de todos sus contratos
     *
     * @param contratos
     * @return
     */
    public List<ItemProducto> obtenerTodosLosProductosDeCliente(List<Contrato> contratos) {
        String sql = "SELECT i FROM ItemProducto i WHERE i.contratonumero.numero IN :contratos AND i.itemProductonumeroSerialpadre IS NULL";
        Query qry = this.em.createQuery(sql);
        List<String> numeroContratos = new ArrayList<String>();
        for (Contrato contrato : contratos) {
            if (contrato.getFechaDeEntregaRecepcion() != null) {
                Calendar fechaMaximaDeValidez = Calendar.getInstance();
                fechaMaximaDeValidez.setTime(contrato.getFechaDeEntregaRecepcion());
                fechaMaximaDeValidez.add(Calendar.YEAR, contrato.getTiempoDeValidez());
                if (fechaMaximaDeValidez.getTime().compareTo(new Date()) >= 0) {
                    numeroContratos.add(contrato.getNumero());
                }
            }
        }
        qry.setParameter("contratos", numeroContratos);
        return qry.getResultList();
    }

    /**
     * Función que permite obtener todos los productos padre.
     *
     * @return
     */
    public List<ItemProducto> obtenerTodosLosProductosPadre() {
        String sql = "SELECT i FROM ItemProducto i WHERE i.itemProductonumeroSerialpadre IS NULL";
        Query qry = this.em.createQuery(sql);
        return qry.getResultList();
    }

    /**
     * Obtiene todos los Productos padre que no son elementos atómicos
     *
     * @param modeloProducto
     * @return
     */
    public List<ItemProducto> obtenerTodosLosProductosPadrePorModelo(ModeloProducto modeloProducto) {
        String sql = "SELECT i FROM ItemProducto i WHERE i.modeloProductoid = ?1 AND i.componenteElectronicoAtomicoid IS NULL";
        Query qry = this.em.createQuery(sql);
        qry.setParameter(1, modeloProducto);
        return qry.getResultList();
    }

    /**
     * Obtiene todos los Productos Padre por categoria y fabricante
     *
     * @param fabricante
     * @param categoriaProducto
     * @return
     */
    public List<ItemProducto> obtenerTodosLosProductosPadrePorCategoriaYFabricante(Fabricante fabricante, CategoriaProducto categoriaProducto) {
        String sql = "SELECT i FROM ItemProducto i JOIN i.modeloProductoid m JOIN m.lineaDeProductoid l WHERE i.itemProductonumeroSerialpadre IS NULL AND ( l.fabricanteid = ?1 AND l.categoriaid = ?2 )";
        Query qry = this.em.createQuery(sql);
        qry.setParameter(1, fabricante);
        qry.setParameter(2, categoriaProducto);
        return qry.getResultList();
    }

    /**
     * Obtiene todo el inventario que se encuentra disponible en bodega sin
     * partes dañadas
     *
     * @return
     */
    public List<ItemProducto> obtenerTodoElInventarioDisponibleEnBodegaLocal() {
        String sql = "SELECT i FROM ItemProducto i JOIN i.condicionFisicaid c WHERE i.contratonumero IS NULL AND i.bodegaid IS NOT NULL AND ( c.id = :id1 OR c.id = :id2 )";
        Query qry = this.em.createQuery(sql);
        qry.setParameter("id1", 1);
        qry.setParameter("id2", 3);
        return qry.getResultList();
    }

    /**
     * Obtiene todo el stock que se encuentra en las bodegas de Sinetcom
     *
     * @return
     */
    public List<ItemProducto> obtenerTodoElInventarioDisponibleEnBogegaLocalConPartesDanadas() {
        String sql = "SELECT i FROM ItemProducto i WHERE i.contratonumero IS NULL AND i.bodegaid IS NOT NULL";
        Query qry = this.em.createQuery(sql);
        return qry.getResultList();
    }

    /**
     * Obtiene todos el inventario dañado
     *
     * @return
     */
    public List<ItemProducto> obtenerTodoElInventarioDanado() {
        String sql = "SELECT i FROM ItemProducto i JOIN i.condicionFisicaid c WHERE i.historialDeMovimientoDeProductoEntraList IS EMPTY AND c.id = :id";
        Query qry = this.em.createQuery(sql);
        qry.setParameter("id", 2);
        return qry.getResultList();
    }

    /**
     * Obtiene todo el inventario de un contrato
     *
     * @param contrato
     * @return
     */
    public List<ItemProducto> obtenerTodoElInventarioDeUnContrato(Contrato contrato) {
        String sql = "SELECT i FROM ItemProducto i WHERE i.contratonumero = ?1";
        Query qry = this.em.createQuery(sql);
        qry.setParameter(1, contrato);
        return qry.getResultList();
    }
}
