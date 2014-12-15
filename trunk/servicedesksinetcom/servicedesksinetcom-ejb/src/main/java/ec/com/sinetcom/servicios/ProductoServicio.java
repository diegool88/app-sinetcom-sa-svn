/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.BodegaFacade;
import ec.com.sinetcom.dao.CategoriaProductoFacade;
import ec.com.sinetcom.dao.ClienteEmpresaFacade;
import ec.com.sinetcom.dao.ComponenteElectronicoAtomicoFacade;
import ec.com.sinetcom.dao.CondicionFisicaFacade;
import ec.com.sinetcom.dao.ContratoFacade;
import ec.com.sinetcom.dao.FabricanteFacade;
import ec.com.sinetcom.dao.DetalleDeMovimientoDeProductoFacade;
import ec.com.sinetcom.dao.ItemProductoFacade;
import ec.com.sinetcom.dao.LineaDeProductoFacade;
import ec.com.sinetcom.dao.ModeloProductoFacade;
import ec.com.sinetcom.dao.ParametroDeProductoFacade;
import ec.com.sinetcom.dao.RegistroDeMovimientoDeInventarioFacade;
import ec.com.sinetcom.dao.TipoDeMovimientoFacade;
import ec.com.sinetcom.dao.UnidadMedidaFacade;
import ec.com.sinetcom.orm.AtributoItemProducto;
import ec.com.sinetcom.orm.AtributoItemProductoPK;
import ec.com.sinetcom.orm.Bodega;
import ec.com.sinetcom.orm.CategoriaProducto;
import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.ComponenteElectronicoAtomico;
import ec.com.sinetcom.orm.CondicionFisica;
import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.Fabricante;
import ec.com.sinetcom.orm.DetalleDeMovimientoDeProducto;
import ec.com.sinetcom.orm.ItemProducto;
import ec.com.sinetcom.orm.LineaDeProducto;
import ec.com.sinetcom.orm.ModeloProducto;
import ec.com.sinetcom.orm.ParametroDeProducto;
import ec.com.sinetcom.orm.RegistroDeMovimientoDeInventario;
import ec.com.sinetcom.orm.TipoDeMovimiento;
import ec.com.sinetcom.orm.UnidadMedida;
import ec.com.sinetcom.orm.Usuario;
import java.util.ArrayList;
import java.util.Calendar;
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
    private ParametroDeProductoFacade parametrosDeProductoFacade;
    @EJB
    private UnidadMedidaFacade unidadMedidaFacade;
    @EJB
    private TipoDeMovimientoFacade tipoDeMovimientoFacade;
    @EJB
    private ClienteEmpresaFacade clienteEmpresaFacade;
    @EJB
    private RegistroDeMovimientoDeInventarioFacade movimientoDeInventarioFacade;
    @EJB
    private DetalleDeMovimientoDeProductoFacade historialDeMovimientoDeProductoFacade;
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
    public List<ParametroDeProducto> obtenerTodosLosParametrosDeProducto(){
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
    public ParametroDeProducto obtenerParametroProducto(int id){
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
    public void crearParametroProducto(ParametroDeProducto parametrosDeProducto){
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
     * Forza la carga del historial de un registro de movimiento de inventario
     * @param registro
     * @return 
     */
    public List<DetalleDeMovimientoDeProducto> forzarCargaDeHistorialDeMovimientoPorRegistro(RegistroDeMovimientoDeInventario registro){
        return this.historialDeMovimientoDeProductoFacade.forzarCargaDeHistorialDeMovimientoPorRegistro(registro);
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
     * Obtiene todos los registros de movimiento de inventario por actualizar
     * @return 
     */
    public List<RegistroDeMovimientoDeInventario> obtenerTodosLosMovimientoDeInventariosPorActualizar(){
        return this.movimientoDeInventarioFacade.obtenerTodosLosRegistrosDeMovimientoDeInventariosPorActualizar();
    }
    
    /**
     * Obtiene todos los registros de movimiento de inventario que están completos
     * @return 
     */
    public List<RegistroDeMovimientoDeInventario> obtenerTodosLosMovimientoDeInventarioFinalizados(){
        return this.movimientoDeInventarioFacade.obtenerTodosLosRegistrosDeMovimientoDeInventariosFinalizados();
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
     * Obtiene todos los tipos de movimiento
     * @return 
     */
    public List<TipoDeMovimiento> obtenerTodosLosTiposDeMovimiento(){
        return this.tipoDeMovimientoFacade.findAll();
    }
    
    /**
     * Obtiene todos los clientes de Sinetcom
     * @return 
     */
    public List<ClienteEmpresa> obtenerTodosLosClientes(){
        return this.clienteEmpresaFacade.findAll();
    }
    
    /**
     * Obtiene todo el inventario dañado
     * @return 
     */
    public List<ItemProducto> obtenerTodoElInventarioDanado(){
        return this.itemProductoFacade.obtenerTodoElInventarioDanado();
    }
    
    /**
     * Obtiene todas las partes y piezas intaladas en el cliente por contrato
     * @param contrato
     * @return 
     */
    public List<ItemProducto> obtenerTodasLasPartesYPiezasDeContrato(Contrato contrato){
        return this.itemProductoFacade.obtenerTodoElInventarioDeUnContrato(contrato);
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
     * Obtiene una tipo de movimiento por Id
     * @param id
     * @return 
     */
    public TipoDeMovimiento obtenerTipoDeMovimientoPorId(int id){
        return this.tipoDeMovimientoFacade.find(id);
    }
    
    /**
     * Obtiene todas las categorías que no posee un fabricante
     * @param fabricante
     * @return 
     */
    public List<CategoriaProducto> obtenerTodasLasCategoriasProductoOpuestasDeFabricante(Fabricante fabricante){
        return this.categoriaProductoFacade.obtenerTodasLasCategoriasProductoOpuestasDeFabricante(fabricante);
    }
    
    /**
     * Obtiene todo el inventario disponible en bodega local
     * @return
     */
    public List<ItemProducto> obtenerTodoElInventarioDisponibleEnBodegaLocal(){
        return this.itemProductoFacade.obtenerTodoElInventarioDisponibleEnBodegaLocal();
    }
    
    /**
     * Obtiene todo el inventario disponible en bodega local incluyendo las partes dañadas
     * @return 
     */
    public List<ItemProducto> obtenerTodoElInventarioDisponibleEnBogegaLocalConPartesDanadas(){
        return this.itemProductoFacade.obtenerTodoElInventarioDisponibleEnBogegaLocalConPartesDanadas();
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
                
                AtributoItemProductoPK itemProductoPK = new AtributoItemProductoPK(atributo.getParametroDeProducto().getId(), itemProducto.getNumeroSerial());
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
    
    /**
     * Actualiza un fabricante
     * @param fabricante
     * @return 
     */
    public boolean actualizarFabricante(Fabricante fabricante){
        try{
            this.fabricanteFacade.edit(fabricante);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Actualiza un item producto
     * @param itemProducto
     * @return 
     */
    public boolean actualizarItemProducto(ItemProducto itemProducto){
        try{
            this.itemProductoFacade.edit(itemProducto);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Crea un nuevo registro de movimiento de inventario
     * @param registroDeMovimientoDeInventario
     * @return 
     */
    public boolean crearRegistroDeMovimientoDeInventario(RegistroDeMovimientoDeInventario registroDeMovimientoDeInventario){
        try{
            this.movimientoDeInventarioFacade.create(registroDeMovimientoDeInventario);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Se crea el hostorial de movimiento de inventario
     * @param itemProducto
     * @param registroDeMovimientoDeInventario
     * @param usuario
     * @param evento
     * @return 
     */
    public boolean crearHistorialDeMovimientoDeInventario(ItemProducto itemProducto, RegistroDeMovimientoDeInventario registroDeMovimientoDeInventario, Usuario usuario){
        try{
            DetalleDeMovimientoDeProducto historialDeMovimientoDeProducto = new DetalleDeMovimientoDeProducto();
            historialDeMovimientoDeProducto.setFechaEvento(Calendar.getInstance().getTime());
            historialDeMovimientoDeProducto.setItemProductonumeroSerialsale(itemProducto);
            historialDeMovimientoDeProducto.setUsuarioid(usuario);
            historialDeMovimientoDeProducto.setRegistroDeMovimientoDeInventariocodigo(registroDeMovimientoDeInventario);
            this.historialDeMovimientoDeProductoFacade.create(historialDeMovimientoDeProducto);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Actualiza un registro de movimiento de inventario
     * @param registroDeMovimientoDeInventario
     * @return 
     */
    public boolean actualizarRegistroDeMovimientoDeInventario(RegistroDeMovimientoDeInventario registroDeMovimientoDeInventario){
        try{
            this.movimientoDeInventarioFacade.edit(registroDeMovimientoDeInventario);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Se coloca en estado dañado el item
     * @param itemProducto
     * @return 
     */
    public boolean colocarItemProductoComoDanado(ItemProducto itemProducto){
        try{
            CondicionFisica danado = this.condicionFisicaFacade.find(2);
            itemProducto.setCondicionFisicaid(danado);
            this.itemProductoFacade.edit(itemProducto);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Crea un nuevo fabricante
     * @param fabricante
     * @return 
     */
    public boolean crearFabricante(Fabricante fabricante){
        try{
            this.fabricanteFacade.create(fabricante);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Crea una categoría de producto
     * @param categoriaProducto
     * @return 
     */
    public boolean crearCategoria(CategoriaProducto categoriaProducto){
        try{
            this.categoriaProductoFacade.edit(categoriaProducto);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
