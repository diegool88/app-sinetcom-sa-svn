/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.AtributoItemProducto;
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
import ec.com.sinetcom.servicios.ProductoServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.Serializable;
import java.util.ArrayList;
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
@ManagedBean(name = "ingresarComponenteAPBean")
@ViewScoped
public class IngresarComponenteAPBean implements Serializable {

    @EJB
    private ProductoServicio productoServicio;
    //Todos los Atributos ya existentes
    private List<ParametrosDeProducto> parametrosDeProductos;
    //Todas los componentes
    private List<ComponenteElectronicoAtomico> componentesEA;
    //Nuevo Componente Atómico
    private ComponenteElectronicoAtomico componenteEASeleccionado;
    //Todos los fabricantes
    private List<Fabricante> fabricantes;
    //Todas las categorias del fabricante
    private List<CategoriaProducto> cpFabricante;
    //Todos los producto por categoria y fabricante
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
    private ItemProducto nuevoProductoCA;
    //Atributos ingresado
    private List<String> seriales;
    //Numero de seriales
    private int numeroSeriales;
    //Atributos ingresados Lista
    private List<AtributoItemProducto> atributosCA;

    @PostConstruct
    public void doInit() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            this.fabricantes = this.productoServicio.obtenerTodosLosFabricantes();
            this.componentesEA = this.productoServicio.obtenerTodosLosComponentesEA();
            this.bodegas = this.productoServicio.obtenerTodasLasBodegas();
            this.contratos = this.productoServicio.obtenerTodosLosContratos();
            this.condicionesFisicas = this.productoServicio.obtenerTodasLasCondicionesFisicas();
            this.nuevoProductoCA = new ItemProducto();
            this.seriales = new ArrayList<String>();
        }
    }

    public void actualizarCategorias() {
        this.categoriaProductoSeleccionada = null;
        this.lineaDeProductoSeleccionada = null;
        this.modeloProductoSeleccionado = null;
        this.modeloProductosCompatibles = null;
        this.nuevoProductoCA.setItemProductonumeroSerialpadre(null);
        this.componenteEASeleccionado = null;
        this.atributosCA = null;
        this.seriales = null;
    }

    public void actualizarLineaDeProducto() {
        this.lineaDeProductoSeleccionada = null;
        this.modeloProductoSeleccionado = null;
        this.modeloProductosCompatibles = null;
        this.componenteEASeleccionado = null;
        this.nuevoProductoCA.setItemProductonumeroSerialpadre(null);
        this.lineaDeProductos = this.productoServicio.obtenerTodasLasLineasDeProductoPorCategoriaYFabricante(this.fabricanteSeleccionado, this.categoriaProductoSeleccionada);
        this.atributosCA = null;
        this.seriales = null;
        this.actualizarCompatibilidad();
    }

    public void actualizarCompatibilidad() {
        this.modeloProductosCompatibles = this.productoServicio.obtenerTodosLosModelosPorCategoriaDeFabricante(this.fabricanteSeleccionado, this.categoriaProductoSeleccionada);
    }

    public void actualizarProductoPadre() {
        this.productosPadre = this.productoServicio.obtenerTodosLosProductosPadrePorModelo(this.modeloProductoSeleccionado);
    }

    public void actualizarContrato() {
        if (this.productoPadreSeleccionado != null && this.productoPadreSeleccionado.getContratonumero() != null) {
            this.nuevoProductoCA.setContratonumero(this.productoPadreSeleccionado.getContratonumero());
            this.nuevoProductoCA.setBodegaid(null);
        }
    }
    
    public void desactivarBodega(){
        if(this.nuevoProductoCA != null && this.nuevoProductoCA.getContratonumero() != null){
            this.nuevoProductoCA.setBodegaid(null);
        }
    }
    
    public void desactivarContrato(){
        if(this.nuevoProductoCA != null & this.nuevoProductoCA.getBodegaid()!= null){
            this.nuevoProductoCA.setContratonumero(null);
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

    public void crearArregloDeEntradas() {
        //this.seriales = new String[this.componenteEASeleccionado.getParametrosDeProductoList().size()];
        this.atributosCA = new ArrayList<AtributoItemProducto>();
        for (ParametrosDeProducto parametro : this.componenteEASeleccionado.getParametrosDeProductoList()) {
            AtributoItemProducto atributoItemProducto = new AtributoItemProducto();
            atributoItemProducto.setParametrosDeProducto(parametro);
            this.atributosCA.add(atributoItemProducto);
        }
    }

    public void ingresarComponenteElectronico(ActionEvent event) throws CloneNotSupportedException {
        this.nuevoProductoCA.setModeloProductoid(this.modeloProductoSeleccionado);
        this.nuevoProductoCA.setComponenteElectronicoAtomicoid(this.componenteEASeleccionado);
        /*
        Nuevo Condigo
        */
        this.nuevoProductoCA.setItemProductonumeroSerialpadre(this.productoPadreSeleccionado);
        this.nuevoProductoCA.setModeloProductoList(this.modeloProductosCompatibles);
        /*
        Fin
        */
        ItemProducto itemProducto = (ItemProducto) this.nuevoProductoCA.clone();
        List<AtributoItemProducto> atributosIP = this.atributosCA;
        for (String serial : seriales) {
            itemProducto = copiarItemProducto(itemProducto);
            itemProducto.setNumeroSerial(serial);
            if (this.productoServicio.ingresarNuevoProductoComponenteAtomico(itemProducto, atributosIP)) {
                Mensajes.mostrarMensajeInformativo("Item con S/N: " + serial + " ingresado exitosamente!");
                //this.enserarTodosLosCampos();
            } else {
                Mensajes.mostrarMensajeDeError("Hubo un error creando el Item con S/N: " + serial);
            }
        }
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
        nuevoItemProducto.setModeloProductoList(itemProducto.getModeloProductoList());
        return nuevoItemProducto;
    }

    public void ingresarComponenteElectronicoYContinuar(ActionEvent event) throws CloneNotSupportedException {
        this.nuevoProductoCA.setModeloProductoid(this.modeloProductoSeleccionado);
        this.nuevoProductoCA.setComponenteElectronicoAtomicoid(this.componenteEASeleccionado);
        ItemProducto itemProducto = (ItemProducto) this.nuevoProductoCA.clone();
        List<AtributoItemProducto> atributosIP = this.atributosCA;
        for (String serial : seriales) {
            itemProducto = copiarItemProducto(itemProducto);
            itemProducto.setNumeroSerial(serial);
            if (this.productoServicio.ingresarNuevoProductoComponenteAtomico(itemProducto, atributosIP)) {
                Mensajes.mostrarMensajeInformativo("Item con S/N: " + serial + " ingresado exitosamente!");
                this.encerarTodosLosCampos();
            } else {
                Mensajes.mostrarMensajeDeError("Hubo un error creando el Item con S/N: " + serial);
            }
        }

        this.encerarCamposDeItemProducto();
    }

    public void encerarTodosLosCampos() {
        this.doInit();
        this.categoriaProductoSeleccionada = null;
        this.lineaDeProductoSeleccionada = null;
        this.modeloProductoSeleccionado = null;
        this.modeloProductosCompatibles = null;
        this.componenteEASeleccionado = null;
        this.atributosCA = null;
        this.seriales = null;
        this.numeroSeriales = 0;
    }

    public void encerarCamposDeItemProducto() {
        this.seriales = null;
        this.numeroSeriales = 0;
        this.nuevoProductoCA.setNumeroDeParte(null);
        this.componenteEASeleccionado = null;
        this.atributosCA = null;
    }

    public List<ParametrosDeProducto> getParametrosDeProductos() {
        return parametrosDeProductos;
    }

    public void setParametrosDeProductos(List<ParametrosDeProducto> parametrosDeProductos) {
        this.parametrosDeProductos = parametrosDeProductos;
    }

    public List<ComponenteElectronicoAtomico> getComponentesEA() {
        return componentesEA;
    }

    public void setComponentesEA(List<ComponenteElectronicoAtomico> componentesEA) {
        this.componentesEA = componentesEA;
    }

    public ComponenteElectronicoAtomico getComponenteEASeleccionado() {
        return componenteEASeleccionado;
    }

    public void setComponenteEASeleccionado(ComponenteElectronicoAtomico componenteEASeleccionado) {
        this.componenteEASeleccionado = componenteEASeleccionado;
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

    public ItemProducto getNuevoProductoCA() {
        return nuevoProductoCA;
    }

    public void setNuevoProductoCA(ItemProducto nuevoProductoCA) {
        this.nuevoProductoCA = nuevoProductoCA;
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

    public List<AtributoItemProducto> getAtributosCA() {
        return atributosCA;
    }

    public void setAtributosCA(List<AtributoItemProducto> atributosCA) {
        this.atributosCA = atributosCA;
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

}
