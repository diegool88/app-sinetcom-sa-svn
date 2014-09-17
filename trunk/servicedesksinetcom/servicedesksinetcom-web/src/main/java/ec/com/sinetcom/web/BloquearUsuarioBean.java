/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.servicios.UsuarioServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author diegoflores
 */

@ManagedBean(name = "bloquearUsuario")
@ViewScoped
public class BloquearUsuarioBean implements Serializable{
    
    @EJB
    private UsuarioServicio usuarioServicio;
    //Lista de Usuarios que son clientes
    private List<Usuario> clientes;
    //Usuario seleccionado
    private Usuario usuarioSeleccionado;
    
    @PostConstruct
    public void doInit(){
        this.clientes = this.usuarioServicio.obtenerTodosLosClientes();
    }
    
    public void editarRegistro (RowEditEvent event){
        this.usuarioSeleccionado = (Usuario) event.getObject();
        this.usuarioServicio.modificarUsuario(this.usuarioSeleccionado);
        Mensajes.mostrarMensajeInformativo("Usuario '" + this.usuarioSeleccionado.getNombreCompleto() + "' ha sido modificado");
    }
    
    public void cancelarEdicion(RowEditEvent event){
        Mensajes.mostrarMensajeInformativo("Se ha cancelado la edición!");
    }

    public List<Usuario> getClientes() {
        return clientes;
    }

    public void setClientes(List<Usuario> clientes) {
        this.clientes = clientes;
    }
}
