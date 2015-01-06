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

/**
 *
 * @author Andres
 */
@ManagedBean(name = "ingresoClienteEmpresaBean")
@ViewScoped
public class ingresoClienteEmpresaBean implements Serializable{
    
    @EJB
    private ClienteEmpresaServicio clienteEmpresaServicio;
    
    private String numRuc;
    private Integer numUsuario;
    private Integer numTipoEmpresa;
    private String razonSocial;
    private String nombreComercial;
    private String telefono;
    private String representantelegal;
    private ClienteEmpresa clienteEmpresaSeleccionado;
    
    private List<ClienteEmpresa> clientesEnpresa;
    private List<Usuario> usuarios;
    private List<TipoEmpresa> tiposEmpresa;
    
    @PostConstruct
    public void init() {
        this.clientesEnpresa = clienteEmpresaServicio.cargarClientesEmpresa();
        this.usuarios = clienteEmpresaServicio.cargarUsuarios();
        this.tiposEmpresa = clienteEmpresaServicio.cargarTiposEmpresa();
    }
    
    public void crearEmpresa() {
        ClienteEmpresa cliente = new ClienteEmpresa();
        
        cliente.setRuc(numRuc);
        cliente.setRazonSocial(razonSocial);
        cliente.setNombreComercial(nombreComercial);
        cliente.setTelefonoPBX(telefono);
        cliente.setNombreRepresentanteLegal(representantelegal);
        cliente.setTipoEmpresaid(clienteEmpresaServicio.recuperarTipoEmpresa(numTipoEmpresa));
        cliente.setUsuarioid(clienteEmpresaServicio.recuperarUsuario(numUsuario));
        
        if(clienteEmpresaServicio.crearClienteEmpresa(cliente)){
            Mensajes.mostrarMensajeInformativo("Cliente Empresa creado exitosamente!");
        }else{
            Mensajes.mostrarMensajeDeError("Error interno, Cliente Empresa no se pudo crear!");
        }
         this.clientesEnpresa = clienteEmpresaServicio.cargarClientesEmpresa();
    }
    
    public void eliminarEmpresa() {
        if(clienteEmpresaServicio.eliminarClienteEmpresa(clienteEmpresaSeleccionado.getRuc())){
            Mensajes.mostrarMensajeInformativo("Cliente Empresa eliminado exitosamente!");
        }else{
            Mensajes.mostrarMensajeDeError("Error interno, Cliente Empresa no se pudo eliminar!");
        }
        this.clientesEnpresa = clienteEmpresaServicio.cargarClientesEmpresa();
    }

    public String getNumRuc() {
        return numRuc;
    }

    public void setNumRuc(String numRuc) {
        this.numRuc = numRuc;
    }

    public Integer getNumUsuario() {
        return numUsuario;
    }

    public void setNumUsuario(Integer numUsuario) {
        this.numUsuario = numUsuario;
    }

    public Integer getNumTipoEmpresa() {
        return numTipoEmpresa;
    }

    public void setNumTipoEmpresa(Integer numTipoEmpresa) {
        this.numTipoEmpresa = numTipoEmpresa;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRepresentantelegal() {
        return representantelegal;
    }

    public void setRepresentantelegal(String representantelegal) {
        this.representantelegal = representantelegal;
    }

    public ClienteEmpresa getClienteEmpresaSeleccionado() {
        return clienteEmpresaSeleccionado;
    }

    public void setClienteEmpresaSeleccionado(ClienteEmpresa clienteEmpresaSeleccionado) {
        this.clienteEmpresaSeleccionado = clienteEmpresaSeleccionado;
    }

    public List<ClienteEmpresa> getClientesEnpresa() {
        return clientesEnpresa;
    }

    public void setClientesEnpresa(List<ClienteEmpresa> clientesEnpresa) {
        this.clientesEnpresa = clientesEnpresa;
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
}
