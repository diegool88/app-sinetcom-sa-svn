/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.ComponenteElectronicoAtomico;
import ec.com.sinetcom.orm.ParametrosDeProducto;
import ec.com.sinetcom.orm.UnidadMedida;
import ec.com.sinetcom.servicios.ComponenteAtomicoServicio;
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
    @EJB
    private ComponenteAtomicoServicio atomicoServicio;
    //Todos los Atributos ya existentes
    private List<ParametrosDeProducto> parametrosDeProductos;
    //Todas las unidades de medida
    private List<UnidadMedida> medidas;
    //Todas los componentes
    private List<ComponenteElectronicoAtomico> componenteElectronicoAtomicos;
    //Nuevo Componente Atómico
    private ComponenteElectronicoAtomico componenteElectronicoAtomico;
    //Nuevo Parametro
    private ParametrosDeProducto nuevoParametro;

    @PostConstruct
    public void doInit() {
        this.medidas = this.atomicoServicio.obtenerTodasLasUnidadesDeMedida();
        this.parametrosDeProductos = this.atomicoServicio.obtenerTodosLosParametrosDeProducto();
        this.componenteElectronicoAtomicos = this.atomicoServicio.obtenerTodosLosComponentesElectronicos();
        this.nuevoParametro = new ParametrosDeProducto();
        this.componenteElectronicoAtomico = new ComponenteElectronicoAtomico();
    }

    public void ingresarParametro(ActionEvent event) {
        this.atomicoServicio.crearParametroProducto(this.nuevoParametro);
        this.parametrosDeProductos = this.atomicoServicio.obtenerTodosLosParametrosDeProducto();
        this.nuevoParametro = new ParametrosDeProducto();
        Mensajes.mostrarMensajeInformativo("Se ha creado un nuevo parámetro de producto");
    }

    public void enserarPantallaParametro(ActionEvent event) {
        this.nuevoParametro = new ParametrosDeProducto();
    }
    
    public void crearComponenteElectronico(ActionEvent event){
        this.atomicoServicio.crearComponenteElectronico(this.componenteElectronicoAtomico);
        this.componenteElectronicoAtomico = new ComponenteElectronicoAtomico();
        this.componenteElectronicoAtomicos = this.atomicoServicio.obtenerTodosLosComponentesElectronicos();
        Mensajes.mostrarMensajeInformativo("Componente creado satisfactoriamente");
    }
    
    public void borrarComponenteElectronico(ActionEvent event){
        if(!this.atomicoServicio.eliminarComponenteElectronico((Integer)event.getComponent().getAttributes().get("idComponente"))){
            Mensajes.mostrarMensajeDeError("El Componente no pudo ser borrado, se encuentra en uso!");
        }else{
            Mensajes.mostrarMensajeInformativo("Componente borrado exitosamente!");
            this.componenteElectronicoAtomicos = this.atomicoServicio.obtenerTodosLosComponentesElectronicos();
        }
    }

    public List<ParametrosDeProducto> getParametrosDeProductos() {
        return parametrosDeProductos;
    }

    public void setParametrosDeProductos(List<ParametrosDeProducto> parametrosDeProductos) {
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

    public ParametrosDeProducto getNuevoParametro() {
        return nuevoParametro;
    }

    public void setNuevoParametro(ParametrosDeProducto nuevoParametro) {
        this.nuevoParametro = nuevoParametro;
    }

    public List<ComponenteElectronicoAtomico> getComponenteElectronicoAtomicos() {
        return componenteElectronicoAtomicos;
    }

    public void setComponenteElectronicoAtomicos(List<ComponenteElectronicoAtomico> componenteElectronicoAtomicos) {
        this.componenteElectronicoAtomicos = componenteElectronicoAtomicos;
    }

}
