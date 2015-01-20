/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.TipoEmpresa;
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.servicios.ClienteEmpresaServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Andres
 */
@ManagedBean(name = "ingresoClienteEmpresaBean")
@ViewScoped
public class ingresoClienteEmpresaBean implements Serializable {

    @EJB
    private ClienteEmpresaServicio clienteEmpresaServicio;

    
    private ClienteEmpresa clienteEmpresaSeleccionado;
    private boolean esNuevoCliente;

    private List<ClienteEmpresa> clientesEmpresa;
    private List<Usuario> usuarios;
    private List<TipoEmpresa> tiposEmpresa;

    @PostConstruct
    public void init() {
        this.clientesEmpresa = clienteEmpresaServicio.cargarClientesEmpresa();
        this.usuarios = clienteEmpresaServicio.cargarUsuarios();
        this.tiposEmpresa = clienteEmpresaServicio.cargarTiposEmpresa();
        this.esNuevoCliente = false;
    }

    public void definirClienteEmpresa(ActionEvent event) {
        boolean esNuevo = Boolean.parseBoolean((String) event.getComponent().getAttributes().get("esNuevo"));
        if (esNuevo) {
            this.clienteEmpresaSeleccionado = new ClienteEmpresa();
            this.esNuevoCliente = true;
        } else {
            this.esNuevoCliente = false;
        }
    }

    public void guardarEmpresa() {
        if (this.esNuevoCliente) {
            if (clienteEmpresaServicio.crearClienteEmpresa(clienteEmpresaSeleccionado)) {
                Mensajes.mostrarMensajeInformativo("Cliente Empresa creado exitosamente!");
            } else {
                Mensajes.mostrarMensajeDeError("Error interno, Cliente Empresa no se pudo crear!");
            }
        } else {
            if (clienteEmpresaServicio.editarClienteEmpresa(clienteEmpresaSeleccionado)) {
                Mensajes.mostrarMensajeInformativo("Cliente Empresa actualizado exitosamente!");
            } else {
                Mensajes.mostrarMensajeDeError("Error interno, Cliente Empresa no se pudo actualizar!");
            }
        }
        this.clientesEmpresa = clienteEmpresaServicio.cargarClientesEmpresa();
    }

    public void eliminarEmpresa() {
        if (clienteEmpresaServicio.eliminarClienteEmpresa(clienteEmpresaSeleccionado.getRuc())) {
            Mensajes.mostrarMensajeInformativo("Cliente Empresa eliminado exitosamente!");
        } else {
            Mensajes.mostrarMensajeDeError("Error interno, Cliente Empresa no se pudo eliminar!");
        }
        this.clientesEmpresa = clienteEmpresaServicio.cargarClientesEmpresa();
    }

    public void editarEmpresa() {
        if (clienteEmpresaServicio.editarClienteEmpresa(clienteEmpresaSeleccionado)) {
            Mensajes.mostrarMensajeInformativo("Cliente Empresa actualizado exitosamente!");
        } else {
            Mensajes.mostrarMensajeDeError("Error interno, Cliente Empresa no se pudo actualizar!");
        }
        this.clientesEmpresa = clienteEmpresaServicio.cargarClientesEmpresa();
    }


    public ClienteEmpresa getClienteEmpresaSeleccionado() {
        return clienteEmpresaSeleccionado;
    }

    public void setClienteEmpresaSeleccionado(ClienteEmpresa clienteEmpresaSeleccionado) {
        this.clienteEmpresaSeleccionado = clienteEmpresaSeleccionado;
    }

    public List<ClienteEmpresa> getClientesEmpresa() {
        return clientesEmpresa;
    }

    public void setClientesEmpresa(List<ClienteEmpresa> clientesEmpresa) {
        this.clientesEmpresa = clientesEmpresa;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<TipoEmpresa> getTiposEmpresa() {
        return tiposEmpresa;
    }

    public void setTiposEmpresa(List<TipoEmpresa> tiposEmpresa) {
        this.tiposEmpresa = tiposEmpresa;
    }

    public boolean isEsNuevoCliente() {
        return esNuevoCliente;
    }

    public void setEsNuevoCliente(boolean esNuevoCliente) {
        this.esNuevoCliente = esNuevoCliente;
    }

}
