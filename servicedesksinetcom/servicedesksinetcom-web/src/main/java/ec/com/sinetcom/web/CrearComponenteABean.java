/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.ComponenteElectronicoAtomico;
import ec.com.sinetcom.orm.ParametroDeProducto;
import ec.com.sinetcom.orm.UnidadMedida;
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
@ManagedBean(name = "crearComponenteABean")
@ViewScoped
public class CrearComponenteABean implements Serializable {

    @EJB
    private ProductoServicio productoServicio;
    //Todos los Atributos ya existentes
    private List<ParametroDeProducto> parametrosDeProductos;
    //Todas las unidades de medida
    private List<UnidadMedida> medidas;
    //Todas los componentes
    private List<ComponenteElectronicoAtomico> componenteElectronicoAtomicos;
    //Nuevo Componente Atómico
    private ComponenteElectronicoAtomico componenteElectronicoAtomico;
    //Nuevo Parametro
    private ParametroDeProducto nuevoParametro;
    //Nueva Unidad de Medida
    private UnidadMedida unidadMedida;

    @PostConstruct
    public void doInit() {
        this.medidas = this.productoServicio.obtenerTodasLasUnidadesDeMedida();
        this.parametrosDeProductos = this.productoServicio.obtenerTodosLosParametrosDeProducto();
        this.componenteElectronicoAtomicos = this.productoServicio.obtenerTodosLosComponentesElectronicos();
        this.nuevoParametro = new ParametroDeProducto();
        this.unidadMedida = new UnidadMedida();
        this.componenteElectronicoAtomico = new ComponenteElectronicoAtomico();
    }

    public void ingresarParametro(ActionEvent event) {
        this.productoServicio.crearParametroProducto(this.nuevoParametro);
        this.parametrosDeProductos = this.productoServicio.obtenerTodosLosParametrosDeProducto();
        this.nuevoParametro = new ParametroDeProducto();
        Mensajes.mostrarMensajeInformativo("Se ha creado un nuevo parámetro de producto!");
    }
    
    public void ingresarUnidadMedida(ActionEvent event){
        this.productoServicio.crearUnidadDeMedida(this.unidadMedida);
        this.medidas = this.productoServicio.obtenerTodasLasUnidadesDeMedida();
        this.unidadMedida = new UnidadMedida();
        Mensajes.mostrarMensajeInformativo("Se ha creado una nueva unidad de medida!");
    }

    public void enserarPantallaParametro(ActionEvent event) {
        this.nuevoParametro = new ParametroDeProducto();
    }
    
    public void crearComponenteElectronico(ActionEvent event){
        this.productoServicio.crearComponenteElectronico(this.componenteElectronicoAtomico);
        this.componenteElectronicoAtomico = new ComponenteElectronicoAtomico();
        this.componenteElectronicoAtomicos = this.productoServicio.obtenerTodosLosComponentesElectronicos();
        Mensajes.mostrarMensajeInformativo("Componente creado satisfactoriamente!");
    }
    
    public void borrarComponenteElectronico(ActionEvent event){
        if(!this.productoServicio.eliminarComponenteElectronico((Integer)event.getComponent().getAttributes().get("idComponente"))){
            Mensajes.mostrarMensajeDeError("El Componente no pudo ser borrado, se encuentra en uso!");
        }else{
            Mensajes.mostrarMensajeInformativo("Componente borrado exitosamente!");
            this.componenteElectronicoAtomicos = this.productoServicio.obtenerTodosLosComponentesElectronicos();
        }
    }

    public List<ParametroDeProducto> getParametrosDeProductos() {
        return parametrosDeProductos;
    }

    public void setParametrosDeProductos(List<ParametroDeProducto> parametrosDeProductos) {
        this.parametrosDeProductos = parametrosDeProductos;
    }

    public List<UnidadMedida> getMedidas() {
        return medidas;
    }

    public void setMedidas(List<UnidadMedida> medidas) {
        this.medidas = medidas;
    }

    public ComponenteElectronicoAtomico getComponenteElectronicoAtomico() {
        return componenteElectronicoAtomico;
    }

    public void setComponenteElectronicoAtomico(ComponenteElectronicoAtomico componenteElectronicoAtomico) {
        this.componenteElectronicoAtomico = componenteElectronicoAtomico;
    }

    public ParametroDeProducto getNuevoParametro() {
        return nuevoParametro;
    }

    public void setNuevoParametro(ParametroDeProducto nuevoParametro) {
        this.nuevoParametro = nuevoParametro;
    }

    public List<ComponenteElectronicoAtomico> getComponenteElectronicoAtomicos() {
        return componenteElectronicoAtomicos;
    }

    public void setComponenteElectronicoAtomicos(List<ComponenteElectronicoAtomico> componenteElectronicoAtomicos) {
        this.componenteElectronicoAtomicos = componenteElectronicoAtomicos;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

}
