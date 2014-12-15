/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.Bodega;
import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.DetalleDeMovimientoDeProducto;
import ec.com.sinetcom.orm.ItemProducto;
import ec.com.sinetcom.orm.RegistroDeMovimientoDeInventario;
import ec.com.sinetcom.servicios.ProductoServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "actualizarRegistroDeMovBean")
@ViewScoped
public class ActualizarRegistroDeMovimientoBean implements Serializable {

    @EJB
    private ProductoServicio productoServicio;
    @ManagedProperty(value = "#{reportesBean}")
    private ReportesBean reportesBean;

    //Registro de Movimiento disponibles para actualizar
    private List<RegistroDeMovimientoDeInventario> movimientoDeInventarios;
    //Registro de Movimiento de inventario seleccionado
    private RegistroDeMovimientoDeInventario seleccion;
    //Todos el inventario dañado que fue ingresado previamente
    private List<ItemProducto> inventarioEntranteDisponible;
    //Todos los movimiento de inventarios finalizados con el ingreso de las partes
    private List<RegistroDeMovimientoDeInventario> movimientosFinalizados;
    //Todas la bodegas
    private List<Bodega> bodegas;

    @PostConstruct
    public void doInit() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            this.movimientoDeInventarios = this.productoServicio.obtenerTodosLosMovimientoDeInventariosPorActualizar();
            this.seleccion = new RegistroDeMovimientoDeInventario();
            this.movimientosFinalizados = this.productoServicio.obtenerTodosLosMovimientoDeInventarioFinalizados();
            this.bodegas = this.productoServicio.obtenerTodasLasBodegas();
        }
    }

    public void registroSeleccionado(SelectEvent event) {
        this.seleccion = (RegistroDeMovimientoDeInventario) event.getObject();
        if (this.seleccion.getDetalleDeMovimientoDeProductoList().isEmpty()) { 
            this.seleccion.setDetalleDeMovimientoDeProductoList(this.productoServicio.forzarCargaDeHistorialDeMovimientoPorRegistro(this.seleccion));
        }
        if (this.seleccion.getTipoDeMovimientoid().getId() == 3 || this.seleccion.getTipoDeMovimientoid().getId() == 4) {
            this.inventarioEntranteDisponible = this.productoServicio.obtenerTodasLasPartesYPiezasDeContrato(this.seleccion.getContratonumero());
        } else {
            this.inventarioEntranteDisponible = this.productoServicio.obtenerTodoElInventarioDanado();
        }
    }

    public void registroDeSeleccionado(UnselectEvent event) {
        this.seleccion = new RegistroDeMovimientoDeInventario();
    }

    public void actualizarRegistroDeMovimientoDeInventario(ActionEvent event) {
        int tipoDeMov = this.seleccion.getTipoDeMovimientoid().getId();

        if (tipoDeMov != 1 && tipoDeMov != 2) {
            for (DetalleDeMovimientoDeProducto movimiento : this.seleccion.getDetalleDeMovimientoDeProductoList()) {
                ItemProducto productoPadre = movimiento.getItemProductonumeroSerialentra().getItemProductonumeroSerialpadre();
                Contrato contrato = movimiento.getItemProductonumeroSerialentra().getContratonumero();
                //Se indica el nuevo padre del componente que se reemplazo de existir uno y se encera el que entra
                if (productoPadre != null) {
                    movimiento.getItemProductonumeroSerialsale().setItemProductonumeroSerialpadre(productoPadre);
                    movimiento.getItemProductonumeroSerialentra().setItemProductonumeroSerialpadre(null);
                }
                //Se indica el nuevo contrato del item que salió y se ensera el otro
                if(contrato != null){
                    movimiento.getItemProductonumeroSerialsale().setContratonumero(contrato);
                    movimiento.getItemProductonumeroSerialentra().setContratonumero(null);
                }
                //Se coloca el producto entrante como dañado <Se hace un cascade cuando se edita el item>
                this.productoServicio.colocarItemProductoComoDanado(movimiento.getItemProductonumeroSerialentra());
                //Se guardan los cambios en el que sale
                this.productoServicio.actualizarItemProducto(movimiento.getItemProductonumeroSerialsale());
                //this.seleccion.getItemProductoList().add(movimiento.getItemProductonumeroSerialentra());
            }
        }else{
            for (DetalleDeMovimientoDeProducto movimiento : this.seleccion.getDetalleDeMovimientoDeProductoList()) {
                //Se coloca en el registro como que nuevamente se ingreso el item en préstamo
                movimiento.setItemProductonumeroSerialentra(movimiento.getItemProductonumeroSerialsale());
                this.productoServicio.actualizarItemProducto(movimiento.getItemProductonumeroSerialentra());
            }
        }

        this.seleccion.setFechaDeEntrada(Calendar.getInstance().getTime());
        if (this.productoServicio.actualizarRegistroDeMovimientoDeInventario(this.seleccion)) {
            Mensajes.mostrarMensajeInformativo("Registro de Movimiento de Inventario actualizado con éxito!");
        } else {
            Mensajes.mostrarMensajeDeError("Registro de Movimiento de Inventario no pudo ser actualizado!");
        }
        this.movimientoDeInventarios = this.productoServicio.obtenerTodosLosMovimientoDeInventariosPorActualizar();
        this.movimientosFinalizados = this.productoServicio.obtenerTodosLosMovimientoDeInventarioFinalizados();
    }

    public void descargarRegistroDeMovimientoDeInventario(ActionEvent event) throws IOException, JRException {
        Integer codigo = (Integer) event.getComponent().getAttributes().get("idRegistro");
        reportesBean.generarRegistroDeMovimientoDeInventario(codigo);
    }

    public List<RegistroDeMovimientoDeInventario> getMovimientoDeInventarios() {
        return movimientoDeInventarios;
    }

    public void setMovimientoDeInventarios(List<RegistroDeMovimientoDeInventario> movimientoDeInventarios) {
        this.movimientoDeInventarios = movimientoDeInventarios;
    }

    public RegistroDeMovimientoDeInventario getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(RegistroDeMovimientoDeInventario seleccion) {
        this.seleccion = seleccion;
    }

    public List<ItemProducto> getInventarioEntranteDisponible() {
        return inventarioEntranteDisponible;
    }

    public void setInventarioEntranteDisponible(List<ItemProducto> inventarioEntranteDisponible) {
        this.inventarioEntranteDisponible = inventarioEntranteDisponible;
    }

    public List<RegistroDeMovimientoDeInventario> getMovimientosFinalizados() {
        return movimientosFinalizados;
    }

    public void setMovimientosFinalizados(List<RegistroDeMovimientoDeInventario> movimientosFinalizados) {
        this.movimientosFinalizados = movimientosFinalizados;
    }

    public void setReportesBean(ReportesBean reportesBean) {
        this.reportesBean = reportesBean;
    }

    public List<Bodega> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<Bodega> bodegas) {
        this.bodegas = bodegas;
    }

}
