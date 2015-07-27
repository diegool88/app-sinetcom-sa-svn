/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;


import ec.com.sinetcom.orm.Bodega;
import ec.com.sinetcom.orm.CategoriaProducto;
import ec.com.sinetcom.orm.CondicionFisica;
import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.Fabricante;
import ec.com.sinetcom.orm.ItemProducto;
import ec.com.sinetcom.orm.LineaDeProducto;
import ec.com.sinetcom.orm.ModeloProducto;
import ec.com.sinetcom.servicios.ProductoServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "ingresarProductoPiezaNA")
@ViewScoped
public class IngresarProductoPiezaNA implements Serializable{
    
    @EJB
    private ProductoServicio productoServicio;
    //Todos los fabricantes
    private List<Fabricante> fabricantes;
    //Todas las categorias del fabricante
    private List<ItemProducto> productosPadre;
    //Todos los modelos disponibles por categoria y fabricante
    private List<ModeloProducto> modeloProductosCompatibles;
    //Todas las lineas de producto
    private List<LineaDeProducto> lineaDeProductos;
    //Todas los contratos
    private List<Contrato> contratos;
    //Todas las bodegas
    private List<Bodega> bodegas;
    //Todas las condiciones físicas
    private List<CondicionFisica> condicionesFisicas;
    //Fabricante Seleccionado
    private Fabricante fabricanteSeleccionado;
    //Categoria Seleccionada
    private CategoriaProducto categoriaProductoSeleccionada;
    //Linea de Producto Seleccionada
    private LineaDeProducto lineaDeProductoSeleccionada;
    //Modelo seleccionado
    private ModeloProducto modeloProductoSeleccionado;
    //Producto Padre seleccionado
    private ItemProducto productoPadreSeleccionado;
    //Lista de productos seleccionados
    private List<ModeloProducto> modelosCompatiblesSeleccionados;
    //Nuevo Producto Componente Atómico
    private ItemProducto nuevoProducto;
    //Atributos ingresado
    private List<String> seriales;
    //Numero de seriales
    private int numeroSeriales;
    //Verifica si se trata de producto padre (Sistema) o subparte
    private Boolean esSistema;
    //Desactivar campos por ingreso de item dañado
    private Boolean esProductoDanado;
    
    @PostConstruct
    public void doInit(){
        if (!FacesContext.getCurrentInstance().isPostback()) {
            this.fabricantes = this.productoServicio.obtenerTodosLosFabricantes();
            this.bodegas = this.productoServicio.obtenerTodasLasBodegas();
            this.contratos = this.productoServicio.obtenerTodosLosContratos();
            this.condicionesFisicas = this.productoServicio.obtenerTodasLasCondicionesFisicas();
            this.nuevoProducto = new ItemProducto();
            this.seriales = new ArrayList<String>();
        }
    }
    
    public void actualizarCategorias() {
        this.categoriaProductoSeleccionada = null;
        this.lineaDeProductoSeleccionada = null;
        this.modeloProductoSeleccionado = null;
        this.modeloProductosCompatibles = null;
        this.nuevoProducto.setItemProductonumeroSerialpadre(null);
        this.seriales = null;
    }

    public void actualizarLineaDeProducto() {
        this.lineaDeProductoSeleccionada = null;
        this.modeloProductoSeleccionado = null;
        this.modeloProductosCompatibles = null;
        this.nuevoProducto.setItemProductonumeroSerialpadre(null);
        this.lineaDeProductos = this.productoServicio.obtenerTodasLasLineasDeProductoPorCategoriaYFabricante(this.fabricanteSeleccionado, this.categoriaProductoSeleccionada);
        this.seriales = null;
        this.actualizarCompatibilidad();
    }
    
    public void actualizarCamposPorProductoDanado(){
        if(this.nuevoProducto != null && this.nuevoProducto.getCondicionFisicaid() != null && 
                this.nuevoProducto.getCondicionFisicaid().getNombre().toLowerCase().trim().equals("dañado")){
            this.nuevoProducto.setFechaDeCompra(Calendar.getInstance().getTime());
            this.nuevoProducto.setNumeroDeFactura("N/A");
            this.nuevoProducto.setNumeroDePedido("N/A");
            this.esProductoDanado = Boolean.TRUE;
        }else if(this.nuevoProducto != null && this.nuevoProducto.getCondicionFisicaid() != null){
            this.esProductoDanado = Boolean.FALSE;
            this.nuevoProducto.setFechaDeCompra(null);
            this.nuevoProducto.setNumeroDeFactura(null);
            this.nuevoProducto.setNumeroDePedido(null);
        }
    }

    public void actualizarCompatibilidad() {
        this.modeloProductosCompatibles = this.productoServicio.obtenerTodosLosModelosPorCategoriaDeFabricante(this.fabricanteSeleccionado, this.categoriaProductoSeleccionada);
    }

    public void actualizarProductoPadre() {
        this.productosPadre = this.productoServicio.obtenerTodosLosProductosPadrePorModelo(this.modeloProductoSeleccionado);
        this.actualizarNumeroParte();
    }

    public void actualizarContratoYBodega() {
        if (this.productoPadreSeleccionado != null) {
            this.nuevoProducto.setContratonumero(this.productoPadreSeleccionado.getContratonumero() != null ? this.productoPadreSeleccionado.getContratonumero() : null);
            this.nuevoProducto.setBodegaid(this.productoPadreSeleccionado.getBodegaid() != null ? this.productoPadreSeleccionado.getBodegaid() : null);
            this.nuevoProducto.setNumeroDeFactura(this.productoPadreSeleccionado.getNumeroDeFactura());
            this.nuevoProducto.setNumeroDePedido(this.productoPadreSeleccionado.getNumeroDePedido());
            this.nuevoProducto.setFechaDeCompra(this.productoPadreSeleccionado.getFechaDeCompra());
            this.nuevoProducto.setCondicionFisicaid(this.productoPadreSeleccionado.getCondicionFisicaid());
        }else{
            this.nuevoProducto.setContratonumero(null);
            this.nuevoProducto.setBodegaid(null);
        }
    }
    
    public void desactivarBodega(){
        if(this.nuevoProducto != null && this.nuevoProducto.getContratonumero() != null){
            this.nuevoProducto.setBodegaid(null);
        }
    }
    
    public void desactivarContrato(){
        if(this.nuevoProducto != null && this.nuevoProducto.getBodegaid()!= null){
            this.nuevoProducto.setContratonumero(null);
            if(this.productoPadreSeleccionado != null && this.productoPadreSeleccionado.getContratonumero() != null){
                this.productoPadreSeleccionado = null;
            }
        }
    }

    public void actualizarCamposDeSeriales() {
        this.seriales = new ArrayList<String>();
        for (int i = 0; i < this.numeroSeriales; i++) {
            this.seriales.add("");
        }
    }
    
    public void actualizarNumeroParte(){
        if(this.esSistema != null && this.esSistema){
            this.nuevoProducto.setNumeroDeParte(this.modeloProductoSeleccionado.getNumeroDeParte());
        }
    }
    
    public void ingresarProducto(ActionEvent event) throws CloneNotSupportedException {
        this.nuevoProducto.setModeloProductoid(this.modeloProductoSeleccionado);
        /*
        Nuevo Condigo
        */
        this.nuevoProducto.setItemProductonumeroSerialpadre(this.productoPadreSeleccionado);
        this.nuevoProducto.setModeloProductoList(this.modeloProductosCompatibles);
        /*
        Fin
        */
        if(this.nuevoProducto.getNumeroDeFactura() != null && this.nuevoProducto.getNumeroDeFactura().equals("N/A"))this.nuevoProducto.setNumeroDeFactura(null);
        if(this.nuevoProducto.getNumeroDeParte() != null && this.nuevoProducto.getNumeroDeParte().equals("N/A"))this.nuevoProducto.setNumeroDeParte(null);
        ItemProducto itemProducto = (ItemProducto) this.nuevoProducto.clone();
        
        for (String serial : seriales) {
            itemProducto = copiarItemProducto(itemProducto);
            itemProducto.setNumeroSerial(serial);
            if (this.productoServicio.ingresarNuevoProducto(itemProducto)) {
                Mensajes.mostrarMensajeInformativo("Item con S/N: " + serial + " ingresado exitosamente!");
                //this.enserarTodosLosCampos();
            } else {
                Mensajes.mostrarMensajeDeError("Hubo un error creando el Item con S/N: " + serial);
            }
        }
        this.productosPadre = this.productoServicio.obtenerTodosLosProductosPadrePorModelo(this.modeloProductoSeleccionado);
        String boton = (String) event.getComponent().getAttributes().get("id");
        if (boton.equals("ingresar1")) {
            this.encerarTodosLosCampos();
        } else {
            this.encerarCamposDeItemProducto();
        }
    }

    private ItemProducto copiarItemProducto(ItemProducto itemProducto) {
        ItemProducto nuevoItemProducto = new ItemProducto();
        nuevoItemProducto.setCondicionFisicaid(itemProducto.getCondicionFisicaid());
        nuevoItemProducto.setModeloProductoid(itemProducto.getModeloProductoid());
        nuevoItemProducto.setComponenteElectronicoAtomicoid(itemProducto.getComponenteElectronicoAtomicoid());
        nuevoItemProducto.setNumeroDeParte(itemProducto.getNumeroDeParte());
        nuevoItemProducto.setItemProductonumeroSerialpadre(itemProducto.getItemProductonumeroSerialpadre());
        nuevoItemProducto.setBodegaid(itemProducto.getBodegaid());
        nuevoItemProducto.setContratonumero(itemProducto.getContratonumero());
        nuevoItemProducto.setFechaDeCompra(itemProducto.getFechaDeCompra());
        nuevoItemProducto.setNumeroDeFactura(itemProducto.getNumeroDeFactura());
        nuevoItemProducto.setNumeroDePedido(itemProducto.getNumeroDePedido());
        nuevoItemProducto.setDescripcion(itemProducto.getDescripcion());
        nuevoItemProducto.setFechaIngresoABodega(itemProducto.getFechaIngresoABodega());
        nuevoItemProducto.setFobUnitario(itemProducto.getFobUnitario());
        nuevoItemProducto.setIndice(itemProducto.getIndice());
        nuevoItemProducto.setCosteo(itemProducto.getCosteo());
        return nuevoItemProducto;
    }

    public void encerarTodosLosCampos() {
        this.doInit();
        this.categoriaProductoSeleccionada = null;
        this.lineaDeProductoSeleccionada = null;
        this.modeloProductoSeleccionado = null;
        this.modeloProductosCompatibles = null;
        this.productoPadreSeleccionado = null;
        this.seriales = null;
        this.numeroSeriales = 0;
    }

    public void encerarCamposDeItemProducto() {
        this.seriales = null;
        this.numeroSeriales = 0;
        this.nuevoProducto.setNumeroDeParte(null);
    }

    public List<Fabricante> getFabricantes() {
        return fabricantes;
    }

    public void setFabricantes(List<Fabricante> fabricantes) {
        this.fabricantes = fabricantes;
    }

    public Fabricante getFabricanteSeleccionado() {
        return fabricanteSeleccionado;
    }

    public void setFabricanteSeleccionado(Fabricante fabricanteSeleccionado) {
        this.fabricanteSeleccionado = fabricanteSeleccionado;
    }

    public CategoriaProducto getCategoriaProductoSeleccionada() {
        return categoriaProductoSeleccionada;
    }

    public void setCategoriaProductoSeleccionada(CategoriaProducto categoriaProductoSeleccionada) {
        this.categoriaProductoSeleccionada = categoriaProductoSeleccionada;
    }

    public List<ItemProducto> getProductosPadre() {
        return productosPadre;
    }

    public void setProductosPadre(List<ItemProducto> productosPadre) {
        this.productosPadre = productosPadre;
    }

    public List<ModeloProducto> getModeloProductosCompatibles() {
        return modeloProductosCompatibles;
    }

    public void setModeloProductosCompatibles(List<ModeloProducto> modeloProductosCompatibles) {
        this.modeloProductosCompatibles = modeloProductosCompatibles;
    }

    public LineaDeProducto getLineaDeProductoSeleccionada() {
        return lineaDeProductoSeleccionada;
    }

    public void setLineaDeProductoSeleccionada(LineaDeProducto lineaDeProductoSeleccionada) {
        this.lineaDeProductoSeleccionada = lineaDeProductoSeleccionada;
    }

    public ModeloProducto getModeloProductoSeleccionado() {
        return modeloProductoSeleccionado;
    }

    public void setModeloProductoSeleccionado(ModeloProducto modeloProductoSeleccionado) {
        this.modeloProductoSeleccionado = modeloProductoSeleccionado;
    }

    public List<LineaDeProducto> getLineaDeProductos() {
        return lineaDeProductos;
    }

    public void setLineaDeProductos(List<LineaDeProducto> lineaDeProductos) {
        this.lineaDeProductos = lineaDeProductos;
    }

    public ItemProducto getNuevoProducto() {
        return nuevoProducto;
    }

    public void setNuevoProducto(ItemProducto nuevoProducto) {
        this.nuevoProducto = nuevoProducto;
    }

    public List<String> getSeriales() {
        return seriales;
    }

    public void setSeriales(List<String> seriales) {
        this.seriales = seriales;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public List<Bodega> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<Bodega> bodegas) {
        this.bodegas = bodegas;
    }

    public List<CondicionFisica> getCondicionesFisicas() {
        return condicionesFisicas;
    }

    public void setCondicionesFisicas(List<CondicionFisica> condicionesFisicas) {
        this.condicionesFisicas = condicionesFisicas;
    }

    public int getNumeroSeriales() {
        return numeroSeriales;
    }

    public void setNumeroSeriales(int numeroSeriales) {
        this.numeroSeriales = numeroSeriales;
    }

    public ItemProducto getProductoPadreSeleccionado() {
        return productoPadreSeleccionado;
    }

    public void setProductoPadreSeleccionado(ItemProducto productoPadreSeleccionado) {
        this.productoPadreSeleccionado = productoPadreSeleccionado;
    }

    public List<ModeloProducto> getModelosCompatiblesSeleccionados() {
        return modelosCompatiblesSeleccionados;
    }

    public void setModelosCompatiblesSeleccionados(List<ModeloProducto> modelosCompatiblesSeleccionados) {
        this.modelosCompatiblesSeleccionados = modelosCompatiblesSeleccionados;
    }

    public Boolean getEsSistema() {
        return esSistema;
    }

    public void setEsSistema(Boolean esSistema) {
        this.esSistema = esSistema;
    }
    
    public Boolean getEsProductoDanado() {
        return esProductoDanado;
    }

    public void setEsProductoDanado(Boolean esProductoDanado) {
        this.esProductoDanado = esProductoDanado;
    }
    
}
