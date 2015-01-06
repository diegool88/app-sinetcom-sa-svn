/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.Ciudad;
import ec.com.sinetcom.orm.ClienteDireccion;
import ec.com.sinetcom.orm.ClienteDireccionPK;
import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.servicios.ContratoServicio;
import ec.com.sinetcom.webutil.Mensajes;
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
@ManagedBean(name = "ingresoDireccionesBean")
@ViewScoped
public class ingresoDireccionesBean implements Serializable {

    @EJB
    private ContratoServicio contratoServicio;

    private List<ClienteDireccion> direcciones;
    private List<ClienteEmpresa> clientes;
    private List<Ciudad> ciudades;
    private List<SelectItem> clientesFiltro;
    private ClienteDireccion direccionSeleccionada;
    private ClienteDireccion nuevaDireccion;
    private List<ClienteDireccion> direccionesFiltradas;

    @PostConstruct
    public void doInit() {
        this.clientes = this.contratoServicio.cargarEmpresas();
        this.direcciones = this.contratoServicio.cargarDirecciones();
        this.ciudades = this.contratoServicio.cargarCiudades();
        this.nuevaDireccion = new ClienteDireccion();
        this.clientesFiltro = new ArrayList<SelectItem>();
        this.clientesFiltro.add(new SelectItem("", "-----", "", false, true, true));
        for (ClienteEmpresa empresa : clientes) {
            this.clientesFiltro.add(new SelectItem(empresa.getNombreComercial()));
        }
    }

    public void crearDireccion() {
        ClienteDireccionPK clienteDireccionPK = new ClienteDireccionPK(this.nuevaDireccion.getCiudad().getId(), this.nuevaDireccion.getClienteEmpresa().getRuc());
        this.nuevaDireccion.setClienteDireccionPK(clienteDireccionPK);
        if (this.contratoServicio.existeDireccionCliente(nuevaDireccion)) {
            Mensajes.mostrarMensajeDeError("No puede ingresar más de una dirección por ciudad de un cliente!");
        } else if (this.contratoServicio.crearDireccionCliente(this.nuevaDireccion)) {
            Mensajes.mostrarMensajeInformativo("Nueva dirección creada con éxito!");
        } else {
            Mensajes.mostrarMensajeDeError("Error interno creando nueva dirección!");
        }
        this.direcciones = this.contratoServicio.cargarDirecciones();
    }

    public void eliminarDireccion() {
        if (this.contratoServicio.eliminarDireccionCliente(this.direccionSeleccionada)) {
            Mensajes.mostrarMensajeInformativo("Dirección eliminada con éxito!");
        } else {
            Mensajes.mostrarMensajeDeError("Error interno al eliminar dirección de cliente!");
        }
        this.direcciones = this.contratoServicio.cargarDirecciones();
    }

    public List<ClienteDireccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<ClienteDireccion> direcciones) {
        this.direcciones = direcciones;
    }

    public List<ClienteEmpresa> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteEmpresa> clientes) {
        this.clientes = clientes;
    }

    public ClienteDireccion getDireccionSeleccionada() {
        return direccionSeleccionada;
    }

    public void setDireccionSeleccionada(ClienteDireccion direccionSeleccionada) {
        this.direccionSeleccionada = direccionSeleccionada;
    }

    public ClienteDireccion getNuevaDireccion() {
        return nuevaDireccion;
    }

    public void setNuevaDireccion(ClienteDireccion nuevaDireccion) {
        this.nuevaDireccion = nuevaDireccion;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public List<SelectItem> getClientesFiltro() {
        return clientesFiltro;
    }

    public void setClientesFiltro(List<SelectItem> clientesFiltro) {
        this.clientesFiltro = clientesFiltro;
    }

    public List<ClienteDireccion> getDireccionesFiltradas() {
        return direccionesFiltradas;
    }

    public void setDireccionesFiltradas(List<ClienteDireccion> direccionesFiltradas) {
        this.direccionesFiltradas = direccionesFiltradas;
    }

}
