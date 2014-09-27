/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.BodegaFacade;
import ec.com.sinetcom.dao.CategoriaProductoFacade;
import ec.com.sinetcom.dao.ComponenteElectronicoAtomicoFacade;
import ec.com.sinetcom.dao.CondicionFisicaFacade;
import ec.com.sinetcom.dao.ContratoFacade;
import ec.com.sinetcom.dao.FabricanteFacade;
import ec.com.sinetcom.dao.ItemProductoFacade;
import ec.com.sinetcom.dao.LineaDeProductoFacade;
import ec.com.sinetcom.dao.ModeloProductoFacade;
import ec.com.sinetcom.dao.ParametrosDeProductoFacade;
import ec.com.sinetcom.dao.UnidadMedidaFacade;
import ec.com.sinetcom.orm.AtributoItemProducto;
import ec.com.sinetcom.orm.AtributoItemProductoPK;
import ec.com.sinetcom.orm.Bodega;
import ec.com.sinetcom.orm.CategoriaProducto;
import ec.com.sinetcom.orm.ComponenteElectronicoAtomico;
import ec.com.sinetcom.orm.CondicionFisica;
import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.Fabricante;
import ec.com.sinetcom.orm.ItemProducto;
import ec.com.sinetcom.orm.LineaDeProducto;
import ec.com.sinetcom.orm.ModeloProducto;
import ec.com.sinetcom.orm.ParametrosDeProducto;
import ec.com.sinetcom.orm.UnidadMedida;
import java.util.ArrayList;
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
    @EJB
    private ModeloProductoFacade modeloProductoFacade;
    @EJB
    private LineaDeProductoFacade lineaDeProductoFacade;
    @EJB
    private ComponenteElectronicoAtomicoFacade componenteElectronicoAtomicoFacade;
    @EJB
    private ContratoFacade contratoFacade;
    @EJB
    private BodegaFacade bodegaFacade;
    @EJB
    private CondicionFisicaFacade condicionFisicaFacade;
    @EJB
    private ParametrosDeProductoFacade parametrosDeProductoFacade;
    @EJB
    private UnidadMedidaFacade unidadMedidaFacade;
    
    /**
     * Servicio que permite crear un nuevo componente electrónico
     * @param atomico 
     */
    public void crearComponenteElectronico(ComponenteElectronicoAtomico atomico){
        this.componenteElectronicoAtomicoFacade.create(atomico);
    }
    
    /**
     * Servicio que obtiene todos los parametros declarados
     * @return 
     */
    public List<ParametrosDeProducto> obtenerTodosLosParametrosDeProducto(){
        return this.parametrosDeProductoFacade.findAll();
    }
    
    /**
     * Servicio que obtiene todas la unidades de medida
     * @return 
     */
    public List<UnidadMedida> obtenerTodasLasUnidadesDeMedida(){
        return this.unidadMedidaFacade.findAll();
    }
    
    /**
     * Obtiene un parametro producto
     * @param id
     * @return 
     */
    public ParametrosDeProducto obtenerParametroProducto(int id){
        return this.parametrosDeProductoFacade.find(id);
    }
    
    /**
     * Obtiene una unidad de medida
     * @param id
     * @return 
     */
    public UnidadMedida obtenerUnidadMedida(int id){
        return this.unidadMedidaFacade.find(id);
    }
    
    /**
     * Crea un parámetro producto
     * @param parametrosDeProducto 
     */
    public void crearParametroProducto(ParametrosDeProducto parametrosDeProducto){
        this.parametrosDeProductoFacade.create(parametrosDeProducto);
    }
    
    /**
     * Obtiene todos los componentes electronicos atómicos
     * @return 
     */
    public List<ComponenteElectronicoAtomico> obtenerTodosLosComponentesElectronicos(){
        return this.componenteElectronicoAtomicoFacade.findAll();
    }
    
    /**
     * Elimina un componente electrónico
     * @param electronicoAtomico
     * @return 
     */
    public boolean eliminarComponenteElectronico(int electronicoAtomico){
        try{
            ComponenteElectronicoAtomico componenteElectronicoAtomico = this.componenteElectronicoAtomicoFacade.find(electronicoAtomico);
            this.componenteElectronicoAtomicoFacade.remove(componenteElectronicoAtomico);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
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
     * Obtiene todas las bodegas
     * @return 
     */
    public List<Bodega> obtenerTodasLasBodegas(){
        return this.bodegaFacade.findAll();
    }
    
    /**
     * Obtiene todas las condiciones físicas
     * @return 
     */
    public List<CondicionFisica> obtenerTodasLasCondicionesFisicas(){
        return this.condicionFisicaFacade.findAll();
    }
    
    /**
     * Obtiene todos los contratos
     * @return 
     */
    public List<Contrato> obtenerTodosLosContratos(){
        return this.contratoFacade.findAll();
    }
    
    /**
     * Obtiene todas las líneas de producto
     * @return 
     */
    public List<LineaDeProducto> obtenerTodasLasLineasDeProducto(){
        return this.lineaDeProductoFacade.findAll();
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
    
    /**
     * Servicio que obtiene  todos los productos padre por modelo, exectuando a los atómicos
     * @param modeloProducto
     * @return 
     */
    public List<ItemProducto> obtenerTodosLosProductosPadrePorModelo(ModeloProducto modeloProducto){
        return this.itemProductoFacade.obtenerTodosLosProductosPadrePorModelo(modeloProducto);
    }
    
    /**
     * Servicio que obtiene todos los modelos por categoria y fabricante
     * @param fabricante
     * @param categoriaProducto
     * @return 
     */
    public List<ModeloProducto> obtenerTodosLosModelosPorCategoriaDeFabricante(Fabricante fabricante, CategoriaProducto categoriaProducto){
        return this.modeloProductoFacade.obtenerTodosLosModelosPorCategoriaDeFabricante(fabricante, categoriaProducto);
    }
    
    /**
     * Servicio que obtiene todas las lineas de prodcuto por categoria y fabricante
     * @param fabricante
     * @param categoriaProducto
     * @return 
     */
    public List<LineaDeProducto> obtenerTodasLasLineasDeProductoPorCategoriaYFabricante(Fabricante fabricante, CategoriaProducto categoriaProducto){
        return this.lineaDeProductoFacade.obtenerTodasLasLineasDeProductoPorCategoriaYFabricante(fabricante, categoriaProducto);
    }
    
    /**
     * Obtiene todos los componentes atómicos
     * @return 
     */
    public List<ComponenteElectronicoAtomico> obtenerTodosLosComponentesEA(){
        return this.componenteElectronicoAtomicoFacade.findAll();
    }
    
    /**
     * Obtiene todos los modelos de productos
     * @return 
     */
    public List<ModeloProducto> obtenerTodosLosModelosDeProductos(){
        return this.modeloProductoFacade.findAll();
    }
    
    /**
     * Obtiene un fabricante por Id
     * @param id
     * @return 
     */
    public Fabricante obtenerFabricantePorId(int id){
        return this.fabricanteFacade.find(id);
    }
    
    /**
     * Obtiene una categoria producto por id
     * @param id
     * @return 
     */
    public CategoriaProducto obtenerCategoriaProductoPorId(int id){
        return this.categoriaProductoFacade.find(id);
    }
    
    /**
     * Obtiene una linea de producto por id
     * @param id
     * @return 
     */
    public LineaDeProducto obtenerLineaDeProductoPorId(int id){
        return this.lineaDeProductoFacade.find(id);
    }
    
    /**
     * Obtiene un modelo de producto por id
     * @param id
     * @return 
     */
    public ModeloProducto obtenerUnModeloPorId(int id){
        return this.modeloProductoFacade.find(id);
    }
    
    /**
     * Obtiene un componente electrónico por Id
     * @param id
     * @return 
     */
    public ComponenteElectronicoAtomico obtenerComponenteElectronicoAPorId(int id){
        return this.componenteElectronicoAtomicoFacade.find(id);
    }
    
    /**
     * Obtiene un contrato por Id
     * @param id
     * @return 
     */
    public Contrato obtenerContratoPorId(String id){
        return this.contratoFacade.find(id);
    }
    
    /**
     * Obtiene una condición física por Id
     * @param id
     * @return 
     */
    public CondicionFisica obtenerCondicionFisicaPorId(int id){
        return this.condicionFisicaFacade.find(id);
    }
    
    /**
     * Obtiene una bodega por Id
     * @param id
     * @return 
     */
    public Bodega obtenerBodegaPorId(int id){
        return this.bodegaFacade.find(id);
    }
    
    /**
     * Verifica la exitencia del número serial ingresado
     * @param numSerial
     * @return 
     */
    public boolean existeNumeroSerial(String numSerial){
        ItemProducto producto = this.itemProductoFacade.find(numSerial);
        return producto != null;
    }
    
    /**
     * Ingresa un nuevo producto electrónico atómico
     * @param itemProducto
     * @param atributos
     * @return 
     */
    public boolean ingresarNuevoProductoComponenteAtomico(ItemProducto itemProducto, List<AtributoItemProducto> atributos){
        try{
            this.itemProductoFacade.create(itemProducto);
            List<AtributoItemProducto> clon = new ArrayList<AtributoItemProducto>(atributos.size());
            for(AtributoItemProducto atributo : atributos){
                clon.add((AtributoItemProducto)atributo.clone());
            }
            for(AtributoItemProducto atributo : clon){
                
                AtributoItemProductoPK itemProductoPK = new AtributoItemProductoPK(atributo.getParametrosDeProducto().getId(), itemProducto.getNumeroSerial());
                atributo.setAtributoItemProductoPK(itemProductoPK);
            }
            itemProducto.setAtributoItemProductoList(clon);
            this.itemProductoFacade.edit(itemProducto);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Ingresa un nuevo producto electrónico atómico
     * @param itemProducto
     * @return 
     */
    public boolean ingresarNuevoProducto(ItemProducto itemProducto){
        try{
            this.itemProductoFacade.create(itemProducto);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Ingresa una nueva línea de producto
     * @param lineaDeProducto
     * @return 
     */
    public boolean ingresarNuevaLineaDeProducto(LineaDeProducto lineaDeProducto){
        try{
            this.lineaDeProductoFacade.create(lineaDeProducto);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Elimina una línea de Producto
     * @param id
     * @return 
     */
    public boolean eliminarLineaDeProducto(int id){
        try{
            LineaDeProducto lineaP = this.lineaDeProductoFacade.find(id);
            if(lineaP.getModeloProductoList() != null && !lineaP.getModeloProductoList().isEmpty()){
                throw new Exception("La línea de Producto ya tiene modelos creados!");
            }else{
                this.lineaDeProductoFacade.remove(lineaP);
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Ingresa un nuevo modelo de producto
     * @param modeloProducto
     * @return 
     */
    public boolean ingresarNuevoModeloDeProducto(ModeloProducto modeloProducto){
        try{
            this.modeloProductoFacade.create(modeloProducto);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Eliminar un modelo de producto
     * @param id
     * @return 
     */
    public boolean eliminarModeloDeProducto(int id){
        try{
            ModeloProducto modeloProducto = this.modeloProductoFacade.find(id);
            if((modeloProducto.getItemProductoList() != null && !modeloProducto.getItemProductoList().isEmpty()) || (modeloProducto.getItemProductoList1() != null && !modeloProducto.getItemProductoList1().isEmpty())){
                throw new Exception("No puede eliminar el modelo por que está siendo usado por algún Item Producto");
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
