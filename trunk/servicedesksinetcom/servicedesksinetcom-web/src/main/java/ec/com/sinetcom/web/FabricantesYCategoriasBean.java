/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.CategoriaProducto;
import ec.com.sinetcom.orm.Fabricante;
import ec.com.sinetcom.servicios.ProductoServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

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

    @PostConstruct
    public void doInit() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            this.fabricantes = this.productoServicio.obtenerTodosLosFabricantes();
            this.categoriaProductos = this.productoServicio.obtenerTodasLasCategoriasProducto();
            this.fabricanteSeleccionado = new Fabricante();
        }
    }
    
    public void registroSeleccionado(SelectEvent event){
        this.fabricanteSeleccionado = (Fabricante)event.getObject();
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
    
    

}
