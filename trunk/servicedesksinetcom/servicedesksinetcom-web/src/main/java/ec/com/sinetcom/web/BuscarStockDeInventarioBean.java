/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.Bodega;
import ec.com.sinetcom.orm.CondicionFisica;
import ec.com.sinetcom.orm.Fabricante;
import ec.com.sinetcom.orm.ItemProducto;
import ec.com.sinetcom.servicios.ProductoServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "buscarStockDeInventario")
@ViewScoped
public class BuscarStockDeInventarioBean implements Serializable{
    @EJB
    private ProductoServicio productoServicio;
    //Todo el inventario disponible en Sinetcom
    private List<ItemProducto> itemProductos;
    //Lista de Bodegas
    private List<SelectItem> bodegas;
    //Lista de estado
    private List<SelectItem> estados;
    //Lista de Fabricantes
    private List<SelectItem> fabricantes;
    //Lista de elemtos filtrados
    private List<ItemProducto> itemProductosFiltrados;
    
    @PostConstruct
    public void doInit(){
        this.itemProductos = this.productoServicio.obtenerTodoElInventarioDisponibleEnBogegaLocalConPartesDanadas();
        this.bodegas = new ArrayList<SelectItem>();
        this.estados = new ArrayList<SelectItem>();
        this.fabricantes = new ArrayList<SelectItem>();
        List<CondicionFisica> condicionesF = this.productoServicio.obtenerTodasLasCondicionesFisicas();
        List<Bodega> bodegasD = this.productoServicio.obtenerTodasLasBodegas();
        List<Fabricante> fabricantesD = this.productoServicio.obtenerTodosLosFabricantes();
        //Se inicializa con ningún valor escogido
        this.bodegas.add(new SelectItem("", "-----", "", false, true, true));
        this.fabricantes.add(new SelectItem("", "-----", "", false, true, true));
        this.estados.add(new SelectItem("", "-----", "", false, true, true));
        //Se agragan las opciones
        for(Bodega bodega : bodegasD){
            this.bodegas.add(new SelectItem(bodega.getNombre()));
        }
        for(CondicionFisica condicionFisica: condicionesF){
            this.estados.add(new SelectItem(condicionFisica.getNombre()));
        }
        for(Fabricante fabricante: fabricantesD){
            this.fabricantes.add(new SelectItem(fabricante.getNombre()));
        }
    }

    public List<ItemProducto> getItemProductos() {
        return itemProductos;
    }

    public void setItemProductos(List<ItemProducto> itemProductos) {
        this.itemProductos = itemProductos;
    }

    public List<SelectItem> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<SelectItem> bodegas) {
        this.bodegas = bodegas;
    }

    public List<SelectItem> getEstados() {
        return estados;
    }

    public void setEstados(List<SelectItem> estados) {
        this.estados = estados;
    }

    public List<ItemProducto> getItemProductosFiltrados() {
        return itemProductosFiltrados;
    }

    public void setItemProductosFiltrados(List<ItemProducto> itemProductosFiltrados) {
        this.itemProductosFiltrados = itemProductosFiltrados;
    }

    public List<SelectItem> getFabricantes() {
        return fabricantes;
    }

    public void setFabricantes(List<SelectItem> fabricantes) {
        this.fabricantes = fabricantes;
    }
    
}
