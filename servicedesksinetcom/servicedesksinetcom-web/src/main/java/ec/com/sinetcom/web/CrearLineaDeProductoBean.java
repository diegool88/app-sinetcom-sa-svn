/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.CategoriaProducto;
import ec.com.sinetcom.orm.Fabricante;
import ec.com.sinetcom.orm.LineaDeProducto;
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
@ManagedBean(name = "crearLineaDeProductoBean")
@ViewScoped
public class CrearLineaDeProductoBean implements Serializable {

    @EJB
    private ProductoServicio productoServicio;
    //Lista de fabricantes
    private List<Fabricante> fabricantes;
    //Fabricante Seleccionado
    private Fabricante fabricanteSeleccionado;
    //Categoria Seleccionada
    private CategoriaProducto categoriaSeleccionada;
    //Nueva Linea de Producto
    private LineaDeProducto nuevaLineaDeProducto;
    //Todas las lineas de Producto de una categoria de 
    private List<LineaDeProducto> lineasDeProducto;

    @PostConstruct
    public void doInit() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            this.fabricantes = this.productoServicio.obtenerTodosLosFabricantes();
            this.lineasDeProducto = this.productoServicio.obtenerTodasLasLineasDeProducto();
            this.nuevaLineaDeProducto = new LineaDeProducto();
        }
    }

    public void ingresarNuevaLineaDeProducto(ActionEvent event) {
        this.nuevaLineaDeProducto.setCategoriaid(this.categoriaSeleccionada);
        this.nuevaLineaDeProducto.setFabricanteid(this.fabricanteSeleccionado);
        if (this.productoServicio.ingresarNuevaLineaDeProducto(this.nuevaLineaDeProducto)) {
            Mensajes.mostrarMensajeInformativo("Se ingreso nueva línea de Producto con éxito!");
            this.lineasDeProducto = this.productoServicio.obtenerTodasLasLineasDeProducto();
        } else {
            Mensajes.mostrarMensajeDeError("Hubo un error al crear nueva línea de producto!");
        }
        this.encerarCampos();
    }

    public void eliminarLineaDeProducto(ActionEvent event) {
        Integer idLineaP = (Integer) event.getComponent().getAttributes().get("idLineaP");
        if(this.productoServicio.eliminarLineaDeProducto(idLineaP)){
            Mensajes.mostrarMensajeInformativo("La línea de producto fue eliminada con éxito!");
        }else{
            Mensajes.mostrarMensajeDeError("La línea de producto no puede ser borrada por tener modelos existentes!");
        }
        this.lineasDeProducto = this.productoServicio.obtenerTodasLasLineasDeProducto();
    }

    public void encerarCampos() {
        this.nuevaLineaDeProducto = new LineaDeProducto();
        this.fabricanteSeleccionado = null;
        this.categoriaSeleccionada = null;
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

    public LineaDeProducto getNuevaLineaDeProducto() {
        return nuevaLineaDeProducto;
    }

    public void setNuevaLineaDeProducto(LineaDeProducto nuevaLineaDeProducto) {
        this.nuevaLineaDeProducto = nuevaLineaDeProducto;
    }

    public List<LineaDeProducto> getLineasDeProducto() {
        return lineasDeProducto;
    }

    public void setLineasDeProducto(List<LineaDeProducto> lineasDeProducto) {
        this.lineasDeProducto = lineasDeProducto;
    }

}
