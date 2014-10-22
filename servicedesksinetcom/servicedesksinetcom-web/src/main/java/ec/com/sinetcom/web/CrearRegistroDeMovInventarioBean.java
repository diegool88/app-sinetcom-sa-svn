/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.CondicionFisica;
import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.ItemProducto;
import ec.com.sinetcom.orm.RegistroDeMovimientoDeInventario;
import ec.com.sinetcom.orm.TipoDeMovimiento;
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
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "crearRegistroDeMovInventarioBean")
@ViewScoped
public class CrearRegistroDeMovInventarioBean implements Serializable {

    @EJB
    private ProductoServicio productoServicio;
    @ManagedProperty(value = "#{login}")
    private AdministracionUsuarioBean administracionUsuarioBean;
    @ManagedProperty(value = "#{reportesBean}")
    private ReportesBean reportesBean;
    //Todos los tipos de movimientos
    private List<TipoDeMovimiento> tipoDeMovimientos;
    //Todos los clientes
    private List<ClienteEmpresa> clienteEmpresas;
    //Todo el inventario disponible en stock
    private List<ItemProducto> inventarioDisponible;
    //El nuevo registro de movimiento de inventario
    private RegistroDeMovimientoDeInventario movimientoDeInventario;
    //Todos los contratos
    private List<Contrato> contratos;
    //El inventario Filtrado
    private List<ItemProducto> inventarioFiltrado;
    //Obtener Estado físico
    private List<SelectItem> estadosFisicos;
    //Toda las condiciones físicas
    private List<CondicionFisica> condicionesFisicas;

    @PostConstruct
    public void doInit() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            this.tipoDeMovimientos = this.productoServicio.obtenerTodosLosTiposDeMovimiento();
            this.clienteEmpresas = this.productoServicio.obtenerTodosLosClientes();
            this.inventarioDisponible = this.productoServicio.obtenerTodoElInventarioDisponibleEnBodegaLocal();
            this.contratos = this.productoServicio.obtenerTodosLosContratos();
            this.movimientoDeInventario = new RegistroDeMovimientoDeInventario();
            this.condicionesFisicas = this.productoServicio.obtenerTodasLasCondicionesFisicas();
            this.estadosFisicos = new ArrayList<SelectItem>();
            for (CondicionFisica condicion : condicionesFisicas) {
                this.estadosFisicos.add(new SelectItem(condicion.getNombre()));
            }
        }
    }

    public void actualizarUbicacionPorContrato() {
        if (this.movimientoDeInventario.getContratonumero() != null) {
            this.movimientoDeInventario.setClienteEmpresaruc(this.movimientoDeInventario.getContratonumero().getClienteEmpresaruc());
        }
    }

    public void actualizarUbicacionPorCliente() {
        this.movimientoDeInventario.setContratonumero(null);
    }

    public void actualizarClienteYContrato() {
        if (this.movimientoDeInventario.getTipoDeMovimientoid().getId() == 4 || this.movimientoDeInventario.getTipoDeMovimientoid().getId() == 3) {
            this.movimientoDeInventario.setClienteEmpresaruc(null);
            this.movimientoDeInventario.setContratonumero(null);
        } else if (this.movimientoDeInventario.getTipoDeMovimientoid().getId() == 1 || this.movimientoDeInventario.getTipoDeMovimientoid().getId() == 2) {
            this.movimientoDeInventario.setClienteEmpresaruc(null);
            this.movimientoDeInventario.setContratonumero(null);
        }
    }

    public void generarRegistroDeMovimiento(ActionEvent event) throws IOException, JRException {
        for (ItemProducto item : this.movimientoDeInventario.getItemProductoList()) {
            if (this.movimientoDeInventario.getContratonumero() != null) {
                item.setContratonumero(this.movimientoDeInventario.getContratonumero());
            }
            //Se lo saca de la bodega
            item.setBodegaid(null);
            //Si es un contrato asociado se lo carga al contrato
            //if(this.movimientoDeInventario.getContratonumero() != null){
            //    item.setContratonumero(this.movimientoDeInventario.getContratonumero());
            //}
            if (this.productoServicio.actualizarItemProducto(item)) {
                Mensajes.mostrarMensajeInformativo("Producto con S/N: " + item.getNumeroSerial() + " fue modificado!");
            }
        }
        this.movimientoDeInventario.setFechaDeEmision(Calendar.getInstance().getTime());
        this.movimientoDeInventario.setFechaDeSalida(Calendar.getInstance().getTime());
        if (this.productoServicio.crearRegistroDeMovimientoDeInventario(this.movimientoDeInventario)) {
            Mensajes.mostrarMensajeInformativo("Registro de Movimiento de Inventario creado satisfactoriamente!");
            for (ItemProducto itemProducto : this.movimientoDeInventario.getItemProductoList()) {
                this.productoServicio.crearHistorialDeMovimientoDeInventario(itemProducto, this.movimientoDeInventario, this.administracionUsuarioBean.getUsuarioActual());
                Mensajes.mostrarMensajeInformativo("Movimiento de salida para el item: " + itemProducto.getNumeroSerial());
            }
        } else {
            Mensajes.mostrarMensajeDeError("No se pudo crea registro de movimiento de inventario!");
        }
        reportesBean.generarRegistroDeMovimientoDeInventario(this.movimientoDeInventario.getCodigo());
        //this.inventarioDisponible = this.productoServicio.obtenerTodoElInventarioDisponibleEnBodegaLocal();
        //this.movimientoDeInventario = new RegistroDeMovimientoDeInventario();
    }

    public List<TipoDeMovimiento> getTipoDeMovimientos() {
        return tipoDeMovimientos;
    }

    public void setTipoDeMovimientos(List<TipoDeMovimiento> tipoDeMovimientos) {
        this.tipoDeMovimientos = tipoDeMovimientos;
    }

    public List<ClienteEmpresa> getClienteEmpresas() {
        return clienteEmpresas;
    }

    public void setClienteEmpresas(List<ClienteEmpresa> clienteEmpresas) {
        this.clienteEmpresas = clienteEmpresas;
    }

    public List<ItemProducto> getInventarioDisponible() {
        return inventarioDisponible;
    }

    public void setInventarioDisponible(List<ItemProducto> inventarioDisponible) {
        this.inventarioDisponible = inventarioDisponible;
    }

    public RegistroDeMovimientoDeInventario getMovimientoDeInventario() {
        return movimientoDeInventario;
    }

    public void setMovimientoDeInventario(RegistroDeMovimientoDeInventario movimientoDeInventario) {
        this.movimientoDeInventario = movimientoDeInventario;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public List<ItemProducto> getInventarioFiltrado() {
        return inventarioFiltrado;
    }

    public void setInventarioFiltrado(List<ItemProducto> inventarioFiltrado) {
        this.inventarioFiltrado = inventarioFiltrado;
    }

    public List<SelectItem> getEstadosFisicos() {
        return estadosFisicos;
    }

    public void setEstadosFisicos(List<SelectItem> estadosFisicos) {
        this.estadosFisicos = estadosFisicos;
    }

    public List<CondicionFisica> getCondicionesFisicas() {
        return condicionesFisicas;
    }

    public void setCondicionesFisicas(List<CondicionFisica> condicionesFisicas) {
        this.condicionesFisicas = condicionesFisicas;
    }

    public void setAdministracionUsuarioBean(AdministracionUsuarioBean administracionUsuarioBean) {
        this.administracionUsuarioBean = administracionUsuarioBean;
    }

    public void setReportesBean(ReportesBean reportesBean) {
        this.reportesBean = reportesBean;
    }

}
