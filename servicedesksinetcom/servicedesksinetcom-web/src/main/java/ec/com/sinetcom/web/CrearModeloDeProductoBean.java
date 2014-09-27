/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.CategoriaProducto;
import ec.com.sinetcom.orm.Fabricante;
import ec.com.sinetcom.orm.LineaDeProducto;
import ec.com.sinetcom.orm.ModeloProducto;
import ec.com.sinetcom.servicios.ProductoServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.Serializable;
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
@ManagedBean(name = "crearModeloDeProductoBean")
@ViewScoped
public class CrearModeloDeProductoBean implements Serializable {

    @EJB
    private ProductoServicio productoServicio;
    //Lista de fabricantes
    private List<Fabricante> fabricantes;
    //Lista de Lineas de Producto
    private List<LineaDeProducto> lineasDeProductos;
    //Lista de las lineas de productos filtrados
    private List<ModeloProducto> modelosProductosFiltrados;
    //Lista de modelos
    private List<ModeloProducto> modelosProductos;
    //Fabricante Seleccionado
    private Fabricante fabricanteSeleccionado;
    //Categoria Seleccionada
    private CategoriaProducto categoriaSeleccionada;
    //Linea de producto Seleccionada
    private LineaDeProducto lineaDeProductoSeleccionada;
    //Nuevo modelo de producto
    private ModeloProducto nuevoModeloProducto;

    @PostConstruct
    public void doInit() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            this.fabricantes = this.productoServicio.obtenerTodosLosFabricantes();
            this.nuevoModeloProducto = new ModeloProducto();
            this.modelosProductos = this.productoServicio.obtenerTodosLosModelosDeProductos();
        }
    }

    public void actualizarLineaDeProductos() {
        this.lineasDeProductos = this.productoServicio.obtenerTodasLasLineasDeProductoPorCategoriaYFabricante(this.fabricanteSeleccionado, this.categoriaSeleccionada);
    }

    public void crearModeloDeProducto(ActionEvent event) {
        this.nuevoModeloProducto.setLineaDeProductoid(this.lineaDeProductoSeleccionada);
        if (this.productoServicio.ingresarNuevoModeloDeProducto(this.nuevoModeloProducto)) {
            Mensajes.mostrarMensajeInformativo("Ingreso de modelo satisfactorio!");
            this.modelosProductos = this.productoServicio.obtenerTodosLosModelosDeProductos();
        } else {
            Mensajes.mostrarMensajeDeError("No se ha podido crear el modelo debido a un error interno!");
        }
        this.encerarCampos();
    }

    public void eliminarModeloDeProducto(ActionEvent event) {
        Integer modeloId = (Integer) event.getComponent().getAttributes().get("modeloId");
        if (this.productoServicio.eliminarModeloDeProducto(modeloId)) {
            Mensajes.mostrarMensajeInformativo("Modelo eliminado Satisfactoriamente!");
        } else {
            Mensajes.mostrarMensajeDeError("El modelo no pudo ser eliminado porque se encuentra en uso por el inventario!");
        }
        this.modelosProductos = this.productoServicio.obtenerTodosLosModelosDeProductos();
    }

    public void encerarCampos() {
        this.nuevoModeloProducto = new ModeloProducto();
        this.fabricanteSeleccionado = null;
        this.categoriaSeleccionada = null;
        this.lineaDeProductoSeleccionada = null;
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

    public CategoriaProducto getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(CategoriaProducto categoriaSeleccionada) {
        this.categoriaSeleccionada = categoriaSeleccionada;
    }

    public LineaDeProducto getLineaDeProductoSeleccionada() {
        return lineaDeProductoSeleccionada;
    }

    public void setLineaDeProductoSeleccionada(LineaDeProducto lineaDeProductoSeleccionada) {
        this.lineaDeProductoSeleccionada = lineaDeProductoSeleccionada;
    }

    public ModeloProducto getNuevoModeloProducto() {
        return nuevoModeloProducto;
    }

    public void setNuevoModeloProducto(ModeloProducto nuevoModeloProducto) {
        this.nuevoModeloProducto = nuevoModeloProducto;
    }

    public List<LineaDeProducto> getLineasDeProductos() {
        return lineasDeProductos;
    }

    public void setLineasDeProductos(List<LineaDeProducto> lineasDeProductos) {
        this.lineasDeProductos = lineasDeProductos;
    }

    public List<ModeloProducto> getModelosProductos() {
        return modelosProductos;
    }

    public void setModelosProductos(List<ModeloProducto> modelosProductos) {
        this.modelosProductos = modelosProductos;
    }

    public List<ModeloProducto> getModelosProductosFiltrados() {
        return modelosProductosFiltrados;
    }

    public void setModelosProductosFiltrados(List<ModeloProducto> modelosProductosFiltrados) {
        this.modelosProductosFiltrados = modelosProductosFiltrados;
    }

}
