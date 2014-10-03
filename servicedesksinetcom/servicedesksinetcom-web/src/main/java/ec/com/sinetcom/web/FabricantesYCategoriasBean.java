/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.CategoriaProducto;
import ec.com.sinetcom.orm.Fabricante;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "fabricantesYCategorias")
@ViewScoped
public class FabricantesYCategoriasBean implements Serializable {

    @EJB
    private ProductoServicio productoServicio;

    //Todos los fabricantes
    private List<Fabricante> fabricantes;
    //Todos las categorias
    private List<CategoriaProducto> categoriaProductos;
    //Fabricante Seleccionado
    private Fabricante fabricanteSeleccionado;
    //Nuevo Fabricante
    private Fabricante nuevoFabricante;
    //Nueva Categoría
    private CategoriaProducto nuevaCategoriaProducto;
    //Categorias seleccionadas
    private DualListModel<CategoriaProducto> categoriasSeleccionadas;

    @PostConstruct
    public void doInit() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            this.fabricantes = this.productoServicio.obtenerTodosLosFabricantes();
            this.categoriaProductos = this.productoServicio.obtenerTodasLasCategoriasProducto();
            this.fabricanteSeleccionado = new Fabricante();
            this.nuevaCategoriaProducto = new CategoriaProducto();
            this.nuevoFabricante = new Fabricante();
            this.categoriasSeleccionadas = new DualListModel<CategoriaProducto>();
        }
    }
    
    public void actualizarCategoriasDeFabricante(ActionEvent event){
        this.fabricanteSeleccionado.setCategoriaProductoList(this.categoriasSeleccionadas.getTarget());
        if(this.productoServicio.actualizarFabricante(this.fabricanteSeleccionado)){
            Mensajes.mostrarMensajeInformativo("Fabricante actualizado con éxito!");
        }else{
            Mensajes.mostrarMensajeInformativo("Hubo un error interno al actualizar fabricante!");
        }
        this.fabricantes = this.productoServicio.obtenerTodosLosFabricantes();
    }
    
    public void crearFabricante(ActionEvent event){
        if(this.productoServicio.crearFabricante(this.nuevoFabricante)){
            Mensajes.mostrarMensajeInformativo("Nuevo fabricante creado con éxito!");
        }else{
            Mensajes.mostrarMensajeInformativo("Hubo un error interno al crear nuevo fabricante!");
        }
        this.fabricantes = this.productoServicio.obtenerTodosLosFabricantes();
        this.nuevoFabricante = new Fabricante();
    }
    
    public void crearCategoria(ActionEvent event){
        if(this.productoServicio.crearCategoria(this.nuevaCategoriaProducto)){
            Mensajes.mostrarMensajeInformativo("Nuevo Categoría Producto creada con éxito!");
        }else{
            Mensajes.mostrarMensajeInformativo("Hubo un error interno al crear nueva categoría!");
        }
        this.categoriaProductos = this.productoServicio.obtenerTodasLasCategoriasProducto();
        if(this.fabricanteSeleccionado.getId() != null){
            this.categoriasSeleccionadas = new DualListModel<CategoriaProducto>(this.categoriaProductos, this.fabricanteSeleccionado.getCategoriaProductoList());
        }
        this.nuevaCategoriaProducto = new CategoriaProducto();
    }
    
    public void registroSeleccionado(SelectEvent event){
        this.fabricanteSeleccionado = (Fabricante)event.getObject();
        this.categoriaProductos = this.productoServicio.obtenerTodasLasCategoriasProductoOpuestasDeFabricante(this.fabricanteSeleccionado);
        this.categoriasSeleccionadas = new DualListModel<CategoriaProducto>(this.categoriaProductos, this.fabricanteSeleccionado.getCategoriaProductoList());
    }
    
    public void transferirCategoria(TransferEvent event){
        
    }

    public List<Fabricante> getFabricantes() {
        return fabricantes;
    }

    public void setFabricantes(List<Fabricante> fabricantes) {
        this.fabricantes = fabricantes;
    }

    public List<CategoriaProducto> getCategoriaProductos() {
        return categoriaProductos;
    }

    public void setCategoriaProductos(List<CategoriaProducto> categoriaProductos) {
        this.categoriaProductos = categoriaProductos;
    }

    public Fabricante getFabricanteSeleccionado() {
        return fabricanteSeleccionado;
    }

    public void setFabricanteSeleccionado(Fabricante fabricanteSeleccionado) {
        this.fabricanteSeleccionado = fabricanteSeleccionado;
    }

    public Fabricante getNuevoFabricante() {
        return nuevoFabricante;
    }

    public void setNuevoFabricante(Fabricante nuevoFabricante) {
        this.nuevoFabricante = nuevoFabricante;
    }

    public CategoriaProducto getNuevaCategoriaProducto() {
        return nuevaCategoriaProducto;
    }

    public void setNuevaCategoriaProducto(CategoriaProducto nuevaCategoriaProducto) {
        this.nuevaCategoriaProducto = nuevaCategoriaProducto;
    }

    public DualListModel<CategoriaProducto> getCategoriasSeleccionadas() {
        return categoriasSeleccionadas;
    }

    public void setCategoriasSeleccionadas(DualListModel<CategoriaProducto> categoriasSeleccionadas) {
        this.categoriasSeleccionadas = categoriasSeleccionadas;
    }
    

}
